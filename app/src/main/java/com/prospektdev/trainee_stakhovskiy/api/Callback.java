package com.prospektdev.trainee_stakhovskiy.api;

public interface Callback<T> {
    void success(T response);
    void failure(Throwable e);
}
