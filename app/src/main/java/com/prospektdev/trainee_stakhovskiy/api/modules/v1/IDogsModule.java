package com.prospektdev.trainee_stakhovskiy.api.modules.v1;

import com.prospektdev.trainee_stakhovskiy.api.dto.RDog;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IDogsModule {

    @GET("image/random/{count}")
    Call<RDog> getRandomSet(@Path("count") int count);

}
