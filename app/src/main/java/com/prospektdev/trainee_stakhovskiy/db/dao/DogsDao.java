package com.prospektdev.trainee_stakhovskiy.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.prospektdev.trainee_stakhovskiy.db.entities.LDog;

import java.util.List;

@Dao
public interface DogsDao {

    @Insert
    void insert(LDog dog);

    @Insert
    void insert(LDog... dogs);

    @Delete
    void delete(LDog dog);

    @Query("SELECT * FROM dogs")
    List<LDog> get();
}
