package com.wendy.instagramviewer;

import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import com.nex3z.flowlayout.FlowLayout;
import com.wendy.instagramviewer.api.ClientUtils.Get_Icon_Client;
import com.wendy.instagramviewer.api.ClientUtils.Get_Recommend_User_C_Client;
import com.wendy.instagramviewer.api.ClientUtils.Get_Target_Icon_Client;
import com.wendy.instagramviewer.api.ClientUtils.Search_Client;
import com.wendy.instagramviewer.api.RunAPI.API_AGENT;
import com.wendy.instagramviewer.api.StaticTags.API_Tags;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

public class DiscoveryActivity extends BottomNavActivity {

    SearchView search_input;
    ListView user_list;
    TextView title_text;
    FlowLayout outerFlow;
    private LinearLayout header;

    ArrayList<User> users = new ArrayList<User>();
    ArrayList<String> histories = new ArrayList<>();

    UsersAdapter usersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_discovery);
        File directory = getApplicationContext().getFilesDir();
        header = (LinearLayout) findViewById(R.id.discovery_entries);

        /*************************************/
        Get_Recommend_User_C_Client gruc = new Get_Recommend_User_C_Client(
                UserSession.ip, UserSession.port,
                UserSession.username, UserSession.password
        );
        API_AGENT agent = new API_AGENT(gruc);
        String respond = agent.perform();
        if (respond!=null) {
            /*****************************************/
            if (!respond.equals(API_Tags.NONE_VAL)){
                Get_Target_Icon_Client gtic = new Get_Target_Icon_Client(
                        UserSession.ip, UserSession.port,
                        UserSession.username, UserSession.password,
                        respond, directory.getPath()
                );
                API_AGENT aa = new API_AGENT(gtic);
                String result2 = aa.perform();
                Uri avatar_uri = null;
                if (result2 != API_Tags.RTN_FAIL){
                    avatar_uri = Uri.parse(result2);
                }

                User user = new User(respond, avatar_uri);
                users.add(user);
            }
        }


        title_text = findViewById(R.id.search_title_text);
        title_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setOriginalUserAdapter();
            }
        });

        user_list = findViewById(R.id.suggested_user_list);
        usersAdapter = new UsersAdapter(this, users);
        user_list.setAdapter(usersAdapter);

        outerFlow = findViewById(R.id.flow_history);
        setHistoryEntries();

        search_input = (SearchView) findViewById(R.id.search_input);
        initSearchView(search_input);

    }

    public int sizeConvert(int sizeInDp){
        float scale = getResources().getDisplayMetrics().density;
        int dpAsPixels = (int) (sizeInDp*scale + 0.5f);
        return dpAsPixels;
    }

    public void setHistoryEntries(){
        outerFlow.removeAllViews();
        for (String entry: histories){
            TextView newText = buildLabel(entry);
            outerFlow.addView(newText);
        }
    }

    private TextView buildLabel(String text) {

        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setTextSize(14);
        textView.setTextColor(Color.WHITE);
        textView.setPadding(sizeConvert(9), sizeConvert(7), sizeConvert(9), sizeConvert(7));
        textView.setBackgroundResource(R.drawable.pink_tag);

        return textView;
    }

    public void initSearchView(SearchView search_input){
        search_input.setQueryHint("Search by username");
        search_input.setFocusable(false);
        search_input.setIconified(false);
        search_input.clearFocus();

        search_input.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                performSearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                return false;
            }
        });

    }

    @Override
    public boolean onSearchRequested(){
        return super.onSearchRequested();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateNavigationBarState();
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_discovery;
    }

    @Override
    public int getNavigationMenuItemId() {
        return R.id.navigation_discovery;
    }

    public void performSearch(String query){
        //compare everything by lowercase
        File directory = getApplicationContext().getFilesDir();
        ArrayList<User> results = new ArrayList<>();
        query = query.toLowerCase(Locale.getDefault());
        if (query.length() >0){
            //get users recommendation
            /*****************************/
            Search_Client sc = new Search_Client(
                    UserSession.ip, UserSession.port,
                    UserSession.username, UserSession.password,
                    query
            );
            API_AGENT agent = new API_AGENT(sc);
            String result = agent.perform();
            if (result != null){
                String[] splited = result.split("#");
                for (String name: splited){
                    //get user icon for the recommended user
                    /****************************************/
                    Get_Target_Icon_Client gtic = new Get_Target_Icon_Client(
                            UserSession.ip, UserSession.port,
                            UserSession.username, UserSession.password,
                            name, directory.getPath()
                    );
                    API_AGENT aa = new API_AGENT(gtic);
                    String result2 = aa.perform();
                    Uri avatar_uri = null;
                    if (result2 != API_Tags.RTN_FAIL){
                        avatar_uri = Uri.parse(result2);
                    }

                    User user = new User(name, avatar_uri);
                    results.add(user);
                }
            }
        }
        histories.add(query);
        setHistoryEntries();
        UsersAdapter newUsersAdapter = new UsersAdapter(this, results);
        user_list.setAdapter(newUsersAdapter);

        //change title text
        title_text.setText("Back to Suggestion List");
    }

    public void setOriginalUserAdapter(){
        UsersAdapter originalAdapter = new UsersAdapter(this, users);
        user_list.setAdapter(originalAdapter);
        title_text.setText("Suggested Users");
    }
}
