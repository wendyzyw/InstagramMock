package com.wendy.instagramviewer;

import android.drm.DrmUtils;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.wendy.instagramviewer.api.ClientUtils.Get_Icon_Client;
import com.wendy.instagramviewer.api.ClientUtils.Get_Target_Icon_Client;
import com.wendy.instagramviewer.api.ClientUtils.Get_Target_Media_Client;
import com.wendy.instagramviewer.api.ClientUtils.Get_Target_User_Media_Info_Client;
import com.wendy.instagramviewer.api.ClientUtils.Get_User_Followings_Client;
import com.wendy.instagramviewer.api.RunAPI.API_AGENT;
import com.wendy.instagramviewer.api.StaticTags.API_Tags;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

public class HomePageActivity extends BottomNavActivity {

    private RecyclerView mRecyclerView;
    private PostFeedsAdapter mPostFeedsAdapter;
    private TextView emptyView;
    private AllMyImages myPosts = new AllMyImages();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mRecyclerView = (RecyclerView) findViewById(R.id.posts_list);
        this.emptyView = (TextView) findViewById(R.id.no_posts_text);

        ArrayList<PostDataModel> posts = AllMyImages.allMyPosts;
        ArrayList<PostDataModel> all_posts = getFollowing();
        all_posts.addAll(posts);

        if (!(all_posts.size()==0)) {
            this.mRecyclerView.setVisibility(View.VISIBLE);
            this.emptyView.setVisibility(View.GONE);
            this.mRecyclerView.setLayoutManager(new LinearLayoutManager(
                    this, LinearLayoutManager.VERTICAL, false));
            this.mRecyclerView.setHasFixedSize(true);
            this.mPostFeedsAdapter = new PostFeedsAdapter(all_posts, this);
            this.mRecyclerView.setAdapter(this.mPostFeedsAdapter);
        } else {
            this.mRecyclerView.setVisibility(View.GONE);
            this.emptyView.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onPause(){
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateNavigationBarState();
    }

    public ArrayList<PostDataModel> getFollowing(){
        String dir = getApplicationContext().getFilesDir().getPath();
        ArrayList<PostDataModel> posts = new ArrayList<>();
        /**************************/
        Get_User_Followings_Client gfc = new Get_User_Followings_Client(
                UserSession.ip, UserSession.port,
                UserSession.username, UserSession.password
        );

        API_AGENT agent = new API_AGENT(gfc);
        String respond = agent.perform();

        if (respond!=null) {
            String[] splited = respond.split("#");
            for (String name : splited) {
                if (!name.equals(UserSession.username)) {
                    /************************************/
                    Get_Target_Icon_Client get_icon_client = new Get_Target_Icon_Client(UserSession.ip, UserSession.port,
                            UserSession.username, UserSession.password,
                            name, dir);
                    // create an API_AGENT instance (aa) to execute API instance (ac)
                    API_AGENT avatar_agent = new API_AGENT(get_icon_client);
                    // execute API by invoking API_AGENT's perform(), then get return value to respond_str
                    String result = avatar_agent.perform();
                    Uri avatar_uri = null;
                    if (result != API_Tags.RTN_FAIL) {
                        avatar_uri = Uri.parse(result);
                    }

                    /************************************/
                    Get_Target_User_Media_Info_Client gtumic = new Get_Target_User_Media_Info_Client(
                            UserSession.ip, UserSession.port,
                            UserSession.username, UserSession.password,
                            name
                    );
                    API_AGENT media_agentt = new API_AGENT(gtumic);
                    String media_result = media_agentt.perform();

                    try {
                        JSONArray media_arr = new JSONArray(media_result);
                        for (int i = 0; i < media_arr.length(); i++) {
                            String one_post_str = media_arr.get(i).toString();
                            JSONObject one_post_json = new JSONObject(one_post_str);
                            String med_name = one_post_json.getString("media_name");
                            String med_time = one_post_json.getString("media_time");
                            String med_loc = one_post_json.getString("media_location");
                            String med_desp = one_post_json.getString("media_description");

                            /************************************/
                            Get_Target_Media_Client gtmc = new Get_Target_Media_Client(
                                    UserSession.ip, UserSession.port,
                                    UserSession.username, UserSession.password,
                                    name,
                                    med_name,
                                    dir
                            );
                            API_AGENT photo_agent = new API_AGENT(gtmc);
                            String photo_respond = photo_agent.perform();
                            if (!photo_respond.equals(API_Tags.RTN_FAIL)) {
                                PostDataModel post = new PostDataModel(
                                        avatar_uri, med_name, name, med_loc,
                                        med_time, Uri.parse(dir + "\\" + med_name), 0, 0
                                );
                                post.setPost_desp(med_desp);

                                posts.add(post);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return posts;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_home_page;
    }

    @Override
    public int getNavigationMenuItemId() {
        return R.id.navigation_home;
    }

}
