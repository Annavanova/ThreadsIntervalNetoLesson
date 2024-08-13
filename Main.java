package MultyThread.MaxInterval;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        String[] texts = new String[25];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("aab", 30_000);
        }
        long startTs = System.currentTimeMillis(); // start time

        List<Future> futuresthreads = new ArrayList<>();
        int maxValue = 0;
        ExecutorService poolThreads = Executors.newFixedThreadPool(texts.length);
        for (String text : texts) {
            futuresthreads.add(poolThreads.submit(new CallableText(text) {
            }));
        }

        for (Future future : futuresthreads) {
            maxValue = Math.max((int) future.get(), maxValue);
        }

        long endTs = System.currentTimeMillis(); // end time
        System.out.println("Time: " + (endTs - startTs) + "ms");
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}
