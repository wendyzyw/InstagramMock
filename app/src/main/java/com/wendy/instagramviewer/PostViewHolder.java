package com.wendy.instagramviewer;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostViewHolder extends RecyclerView.ViewHolder {

    private CircleImageView post_avatar;
    private TextView post_author;
    private TextView post_location;
    private TextView post_timestamp;
    private ImageView post_image;
    private TextView post_likes_cnt;
    private TextView post_comments_cnt;
    private ImageView post_btn;
    private ImageView comment_btn;
    private TextView post_desp;

    public PostViewHolder(View itemView) {
        super(itemView);
        post_avatar = (CircleImageView)itemView.findViewById(R.id.avatar);
        post_author = (TextView)itemView.findViewById(R.id.post_author_name);
        post_location = (TextView)itemView.findViewById(R.id.post_location);
        post_timestamp = (TextView)itemView.findViewById(R.id.post_timestamp);
        post_image = (ImageView) itemView.findViewById(R.id.post_image);
        post_likes_cnt = (TextView)itemView.findViewById(R.id.likes_user_list);
        post_comments_cnt = (TextView)itemView.findViewById(R.id.comments_view_list);
        post_btn = (ImageView)itemView.findViewById(R.id.post_like);
        comment_btn = (ImageView)itemView.findViewById(R.id.post_comment);
        post_desp = (TextView)itemView.findViewById(R.id.post_description);
    }

    public CircleImageView getPost_avatar() { return post_avatar; }

    public TextView getPost_author() { return post_author; }

    public TextView getPost_location() { return post_location; }

    public TextView getPost_timestamp() { return post_timestamp; }

    public ImageView getPost_image() { return post_image; }

    public TextView getPost_likes_cnt() { return post_likes_cnt; }

    public TextView getPost_comments_cnt() { return post_comments_cnt; }

    public TextView getPost_desp() { return post_desp; }
}
