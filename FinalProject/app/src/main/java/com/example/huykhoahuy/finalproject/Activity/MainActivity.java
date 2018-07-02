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
import android.util.Pair;
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

import java.util.Observable;
import java.util.Observer;

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
        ResultTableFragment.OnFragmentInteractionListener, Observer {

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

        // thêm history look up more
        historyLookUpMoreObservable = new HistoryLookUpMoreObservable();
        historyLookUpMoreObservable.addObserver(this);
        // kết thúc thêm history look up more
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

        // thêm history look up more
        if (id == R.id.nav_home && lookUpLottery != null) {
            HomeFragment homeFragment = (HomeFragment)fragment;
            homeFragment.callType = 1;
            homeFragment.LookUpMore(lookUpLottery.first, lookUpLottery.second);
            lookUpLottery = null;
        }
        // kết thúc thêm history look up more

        return true;

    }


    @Override
    public void onFragmentInteraction(Uri uri) {
    }

    // thêm history look up more
    private Pair<String,String> lookUpLottery = null;
    public HistoryLookUpMoreObservable historyLookUpMoreObservable = null;
    @Override
    public void update(Observable o, Object arg) {
        lookUpLottery = (Pair<String,String>)arg;
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_home);
        onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_home));
    }
    public class HistoryLookUpMoreObservable extends Observable {
        public void historyLookUpMore(String company_Name, String date) {
            if (company_Name != "" && date != "") {
                Pair<String,String> data = new Pair<>(company_Name,date);
                setChanged();
                notifyObservers(data);
            }
        }
    }
    // kết thúc thêm history look up more
}
