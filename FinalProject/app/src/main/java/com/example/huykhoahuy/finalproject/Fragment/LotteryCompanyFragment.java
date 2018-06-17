package com.example.huykhoahuy.finalproject.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.huykhoahuy.finalproject.Adapter.LotteryCompanyAdapter;
import com.example.huykhoahuy.finalproject.Class.LotteryCompany;
import com.example.huykhoahuy.finalproject.Class.ParseHostFile;
import com.example.huykhoahuy.finalproject.Other.SwipeControllerActions;
import com.example.huykhoahuy.finalproject.Other.SwipeController;
import com.example.huykhoahuy.finalproject.R;

import java.util.ArrayList;


public class LotteryCompanyFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match


    private OnFragmentInteractionListener mListener;
    private ArrayList<LotteryCompany> lotteryCompanies;
    View mView;
    private LotteryCompanyAdapter adapter;
    private SwipeController swipeController;
    public LotteryCompanyFragment() {
    }

    // TODO: Rename and change types and number of parameters
    public static LotteryCompanyFragment newInstance() {
        LotteryCompanyFragment fragment = new LotteryCompanyFragment();
        return fragment;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
    public void initList()
    {
        ParseHostFile parseHostFile = new ParseHostFile(getContext(),R.xml.main_host);
        lotteryCompanies = parseHostFile.lotteryCompanies;
        adapter = new LotteryCompanyAdapter(lotteryCompanies,mView.getContext());
        RecyclerView recyclerView = (RecyclerView)mView.findViewById(R.id.my_lottery_company_list);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mView.getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        LotteryCompanyAdapter lotteryCompanyAdapter = new LotteryCompanyAdapter(lotteryCompanies,mView.getContext());
        recyclerView.setAdapter(adapter);

        swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {
            }

            @Override
            public void onLeftClicked(int position) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + lotteryCompanies.get(position).getPhone()));
                startActivity(intent);
            }
        });

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(recyclerView);

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });


    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView= inflater.inflate(R.layout.fragment_lottery_company, container, false);
        initList();
        return mView;
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


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
