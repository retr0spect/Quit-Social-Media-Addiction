package com.retr0spect.quit.social.media.addiction;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aditya on 2/18/2017.
 */

class Utils {

    static boolean doesPackageExist(String targetPackage, Context context) {
        List<ApplicationInfo> packages;
        PackageManager pm;
        pm = context.getPackageManager();
        packages = pm.getInstalledApplications(0);
        for (ApplicationInfo packageInfo : packages) {
            if (packageInfo.packageName.equals(targetPackage))
                return true;
        }
        return false;
    }

    @Nullable
    static ArrayList<ProfileContents> getProfilesFromSharedPrefs(SharedPreferences sharedPrefs) {
        Gson gson = new Gson();
        String json = sharedPrefs.getString("profiles", null);
        Type type = new TypeToken<ArrayList<ProfileContents>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    static void saveProfilesToSharedPrefs(ArrayList<ProfileContents> pcs, SharedPreferences mPrefs) {
        Gson gson = new Gson();
        String json = gson.toJson(pcs);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putString("profiles", json);
        prefsEditor.apply();
    }
}
