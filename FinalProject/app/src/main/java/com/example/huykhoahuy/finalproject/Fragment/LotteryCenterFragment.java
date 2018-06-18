package com.example.huykhoahuy.finalproject.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huykhoahuy.finalproject.Adapter.LotteryCompanyAdapter;
import com.example.huykhoahuy.finalproject.Class.LotteryCompany;
import com.example.huykhoahuy.finalproject.Class.ParseHostFile;
import com.example.huykhoahuy.finalproject.Other.SwipeController;
import com.example.huykhoahuy.finalproject.Other.SwipeControllerActions;
import com.example.huykhoahuy.finalproject.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LotteryCenterFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LotteryCenterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LotteryCenterFragment extends Fragment{

    private OnFragmentInteractionListener mListener;
    private ArrayList<LotteryCompany> lotteryCenters;
    View mView;
    private LotteryCompanyAdapter adapter;
    private SwipeController swipeController;
    public LotteryCenterFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static LotteryCenterFragment newInstance(String param1, String param2) {
        LotteryCenterFragment fragment = new LotteryCenterFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView= inflater.inflate(R.layout.fragment_lottery_center, container, false);
        initList();
        TextView tvName = mView.findViewById(R.id.tv_lottery_company_name);
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
        ParseHostFile parseHostFile = new ParseHostFile(getContext(),R.xml.hosts);
        lotteryCenters = parseHostFile.lotteryCompanies;
        adapter = new LotteryCompanyAdapter(lotteryCenters,mView.getContext());
        RecyclerView recyclerView = (RecyclerView)mView.findViewById(R.id.my_lottery_center_list);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mView.getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        LotteryCompanyAdapter lotteryCompanyAdapter = new LotteryCompanyAdapter(lotteryCenters,mView.getContext());
        recyclerView.setAdapter(adapter);

        swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {
                // sửa chỗ chỉ đường
                ListFragment listFragment = (ListFragment)getParentFragment();
                listFragment.FindWayToLotteryCompany(lotteryCenters.get(position));
                // kết thúc sửa chỗ chỉ đường
            }

            @Override
            public void onLeftClicked(int position) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + lotteryCenters.get(position).getPhone()));
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
