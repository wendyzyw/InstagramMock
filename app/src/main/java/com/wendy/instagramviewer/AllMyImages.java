package com.wendy.instagramviewer;

import java.util.ArrayList;
import java.util.Date;

public class AllMyImages {

    //this is a class to hold all user's feeds for a logged in user
    public static ArrayList<PostDataModel> allMyPosts = new ArrayList<>();
    public static ArrayList<PostDataModel> allPosts = new ArrayList<>();
    public static String[] all_media_names = new String[]{};

    public static ArrayList<PostDataModel> getAllMyPosts() {
        return allMyPosts;
    }
    public static void setAllMyPosts(ArrayList<PostDataModel> posts){
        allMyPosts = posts;
    }

    public static String[] getAll_media_names() {
        return all_media_names;
    }

    public static void setAll_media_names(String[] all_media_names) {
        AllMyImages.all_media_names = all_media_names;
    }
}
