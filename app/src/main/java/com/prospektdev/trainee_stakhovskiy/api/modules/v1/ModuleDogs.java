package com.prospektdev.trainee_stakhovskiy.api.modules.v1;

import com.prospektdev.trainee_stakhovskiy.api.Callback;
import com.prospektdev.trainee_stakhovskiy.db.entities.LDog;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface ModuleDogs {

    List<LDog> getRandomSet();

    void getRandomSet(@NotNull final Callback<List<LDog>> callback);
}
