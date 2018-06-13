package com.prospektdev.trainee_stakhovskiy.utils;

import android.os.Handler;
import android.os.Looper;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class ThreadUtils {

    private static final int DEVICE_NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
    private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(DEVICE_NUMBER_OF_CORES);

    private ThreadUtils() {
    }

    public static Future runInBackground(@NotNull final Runnable runnable) {
        return runInBackground(runnable, true);
    }

    public static Future runInBackground(@NotNull final Runnable runnable, boolean immediately) {
        if (immediately && !isUiThread()) {
            runnable.run();
            return null;
        }
        return EXECUTOR.submit(runnable);
    }

    public static void runOnUiThread(Runnable r) {
        if (isUiThread()) {
            r.run();

            return;
        }
        getMainHandler().post(r);
    }

    public static void runOnUiThread(Runnable r, long delayMillis) {
        getMainHandler().postDelayed(r, delayMillis);
    }

    public static boolean isUiThread() {
        return Thread.currentThread().equals(Looper.getMainLooper().getThread());
    }

    private static Handler getMainHandler() {
        return MainThreadHandlerHolder.INSTANCE;
    }

    private static final class MainThreadHandlerHolder {
        static final Handler INSTANCE = new Handler(Looper.getMainLooper());
    }
}
