package com.retr0spect.quit.social.media.addiction;

import android.content.pm.ApplicationInfo;

import java.util.ArrayList;

/**
 * Created by Aditya on 2/16/2017.
 */

public class ProfileContentsFull {

    private String profileName;
    private ArrayList<ApplicationInfo> packageInfos;
    private ArrayList<String> days;
    private boolean isActive;

    public ProfileContentsFull(String profileName, ArrayList<ApplicationInfo> packageInfos, ArrayList<String> days, boolean isActive) {
        this.profileName = profileName;
        this.packageInfos = packageInfos;
        this.days = days;
        this.isActive = isActive;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public ArrayList<ApplicationInfo> getPackageInfos() {
        return packageInfos;
    }

    public void setPackageInfos(ArrayList<ApplicationInfo> packageInfos) {
        this.packageInfos = packageInfos;
    }

    public ArrayList<String> getDays() {
        return days;
    }

    public void setDays(ArrayList<String> days) {
        this.days = days;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
