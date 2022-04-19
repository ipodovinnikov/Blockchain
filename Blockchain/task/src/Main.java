import blockchain.BlockChainManager;
import clients.MinerManager;
import lombok.extern.slf4j.Slf4j;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) throws InterruptedException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {

        BlockChainManager blockChainManager = new BlockChainManager(0);
        submitClients(15, blockChainManager);
    }

    public static void submitClients(int minersQty, BlockChainManager blockChainManager) throws InterruptedException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        ExecutorService executorService = Executors.newFixedThreadPool(minersQty);
        executorService.invokeAll(MinerManager.createMiners(minersQty, blockChainManager));
    }
}
