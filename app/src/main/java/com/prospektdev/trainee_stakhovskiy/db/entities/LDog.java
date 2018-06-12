package com.prospektdev.trainee_stakhovskiy.db.entities;

public class LDog {

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
}
