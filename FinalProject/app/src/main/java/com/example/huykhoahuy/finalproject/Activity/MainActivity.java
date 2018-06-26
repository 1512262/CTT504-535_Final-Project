package com.example.huykhoahuy.finalproject.Activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.huykhoahuy.finalproject.Class.LotteryViewModel;
import com.example.huykhoahuy.finalproject.Fragment.AboutFragment;
import com.example.huykhoahuy.finalproject.Fragment.HistoryFragment;
import com.example.huykhoahuy.finalproject.Fragment.HomeFragment;
import com.example.huykhoahuy.finalproject.Fragment.ListFragment;
import com.example.huykhoahuy.finalproject.Fragment.LotteryCenterFragment;
import com.example.huykhoahuy.finalproject.Fragment.LotteryCompanyFragment;
import com.example.huykhoahuy.finalproject.Fragment.MapFragment;
import com.example.huykhoahuy.finalproject.Fragment.MyResultFragment;
import com.example.huykhoahuy.finalproject.R;
import com.example.huykhoahuy.finalproject.Fragment.ResultTableFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        HomeFragment.OnFragmentInteractionListener,
        HistoryFragment.OnFragmentInteractionListener,
        ListFragment.OnFragmentInteractionListener,
        AboutFragment.OnFragmentInteractionListener,
        LotteryCompanyFragment.OnFragmentInteractionListener,
        LotteryCenterFragment.OnFragmentInteractionListener,
        MapFragment.OnFragmentInteractionListener,
        MyResultFragment.OnFragmentInteractionListener,
        ResultTableFragment.OnFragmentInteractionListener {

    private FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                boolean isFirstStart = getPrefs.getBoolean("firstStart",true);

                if(isFirstStart)
                {
                    startActivity(new Intent(MainActivity.this, Intro.class));
                    SharedPreferences.Editor e = getPrefs.edit();
                    e.putBoolean("firstStart",false);
                    e.apply();

                }
            }
        });

        thread.start();

        Fragment fragment = null;
        Class fragmentClass = null;
        fragmentClass = HomeFragment.class;
        setTitle(getString(R.string.homescreen));
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

//        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).addToBackStack("Fragment").commit();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        LotteryViewModel.setInstance(ViewModelProviders.of(this).get(LotteryViewModel.class));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        getFragmentManager().popBackStack();
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Fragment fragment = null;
        Class fragmentClass = null;

        if (id == R.id.nav_home) {
            fragmentClass = HomeFragment.class;
            setTitle(getString(R.string.homescreen));
        } else if (id == R.id.nav_history) {
            fragmentClass = HistoryFragment.class;
            setTitle(getString(R.string.history));
        } else if (id == R.id.nav_list) {
            fragmentClass = ListFragment.class;
            setTitle(getString(R.string.list));
        } else if (id == R.id.nav_about) {
            fragmentClass = AboutFragment.class;
            setTitle(getString(R.string.aboutus));
        }
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

//        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).addToBackStack("Fragment").commit();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }


    @Override
    public void onFragmentInteraction(Uri uri) {
    }

}
