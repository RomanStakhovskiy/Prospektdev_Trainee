package com.prospektdev.trainee_stakhovskiy.api;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.prospektdev.trainee_stakhovskiy.api.modules.v1.V1;
import com.prospektdev.trainee_stakhovskiy.utils.thread.ThreadUtils;

import java.util.concurrent.ExecutorService;

public class NetworkModule {

    private static final NetworkModule INSTANCE = new NetworkModule();

    public static final ExecutorService NETWORK_EXECUTOR = ThreadUtils.newExecutor(2, "NetworkExecutor");

    private V1 v1;

    private static final String API_ENDPOINT = "https://dog.ceo/api/breeds/";

    public static final String DEFAULT_NETWORK_ERROR_MESSAGE = "Sorry, something went wrong.";

    private NetworkModule() throws IllegalStateException {
        v1 = new V1(API_ENDPOINT);
    }

    public static NetworkModule get() {
        return INSTANCE;
    }

    public static API v1() throws IllegalStateException {
        return get().v1;
    }

    public boolean isConnected(Context context) {
        if (context == null)
            return false;
        final ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null)
            return false;
        final NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        final NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        return wifi != null && wifi.isConnected() || mobile != null && mobile.isConnected();
    }

    public String parseError(Throwable error) {
        String errorMessage = DEFAULT_NETWORK_ERROR_MESSAGE;
        if (error != null && error.getMessage() != null)
            errorMessage = error.getMessage();
        return errorMessage;
    }
}
