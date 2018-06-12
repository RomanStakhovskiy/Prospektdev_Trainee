package com.prospektdev.trainee_stakhovskiy.utils.thread;

import android.os.Process;

import org.intellij.lang.annotations.MagicConstant;

/**
 * Runnable that set thread priority before execution
 */
public abstract class PriorityRunnable implements Runnable {
    private int threadPriority = Process.THREAD_PRIORITY_BACKGROUND;

    public PriorityRunnable() {

    }

    public PriorityRunnable(@MagicConstant(valuesFromClass = Process.class) int threadPriority) {
        this.threadPriority = threadPriority;
    }

    @Override
    public final void run() {
        Process.setThreadPriority(threadPriority);
        runImpl();
    }

    protected abstract void runImpl();

    public int getPriority() {
        return threadPriority;
    }
}
