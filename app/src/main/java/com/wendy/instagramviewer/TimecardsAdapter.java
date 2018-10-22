package com.wendy.instagramviewer;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.vipulasri.timelineview.TimelineView;

import java.util.ArrayList;

public class TimecardsAdapter extends RecyclerView.Adapter<TimecardsViewHolder> {

    protected final static String LIKED_MSG = "liked your post";
    protected final static String COMMENT_MSG = "commented on your post";
    protected final static String FOLLOW_MSG = "started following you";

    private ArrayList<SocialEvent> mEvents;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private Boolean mChecked;

    public TimecardsAdapter(ArrayList<SocialEvent> events, Context context, Boolean checked){
        this.mEvents = events;
        this.mContext = context;
        this.mChecked = checked;
    }

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position, getItemCount());
    }

    @Override
    public TimecardsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.mContext = parent.getContext();
        this.mLayoutInflater = LayoutInflater.from(this.mContext);
//        View view = this.mLayoutInflater.inflate(this.mChecked ? R.layout.timecard : R.layout.timecard_unchecked, parent, false);
        View view = this.mLayoutInflater.inflate(R.layout.timecard, parent, false);

        return new TimecardsViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(TimecardsViewHolder holder, int position) {
        SocialEvent event = this.mEvents.get(position);
        if (event.getEventType() == SocialEvent.LIKE_EVENT){
            holder.getmTimeMarker().setMarker(VectorDrawableCompat.create(
                    this.mContext.getResources(),
                    R.drawable.timeline_marker_like,
                    this.mContext.getTheme()));
            holder.getmMessage().setText(LIKED_MSG);
            holder.getmPostImg().setImageURI(event.getPostImg());
        } else if (event.getEventType() == SocialEvent.COMMENT_EVENT){
            holder.getmTimeMarker().setMarker(VectorDrawableCompat.create(
                    this.mContext.getResources(),
                    R.drawable.timeline_marker_comment,
                    this.mContext.getTheme()));
            holder.getmMessage().setText(COMMENT_MSG);
            holder.getmPostImg().setImageURI(event.getPostImg());
        } else if (event.getEventType() == SocialEvent.FOLLOW_EVENT){
            holder.getmTimeMarker().setMarker(VectorDrawableCompat.create(
                    this.mContext.getResources(),
                    R.drawable.timeline_marker_follow,
                    this.mContext.getTheme()));
            holder.getmMessage().setText(FOLLOW_MSG);
        }
        String formated = "";
        if (event.getTime() != null){
            formated = TimeConverter.formatDateFromUnixTime(event.getTime());
        }
        holder.getmPostTime().setText(formated);
        holder.getmFromWho().setText(event.getFromWho());
    }

    @Override
    public int getItemCount() {
        return (this.mEvents!=null? this.mEvents.size():0);
    }

    public static class SelectedPhotoForBluetooth {

        public static Bitmap selectedImg = null;
    }
}
