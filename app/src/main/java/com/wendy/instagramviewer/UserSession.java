package com.wendy.instagramviewer;

import android.net.Uri;

import java.io.File;

public class UserSession {

    //this is to hold currently logged in user's information for api connection
    static final String ip = "10.96.231.69";
    static final String port = "5000";

    static String username;
    static String password;
    static Uri avatar;
    static String email;

    public UserSession(String username, String password){
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public Uri getAvatar() {
        return avatar;
    }


}
