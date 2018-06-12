package com.prospektdev.trainee_stakhovskiy.utils.thread;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Helpful thread utils. This class provides set of methods for creating {@link ExecutorService} or {@link ScheduledExecutorService},
 * methods for running tasks in background or foreground.
 */
public class ThreadUtils {

    private static final int DEVICE_NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();

    public static final ExecutorService RANDOM_THREAD_EXECUTOR = newExecutor(4, "BackgroundExecutor");
    public static final ScheduledExecutorService SCHEDULED_EXECUTOR = newScheduledExecutor(4, "ScheduledExecutor");

    private static ExceptionHandler EXCEPTION_HANDLER = new ExceptionHandler();

    private ThreadUtils() {
    }

    static abstract class CustomExecutor extends ThreadPoolExecutor {

        private String factoryName;

        public CustomExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, DefaultThreadFactory threadFactory) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
            factoryName = threadFactory.getNamePrefix();
        }

        @NonNull
        @Override
        public Future<?> submit(Runnable task) {
            Log.i("THREAD_EXECUTOR", factoryName + ": submit new task 1");
            return super.submit(task);
        }

        @NonNull
        @Override
        public <T> Future<T> submit(Callable<T> task) {
            Log.i("THREAD_EXECUTOR", factoryName + ": submit new task 2");
            return super.submit(task);
        }

        @NonNull
        @Override
        public <T> Future<T> submit(Runnable task, T result) {
            Log.i("THREAD_EXECUTOR", factoryName + ": submit new task 3");
            return super.submit(task, result);
        }
    }

    static abstract class CustomScheduledExecutor extends ScheduledThreadPoolExecutor {

        private String factoryName;

        public CustomScheduledExecutor(int corePoolSize, DefaultThreadFactory threadFactory) {
            super(corePoolSize, threadFactory);
            factoryName = threadFactory.getNamePrefix();
        }

        @NonNull
        @Override
        public Future<?> submit(Runnable task) {
            return super.submit(task);
        }

        @NonNull
        @Override
        public <T> Future<T> submit(Callable<T> task) {
            return super.submit(task);
        }

        @NonNull
        @Override
        public <T> Future<T> submit(Runnable task, T result) {
            return super.submit(task, result);
        }
    }

    static class DefaultExecutor extends CustomExecutor {

        public DefaultExecutor(int corePoolSize, DefaultThreadFactory threadFactory) {
            super(corePoolSize, corePoolSize, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), threadFactory);
        }

        @Override
        protected void afterExecute(Runnable r, Throwable t) {
            super.afterExecute(r, t);
            EXCEPTION_HANDLER.afterExecute(r, t);
        }
    }

    static class DefaultCachedExecutor extends CustomExecutor {

        public DefaultCachedExecutor(DefaultThreadFactory threadFactory, int maximumPoolSize) {
            super(maximumPoolSize, maximumPoolSize, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), threadFactory);
        }

        @Override
        protected void afterExecute(Runnable r, Throwable t) {
            super.afterExecute(r, t);
            EXCEPTION_HANDLER.afterExecute(r, t);
        }
    }

    static class DefaultScheduledExecutor extends CustomScheduledExecutor {

        public DefaultScheduledExecutor(int corePoolSize, DefaultThreadFactory threadFactory) {
            super(corePoolSize, threadFactory);
        }

        @Override
        protected void afterExecute(Runnable r, Throwable t) {
            super.afterExecute(r, t);
            EXCEPTION_HANDLER.afterExecute(r, t);
        }
    }

    static class DefaultThreadFactory implements ThreadFactory {
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        DefaultThreadFactory(String poolName) {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
            namePrefix = poolName + "-" + poolNumber.getAndIncrement() + "-thread-";
        }

        public String getNamePrefix() {
            return namePrefix;
        }

        public Thread newThread(@NotNull Runnable r) {
            Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
            if (t.isDaemon())
                t.setDaemon(false);
            if (r instanceof PriorityRunnable) {
                t.setPriority(((PriorityRunnable) r).getPriority());
            } else if (t.getPriority() != Thread.NORM_PRIORITY) {
                t.setPriority(Thread.NORM_PRIORITY);
            }
            t.setUncaughtExceptionHandler(EXCEPTION_HANDLER);
            return t;
        }
    }

    /**
     * Create new {@link ExecutorService}.
     *
     * @param multiplier multiplier for number of cores. Core pool size calculated by this formula: <br />{@code corePoolSize = numberOfCores * multiplier}
     * @param threadName name used for generating threads
     * @return new instance of ExecutorService
     * @throws IllegalArgumentException if {@code threadName} is empty
     */
    public static ExecutorService newExecutor(int multiplier, @NotNull String threadName) throws IllegalArgumentException {
        if (TextUtils.isEmpty(threadName))
            throw new IllegalArgumentException();
        return new DefaultExecutor(DEVICE_NUMBER_OF_CORES * multiplier, new DefaultThreadFactory(threadName));
    }

    /**
     * Create new {@link ExecutorService}.
     *
     * @param threadName name used for generating threads
     * @return new instance of ExecutorService
     * @throws IllegalArgumentException if {@code threadName} is empty
     */
    public static ExecutorService newCacheExecutor(@NotNull String threadName, int maximumPoolSize) throws IllegalArgumentException {
        if (TextUtils.isEmpty(threadName))
            throw new IllegalArgumentException();
        return new DefaultCachedExecutor(new DefaultThreadFactory(threadName), maximumPoolSize);
    }

    public static ScheduledExecutorService newScheduledExecutor(int multiplier, @NotNull String threadName) throws IllegalArgumentException {
        if (TextUtils.isEmpty(threadName))
            throw new IllegalArgumentException();
        return new DefaultScheduledExecutor(DEVICE_NUMBER_OF_CORES * multiplier, new DefaultThreadFactory(threadName));
    }

    /**
     * Run task in background. If current thread is not a main thread, task will be executed on this thread.
     *
     * @param runnable task to run
     * @return future for task
     */
    @Nullable
    public static Future runInBackground(@NotNull final Runnable runnable) {
        return runInBackground(runnable, true);
    }

    /**
     * Run task in background
     *
     * @param runnable    task to run
     * @param immediately flag indicates that task should be ran immediately if current thread is not a main thread
     * @return future for task
     */
    @Nullable
    public static Future runInBackground(@NotNull final Runnable runnable, boolean immediately) {
        if (immediately && !isUiThread()) {
            runnable.run();
            return null;
        }
        return RANDOM_THREAD_EXECUTOR.submit(runnable);
    }

    /**
     * Schedule task to run in background.
     *
     * @param runnable task to run
     * @param delay    delay before executing task
     * @param unit     time unit
     * @return future for task
     */
    public static Future schedule(@NotNull final Runnable runnable, final long delay, final TimeUnit unit) {
        return SCHEDULED_EXECUTOR.schedule(runnable, delay, unit);
    }

    /**
     * Schedule task to run in background.
     *
     * @param runnable task to run
     * @param delay    delay before executing task
     * @param period   period between successive executions
     * @param unit     time unit
     * @return future for task
     */
    public static Future scheduleAtFixedRate(@NotNull final Runnable runnable, final long delay, final long period, final TimeUnit unit) {
        return SCHEDULED_EXECUTOR.scheduleAtFixedRate(runnable, delay, period, unit);
    }

    private static Handler getMainHandler() {
        return MainThreadHandlerHolder.INSTANCE;
    }

    /**
     * Run task on main thread. If current thread is a main thread, task will be executed immediately on this thread.
     *
     * @param r task to run
     */
    public static void runOnUiThread(Runnable r) {
        if (isUiThread()) {
            r.run();

            return;
        }
        getMainHandler().post(r);
    }

    /**
     * Run task on main thread after delay.
     *
     * @param r           task to run
     * @param delayMillis delay in milliseconds
     */
    public static void runOnUiThread(Runnable r, long delayMillis) {
        getMainHandler().postDelayed(r, delayMillis);
    }

    private static final class MainThreadHandlerHolder {
        public static final Handler INSTANCE = new Handler(Looper.getMainLooper());
    }

    /**
     * Check if current thread is UI thread
     *
     * @return true or false
     */
    public static boolean isUiThread() {
        return Thread.currentThread().equals(Looper.getMainLooper().getThread());
    }

    private static class ExceptionHandler implements Thread.UncaughtExceptionHandler {

        public void afterExecute(Runnable r, Throwable t) {
            if (t == null && r instanceof Future<?>) {
                try {
                    Future<?> future = (Future<?>) r;
                    if (future.isDone() && !future.isCancelled())
                        future.get();
                } catch (CancellationException ce) {
                    t = ce;
                } catch (ExecutionException ee) {
                    t = ee.getCause();
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt(); // ignore/reset
                }
            }
            if (t != null)
                uncaughtException(Thread.currentThread(), t);
        }

        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
            if (ex != null)
                ex.printStackTrace();
        }
    }
}
