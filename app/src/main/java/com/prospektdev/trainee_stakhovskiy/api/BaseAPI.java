package com.prospektdev.trainee_stakhovskiy.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.prospektdev.trainee_stakhovskiy.api.modules.v1.ModuleDogs;

import org.jetbrains.annotations.NotNull;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public abstract class BaseAPI implements API {

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
}
