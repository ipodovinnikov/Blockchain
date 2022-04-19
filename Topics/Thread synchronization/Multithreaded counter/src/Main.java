import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Counter {

    int count = 0;

    public synchronized void inc() {
        count++;
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.MILLISECONDS);
    }
}