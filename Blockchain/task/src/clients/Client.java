package clients;

import blockchain.BlockChainManager;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.concurrent.Callable;

public interface Client extends Callable<Boolean> {

    void sendMoney() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException;

    void receiveMoney(int amount);

    int getBalance();

    void setBlockChainManager(BlockChainManager blockChainManager);

    boolean mineBlock() throws NoSuchAlgorithmException, InvalidKeyException, SignatureException;
}
