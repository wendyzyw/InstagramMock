package com.wendy.instagramviewer;

import android.net.Uri;

public class Comment {

    private String commentUsername;
    private String commentText;
    private String commentTime;
    private Uri avatar;

    //this is a data model for comment for each post, used for comment list view and comment adapter
    public Comment(String commentUsername, Uri avatar, String commentText, String commentTime){
        this.commentUsername = commentUsername;
        this.avatar = avatar;
        this.commentText = commentText;
        this.commentTime = commentTime;
    }

    public String getCommentUsername() {
        return this.commentUsername;
    }

    public String getCommentText(){
        return this.commentText;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public Uri getAvatar() {
        return avatar;
    }
}
