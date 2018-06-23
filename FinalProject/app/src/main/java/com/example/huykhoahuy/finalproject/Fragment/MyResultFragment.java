package com.example.huykhoahuy.finalproject.Fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huykhoahuy.finalproject.Class.Lottery;
import com.example.huykhoahuy.finalproject.Class.LotteryCompany;
import com.example.huykhoahuy.finalproject.Class.LotteryResult;
import com.example.huykhoahuy.finalproject.Other.ParseHostFile;
import com.example.huykhoahuy.finalproject.Other.RetrieveLotteryResult;
import com.example.huykhoahuy.finalproject.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyResultFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyResultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyResultFragment extends Fragment implements View.OnClickListener {
    private OnFragmentInteractionListener mListener;
    private FloatingActionButton fabCreate;
    private View view;
    private ArrayList<LotteryCompany> lotteryCompanies;
    private Lottery lottery;
    private  ArrayList<String> lotterycompanynames = new ArrayList<String>();
    private Map<String,String> map_name_id=new HashMap<String,String>();
    private RetrieveLotteryResult retrieveLotteryResult;

    public MyResultFragment() {
    }


    // TODO: Rename and change types and number of parameters
    public static MyResultFragment newInstance(String param1, String param2) {
        MyResultFragment fragment = new MyResultFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fabCreate = (FloatingActionButton)view.findViewById(R.id.floatingActionButton);
        TextView tvMyResultLotteryComapany = (TextView)view.findViewById(R.id.tv__my_result_lottery_company);
        TextView tvMyResultLotteryDate = (TextView)view.findViewById(R.id.tv_my_result_lottery_date);
        TextView tvMyResultLotteryCode = (TextView)view.findViewById(R.id.tv_my_result_lottery_code);
        TextView tvMyResultLotteryPrize = (TextView)view.findViewById(R.id.tv_my_result_lottery_prize);

        fabCreate.setOnClickListener(this);
        initData();
    }

    public void initData()
    {
        ParseHostFile parseHostFile = new ParseHostFile(getContext(),R.xml.main_host);
        lotteryCompanies = parseHostFile.lotteryCompanies;
        for(LotteryCompany company: lotteryCompanies)
        {
            lotterycompanynames.add(company.getName());
            map_name_id.put(company.getName(),company.getProvince_id());
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_my_result, container, false);
        return view;
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
    @Override
    public void onClick(View v) {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(view.getContext());
        View mView = getLayoutInflater().inflate(R.layout.dialog_input_lottery_info,null);
        final ProgressBar progressBarForm = (ProgressBar)mView.findViewById(R.id.prb_loading_form);
        final EditText etLotteryCode = (EditText)mView.findViewById(R.id.et_lottery_code);
        final EditText etLotteryDate = (EditText) mView.findViewById(R.id.et_lottery_date);
        final AutoCompleteTextView tvLotteryCompany = (AutoCompleteTextView)mView.findViewById(R.id.tv_lottery_company);
        ArrayAdapter<String> adapter;


        Button btnCheck = (Button)mView.findViewById(R.id.btn_check);
        Button btnAutoFill = (Button)mView.findViewById(R.id.btn_autofill);

        etLotteryDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int yy = calendar.get(Calendar.YEAR);
                int mm = calendar.get(Calendar.MONTH);
                int dd = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(view.getContext(),new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        String strDay = null;
                        String strMonth = null;
                        strMonth = (month<9)?"0"+String.valueOf(month+1):String.valueOf(month+1);
                        strDay = (day<10)?"0"+String.valueOf(day):String.valueOf(day);
                        String date = strDay +"-"+strMonth +"-"+String.valueOf(year);
                        etLotteryDate.setText(date);
                    }
                }, yy, mm, dd);
                datePicker.show();
            }
        });



        adapter = new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_list_item_1,lotterycompanynames);
        tvLotteryCompany.setAdapter(adapter);
        tvLotteryCompany.setThreshold(1);

        mBuilder.setView(mView);

        btnAutoFill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"Tính năng này tạm thời chưa phát triển hoàn thiện",Toast.LENGTH_LONG).show();
            }
        });
        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasInternetConnection(v)) {
                    String lottery_date = etLotteryDate.getText().toString();
                    String lottery_company = tvLotteryCompany.getText().toString();
                    String lottery_code = etLotteryCode.getText().toString();
                    String lottery_province_id;
                    if(lottery_code.equals("") || lottery_date.equals("") || lottery_company.equals(""))
                    {
                        Toast.makeText(v.getContext(),"Vui lòng nhập đầy đủ",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        lottery_province_id = map_name_id.get(lottery_company);
                        lottery = new Lottery(lottery_company,lottery_date,lottery_province_id,lottery_code);
                        retrieveLotteryResult = new RetrieveLotteryResult(lottery, getView(),progressBarForm);
                        retrieveLotteryResult.execute();
                    }
                }
                else {
                    Toast.makeText(v.getContext(), "Không có kết nối Internet!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        AlertDialog dialog = mBuilder.create();
        dialog.show();
        doKeepDialog(dialog);
    }

    private boolean hasInternetConnection(View v) {
        ConnectivityManager cm = (ConnectivityManager) v.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm != null
                && cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected();
    }

    private static void doKeepDialog(Dialog dialog){
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
