package com.wendy.instagramviewer;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.wendy.instagramviewer.api.ClientUtils.Get_Icon_Client;
import com.wendy.instagramviewer.api.ClientUtils.Put_Comment_On_Media_Client;
import com.wendy.instagramviewer.api.RunAPI.API_AGENT;
import com.wendy.instagramviewer.api.StaticTags.API_Tags;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentList extends AppCompatActivity {

    private ArrayList<Comment> comments = new ArrayList<>();
    private EditText comment_area;
    private Button comment_submit;
    private ConstraintLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_list);
        File directory = getApplicationContext().getFilesDir();

        CircleImageView avatar = (CircleImageView) findViewById(R.id.comment_user_avatar);
        Get_Icon_Client gic = new Get_Icon_Client(UserSession.ip, UserSession.port,
                UserSession.username, UserSession.password, directory.toString());
        // create an API_AGENT instance (aa) to execute API instance (ac)
        API_AGENT agent = new API_AGENT(gic);
        // execute API by invoking API_AGENT's perform(), then get return value to respond_str
        String result2 = agent.perform();
        if (result2 != API_Tags.RTN_FAIL){
            Uri avatar_uri = Uri.parse(result2);
            avatar.setImageURI(avatar_uri);
        }

        //get all fields to show in this view
        String respond = getIntent().getStringExtra("commentors_str");
        String media_name = getIntent().getStringExtra("media_name");
        String post_author = getIntent().getStringExtra("post_author");
        int post_id = getIntent().getIntExtra("media_id", 0);

        if (respond!=null) {
            try {
                JSONArray all_comments = new JSONArray(respond);
                for (int i = 0; i < all_comments.length(); i++) {
                    String comment_str = all_comments.get(i).toString();
                    JSONObject comment_json = new JSONObject(comment_str);
                    String username = comment_json.getString("commentor_name");
                    String comment_txt = comment_json.getString("comment_text");
                    String comment_time = comment_json.getString("comment_time");

                    String formatedDate = TimeConverter.formatDateFromUnixTime(comment_time);

                    Uri avatar_uri = null;
                    /***********************************************/
                    Get_Icon_Client get_icon_client = new Get_Icon_Client(
                            UserSession.ip, UserSession.port,
                            UserSession.username, UserSession.password,
                            directory.toString());
                    API_AGENT aa = new API_AGENT(get_icon_client);
                    String result = aa.perform();
                    if (result != API_Tags.RTN_FAIL) {
                        avatar_uri = Uri.parse(result);
                    }

                    Comment comment = new Comment(username, avatar_uri, comment_txt, formatedDate);
                    comments.add(comment);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        CommentsAdapter commentsAdapter = new CommentsAdapter(this, comments);
        ListView comment_list = (ListView)findViewById(R.id.comment_list);
        comment_list.setAdapter(commentsAdapter);

        //initialize all fields for a list of comments
        comment_area = (EditText) findViewById(R.id.post_new_comment);
        comment_submit = (Button) findViewById(R.id.comment_submit);
        layout = (ConstraintLayout) findViewById(R.id.commenter_list_layout);
        comment_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean result = submitComment(comment_area, post_author, media_name, view.getContext());
                if (result){
                    comment_area.getText().clear();
                    finish();
                    Intent intent = new Intent(view.getContext(), ViewPost.class);
                    intent.putExtra("from_commentlist", true);
                    intent.putExtra("is_already_commented", true);
                    intent.putExtra("media_id", post_id);
                    startActivity(intent);
                }
            }
        });


    }

    public boolean submitComment(EditText text_box, String author, String media, Context context){
        String comment_txt = text_box.getText().toString();
        /***********************************************/
        Put_Comment_On_Media_Client pmc = new Put_Comment_On_Media_Client(
                UserSession.ip, UserSession.port,
                UserSession.username, UserSession.password,
                author, media, comment_txt, "", ""
        );
        API_AGENT agent = new API_AGENT(pmc);
        String result = agent.perform();
        if (!result.equals(API_Tags.RTN_FAIL)){
            Toast.makeText(context, "Comment submitted successfully", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            return false;
        }

    }

}
