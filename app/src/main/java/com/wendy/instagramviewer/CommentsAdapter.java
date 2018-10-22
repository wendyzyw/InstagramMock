package com.wendy.instagramviewer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentsAdapter extends BaseAdapter {

    private ArrayList<Comment> comments;
    private Context context;

    public CommentsAdapter(Context mContext, ArrayList<Comment> comments){
        this.context = mContext;
        this.comments = comments;
    }

    @Override
    public int getCount() { return comments.size(); }

    @Override
    public Object getItem(int position) { return comments.get(position); }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.comment_listcell, parent, false);
        }
        Comment comment = (Comment) getItem(position);

        //view binding and data set up
        CircleImageView avatar = convertView.findViewById(R.id.user_avatar);
        TextView username = convertView.findViewById(R.id.comment_username);
        TextView text = convertView.findViewById(R.id.comment_text);
        TextView time = convertView.findViewById(R.id.comment_time);

        username.setText(comment.getCommentUsername());
        text.setText(comment.getCommentText());
        avatar.setImageURI(comment.getAvatar());
        time.setText(comment.getCommentTime());

        return convertView;
    }
}
