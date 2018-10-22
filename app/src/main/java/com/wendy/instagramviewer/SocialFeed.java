package com.wendy.instagramviewer;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.JsonReader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wendy.instagramviewer.api.ClientUtils.Get_Icon_Client;
import com.wendy.instagramviewer.api.ClientUtils.Get_Media_Client;
import com.wendy.instagramviewer.api.ClientUtils.Get_User_Event_Client;
import com.wendy.instagramviewer.api.RunAPI.API_AGENT;
import com.wendy.instagramviewer.api.StaticTags.API_Tags;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class SocialFeed extends Fragment {

    private RecyclerView mRecyclerView;
    private TimecardsAdapter mTimecardAdapter;
    private ArrayList<SocialEvent> mEvents = new ArrayList<SocialEvent>();

    public SocialFeed(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_social_feed);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.activity_social_feed, container, false);

        this.mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_socialEvents_list);
        this.mRecyclerView.setLayoutManager(getLinearLayoutManager(rootView));
        this.mRecyclerView.setHasFixedSize(true);
        initView(container);

        mEvents = getAllEvents();

        return rootView;
    }

    private LinearLayoutManager getLinearLayoutManager(View container) {
        return new LinearLayoutManager(container.getContext(), LinearLayoutManager.VERTICAL, false);
    }

    private void initView(View container){
        mEvents = getAllEvents();
        this.mTimecardAdapter = new TimecardsAdapter(this.mEvents, container.getContext(), false);
        this.mTimecardAdapter.notifyDataSetChanged();
        this.mRecyclerView.setAdapter(this.mTimecardAdapter);
    }


    public ArrayList<SocialEvent> getAllEvents(){
        ArrayList<SocialEvent> allEvents = new ArrayList<>();
        Get_User_Event_Client gec = new Get_User_Event_Client(
                UserSession.ip, UserSession.port,
                UserSession.username, UserSession.password
        );
        API_AGENT agent = new API_AGENT(gec);
        String respond = agent.perform();

        try {
            //convert the received string from api to json and parse each to each event object
            JSONArray events_arr = new JSONArray(respond);
            for (int i=0; i<events_arr.length(); i++){
                String event_str = events_arr.get(i).toString();
                JSONObject event_obj = new JSONObject(event_str);
                String commentor = event_obj.getString("user_name");
                String event_id = event_obj.getString("event_identifier");
                String media_name = event_obj.getString("media_name");
                String time = event_obj.getString("event_time");
                Uri img = null;

                SocialEvent socialEvent = null;
                int event_type = -1;
                if (!event_id.equals(API_Tags.EVT_FLW)){
                    if (event_id.equals(API_Tags.EVT_LIKE)){
                        event_type = SocialEvent.LIKE_EVENT;
                    } else if (event_id.equals(API_Tags.EVT_CMT)){
                        event_type = SocialEvent.COMMENT_EVENT;
                    }
                    for (PostDataModel post : AllMyImages.allMyPosts){
                        if (post.getMedia_name().equals(media_name)){
                            img = post.getPost_media_uri();
                            socialEvent = new SocialEvent(commentor, UserSession.username, event_type, img, time);
                        }
                    }
                } else {
                    if (event_id.equals(API_Tags.EVT_FLW)){
                        event_type  =SocialEvent.FOLLOW_EVENT;
                        socialEvent = new SocialEvent(commentor, UserSession.username, event_type);
                    }
                }
                allEvents.add(socialEvent);
            }
            Collections.reverse(mEvents);
            return allEvents;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
