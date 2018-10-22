package com.wendy.instagramviewer;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.wendy.instagramviewer.api.StaticTags.API_Tags;

public class RegisterActivity extends AppCompatActivity {

    private TextView username;
    private TextView password;
    private TextView email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = (TextView) findViewById(R.id.register_username);
        email = (TextView) findViewById(R.id.register_email);
        password = (TextView) findViewById(R.id.register_password);

    }

    public void clickToLogin(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void register(View view) {
        String new_username = username.getText().toString();
        String new_password = password.getText().toString();
        String new_email = email.getText().toString();

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://10.96.231.69:5000"+ API_Tags.USER_REG+
                API_Tags.USR_NAME+new_username+'&'+
                API_Tags.USR_PWD+new_password+'&'+
                API_Tags.USR_EMAIL+new_email;
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Intent intent = new Intent(view.getContext(), LoginActivity.class);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
            }
        });
        queue.add(request);
    }
}
