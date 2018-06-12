package com.prospektdev.trainee_stakhovskiy;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ItemLab {

    private static ItemLab itemLab;
    private List<Item> items;

    public static ItemLab get(Context context) {
        if (itemLab == null) {
            itemLab = new ItemLab(context);
        }
        return itemLab;
    }

    private ItemLab(Context context) {
        items = new ArrayList<>();
        for (int i = 1; i < 100; i++) {
            Item item = new Item();
            item.setTitle("Item #" + (i));
            items.add(item);
        }
    }

    public List<Item> getItems() {
        return items;
    }
    public Item getItem(UUID id) {
        for (Item item : items) {
            if (item.getId().equals(id)) {
                return item;
            }
        }
        return null;
    }
}
