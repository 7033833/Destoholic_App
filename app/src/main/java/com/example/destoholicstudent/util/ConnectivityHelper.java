package com.example.destoholicstudent.util;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;

public class ConnectivityHelper {
    /**
     * Checking for all possible internet providers
     **/
    public static boolean isConnectingToInternet(Context context) {
        final ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {

            if (Build.VERSION.SDK_INT < 23) {
//                Debug.trace("this is true SDK_INT less than 23");
                @Deprecated final NetworkInfo ni = connectivity.getActiveNetworkInfo();

                if (ni != null) {
                    return (ni.isConnected() && (ni.getType() == ConnectivityManager.TYPE_WIFI || ni.getType() == ConnectivityManager.TYPE_MOBILE));
                }
            } else {
//                Debug.trace("this is true SDK_INT gt than 23");
                final Network info = connectivity.getActiveNetwork();
                if (info != null) {
                    final NetworkCapabilities nc = connectivity.getNetworkCapabilities(info);
                    if (nc != null) {
                        return (nc.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                                nc.hasTransport(NetworkCapabilities.TRANSPORT_VPN) || nc.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET));
                    }
                }
            }


        }
        return false;
    }
}
