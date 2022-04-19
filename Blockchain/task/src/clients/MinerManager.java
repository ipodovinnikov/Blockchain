package clients;

import blockchain.BlockChainManager;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class MinerManager {

    /**
     * Created by ipodovinnikov (podovinnii@ae.com) on 4/4/22.
     */

    private static Queue<Client> miners;

    public static Queue<Client> createMiners(int qty, BlockChainManager blockChainManager) {
        miners = new ArrayBlockingQueue<>(qty);
        for (int i = 0; i < qty; i++) {
            Miner miner = new Miner();
            miner.setBlockChainManager(blockChainManager);
            miners.add(miner);
        }
        return miners;
    }

    public static Queue<Client> getMiners() {
        return miners;
    }
}
