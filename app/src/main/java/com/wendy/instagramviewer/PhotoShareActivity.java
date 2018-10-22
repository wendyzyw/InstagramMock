package com.wendy.instagramviewer;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.wendy.instagramviewer.api.ClientUtils.Put_Photo_Client;
import com.wendy.instagramviewer.api.RunAPI.API_AGENT;
import com.wendy.instagramviewer.api.StaticTags.API_Tags;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class PhotoShareActivity extends AppCompatActivity {

    private ImageView img;
    private EditText img_description;
    private EditText location;
    private EditText description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_share);
        img = (ImageView)findViewById(R.id.uploaded_img);
        img.setImageBitmap(UserUploadedImg.processedImg);
        img_description = (EditText)findViewById(R.id.img_description);
        location = (EditText)findViewById(R.id.img_location);
        description = (EditText)findViewById(R.id.img_description);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_photo_share, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
            case R.id.action_share:
                String location_txt = location.getText().toString();
                String desp_txt = description.getText().toString();
                String[] img_output = saveToInternalStorage(UserUploadedImg.processedImg);
                System.out.println("12123231312");
                System.out.println("loc"+location_txt);
                Put_Photo_Client ppc = new Put_Photo_Client(
                        UserSession.ip, UserSession.port,
                        UserSession.username, UserSession.password,
                        location_txt,
                        desp_txt,
                        img_output[0],
                        img_output[1]
                );

                API_AGENT agent = new API_AGENT(ppc);
                String result = agent.perform();
                System.out.print(result);

                if (!result.equals(API_Tags.RTN_FAIL)){
                    Toast.makeText(this, "Image shared successfully",
                            Toast.LENGTH_SHORT).show();

                    GetAllMedia.setAllPosts(UserSession.username, UserSession.password, this);

                    //redirect to profile page
                    Intent intent = new Intent(this, ProfileHomeActivity.class);
                    startActivity(intent);
                } else {
                    System.out.println("fail share");
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private String[] saveToInternalStorage(Bitmap bitmapImage){
        String[] output = new String[2];
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File baseDir = getApplicationContext().getFilesDir();

        //compute image name
        long current_time = System.currentTimeMillis() / 1000;
        String img_name = UserSession.username + Long.toString(current_time) + ".png";
        output[0] = img_name;

        // Create imageDir
        File mypath=new File(baseDir, img_name);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        output[1] = baseDir.toString();
        System.out.println(baseDir);
        return output;
    }
}
