package com.utapass.topwords.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;

public class Utils {
    public static boolean isNetWorkConnected(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnected();
    }

    public static boolean isRtlString(String string) {
        if (TextUtils.isEmpty(string)) {
            return false;
        }
        return (Character.getDirectionality(string.charAt(0)) == Character.DIRECTIONALITY_RIGHT_TO_LEFT ||
                Character.getDirectionality(string.charAt(0)) == Character.DIRECTIONALITY_RIGHT_TO_LEFT_ARABIC ||
                Character.getDirectionality(string.charAt(0)) == Character.DIRECTIONALITY_RIGHT_TO_LEFT_EMBEDDING ||
                Character.getDirectionality(string.charAt(0)) == Character.DIRECTIONALITY_RIGHT_TO_LEFT_OVERRIDE);
    }

    public static void fullScreen(Window window) {
        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE;
        window.getDecorView().setSystemUiVisibility(uiOptions);
    }

}
