package com.prospektdev.trainee_stakhovskiy.utils;

import android.content.Intent;

public class ShareUtils {

    private ShareUtils() {

    }

    public static Intent createSharingIntent(String url) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Image of Dog");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, url);
        return sharingIntent;
    }
}
