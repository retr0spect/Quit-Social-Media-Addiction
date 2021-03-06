package com.retr0spect.quit.social.media.addiction;

import android.graphics.drawable.Drawable;

/**
 * Created by Aditya on 2/11/2017.
 */

public class AppMetadata {

    private String appName;
    private String packageName;
    private Drawable icon;

    public AppMetadata(String appName, String packageName, Drawable icon) {
        this.appName = appName;
        this.packageName = packageName;
        this.icon = icon;
    }

    @Override
    public int hashCode() {
        return this.appName == null ? 0 : this.appName.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (!(obj instanceof AppMetadata))
            return false;
        AppMetadata am = (AppMetadata) obj;
        return this.appName != null && this.appName.equals(am.appName);
    }

    public String getAppName() {
        return appName;
    }

    public String getPackageName() {
        return packageName;
    }

    public Drawable getIcon() {
        return icon;
    }
}
