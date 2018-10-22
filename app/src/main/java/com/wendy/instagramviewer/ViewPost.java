package com.wendy.instagramviewer;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wendy.instagramviewer.api.ClientUtils.Get_Icon_Client;
import com.wendy.instagramviewer.api.ClientUtils.Get_Media_Comments_Client;
import com.wendy.instagramviewer.api.ClientUtils.Get_Media_Likers_Client;
import com.wendy.instagramviewer.api.ClientUtils.Put_Like_On_Media_Client;
import com.wendy.instagramviewer.api.RunAPI.API_AGENT;
import com.wendy.instagramviewer.api.StaticTags.API_Tags;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.File;
import java.sql.Time;

public class ViewPost extends AppCompatActivity {

    private static final int ENABLE_BT_REQUEST_CODE = 1;

    private Boolean liked = false;
    private Boolean commented = false;

    private ImageView avatar;
    private TextView author;
    private TextView location;
    private TextView timestamp;
    private ImageView post_image;
    private TextView likes_cnt;
    private TextView comments_all;
    private TextView post_desp;

    private ImageView bluetooth_btn;
    private ImageView like;
    private ImageView comment;

    String comments_respond;
    String likers_respond;
    boolean already_liked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post);
        File directory = getApplicationContext().getFilesDir();

        int media_id = getIntent().getIntExtra("media_id", 0);
        PostDataModel post = AllMyImages.getAllMyPosts().get(media_id);

        //get location, timestamp
        Context current_context = this;

        comments_respond = PostViewHandler.getAllComments(post);
        likers_respond = PostViewHandler.getAllLikes(post);
        initView(post);
        boolean from_commentlist = getIntent().getBooleanExtra("from_commentlist", false);
        if (from_commentlist){
            post.setPost_comments_cnt(post.getPost_comments_cnt()+1);
        }
        boolean prev_commented_test = getIntent().getBooleanExtra("is_already_commented", false);
        if (prev_commented_test){
            commented = prev_commented_test;

            comment.setImageResource(R.drawable.ic_sms_pink_24dp);
        }
        already_liked = checkIfAlreadyLiked(likers_respond);
        if (already_liked){
            like.setImageResource(R.drawable.ic_thumb_up_pink_24dp);
        } else {
            like.setImageResource(R.drawable.ic_thumb_up_grey_24dp);
        }

        likes_cnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PostViewHandler.onLikesCntClick(view, post, current_context, likers_respond);
            }
        });

        comments_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PostViewHandler.onCommentsCntClick(view, post, current_context, comments_respond, media_id);
            }
        });

        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                liked = PostViewHandler.onLikeBtnCliek(view, post, liked, already_liked,
                        likes_cnt, like);
            }
        });

        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commented = PostViewHandler.onCommentBtnClick(view, post, commented, comment, comments_all);
            }
        });

        bluetooth_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BluetoothAdapter bluetoothAdapter = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.ECLAIR) {
                    bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                }
                if (bluetoothAdapter == null) {
                    Toast.makeText(getApplicationContext(),
                            "This device doesn’t support Bluetooth",Toast.LENGTH_SHORT).show();
                } else {
                    if (!bluetoothAdapter.isEnabled()){
                        //enable bluetooth prompt
                        AlertDialog.Builder builder;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            builder = new AlertDialog.Builder(view.getContext(), android.R.style.Theme_Material_Dialog_Alert);
                        } else {
                            builder = new AlertDialog.Builder(view.getContext());
                        }
                        builder.setTitle("BLUETOOTH")
                                .setMessage("Turn on bluetooth?")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // try to turn on bluetooth
                                        Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                                        startActivityForResult(enableIntent, ENABLE_BT_REQUEST_CODE);
                                    }
                                })
                                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // do nothing
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    } else {
                        Intent intent = new Intent(view.getContext(), BluetoothSwiping.class);
                        startActivity(intent);
                    }
                }
            }
        });
    }

    public boolean checkIfAlreadyLiked(String respond){
        try {
            JSONArray all_likers = new JSONArray(respond);
            for (int i=0; i<all_likers.length(); i++){
                String liker_usr_str = all_likers.get(i).toString();
                JSONObject liker = new JSONObject(liker_usr_str);
                String username = liker.getString("commentor_name");

                if (username.equals(UserSession.username)){
                    return true;
                }
            }
            return false;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void initView(PostDataModel post){
        avatar = (ImageView) findViewById(R.id.avatar);
        author = (TextView) findViewById(R.id.post_author_name);
        location = (TextView) findViewById(R.id.post_location);
        timestamp = (TextView) findViewById(R.id.post_timestamp);
        post_image = (ImageView) findViewById(R.id.post_image);
        likes_cnt = (TextView)findViewById(R.id.likes_user_list);
        comments_all = (TextView)findViewById(R.id.comments_view_list);
        like = (ImageView)findViewById(R.id.post_like);
        comment = (ImageView)findViewById(R.id.post_comment);
        bluetooth_btn = (ImageView) findViewById(R.id.bluetooth_btn);
        post_desp = (TextView)findViewById(R.id.post_description);

        avatar.setImageURI(post.getPost_avatar());
        author.setText(post.getPost_author());
        String unix_time = post.getPost_timestamp();
        String formated_time = TimeConverter.formatDateFromUnixTime(unix_time);
        location.setText(post.getPost_location());
        timestamp.setText(formated_time);
        post_image.setImageURI(post.getPost_media_uri());
        likes_cnt.setText(Integer.toString(post.getPost_likes_cnt()));
        comments_all.setText(Integer.toString(post.getPost_comments_cnt()));
        post_desp.setText(post.getPost_desp());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == ENABLE_BT_REQUEST_CODE){
            if (resultCode == Activity.RESULT_OK){
                Toast.makeText(getApplicationContext(), "Bluetooth has been enabled", Toast.LENGTH_SHORT).show();
                SelectedPhotoForBluethooth.selectedImg = R.drawable.pic_1;
                Intent intent = new Intent(this, BluetoothSwiping.class);
                startActivity(intent);
            }

            if (resultCode == RESULT_CANCELED){
                Toast.makeText(getApplicationContext(), "Error occurred while attempting to enable bluetooth"
                , Toast.LENGTH_SHORT).show();
            }
        }
    }

    public int getContentViewId() {
        return R.layout.activity_discovery;
    }
    public int getNavigationMenuItemId() {
        return R.id.navigation_discovery;
    }
}
