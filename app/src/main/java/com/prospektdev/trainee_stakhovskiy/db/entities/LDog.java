package com.prospektdev.trainee_stakhovskiy.db.entities;

import java.io.Serializable;

public class LDog implements Serializable{

    private long id;
    private String url;
    private String title;

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
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
