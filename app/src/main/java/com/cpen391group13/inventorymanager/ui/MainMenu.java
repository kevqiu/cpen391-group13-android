package com.cpen391group13.inventorymanager.ui;

import android.Manifest;
import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.cpen391group13.inventorymanager.R;
import com.google.firebase.messaging.FirebaseMessaging;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainMenu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.drawer_layout) DrawerLayout drawer;
    @BindView(R.id.nav_view) NavigationView navigationView;

    Fragment nextFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        getFragmentManager().beginTransaction().replace(R.id.main_layout, new OverviewFragment()).commit();

        // get location permissions for Google maps
        ActivityCompat.requestPermissions(this,
            new String[] {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
            }, 1);

        // subscribe to the 'sort' topic for FCM
        FirebaseMessaging.getInstance().subscribeToTopic("sort");

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        // if we access the MainMenu from a notification, send to ItemFragment with cycle data
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            ItemFragment itemFragment = new ItemFragment();
            Bundle itemBundle = new Bundle();

            String startTime = bundle.getString("start_time");
            String endTime = bundle.getString("end_time");

            if (startTime != null && endTime != null) {
                String timeRange = startTime + "," + endTime;
                itemBundle.putString("time_range", timeRange);
                itemFragment.setArguments(itemBundle);

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_layout, itemFragment)
                        .addToBackStack(null)
                        .commit();
            }
        }

        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override public void onDrawerSlide(View drawerView, float slideOffset) {}
            @Override public void onDrawerOpened(View drawerView) {}
            @Override public void onDrawerStateChanged(int newState) {}

            @Override
            public void onDrawerClosed(View drawerView) {
                //Set your new fragment here
                if (nextFragment != null) {
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_layout, nextFragment)
                            .addToBackStack(null)
                            .commit();
                    nextFragment = null;
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // instantiate a fragment
        Fragment fragment = null;
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.overview) {
            toolbar.setTitle("Overview");
            fragment = new OverviewFragment();
        }
        else if (id == R.id.nav_warehouses) {
            fragment = new WarehouseFragment();
        }
        else if (id == R.id.nav_history) {
            fragment = new HistoryFragment();
        }
        else if (id == R.id.nav_controls) {
            fragment = new ControlsFragment();
        }
        else if (id == R.id.nav_scan) {
            fragment = new CaptureFragment();
        }
        else if (id == R.id.nav_settings) {
            fragment = new SettingsFragment();
        }

        if (fragment != null) {
            drawer.closeDrawers();

            nextFragment = fragment;
        }

        return true;
    }


}
