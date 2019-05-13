package com.sun.colornotetaking.ui.main;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.sun.colornotetaking.R;

public class MainActivity extends AppCompatActivity {

    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;

    private static int sNavigationIndex = 0;
    private static final String TAG_HOME = "home";
    private static final String TAG_REMINDER = "reminder";
    private static final String TAG_LABEL = "label";
    private static final String TAG_RECYCLE_BIN = "recycler bin";
    private static final String TAG_SETTING = "setting";
    private static final String TAG_ABOUT = "about author";
    private static String CURRENT_TAG = TAG_HOME;
    private String[] mToolbarTitle;

    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initNavigationView();
        if (savedInstanceState == null) {
            CURRENT_TAG = TAG_HOME;
            sNavigationIndex = 0;
            loadFragment();
        }
    }

    private void initView() {
        mToolbarTitle = getResources().getStringArray(R.array.title_toolbar);
        mToolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_navigation_black_24dp);
        mHandler = new Handler();
        mNavigationView = findViewById(R.id.navigation_view);
        mDrawerLayout = findViewById(R.id.layout_drawer);
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(mToolbarTitle[sNavigationIndex]);
    }

    private Fragment getFragment() {
        Fragment fragment = null;
        switch (CURRENT_TAG) {
            case TAG_REMINDER:
                // Todo: create reminder fragment
                break;
            case TAG_LABEL:
                // Todo: create label fragment
                break;
            case TAG_RECYCLE_BIN:
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
                        CURRENT_TAG = TAG_HOME;
                        sNavigationIndex = 0;
                        break;
                    case R.id.menu_reminder:
                        CURRENT_TAG = TAG_REMINDER;
                        sNavigationIndex = 1;
                        break;
                    case R.id.menu_label:
                        CURRENT_TAG = TAG_LABEL;
                        sNavigationIndex = 2;
                        break;
                    case R.id.menu_recycle_bin:
                        CURRENT_TAG = TAG_RECYCLE_BIN;
                        sNavigationIndex = 3;
                        break;
                    case R.id.menu_setting:
                        CURRENT_TAG = TAG_SETTING;
                        sNavigationIndex = 4;
                        break;
                    case R.id.menu_about:
                        CURRENT_TAG = TAG_ABOUT;
                        sNavigationIndex = 5;
                        break;
                    default:
                        CURRENT_TAG = TAG_HOME;
                        sNavigationIndex = 0;
                        break;
                }
                loadFragment();
                return true;
            }
        });
    }

    private void loadFragment() {
        Runnable loadFragmentRunnable = new Runnable() {
            @Override
            public void run() {
                Fragment fragment = getFragment();
                if (fragment == null) return;
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container_layout, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };
        if (mHandler != null) {
            mHandler.post(loadFragmentRunnable);
            setToolbarTitle();
            mDrawerLayout.closeDrawer(mNavigationView);
        }
    }
}
