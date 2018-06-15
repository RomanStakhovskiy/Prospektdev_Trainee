package com.prospektdev.trainee_stakhovskiy.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.prospektdev.trainee_stakhovskiy.db.dao.DogsDao;
import com.prospektdev.trainee_stakhovskiy.db.entities.LDog;

@Database(entities = {LDog.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "dogs-db";

    private static AppDatabase instance;

    public abstract DogsDao dogsDao();

    public static void init(Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null)
                    instance = Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME).build();
            }
        }
    }

    public static AppDatabase instance() {
        return instance;
    }
}
