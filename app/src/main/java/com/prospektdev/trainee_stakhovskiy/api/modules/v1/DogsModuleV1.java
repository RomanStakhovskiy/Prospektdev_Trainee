package com.prospektdev.trainee_stakhovskiy.api.modules.v1;

import com.prospektdev.trainee_stakhovskiy.api.Callback;
import com.prospektdev.trainee_stakhovskiy.api.dto.RDog;
import com.prospektdev.trainee_stakhovskiy.db.entities.LDog;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class DogsModuleV1 implements ModuleDogs {

    private final static int DOGS_COUNT = 45;

    private IDogsModule module;

    DogsModuleV1(IDogsModule module) {
        this.module = module;
    }

    @Override
    public List<LDog> getRandomSet() {
        try {
            Response<RDog> response = generateGetRandomSetCall().execute();
            if (response.isSuccessful()) {
                RDog body = response.body();
                if (body != null)
                    return parse(body);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public void getRandomSet(@NotNull final Callback<List<LDog>> callback) {
        generateGetRandomSetCall().enqueue(new retrofit2.Callback<RDog>() {
            @Override
            public void onResponse(Call<RDog> call, Response<RDog> response) {
                List<LDog> dogs = new ArrayList<>();
                if (response.isSuccessful()) {
                    RDog body = response.body();
                    if (body != null)
                        dogs = parse(body);
                }
                callback.success(dogs);
            }

            @Override
            public void onFailure(Call<RDog> call, Throwable t) {
                callback.failure(t);
            }
        });
    }

    private List<LDog> parse(RDog dogs) {
        List<LDog> result = new ArrayList<>();
        for (String url : dogs.getUrls()) {
            LDog dog = new LDog(url);
            result.add(dog);
        }
        return result;
    }

    private Call<RDog> generateGetRandomSetCall() {
        return module.getRandomSet(DOGS_COUNT);
    }
}
