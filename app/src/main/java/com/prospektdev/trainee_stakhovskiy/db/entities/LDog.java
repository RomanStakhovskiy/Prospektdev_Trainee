package com.prospektdev.trainee_stakhovskiy.db.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.util.Objects;

@Entity(tableName = "dogs")
public class LDog implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String url;

    public LDog() {
    }

    public LDog(String url) {
        this.url = url;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        String title = "Empty Dog";
        try {
            String[] urlParts = url.split("/");
            title = urlParts[urlParts.length - 2];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return title.substring(0, 1).toUpperCase() + title.substring(1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LDog dog = (LDog) o;
        return Objects.equals(url, dog.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url);
    }
}
