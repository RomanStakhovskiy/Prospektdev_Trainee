package com.prospektdev.trainee_stakhovskiy.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.prospektdev.trainee_stakhovskiy.api.modules.v1.ModuleDogs;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Base API implementation
 */
public abstract class BaseAPI implements API {

    private static final long CONNECT_TIMEOUT = 15;
    private static final long WRITE_TIMEOUT = 30;
    private static final long READ_TIMEOUT = 15;

    private Retrofit restAdapter;

    private HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();

    public BaseAPI(@NotNull String baseUrl) {
        generateRestAdapter(baseUrl);
    }

    private void generateRestAdapter(@NotNull String baseUrl) {
        if (restAdapter != null)
            return;

        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient baseRequestClient = new OkHttpClient().newBuilder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .build();

        Gson gson = new GsonBuilder().create();
        restAdapter = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(baseRequestClient)
                .build();
    }

    protected Retrofit getRestAdapter() {
        return restAdapter;
    }

    protected abstract ModuleDogs generateDogs(Retrofit restAdapter);

    @Override
    public ModuleDogs dogs() {
        throw new UnsupportedOperationException();
    }
}
