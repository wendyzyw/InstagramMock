package com.wendy.instagramviewer;

import android.content.Context;
import android.net.Uri;

import com.wendy.instagramviewer.api.ClientUtils.Get_All_User_Media_Info_Client;
import com.wendy.instagramviewer.api.ClientUtils.Get_All_User_Media_Names_Client;
import com.wendy.instagramviewer.api.ClientUtils.Get_Icon_Client;
import com.wendy.instagramviewer.api.ClientUtils.Get_Media_Client;
import com.wendy.instagramviewer.api.ClientUtils.Get_Media_Comments_Client;
import com.wendy.instagramviewer.api.ClientUtils.Get_Media_Description_Client;
import com.wendy.instagramviewer.api.ClientUtils.Get_Media_Likers_Client;
import com.wendy.instagramviewer.api.RunAPI.API_AGENT;
import com.wendy.instagramviewer.api.StaticTags.API_Tags;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

public class GetAllMedia {

    public static void setAllPosts(String username, String pwd, Context context){
        File directory = context.getApplicationContext().getFilesDir();
        String[] all_media_names = GetAllMedia.initAllMediaNames(UserSession.username, UserSession.password);
        ArrayList<PostDataModel> posts = GetAllMedia.initPostData(directory, all_media_names,
                UserSession.username, UserSession.password);

        AllMyImages.setAll_media_names(all_media_names);
        AllMyImages.setAllMyPosts(posts);
    }

    public static String[] initAllMediaNames(String username, String pwd){
        String[] all_media = new String[]{};

        /**************************/
        Get_All_User_Media_Names_Client get_all_user_media_names_client = new Get_All_User_Media_Names_Client(
                UserSession.ip, UserSession.port,
                username, pwd);
        // create an API_AGENT instance (aa) to execute API instance (ac)
        API_AGENT aa = new API_AGENT(get_all_user_media_names_client);
        String respond = aa.perform();
        if ((respond!=null) && (!respond.equals(API_Tags.RTN_FAIL)))
        {
            all_media = respond.split("#");
        }
        return all_media;
    }


    public static ArrayList<PostDataModel> initPostData(File dir, String[] all_media_names, String username, String pwd){
        ArrayList<PostDataModel> posts = new ArrayList<>();

        Uri avatar_uri;
        /**************************/
        Get_Icon_Client get_icon_client = new Get_Icon_Client(UserSession.ip, UserSession.port,
                username, pwd,
                dir.toString());
        // create an API_AGENT instance (aa) to execute API instance (ac)
        API_AGENT aa = new API_AGENT(get_icon_client);
        // execute API by invoking API_AGENT's perform(), then get return value to respond_str
        String result = aa.perform();
        if (result != API_Tags.RTN_FAIL){
            avatar_uri = Uri.parse(result);
        } else {
            avatar_uri = null;
        }

        for (String name : all_media_names)
        {
            String img_str = dir.toString() + "\\" + name;
            /**************************/
            Get_Media_Client gm = new Get_Media_Client( UserSession.ip,
                    UserSession.port,
                    username, pwd,
                    //UserSession.username,
                    name,
                    dir.toString()
            );
            API_AGENT newaa = new API_AGENT(gm);
            String result2 = newaa.perform();
            if (!result2.equals(API_Tags.RTN_FAIL)){
                PostDataModel postDataModel = new PostDataModel(
                        avatar_uri,
                        name,
                        username,
                        "---",
                        "---",
                        Uri.parse(img_str),
                        0,
                        0
                );
                postDataModel.setPost_id(posts.size());
                getAllCommentsCnt(postDataModel, username, pwd);
                getAllLikes(postDataModel, username, pwd);
                posts.add(postDataModel);
            }
        }
        posts = GetAllMedia.getMediaInfo(posts, username, pwd);
        return posts;
    }

    public static void getAllCommentsCnt(PostDataModel post, String username, String pwd){
        /**************************/
        Get_Media_Comments_Client gmc = new Get_Media_Comments_Client(
                UserSession.ip, UserSession.port,
                username, pwd,
                post.getPost_author(), post.getMedia_name()
        );
        API_AGENT agent = new API_AGENT(gmc);
        String respond = agent.perform();

        try {
            JSONArray all_comments = new JSONArray(respond);
            post.setPost_comments_cnt(all_comments.length());
        } catch (JSONException e) {
            e.printStackTrace();
            post.setPost_comments_cnt(0);
        }
    }

    public static void getAllLikes(PostDataModel post, String username, String pwd){
        /**************************/
        Get_Media_Likers_Client gml = new Get_Media_Likers_Client(
                UserSession.ip, UserSession.port,
                username, pwd,
                post.getPost_author(), post.getMedia_name());
        API_AGENT agent = new API_AGENT(gml);
        String respond = agent.perform();

        try {
            JSONArray all_likers = new JSONArray(respond);
            post.setPost_likes_cnt(all_likers.length());
        } catch (JSONException e) {
            e.printStackTrace();
            post.setPost_likes_cnt(0);
        }
    }

    public static ArrayList<PostDataModel> getMediaInfo(ArrayList<PostDataModel> posts, String name, String pwd){
       //get location , time , desecription for  posts
        /**************************/
        Get_All_User_Media_Info_Client gmic = new Get_All_User_Media_Info_Client(
                UserSession.ip, UserSession.port,
                name, pwd
        );
        API_AGENT agent = new API_AGENT(gmic);
        String respond = agent.perform();

        try {
            JSONArray all_info = new JSONArray(respond);
            for (int i=0; i<all_info.length(); i++){
                String info_str = all_info.get(i).toString();
                JSONObject info_json = new JSONObject(info_str);
                String medname = info_json.getString("media_name");
                String location = info_json.getString("media_location");
                String time = info_json.getString("media_time");

                //get description separatrely as long text
                /**************************/
                Get_Media_Description_Client gmdc = new Get_Media_Description_Client(
                        UserSession.ip, UserSession.port,
                        UserSession.username, UserSession.password,
                        medname
                );
                API_AGENT agent2 = new API_AGENT(gmdc);
                String result = agent2.perform();

                for (PostDataModel eachpost: posts){
                    if (eachpost.getMedia_name().equals(medname)){
                        eachpost.setPost_location(location);
                        eachpost.setPost_timestamp(time);
                        if (result!=null && !result.equals("%NONE%")){
                            eachpost.setPost_desp(result);
                        } else {
                            eachpost.setPost_desp("--");
                        }
                    }
                }
            }
            return posts;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return posts;
    }
}
