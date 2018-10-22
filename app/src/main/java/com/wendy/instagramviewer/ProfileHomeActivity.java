package com.wendy.instagramviewer;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wendy.instagramviewer.api.ClientUtils.Get_All_User_Media_Names_Client;
import com.wendy.instagramviewer.api.ClientUtils.Get_Icon_Client;
import com.wendy.instagramviewer.api.ClientUtils.Get_Media_Client;
import com.wendy.instagramviewer.api.ClientUtils.Get_Target_Icon_Client;
import com.wendy.instagramviewer.api.ClientUtils.Get_User_Followers_Client;
import com.wendy.instagramviewer.api.ClientUtils.Get_User_Followings_Client;
import com.wendy.instagramviewer.api.RunAPI.API_AGENT;
import com.wendy.instagramviewer.api.StaticTags.API_Tags;

import org.w3c.dom.Text;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileHomeActivity extends BottomNavActivity {

    private static final int GRID = 0;
    private static final int SORT_TIME = 1;
    private static final int SORT_LOCATION = 2;

    private TextView username;
    private TextView posts_cnt;
    private CircleImageView avatar;
    private TextView followers_cnt;
    private TextView followings_cnt;

    private LinearLayout profile_body;

    private ImageView grid_btn;
    private ImageView sort_time_btn;
    private ImageView sort_location_btn;
    private int selected = GRID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        File directory = getApplicationContext().getFilesDir();

        grid_btn = (ImageView) findViewById(R.id.profile_grid_option);
        sort_location_btn = (ImageView) findViewById(R.id.profile_sort_by_location);
        sort_time_btn = (ImageView) findViewById(R.id.profile_sort_by_time);
        profile_body = (LinearLayout) findViewById(R.id.profile_home_body);
        followers_cnt = (TextView) findViewById(R.id.client_followers_cnt);
        followings_cnt = (TextView) findViewById(R.id.client_following_cnt);
        posts_cnt = (TextView) findViewById(R.id.client_posts_cnt);

        username = (TextView) findViewById(R.id.client_username);
        avatar = (CircleImageView) findViewById(R.id.client_avatar);

        ArrayList<User> followers = getFollowers(followers_cnt, directory);
        ArrayList<User> followings = getFollowing(followings_cnt, directory);
        posts_cnt.setText(Integer.toString(AllMyImages.allMyPosts.size()));

        selected = -1;

        username.setText(UserSession.username);

        initAvatar(directory, avatar);

        profile_body.removeAllViews();
        grid_btn.setImageResource(R.drawable.ic_grid_pink_24dp);
        profile_body.addView(buildGridView(profile_body, AllMyImages.getAllMyPosts()));

        //set up utility buttons
        grid_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(selected == GRID)){
                    selected = GRID;
                    profile_body.removeAllViews();
                    profile_body.addView(buildGridView(view, AllMyImages.getAllMyPosts()));
                }
                grid_btn.setImageResource(R.drawable.ic_grid_pink_24dp);
                sort_location_btn.setImageResource(R.drawable.ic_place_grey_24dp);
                sort_time_btn.setImageResource(R.drawable.ic_time_grey_24dp);

            }
        });
        sort_location_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(selected == SORT_LOCATION)){
                    selected = SORT_LOCATION;
                    profile_body.removeAllViews();
                    profile_body.addView(buildRecyclerView(view));
                }
                grid_btn.setImageResource(R.drawable.ic_grid_on_grey_24dp);
                sort_location_btn.setImageResource(R.drawable.ic_place_pink_24dp);
                sort_time_btn.setImageResource(R.drawable.ic_time_grey_24dp);
            }
        });
        sort_time_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(selected == SORT_TIME)){
                    selected = SORT_TIME;
                    profile_body.removeAllViews();
                    profile_body.addView(buildRecyclerView(view));
                }
                grid_btn.setImageResource(R.drawable.ic_grid_on_grey_24dp);
                sort_location_btn.setImageResource(R.drawable.ic_place_grey_24dp);
                sort_time_btn.setImageResource(R.drawable.ic_time_pink_24dp);
            }
        });

        followers_cnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UsersArrayList.users = followers;
                Intent intent = new Intent(view.getContext(), UserList.class);
                intent.putExtra("FOLLOWERS", "FOLLOWERS");
                startActivity(intent);
            }
        });

        followings_cnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UsersArrayList.followings = followings;
                Intent intent = new Intent(view.getContext(), UserList.class);
                intent.putExtra("FOLLOWINGS", "FOLLOWINGS");
                startActivity(intent);
            }
        });

    }

    //get user icon for profile
    public void initAvatar(File dir, CircleImageView avatar){
        Get_Icon_Client get_icon_client = new Get_Icon_Client(UserSession.ip, UserSession.port, UserSession.username, UserSession.password, dir.toString());

        // create an API_AGENT instance (aa) to execute API instance (ac)
        API_AGENT aa = new API_AGENT(get_icon_client);
        // execute API by invoking API_AGENT's perform(), then get return value to respond_str
        String result = aa.perform();
        if (result != API_Tags.RTN_FAIL){
            Uri avatar_uri = Uri.parse(result);
            avatar.setImageURI(avatar_uri);
        }
    }

    //get all followers for a logged in users
    public ArrayList<User> getFollowers(TextView textView, File dir){
        ArrayList<User> followers = new ArrayList<>();

        Get_User_Followers_Client gfc = new Get_User_Followers_Client(
                UserSession.ip, UserSession.port,
                UserSession.username, UserSession.password
        );
        API_AGENT agent = new API_AGENT(gfc);
        String respond = agent.perform();
        setUsersList(followers, respond, dir);
        textView.setText(Integer.toString(followers.size()));

        return followers;
    }

    public ArrayList<User> getFollowing(TextView textView, File dir){
        ArrayList<User> followings = new ArrayList<>();

        Get_User_Followings_Client gfc = new Get_User_Followings_Client(
                UserSession.ip, UserSession.port,
                UserSession.username, UserSession.password
        );

        API_AGENT agent = new API_AGENT(gfc);
        String respond = agent.perform();
        setUsersList(followings, respond, dir);
        textView.setText(Integer.toString(followings.size()));

        return followings;
    }

    //set up the list of users who comment on each posts
    public void setUsersList(ArrayList<User> users, String respond, File dir){
        if (respond!=null) {
            String[] splited = respond.split("#");
            for (String name : splited) {
                Get_Target_Icon_Client gtic = new Get_Target_Icon_Client(
                        UserSession.ip, UserSession.port,
                        UserSession.username, UserSession.password,
                        name, dir.getPath()
                );
                Uri avatar_uri = null;
                API_AGENT agent2 = new API_AGENT(gtic);
                String result = agent2.perform();
                if (!result.equals(API_Tags.RTN_FAIL)) {
                    avatar_uri = Uri.parse(result);
                }
                User user = new User(name, avatar_uri);
                users.add(user);
            }
        }
    }

    //grid view for displaying all images
    public ExpandableHeightGridView buildGridView(View view, ArrayList<PostDataModel> posts){
        //build grid view
        ExpandableHeightGridView gridView = new ExpandableHeightGridView(view.getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0,0,0,0);
        gridView.setLayoutParams(params);
        gridView.setPadding(0,0,0,0);
        gridView.setGravity(Gravity.CENTER);
        gridView.setColumnWidth((int)dpToPx(120));
        gridView.setNumColumns(GridView.AUTO_FIT);
        gridView.setVerticalSpacing((int)dpToPx(2));
        gridView.setHorizontalSpacing(0);

        gridView.setExpanded(true);

        PostsAdapter postsAdapter = new PostsAdapter(view.getContext(), posts);
        gridView.setAdapter(postsAdapter);

        return gridView;
    }

    //recyclerview for sorting by time and location
    public RecyclerView buildRecyclerView(View view){
        RecyclerView recyclerView = new RecyclerView(view.getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        recyclerView.setLayoutParams(params);
        recyclerView.setClipToPadding(false);
        recyclerView.setNestedScrollingEnabled(false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<PostDataModel> all_posts = AllMyImages.allMyPosts;
        if (selected == SORT_TIME){
            Collections.sort(all_posts, new Comparator<PostDataModel>() {
                @Override public int compare(PostDataModel p1, PostDataModel p2) {
                    return p1.getPost_timestamp().compareTo(p2.getPost_timestamp());
                }

            });
        } else if (selected == SORT_LOCATION){
            Collections.sort(all_posts, new Comparator<PostDataModel>() {
                @Override public int compare(PostDataModel p1, PostDataModel p2) {
                    return p1.getPost_location().compareTo(p2.getPost_location());
                }

            });
        }
        PostFeedsAdapter postFeedsAdapter = new PostFeedsAdapter(AllMyImages.getAllMyPosts(), this);
        recyclerView.setAdapter(postFeedsAdapter);

        return recyclerView;
    }

    public void refreshToolbar(){
        if(this.selected == GRID){
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateNavigationBarState();
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_profile_home;
    }

    @Override
    public int getNavigationMenuItemId() {
        return R.id.navigation_profile;
    }

    public float dpToPx(int dpVal){
        Resources r = getResources();
        float px = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dpVal,
                r.getDisplayMetrics()
        );
        return px;
    }
}
