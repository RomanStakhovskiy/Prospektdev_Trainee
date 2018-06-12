package com.prospektdev.trainee_stakhovskiy.api.modules;

import com.prospektdev.trainee_stakhovskiy.api.Callback;
import com.prospektdev.trainee_stakhovskiy.api.NetworkModule;
import com.prospektdev.trainee_stakhovskiy.utils.thread.ThreadUtils;

import java.util.concurrent.Future;

public abstract class AbstractModule<T> {

    protected T module;

    public AbstractModule(T module) {
        this.module = module;
    }

    protected <E> void notifyError(final Callback<E> callback, final Throwable e) {
        ThreadUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                callback.failure(e);
            }
        });
    }

    protected <E> Future executeAsync(final Runnable runnable, final Callback<E> callback) {
        return NetworkModule.NETWORK_EXECUTOR.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    runnable.run();
                } catch (final Throwable e) {
                    notifyError(callback, e);
                }
            }
        });
    }
}
