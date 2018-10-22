package com.wendy.instagramviewer;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class TabbedSocialActivity extends BottomNavActivity {

    private TabLayout tabs;
    private ViewPager body_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_tabbed_social);
        body_content = (ViewPager)findViewById(R.id.social_pager);
        setupBodyContent(body_content);

        tabs = (TabLayout)findViewById(R.id.social_category);
        tabs.setupWithViewPager(body_content);

    }

    private void setupBodyContent(ViewPager body_content){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new SocialFeed(), "You");
        adapter.addFragment(new SocialFeed(), "Following");
        body_content.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateNavigationBarState();
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_tabbed_social;
    }

    @Override
    public int getNavigationMenuItemId() {
        return R.id.navigation_notifications;
    }
}
