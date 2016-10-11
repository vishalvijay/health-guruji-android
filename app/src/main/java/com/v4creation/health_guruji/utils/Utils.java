package com.v4creation.health_guruji.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import com.v4creation.health_guruji.BuildConfig;


public class Utils {
    public static String getPackageName() {
        return BuildConfig.APPLICATION_ID;
    }

    public static String getAppPlayStoreURL() {
        return "https://play.google.com/store/apps/details?id="
                + getPackageName();
    }

    public static int getAppVersionCode(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getAppVersionName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getAndroidVersion() {
        return Build.VERSION.RELEASE;
    }

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

}
