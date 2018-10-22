package com.wendy.instagramviewer;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wendy.instagramviewer.api.ClientUtils.Get_Media_Comments_Client;
import com.wendy.instagramviewer.api.ClientUtils.Get_Media_Likers_Client;
import com.wendy.instagramviewer.api.ClientUtils.Put_Like_On_Media_Client;
import com.wendy.instagramviewer.api.RunAPI.API_AGENT;
import com.wendy.instagramviewer.api.StaticTags.API_Tags;

public class PostViewHandler {

    public static String getAllComments(PostDataModel post){
        /**************************************/
        Get_Media_Comments_Client gmc = new Get_Media_Comments_Client(
                UserSession.ip, UserSession.port,
                UserSession.username, UserSession.password,
                post.getPost_author(), post.getMedia_name()
        );
        API_AGENT agent = new API_AGENT(gmc);
        String respond = agent.perform();
        return respond;
    }

    public static String getAllLikes(PostDataModel post){
        /**************************************/
        Get_Media_Likers_Client gml = new Get_Media_Likers_Client(
                UserSession.ip, UserSession.port,
                UserSession.username, UserSession.password,
                post.getPost_author(), post.getMedia_name());
        API_AGENT agent = new API_AGENT(gml);
        String respond = agent.perform();
        return respond;
    }

    public static void onLikesCntClick(View view, PostDataModel post, Context current_context, String likers_respond){
        likers_respond = getAllLikes(post);
        Intent intent = new Intent(current_context, UserList.class);
        intent.putExtra("likers_str", likers_respond);
        current_context.startActivity(intent);
    }

    public static void onCommentsCntClick(View view, PostDataModel post, Context current_context, String comments_respond, int media_id){
        comments_respond = getAllComments(post);
        Intent intent = new Intent(current_context, CommentList.class);
        intent.putExtra("commentors_str", comments_respond);
        intent.putExtra("media_name", post.getMedia_name());
        intent.putExtra("post_author", post.getPost_author());
        intent.putExtra("media_id", media_id);
        current_context.startActivity(intent);
    }

    public static boolean onLikeBtnCliek(View view, PostDataModel post, boolean liked,
                                      boolean already_liked, TextView likes_cnt, ImageView like){
        if (!already_liked){
            //not liked , change it to liked
            like.setImageResource(R.drawable.ic_thumb_up_pink_24dp);

            /**************************************/
            Put_Like_On_Media_Client plc = new Put_Like_On_Media_Client(
                    UserSession.ip, UserSession.port,
                    UserSession.username, UserSession.password,
                    post.getPost_author(), post.getMedia_name(),
                    ""
            );
            API_AGENT agent = new API_AGENT(plc);
            String result = agent.perform();
            if (!result.equals(API_Tags.RTN_FAIL)){
                Toast.makeText(view.getContext(), "Liked!", Toast.LENGTH_SHORT).show();
            }
            int cnt = post.getPost_likes_cnt()+1;
            post.setPost_likes_cnt(cnt);
            likes_cnt.setText(Integer.toString(cnt));
        } else {
            Toast.makeText(view.getContext(), "You have already liked this post",
                    Toast.LENGTH_SHORT).show();
        }
        return !liked;
    }

    public static boolean onCommentBtnClick(View view, PostDataModel post, boolean commented, ImageView comment, TextView comments_all){
        if (!commented){
            comment.setImageResource(R.drawable.ic_sms_pink_24dp);
            int cnt = post.getPost_comments_cnt()+1;
            post.setPost_comments_cnt(cnt);
            comments_all.setText(Integer.toString(cnt));
        } else {
            comment.setImageResource(R.drawable.ic_sms_grey_24dp);
        }
        return !commented;
    }
}
