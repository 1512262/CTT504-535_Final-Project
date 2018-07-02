package com.example.huykhoahuy.finalproject.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huykhoahuy.finalproject.Activity.MainActivity;
import com.example.huykhoahuy.finalproject.Adapter.HistoryAdapter;
import com.example.huykhoahuy.finalproject.Adapter.LotteryCompanyAdapter;
import com.example.huykhoahuy.finalproject.Class.Lottery;
import com.example.huykhoahuy.finalproject.Class.LotteryViewModel;
import com.example.huykhoahuy.finalproject.Interface.ItemClickListener;
import com.example.huykhoahuy.finalproject.Other.ParseHostFile;
import com.example.huykhoahuy.finalproject.Other.SwipeController;
import com.example.huykhoahuy.finalproject.Other.SwipeControllerActions;
import com.example.huykhoahuy.finalproject.R;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment implements ItemClickListener {

    private OnFragmentInteractionListener mListener;
    private View mView;
    private AlertDialog dialog;
    ArrayList<Lottery> lotteryArrayList;
    private HistoryAdapter adapter;
    public HistoryFragment() {
    }



    // TODO: Rename and change types and number of parameters
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    public static HistoryFragment newInstance() {
        HistoryFragment fragment = new HistoryFragment();
        return fragment;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_in_history, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete_all:
                showDeleteAllDialog();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
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
        LotteryViewModel lotteryViewModel = LotteryViewModel.getInstance();
        adapter = new HistoryAdapter(mView.getContext());
        RecyclerView recyclerView = (RecyclerView)mView.findViewById(R.id.history_list);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mView.getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setClickListener(this);

        lotteryViewModel.getAllLotteries().observe(this, new Observer<List<Lottery>>() {
            @Override
            public void onChanged(@Nullable List<Lottery> lotteryList) {
                lotteryArrayList = new ArrayList<>(lotteryList);
                adapter.setLotteries(lotteryArrayList);
            }
        });
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView= inflater.inflate(R.layout.fragment_history, container, false);
        initList();
        return  mView;
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
    public void onClick(View view, final int position) {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(view.getContext());
        View mView = getLayoutInflater().inflate(R.layout.lottery_result_view,null);
        mBuilder.setView(mView);
        FloatingActionMenu fabMore = (FloatingActionMenu) mView.findViewById(R.id.fab_more);
        FloatingActionButton fabDelete = (FloatingActionButton)mView.findViewById(R.id.fab_delete);
        FloatingActionButton fabLookupinHistory = (FloatingActionButton)mView.findViewById(R.id.fab_lookup_in_history);
        fabMore.setVisibility(View.INVISIBLE);
        final TextView tvMyResultLotteryComapany =(TextView)mView.findViewById(R.id.tv_my_result_lottery_company);
        final TextView tvMyResultLotteryDate =(TextView)mView.findViewById(R.id.tv_my_result_lottery_date);
        final TextView tvMyResultLotteryCode =(TextView)mView.findViewById(R.id.tv_my_result_lottery_code);
        final TextView tvMyResultLotteryPrize =(TextView)mView.findViewById(R.id.tv_my_result_lottery_prize);
        final TextView tvMyResultLotteryProvinceID =(TextView)mView.findViewById(R.id.tv_my_result_lottery_province_id);
        final TextView tvMyResultLotteryCheckDate =(TextView)mView.findViewById(R.id.tv_my_result_lottery_check_date);

        tvMyResultLotteryComapany.setText(lotteryArrayList.get(position).getLottery_Company_Name());
        tvMyResultLotteryDate.setText(lotteryArrayList.get(position).getLottery_Date());
        tvMyResultLotteryCode.setText(lotteryArrayList.get(position).Lottery_Code);
        tvMyResultLotteryPrize.setText(lotteryArrayList.get(position).getLottery_Prize());
        tvMyResultLotteryProvinceID.setText(lotteryArrayList.get(position).getLottery_Province_ID().toUpperCase());
        tvMyResultLotteryCheckDate.setText(lotteryArrayList.get(position).getLottery_Check_Date()+" "+lotteryArrayList.get(position).getLottery_Check_Time());

        Bitmap bitmap = lotteryArrayList.get(position).get_Lottery_Image();
        ImageView ivLotteryImageResult = (ImageView)view.findViewById(R.id.iv_lottery_image_result);
        if (bitmap != null) {
            ivLotteryImageResult.setImageBitmap(bitmap);
        } else ivLotteryImageResult.setImageResource(R.drawable.noimage);

        fabDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteDialog(position);
                dialog.dismiss();
            }
        });
        fabLookupinHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.hide();
                MainActivity mainActivity = (MainActivity)getActivity();
                mainActivity.historyLookUpMoreObservable.historyLookUpMore(tvMyResultLotteryComapany.getText().toString(), tvMyResultLotteryDate.getText().toString());
            }
        });
        dialog = mBuilder.create();
        dialog.show();
    }

    public void showDeleteDialog(final int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(mView.getContext());
        builder.setTitle("Xóa tìm kiếm");
        builder.setMessage("Bạn có thật sự muốn xóa không");
        builder.setCancelable(false);
        builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(mView.getContext(), "OK. Hiểu rồi", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Xóa đi", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                LotteryViewModel.getInstance().deleteLottery(lotteryArrayList.get(position));
                Toast.makeText(mView.getContext(), "OK. Đã xóa", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
    public void showDeleteAllDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(mView.getContext());
        builder.setTitle("Xóa tìm kiếm");
        builder.setMessage("Bạn có thật sự muốn xóa toàn bộ không");
        builder.setCancelable(false);
        builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(mView.getContext(), "OK. Hiểu rồi", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Xóa hết đi", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                LotteryViewModel.getInstance().deleteAllLotteries();
                Toast.makeText(mView.getContext(), "OK. Đã xóa toàn bộ", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

