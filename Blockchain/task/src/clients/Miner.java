package clients;

import blockchain.Block;
import blockchain.BlockChainManager;
import blockchain.Transaction;
import lombok.Getter;
import lombok.Setter;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadLocalRandom;

public class Miner implements Callable<Boolean>, Client {

    /**
     * Created by ipodovinnikov (podovinnii@ae.com) on 4/4/22.
     */

    @Getter
    private int balance = 100;
    @Setter
    private BlockChainManager blockChainManager;
    private static final Object object = new Object();
    private static int clientNumberCount = 1;
    @Getter
    private final int minerNumber;

    public Miner() {
        this.minerNumber = clientNumberCount++;
    }


    public void sendMoney() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        List<Client> clients = new ArrayList<>(MinerManager.getMiners());
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int amount = random.nextInt(50);
        Transaction transaction = new Transaction();
        transaction.sendMoney(amount, clients.get(random.nextInt(clients.size() - 1)));
        receiveMoney(-amount);
        transaction.sign();
        blockChainManager.addTransaction(transaction);
    }

    @Override
    public void receiveMoney(int amount) {
        balance += amount;
    }

    public boolean mineBlock() throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {

        synchronized (object) {
            sendMoney();
            Block block = new Block(blockChainManager.getLastBlock(), blockChainManager.addTransactionsToBlock());
            block.setBlockChainManager(blockChainManager);
            block.mineBlock(blockChainManager.getDifficulty());
            return blockChainManager.offerBlock(block, this);
        }
    }

    @Override
    public Boolean call() throws Exception {
        return mineBlock();
    }

    @Override
    public String toString() {
        return "Miner #" + minerNumber;
    }
}
