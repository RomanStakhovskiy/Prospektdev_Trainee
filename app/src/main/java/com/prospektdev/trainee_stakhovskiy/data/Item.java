package com.prospektdev.trainee_stakhovskiy.data;

import java.util.UUID;

public class Item {

    private UUID uuid;
    private String title;

    public Item() {
        uuid = UUID.randomUUID();
    }

    public UUID getId() {
        return uuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
