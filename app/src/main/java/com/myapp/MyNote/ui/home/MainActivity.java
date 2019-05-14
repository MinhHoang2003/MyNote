package com.myapp.MyNote.ui.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.myapp.MyNote.R;
import com.myapp.MyNote.ui.recycle.RecycleBinFragment;


import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;

    private Map<String, String> mTitleMap;
    private static final String TAG_HOME = "home";
    private static final String TAG_REMINDER = "reminder";
    private static final String TAG_LABEL = "label";
    private static final String TAG_RECYCLE_BIN = "recycler bin";
    private static final String TAG_SETTING = "setting";
    private static final String TAG_ABOUT = "about author";
    private String mCurrentTag = TAG_HOME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initNavigationView();
        initToolbarTitle();
        loadFragment(mCurrentTag);
    }

    private void initView() {
        mToolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_navigation_black_24dp);
        mNavigationView = findViewById(R.id.navigation_view);
        mDrawerLayout = findViewById(R.id.layout_drawer);
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(mTitleMap.get(mCurrentTag));
    }

    private Fragment getFragment(String tag) {
        Fragment fragment = null;
        switch (tag) {
            case TAG_REMINDER:
                // Todo: create reminder fragment
                break;
            case TAG_LABEL:
                // Todo: create label fragment
                break;
            case TAG_RECYCLE_BIN:
                fragment = new RecycleBinFragment();
                break;
            case TAG_SETTING:
                // Todo: create setting fragment
                break;
            case TAG_ABOUT:
                // Todo: create about author fragment
                break;
            default:
                fragment = new HomeFragment();
        }
        return fragment;
    }

    private void initNavigationView() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(mNavigationView);
            }
        });
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menu_note:
                        mCurrentTag = TAG_HOME;
                        break;
                    case R.id.menu_reminder:
                        mCurrentTag = TAG_REMINDER;
                        break;
                    case R.id.menu_label:
                        mCurrentTag = TAG_LABEL;
                        break;
                    case R.id.menu_recycle_bin:
                        mCurrentTag = TAG_RECYCLE_BIN;
                        break;
                    case R.id.menu_setting:
                        mCurrentTag = TAG_SETTING;
                        break;
                    case R.id.menu_about:
                        mCurrentTag = TAG_ABOUT;
                        break;
                    default:
                        mCurrentTag = TAG_HOME;
                        break;
                }
                loadFragment(mCurrentTag);
                return true;
            }
        });
    }

    private void loadFragment(String tag) {
        Fragment fragment = getFragment(tag);
        if (fragment == null) return;
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_layout, fragment, tag);
        fragmentTransaction.commit();
        setToolbarTitle();
        mDrawerLayout.closeDrawer(mNavigationView);
    }

    private void initToolbarTitle() {
        mTitleMap = new HashMap<>();
        mTitleMap.put(TAG_HOME, getResources().getString(R.string.title_note));
        mTitleMap.put(TAG_REMINDER, getResources().getString(R.string.title_reminder));
        mTitleMap.put(TAG_LABEL, getResources().getString(R.string.title_label));
        mTitleMap.put(TAG_RECYCLE_BIN, getResources().getString(R.string.title_recycle_bin));
        mTitleMap.put(TAG_SETTING, getResources().getString(R.string.title_setting));
        mTitleMap.put(TAG_ABOUT, getResources().getString(R.string.title_about_author));
    }
}
