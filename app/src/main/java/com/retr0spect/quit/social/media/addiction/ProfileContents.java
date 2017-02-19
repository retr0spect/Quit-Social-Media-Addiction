package com.retr0spect.quit.social.media.addiction;

import java.util.ArrayList;

/**
 * Created by Aditya on 2/16/2017.
 */

public class ProfileContents {

    String profileName;
    ArrayList<String> packageNames;
    ArrayList<String> days;
    boolean isActive;

    public ProfileContents() {
    }

    public ProfileContents(String profileName, ArrayList<String> packageNames) {
        this.profileName = profileName;
        this.packageNames = packageNames;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public ArrayList<String> getPackageNames() {
        return packageNames;
    }

    public void setPackageNames(ArrayList<String> packageNames) {
        this.packageNames = packageNames;
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
