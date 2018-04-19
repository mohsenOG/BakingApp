package com.oghbaei.bakingapp.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Mohsen on 19.04.2018.
 *
 */

public class Utils {
    private Utils() {}

    /**
     * @return true if online otherwise false.
     * @see https://stackoverflow.com/a/4009133/6072457
     */
    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) return false;
        NetworkInfo info = cm.getActiveNetworkInfo();
        return info != null && info.isConnected();
    }
}
