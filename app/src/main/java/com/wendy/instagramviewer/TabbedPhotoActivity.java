package com.wendy.instagramviewer;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.system.ErrnoException;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import com.sun.jna.platform.win32.Netapi32Util;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class TabbedPhotoActivity extends BottomNavActivity {

    private static final int CROP = 0;
    private static final int BRIGHTNESS = 1;
    private static final int CONTRAST = 2;
    private static final int FILTER = 1;
    private int selected_item = Integer.MAX_VALUE;

    private CropImageView mCropImageView;
    private Uri mCropImageUri;
    private Bitmap original_img;
    private Bitmap cropped;
    private boolean isCropped = false;
//    private Bitmap processed_img;

    private ImageView cropped_btn;
    private ImageView brightness_btn;
    private ImageView contrast_btn;
    private ImageView filter_btn;
    private LinearLayout toolbar;

    private float brightness;
    private float contrast;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cropped_btn = (ImageView)findViewById(R.id.start_crop);
        brightness_btn = (ImageView)findViewById(R.id.brightness_btn);
        contrast_btn = (ImageView)findViewById(R.id.contrast_btn);
        filter_btn = findViewById(R.id.filter_btn);
        toolbar = (LinearLayout)findViewById(R.id.edit_toolbar);

        mCropImageView = (CropImageView)  findViewById(R.id.CropImageView);
        mCropImageView.setAspectRatio(500,500);
        mCropImageView.setFixedAspectRatio(true);
        mCropImageView.setShowCropOverlay(false);

        mCropImageUri = getIntent().getData();
        boolean requirePermissions = getIntent().getExtras().getBoolean("requirePermission");
        String img_src = getIntent().getExtras().getString("image_src");
        if (!requirePermissions) {
                if (img_src == null){
                    mCropImageView.setImageUriAsync(mCropImageUri);
                    original_img = mCropImageView.getCroppedImage();
                    try {
                        cropped = MediaStore.Images.Media.getBitmap(this.getContentResolver(), mCropImageUri);
                        UserUploadedImg.processedImg = cropped;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    mCropImageView.setImageBitmap(UserUploadedImg.processedImg);
                    cropped = UserUploadedImg.processedImg;
                }
        }

        build_crop_btn();
        refreshToolbarItems(CROP);

        cropped_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                build_crop_btn();
            }
        });

        brightness_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                build_brightness_btn(view);
            }
        });

        contrast_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                build_contrast_btn(view);
            }
        });

        filter_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent filter = new Intent(TabbedPhotoActivity.this, ActivityGallery.class);
