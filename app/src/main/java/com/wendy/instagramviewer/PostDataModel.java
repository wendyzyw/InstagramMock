package com.wendy.instagramviewer;

import android.net.Uri;

public class PostDataModel {

    private Uri post_avatar;
    private String media_name;
    private String post_author;
    private String post_location;
    private String post_timestamp;
    private String post_desp;
    private Uri post_media_uri;
    private int post_likes_cnt;
    private int post_comments_cnt;

    private int post_id;

    public PostDataModel(Uri post_avatar, String media_name, String post_author,
                         String post_location, String post_timestamp,
                         Uri post_media_uri,
                         int post_likes_cnt, int post_comments_cnt) {
        this.post_avatar = post_avatar;
        this.media_name = media_name;
        this.post_author = post_author;
        this.post_location = post_location;
        this.post_timestamp = post_timestamp;
        this.post_media_uri = post_media_uri;
        this.post_likes_cnt = post_likes_cnt;
        this.post_comments_cnt = post_comments_cnt;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public int getPost_id() {
        return post_id;
    }

    public Uri getPost_avatar() { return post_avatar; }

    public String getPost_author() { return post_author; }

    public String getPost_location() { return post_location; }

    public String getPost_timestamp() { return post_timestamp; }

    public Uri getPost_media_uri() { return post_media_uri; }

    public int getPost_likes_cnt() { return post_likes_cnt; }

    public int getPost_comments_cnt() { return post_comments_cnt; }

    public String getMedia_name() {
        return media_name;
    }

    public void setPost_likes_cnt(int post_likes_cnt) {
        this.post_likes_cnt = post_likes_cnt;
    }

    public void setPost_comments_cnt(int post_comments_cnt) {
        this.post_comments_cnt = post_comments_cnt;
    }

    public void setPost_location(String post_location) {
        this.post_location = post_location;
    }

    public void setPost_timestamp(String post_timestamp) {
        this.post_timestamp = post_timestamp;
    }

    public String getPost_desp() {
        return post_desp;
    }

    public void setPost_desp(String post_desp) {
        this.post_desp = post_desp;
    }
}
