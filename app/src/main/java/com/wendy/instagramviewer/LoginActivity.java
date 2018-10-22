package com.wendy.instagramviewer;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.wendy.instagramviewer.api.ClientUtils.*;
import com.wendy.instagramviewer.api.RunAPI.API_AGENT;
import com.wendy.instagramviewer.api.StaticTags.API_Tags;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void clickToRegister(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void logIn(View view) {

        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);

        String email_text = email.getText().toString();
        String password_text = password.getText().toString();

        // create an API instance to perform (ac)
        Authenticate_Client ac = new Authenticate_Client(UserSession.ip, UserSession.port, password_text, email_text);
        // create an API_AGENT instance (aa) to execute API instance (ac)
        API_AGENT aa = new API_AGENT(ac);
        // execute API by invoking API_AGENT's perform(), then get return value to respond_str
        String respond_str = aa.perform();
        // testing on the value of respond_str:

        if (respond_str.equals(API_Tags.RTN_FAIL))
        {
            // re-enter user email and password
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this,  "Log in success", Toast.LENGTH_SHORT).show();
            String user_name = respond_str;
            UserSession.username = user_name;
            UserSession.password = password_text;
            UserSession.email = email_text;

            GetAllMedia.setAllPosts(UserSession.username, UserSession.password, view.getContext());

            //store login user info in application session

            // moving onto the homepages
            Intent intent = new Intent(this, HomePageActivity.class);
            startActivity(intent);
        }
    }

}
