package evolution.utilities;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class ExecutorServiceUtil {
    private static final int THREADS_NUMBER = 5;
    private static ExecutorService executor = Executors.newFixedThreadPool(THREADS_NUMBER);
    private static ScheduledExecutorService executorScheduler = Executors.newSingleThreadScheduledExecutor();

    public static ExecutorService getExecutor() {
        return executor;
    }

    public static ScheduledExecutorService getExecutorScheduler() {
        return executorScheduler;
    }
}
