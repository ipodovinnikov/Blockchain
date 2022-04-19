package blockchain;

import clients.Miner;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class BlockChainManager {

    /**
     * Created by ipodovinnikov (podovinnii@ae.com) on 4/2/22.
     */

    private final List<Block> blockChain = new ArrayList<>();
    @Getter
    private int blockChainLength = blockChain.size();
    @Getter
    private int difficulty;
    private String hashTarget;
    @Getter
    private final List<Transaction> transactions = new ArrayList<>();

    public BlockChainManager(int initialDifficulty) {
        this.difficulty = initialDifficulty;
        Block genesisBlock = new Block();
        genesisBlock.setBlockChainManager(this);
        genesisBlock.mineBlock(initialDifficulty);
        blockChain.add(genesisBlock);
        genesisBlock.printBlockInfo(new Miner());
        hashTarget = new String(new char[initialDifficulty]).replace('\0', '0');
    }

    public synchronized Block getLastBlock() {
        return blockChain.get(blockChain.size() - 1);
    }

    public synchronized boolean offerBlock(Block block, Miner miner) {
        blockChain.add(block);
        if (isBlockValid(block)) {
            miner.receiveMoney(100);
            block.printBlockInfo(miner);
            block.getBlockChainManager().tuneDifficulty(block);
            return true;
        } else {
            blockChain.remove(block);
        }
        return false;
    }

    public boolean isChainValid() {
        Block previousBlock;
        Block currentBlock;

        for (int i = 1; i < blockChain.size(); i++) {
            currentBlock = blockChain.get(i);
            previousBlock = blockChain.get(i - 1);

            if (!currentBlock.getHash().equals(currentBlock.calculateHash())) {
                return false;
            }
            if (!previousBlock.getHash().equals(currentBlock.getPreviousBlockHash())) {
                return false;
            }
            if (!currentBlock.getHash().substring(0, difficulty).equals(hashTarget)) {
                return false;
            }
        }
        return true;
    }

    public boolean isBlockValid(Block block) {
        Block previousBlock = blockChain.get(blockChain.size() - 2);
        if (!block.getHash().equals(block.calculateHash())) {
            System.out.println("block hash is not ok");
            return false;
        }
        if (!previousBlock.getHash().equals(block.getPreviousBlockHash())) {
            System.out.println("previous hash is not ok");
            return false;
        }
        if (!block.getHash().substring(0, difficulty).equals(hashTarget)) {
            System.out.println("hash difficulty is not ok");
            return false;
        }
        return true;
    }

    public void tuneDifficulty(Block block) {
        if (block.getTimeOfMining() == 0) {
            difficulty++;
            hashTarget = new String(new char[difficulty]).replace('\0', '0');
            System.out.printf("N was increased to %s%n", difficulty);
            System.out.println();
        } else if (block.getTimeOfMining() > 0) {
            difficulty--;
            hashTarget = new String(new char[difficulty]).replace('\0', '0');
            System.out.printf("N was decreased to %d%n", difficulty);
            System.out.println();

        } else {
            System.out.printf("N stays the same%n");
            System.out.println();

        }
    }

    public synchronized void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public List<Transaction> addTransactionsToBlock() {
        List<Transaction> copyOfTransactions = new ArrayList<>(transactions);
        transactions.clear();
        return copyOfTransactions;
    }
}
