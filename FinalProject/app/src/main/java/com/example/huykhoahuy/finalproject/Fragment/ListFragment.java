package com.example.huykhoahuy.finalproject.Fragment;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.huykhoahuy.finalproject.Class.LotteryCompany;
import com.example.huykhoahuy.finalproject.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment implements
        BottomNavigationView.OnNavigationItemSelectedListener,
        LotteryCompanyFragment.OnFragmentInteractionListener,
        MapFragment.OnFragmentInteractionListener,
        LotteryCenterFragment.OnFragmentInteractionListener{

    private OnFragmentInteractionListener mListener;

    public ListFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ListFragment newInstance(String param1, String param2) {
        ListFragment fragment = new ListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LotteryCompanyFragment lotteryCompanyFragment = new LotteryCompanyFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.flContent_list, lotteryCompanyFragment).commit();
        getActivity().setTitle(getString(R.string.lotterycompanylist));
        BottomNavigationView bottomNavigationView = (BottomNavigationView)getView().findViewById(R.id.navigation_list);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        if (id == R.id.navigation_lottery_list_company) {
            LotteryCompanyFragment lotteryCompanyFragment = new LotteryCompanyFragment();
            transaction.replace(R.id.flContent_list, lotteryCompanyFragment).commit();
            getActivity().setTitle(getString(R.string.lotterycompanylist));
        }else if (id == R.id.navigation_lottery_list_center) {
            LotteryCenterFragment lotteryCenterFragment = new LotteryCenterFragment();
            transaction.replace(R.id.flContent_list,lotteryCenterFragment).commit();
            getActivity().setTitle(getString(R.string.lotterycenterlist));
        } else if (id == R.id.navigation_map) {
            MapFragment mapFragment = new MapFragment();
            transaction.replace(R.id.flContent_list,mapFragment).commit();
            getActivity().setTitle(getString(R.string.mapfragment));
            // chỉ đường cho lottery company
            if (target != null) {
                mapFragment.FindWayToLotteryCompany(target);
                target = null;
            }
            // kết thúc chỉ đường cho lottery company
        }
        return true;
    }



    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    // chỉ đường cho lottery company
    LotteryCompany target = null;
    public void FindWayToLotteryCompany(LotteryCompany lotteryCompany) {
        target = lotteryCompany;
        BottomNavigationView bottomNavigationView = (BottomNavigationView)getView().findViewById(R.id.navigation_list);
        bottomNavigationView.setSelectedItemId(R.id.navigation_map);
    }
    // kết thúc chỉ đường cho lottery company
}