//                filter.setData(bitmapToUri(view.getContext(), UserUploadedImg.processedImg));
                startActivityForResult(filter, FILTER);

            }
        });
    }

    public Uri bitmapToUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "editted_bitmap", null);
        return Uri.parse(path);
    }

    private void build_crop_btn(){
        mCropImageView.setShowCropOverlay(true);
        //first time click
        if (!(selected_item == CROP)) {
            Button set_crop_btn = createToolbarBtn("Crop");
            Button rotate_btn = createToolbarBtn("Rotate");

            set_crop_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onCropImageClick(view);
                }
            });
            rotate_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCropImageView.rotateImage(90);
                }
            });
            toolbar.removeAllViews();
            toolbar.addView(set_crop_btn);
            toolbar.addView(rotate_btn);
        }
        refreshToolbarItems(CROP);
    }

    private void build_brightness_btn(View view){
        mCropImageView.setShowCropOverlay(false);
        if (!(selected_item == BRIGHTNESS)){
            SeekBar seekbar = build_seekbar(view, 510, 255, BRIGHTNESS);

            toolbar.removeAllViews();
            toolbar.addView(seekbar);
        }
        refreshToolbarItems(BRIGHTNESS);
    }

    private void build_contrast_btn(View view){
        mCropImageView.setShowCropOverlay(false);
        if (!(selected_item == CONTRAST)){
            //middle: 1, 0~1, 1~5
            SeekBar seekbar = build_seekbar(view, 500, 250, CONTRAST);

            toolbar.removeAllViews();
            toolbar.addView(seekbar);
        }
        refreshToolbarItems(CONTRAST);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_photo_edit, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
            case R.id.action_next_to_share:
                Intent intent = new Intent(this, PhotoShareActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //set up seekbar for controlling brightness and contrast
    private SeekBar build_seekbar(View view, int max, int mid, int indicator){
        SeekBar seekbar = new SeekBar(view.getContext());
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                (int)dpToPx(50),
                1.0f
        );
        param.setMargins(
                (int)dpToPx(15), (int)dpToPx(2), (int)dpToPx(15), (int)dpToPx(2)
        );
        seekbar.setLayoutParams(param);
        seekbar.setMax(max);
        seekbar.setProgress(mid);
        seekbar.setOnSeekBarChangeListener( new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (indicator == BRIGHTNESS){
                    brightness = (float) progress - mid;
                } else {
                    if (progress <= mid){ // progress=5 corresponds to contrast = 1
                        contrast = (float) progress/mid;
                    } else {
                        float step = (max-mid)/4;
                        contrast = (float) 1+(progress-mid)/step;
                    }
                }
            }

            public void onStartTrackingTouch(SeekBar seekBar) {}
            public void onStopTrackingTouch(SeekBar seekBar){
                Bitmap new_img;
                if (indicator == BRIGHTNESS){
                    new_img = processing_image(cropped, brightness, 1);
                } else {
                    new_img = processing_image(cropped, 0, contrast);
                }
                UserUploadedImg.processedImg = new_img;
                mCropImageView.setImageBitmap(new_img);
            }
        });
        return seekbar;
    }

    // to properly handle which tool icon is active
    private void refreshToolbarItems(int indicator){
        if (indicator == CROP){
            cropped_btn.setImageResource(R.drawable.ic_crop_rotate_pink_24dp);
            brightness_btn.setImageResource(R.drawable.ic_brightness_grey_24dp);
            contrast_btn.setImageResource(R.drawable.ic_contrast_grey_24dp);
        } else if (indicator == BRIGHTNESS){
            cropped_btn.setImageResource(R.drawable.ic_crop_rotate_grey_24dp);
            brightness_btn.setImageResource(R.drawable.ic_wb_sunny_pink_24dp);
            contrast_btn.setImageResource(R.drawable.ic_contrast_grey_24dp);
        } else if (indicator == CONTRAST){
            cropped_btn.setImageResource(R.drawable.ic_crop_rotate_grey_24dp);
            brightness_btn.setImageResource(R.drawable.ic_brightness_grey_24dp);
            contrast_btn.setImageResource(R.drawable.ic_contrast_pink_24dp);
        }
        this.selected_item = indicator;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_tabbed_photo;
    }

    @Override
    public int getNavigationMenuItemId() {
        return R.id.navigation_camera;
    }

    // change bitmap brightness and contrast
    private Bitmap processing_image(Bitmap src, float brightness, float contrast){
        ColorMatrix cm = new ColorMatrix(new float[]
                {
                   contrast, 0,0,0,brightness,
                   0,contrast,0,0,brightness,
                   0,0,contrast,0,brightness,
                   0,0,0,1,0
                });
        Bitmap dest = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig());
        Canvas canvas = new Canvas(dest);
        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(cm));
        canvas.drawBitmap(src, 0, 0, paint);
        return dest;
    }

    //create the toolbars with button
    public Button createToolbarBtn(String text){
        Button btn = new Button(this);
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                0,
                (int)dpToPx(30),
                1.0f
        );
        param.setMargins(
                (int)dpToPx(15), (int)dpToPx(8),(int)dpToPx(15),(int)dpToPx(8)
        );
        btn.setLayoutParams(param);
        btn.setText(text);
        btn.setTextColor(getResources().getColor(R.color.white));
        btn.setPadding((int)dpToPx(5),(int)dpToPx(5),(int)dpToPx(5),(int)dpToPx(5));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            btn.setBackground(getResources().getDrawable(R.drawable.pink_tag));
        }
        return btn;
    }

    // unit conversion
    public float dpToPx(int dpVal){
        Resources r = getResources();
        float px = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dpVal,
                r.getDisplayMetrics()
        );
        return px;
    }

    //Crop the image and set it back to the  cropping view.
    protected void onCropImageClick(View view) {

        cropped =  mCropImageView.getCroppedImage();
        UserUploadedImg.processedImg = cropped;
        if (cropped != null) {
            mCropImageView.setImageBitmap(cropped);
            isCropped = true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (mCropImageUri != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            mCropImageView.setImageUriAsync(mCropImageUri);
        } else {
            Toast.makeText(this, "Required permissions are not granted", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onActivityResult(final int requestCode,
                                 final int resultCode, final Intent data) {
        switch (requestCode) {
            case FILTER:
                if (resultCode == RESULT_OK) {
                    mCropImageView.setImageUriAsync(data.getData());
                }
                break;

            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

}
