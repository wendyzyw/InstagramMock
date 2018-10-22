package com.wendy.instagramviewer;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.vipulasri.timelineview.TimelineView;

public class TimecardsViewHolder extends RecyclerView.ViewHolder {

    private TextView mFromWho;
    private TextView mMessage;
    private TimelineView mTimeMarker;
    private ImageView mPostImg;
    private TextView mPostTime;

    public TimecardsViewHolder(View itemView, int viewType) {
        super(itemView);

        mFromWho = (TextView)itemView.findViewById(R.id.event_fromWho);
        mMessage = (TextView)itemView.findViewById(R.id.event_message);
        mPostImg = (ImageView)itemView.findViewById(R.id.event_post_img);
        mTimeMarker = (TimelineView)itemView.findViewById(R.id.time_marker);
        mPostTime = (TextView)itemView.findViewById(R.id.event_timestamp);
        mTimeMarker.initLine(viewType);
    }

    TimelineView getmTimeMarker(){
        return this.mTimeMarker;
    }

    TextView getmFromWho(){
        return this.mFromWho;
    }

    TextView getmMessage(){
        return this.mMessage;
    }

    ImageView getmPostImg(){
        return this.mPostImg;
    }

    public TextView getmPostTime() {
        return mPostTime;
    }
}

