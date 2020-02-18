package Utilities;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorServiceUtil {
    private static final int THREADS_NUMBER = 3;
    private static ExecutorService executor = Executors.newFixedThreadPool(THREADS_NUMBER);

    public static ExecutorService getExecutor() {
        return executor;
    }
}
