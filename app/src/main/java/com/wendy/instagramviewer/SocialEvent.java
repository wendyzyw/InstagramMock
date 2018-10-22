package com.wendy.instagramviewer;

import android.net.Uri;

public class SocialEvent {

    //this is a data model for app events used in activity feeds
    protected final static int LIKE_EVENT = 0;
    protected final static int COMMENT_EVENT = 1;
    protected final static int FOLLOW_EVENT = 2;

    private String fromWho; //username
    private String toWho; //username
    private int eventType;
    private Uri postImg; //optional
    private String time;

    public SocialEvent(String fromWho, String toWho, int eventType){
        this.fromWho = fromWho;
        this.toWho = toWho;
        this.eventType = eventType;
    }

    public SocialEvent(String fromWho, String toWho, int eventType, Uri postImg, String time){
        this.fromWho = fromWho;
        this.toWho = toWho;
        this.eventType = eventType;
        this.postImg = postImg;
        this.time = time;
    }

    public String getFromWho() {
        return fromWho;
    }

    public int getEventType(){
        return eventType;
    }

    public Uri getPostImg(){
        return postImg;
    }

    public String getTime() {
        return time;
    }

}
