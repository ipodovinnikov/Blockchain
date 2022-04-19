import blockchain.Block;
import blockchain.BlockChainManager;
import clients.Client;
import clients.Miner;
import org.assertj.core.api.SoftAssertions;
import org.junit.Before;
import org.junit.Test;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

public class CustomTest {

    /**
     * Created by ipodovinnikov (podovinnii@ae.com) on 4/14/22.
     */

    private final BlockChainManager blockChainManager = new BlockChainManager(0);
    private final Block block = new Block(blockChainManager.getLastBlock(), blockChainManager.addTransactionsToBlock());
    private final Client miner1 = new Miner();
    private int difficulty = 3;
    SoftAssertions softAssertions = new SoftAssertions();

    @Before
    public void submitClients() throws InterruptedException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Main.submitClients(2, blockChainManager);
    }

    @Test
    public void testBlockHash() {
        String hash = block.calculateHash();
        System.out.println(hash.length());
        softAssertions.assertThat(hash.length()).describedAs("Block hash length is wrong")
                .isEqualTo(64);
        softAssertions.assertThat(block.getHash().equals(block.calculateHash())).describedAs("Stored hash is not equal to calculated hash")
        .isTrue();
        softAssertions.assertAll();
    }

    @Test
    public void testBlockMining() {
        String hash = block.calculateHash();
        Block minedBlock = block.mineBlock(3);
        softAssertions.assertThat(hash.equals(minedBlock.getHash())).describedAs("Block hash is same after mining")
                .isFalse();
        softAssertions.assertThat(minedBlock.getHash().startsWith("000")).describedAs("Block hash does not start with 3 zeros")
                .isTrue();
        softAssertions.assertAll();
    }

    @Test
    public void testSendMoney() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        int initialTransactionsSize = blockChainManager.getTransactions().size();
        miner1.setBlockChainManager(blockChainManager);
        miner1.sendMoney();
        softAssertions.assertThat(blockChainManager.getTransactions().size() == initialTransactionsSize + 1)
                .describedAs("Transaction size did not increment by 1")
                .isTrue();
        softAssertions.assertAll();
    }

    @Test
    public void testReceiveMoney() {
        int amount = 122;
        int initialBalance = miner1.getBalance();
        miner1.receiveMoney(amount);
        softAssertions.assertThat(miner1.getBalance() == initialBalance + 122).describedAs("Wrong amount added")
        .isTrue();
        softAssertions.assertAll();
    }

    @Test
    public void testMineBlockByMiner() throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        miner1.setBlockChainManager(blockChainManager);
        int initialBlockChainLength = blockChainManager.getBlockChainLength();
        miner1.mineBlock();
        softAssertions.assertThat(blockChainManager.getBlockChainLength())
                .describedAs("Blockchain length is unexpected")
                .isEqualTo(initialBlockChainLength + 1);
        softAssertions.assertAll();
    }
}
