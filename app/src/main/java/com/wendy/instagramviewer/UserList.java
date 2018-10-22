package com.wendy.instagramviewer;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.wendy.instagramviewer.api.ClientUtils.Get_Icon_Client;
import com.wendy.instagramviewer.api.RunAPI.API_AGENT;
import com.wendy.instagramviewer.api.StaticTags.API_Tags;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class UserList extends AppCompatActivity {

    private ArrayList<User> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        File directory = getApplicationContext().getFilesDir();

        ListView user_list = findViewById(R.id.user_list);

        String respond = getIntent().getStringExtra("likers_str");
        String is_followers = getIntent().getStringExtra("FOLLOWERS");
        String is_followings = getIntent().getStringExtra("FOLLOWINGS");
        //check if this is for commentor list or searched user list
        if (is_followers != null) {
            UsersAdapter followersAdapter = new UsersAdapter(this, UsersArrayList.users);
            user_list.setAdapter(followersAdapter);
        } else if (is_followings != null) {
            UsersAdapter usersAdapter = new UsersAdapter(this, UsersArrayList.followings);
            user_list.setAdapter(usersAdapter);
        } else if (respond!=null){
            getAllLikers(respond, users, directory);
            UsersAdapter usersAdapter = new UsersAdapter(this, users);
            user_list.setAdapter(usersAdapter);
        }
    }

    public void getAllLikers(String respond, ArrayList<User> users, File directory){
        try {
            JSONArray all_likers = new JSONArray(respond);
            for (int i=0; i<all_likers.length(); i++){
                String liker_usr_str = all_likers.get(i).toString();
                JSONObject liker = new JSONObject(liker_usr_str);
                String username = liker.getString("commentor_name");
                Uri avatar_uri = null;

                //get user avatar icon for them using api
                /****************************************/
                Get_Icon_Client get_icon_client = new Get_Icon_Client(
                        UserSession.ip, UserSession.port,
                        UserSession.username, UserSession.password,
                        directory.toString());
                API_AGENT aa = new API_AGENT(get_icon_client);
                String result = aa.perform();
                if (result != API_Tags.RTN_FAIL){
                    avatar_uri = Uri.parse(result);
                }

                User liker_usr = new User(username, avatar_uri);
                users.add(liker_usr);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
