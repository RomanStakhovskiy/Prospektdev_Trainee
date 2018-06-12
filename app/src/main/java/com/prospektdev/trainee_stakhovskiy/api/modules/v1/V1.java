package com.prospektdev.trainee_stakhovskiy.api.modules.v1;

import com.prospektdev.trainee_stakhovskiy.api.BaseAPI;

import org.jetbrains.annotations.NotNull;

import retrofit2.Retrofit;

public class V1 extends BaseAPI {

    private ModuleDogs dogs;

    public V1(@NotNull String baseUrl) {
        super(baseUrl);
        Retrofit restAdapter = getRestAdapter();
        dogs = generateDogs(restAdapter);
    }

    @Override
    protected ModuleDogs generateDogs(Retrofit restAdapter) {
        return new DogsModuleV1(restAdapter.create(IDogsModule.class));
    }

    @Override
    public ModuleDogs dogs() {
        return dogs;
    }
}
