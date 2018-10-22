package com.wendy.instagramviewer;

import android.net.Uri;

//this is a data model for user object, representing user lists in who liked your post and search for users
public class User {

    private String userId;
    private String username;
    private String[] user_tags;
    private int avatar;
    private Uri user_avatar;

    public User(String username, Uri avatar){
        this.username = username;
        this.user_avatar = avatar;
    }

    public String getUsername(){
        return this.username;
    }

    public String[] getUser_tags(){
        return this.user_tags;
    }

    public int getAvatar(){
        return this.avatar;
    }

    public Uri getUser_avatar() { return this.user_avatar; }

}
