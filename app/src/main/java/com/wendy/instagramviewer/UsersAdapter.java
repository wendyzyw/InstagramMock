package com.wendy.instagramviewer;

import android.content.Context;
import android.media.Image;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wendy.instagramviewer.api.ClientUtils.Follow_Client;
import com.wendy.instagramviewer.api.RunAPI.API_AGENT;
import com.wendy.instagramviewer.api.StaticTags.API_Tags;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class UsersAdapter extends BaseAdapter {

    private static final String SEPERATOR = " ";
    private ArrayList<User> users;
    private Context context;

    public UsersAdapter(Context mContext, ArrayList<User> users){
        this.context = mContext;
        this.users = users;
    }

    @Override
    public int getCount(){
        return users.size();
    }

    @Override
    public Object getItem(int position){
        return users.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        User user = (User) getItem(position);
        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.user_listcell, parent, false);
        }

        TextView username = (TextView)convertView.findViewById(R.id.username);
        TextView user_tags = (TextView)convertView.findViewById(R.id.user_tags);
        ImageView photo = (ImageView)convertView.findViewById(R.id.user_avatar);
        Button follow_btn = (Button)convertView.findViewById(R.id.follow_btn);

        username.setText(user.getUsername());
        user_tags.setText("---");
        photo.setImageURI(user.getUser_avatar());

        //to follow each specific user and show a toast with status
        follow_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Follow_Client fc = new Follow_Client(
                        UserSession.ip, UserSession.port,
                        UserSession.username, UserSession.password,
                        user.getUsername()
                );
                API_AGENT agent = new API_AGENT(fc);
                String rersult = agent.perform();

                if (!rersult.equals(API_Tags.RTN_FAIL)){
                    Toast.makeText(view.getContext(), "Follow successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return convertView;
    }
}
