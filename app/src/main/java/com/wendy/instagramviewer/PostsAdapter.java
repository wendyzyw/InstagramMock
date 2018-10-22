package com.wendy.instagramviewer;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class PostsAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<PostDataModel> posts = new ArrayList<>();

    public PostsAdapter(Context context, ArrayList<PostDataModel> posts){
        this.mContext = context;
        this.posts = posts;
    }

    @Override
    public int getCount(){
        return posts.size();
    }

    @Override
    public long getItemId(int position){
        return 0;
    }

    @Override
    public Object getItem(int position){
        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        final PostDataModel post = posts.get(position);

        if (convertView == null){
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.post_gridcell, null);
        }

        //click to view each feed in detail to view post activity
        final ImageView imageView = (ImageView)convertView.findViewById(R.id.post_grid_view);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ViewPost.class);
                intent.putExtra("media_id", position);
                mContext.startActivity(intent);
            }
        });

        final ImageView imageViewFavourite = (ImageView)convertView.findViewById(R.id.post_favorite);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setPadding(0,0,0,0);

        imageView.setImageURI(post.getPost_media_uri());

        return convertView;
    }
}
