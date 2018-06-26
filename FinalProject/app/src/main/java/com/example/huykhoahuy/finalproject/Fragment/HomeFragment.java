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
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.huykhoahuy.finalproject.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements
        BottomNavigationView.OnNavigationItemSelectedListener,
        MyResultFragment.OnFragmentInteractionListener,
        ResultTableFragment.OnFragmentInteractionListener{

    private OnFragmentInteractionListener mListener;
    private View view;
    public HomeFragment() {
    }


    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MyResultFragment myResultFragment = new MyResultFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.flContent_home,myResultFragment).commit();
        getActivity().setTitle(getString(R.string.mynewresult));
        BottomNavigationView bottomNavigationView = (BottomNavigationView)getView().findViewById(R.id.navigation_list);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        if (id == R.id.navigation_result) {
            MyResultFragment myResultFragment = new MyResultFragment();
            transaction.replace(R.id.flContent_home,myResultFragment).commit();
            getActivity().setTitle(getString(R.string.mynewresult));
        } else if (id == R.id.navigation_more) {
            ResultTableFragment resultTableFragment = new ResultTableFragment();
            transaction.replace(R.id.flContent_home,resultTableFragment).commit();
            getActivity().setTitle(getString(R.string.moreinfo));
            // tra cuu them
            if (latestLottery != null) {
                resultTableFragment.LookUpMore(latestLottery);
                latestLottery = null;
            }
            //ket thuc tra cuu them
        }

        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    // Cai dat tra cuu them
    Pair<String,String> latestLottery = null;
    public void LookUpMore(String company_Name, String date) {
        if (company_Name != "" && date != "") {
            latestLottery = new Pair<>(company_Name,date);
            BottomNavigationView bottomNavigationView = (BottomNavigationView)getView().findViewById(R.id.navigation_list);
            bottomNavigationView.setSelectedItemId(R.id.navigation_more);
        }
    }
    // Ket thuc cai dat tra cuu them
}
