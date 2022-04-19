package blockchain;

import clients.Miner;
import lombok.Getter;
import lombok.Setter;
import utils.StringUtil;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Block {

    /**
     * Created by ipodovinnikov (podovinnii@ae.com) on 4/2/22.
     */

    @Getter
    private String hash;
    @Getter
    private String previousBlockHash;
    @Getter
    private int magicNumber;
    private static int idCounter = 1;
    @Getter
    private final int id;
    @Getter
    private final long timeStamp = new Date().getTime();
    @Getter
    private long timeOfMining;
    @Getter
    @Setter
    private BlockChainManager blockChainManager;
    private List<Transaction> transactions = new ArrayList<>();

    Block() {
        this.id = 0;
        this.previousBlockHash = "0";
        this.hash = calculateHash();
    }

    public Block(Block previousBlock, List<Transaction> transactions) {
        this.previousBlockHash = previousBlock.getHash();
        this.id = idCounter++;
        this.transactions = transactions;
        this.hash = calculateHash();
    }

    public String calculateHash() {
        return StringUtil.applySha256(
                previousBlockHash
                        + timeStamp
                        + magicNumber
                        + transactions
                        + id);
    }

    public Block mineBlock(int difficulty) {
        LocalTime blockMiningStartTime = LocalTime.now();
        String target = new String(new char[difficulty]).replace('\0', '0');
        while (!hash.substring(0, difficulty).equals(target)) {
            magicNumber++;
            hash = calculateHash();
        }
        LocalTime blockMiningEndTime = LocalTime.now();
        timeOfMining = Duration.between(blockMiningStartTime, blockMiningEndTime).getSeconds();
        return this;
    }

    public void printBlockInfo(Miner miner) {
        System.out.println("Block:\n" +
                "Created by miner # " + miner.getMinerNumber() + "\n" +
                "Miner # " + miner.getMinerNumber() + " gets 100 VC\n" +
                "Id: " + getId() + "\n" +
                "Timestamp: " + getTimeStamp() + "\n" +
                "Magic number: " + getMagicNumber() + "\n" +
                "Hash of the previous block: \n" + getPreviousBlockHash() + "\n" +
                "Hash of the block: \n" + getHash() + "\n" +
                "Block data:");
        printTransactions();
        System.out.println("Block was generating for " + getTimeOfMining() + " seconds");
    }

    public void printTransactions() {
        if (transactions.isEmpty()) System.out.println("no messages");
        for (Transaction transaction : transactions) {
            System.out.println(transaction.getInfo());
        }
    }
}
