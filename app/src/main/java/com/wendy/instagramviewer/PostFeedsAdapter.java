package com.wendy.instagramviewer;

        import android.content.Context;
        import android.support.annotation.NonNull;
        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;

        import java.util.ArrayList;

public class PostFeedsAdapter extends RecyclerView.Adapter<PostViewHolder>{

    private ArrayList<PostDataModel> mPosts;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public PostFeedsAdapter(ArrayList<PostDataModel> posts, Context context) {
        this.mPosts = posts;
        this.mContext = context;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() { return (this.mPosts!=null? this.mPosts.size():0); }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.mContext = parent.getContext();
        this.mLayoutInflater = LayoutInflater.from(this.mContext);
        View view = this.mLayoutInflater.inflate(R.layout.activity_view_post, parent, false);

        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        //view initialization
        PostDataModel img_res = this.mPosts.get(position);
        holder.getPost_avatar().setImageURI(img_res.getPost_avatar());
        holder.getPost_author().setText(img_res.getPost_author());
        String unix_time = img_res.getPost_timestamp();
        String formated_time = TimeConverter.formatDateFromUnixTime(unix_time);
        holder.getPost_location().setText(img_res.getPost_location());
        holder.getPost_timestamp().setText(formated_time);
        holder.getPost_image().setImageURI(img_res.getPost_media_uri());
        holder.getPost_likes_cnt().setText(Integer.toString(img_res.getPost_likes_cnt()));
        holder.getPost_comments_cnt().setText(Integer.toString(img_res.getPost_comments_cnt()));
        holder.getPost_desp().setText(img_res.getPost_desp());

        String comments_respond = PostViewHandler.getAllComments(img_res);
        String likers_respond = PostViewHandler.getAllLikes(img_res);
        holder.getPost_likes_cnt().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PostViewHandler.onLikesCntClick(view, img_res, mContext, likers_respond);
            }
        });

        holder.getPost_comments_cnt().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PostViewHandler.onCommentsCntClick(view, img_res, mContext, comments_respond, img_res.getPost_id());
            }
        });

    }

}
