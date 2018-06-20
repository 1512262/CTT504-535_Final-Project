package com.example.huykhoahuy.finalproject.Fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.huykhoahuy.finalproject.Class.LotteryCompany;
import com.example.huykhoahuy.finalproject.Other.ParseHostFile;
import com.example.huykhoahuy.finalproject.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ResultTableFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ResultTableFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResultTableFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    private View mView;
    private Map<String,String> map_name_id=new HashMap<String,String>();
    private ArrayList<LotteryCompany> lotteryCompanies;
    private ArrayList<String> lotterycompanynames = new ArrayList<String>();
    public ResultTableFragment() {
    }


    // TODO: Rename and change types and number of parameters
    public static ResultTableFragment newInstance(String param1, String param2) {
        ResultTableFragment fragment = new ResultTableFragment();
        return fragment;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
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
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ArrayAdapter<String> adapter;
        mView =inflater.inflate(R.layout.fragment_result_table, container, false);
        final EditText etMyLotteryDate = (EditText)mView.findViewById(R.id.et_my_lottery_date);
        final AutoCompleteTextView tvMyLotteryCompany = (AutoCompleteTextView)mView.findViewById(R.id.tv_my_lottery_company);

        etMyLotteryDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int yy = calendar.get(Calendar.YEAR);
                int mm = calendar.get(Calendar.MONTH);
                int dd = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(mView.getContext(),new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        String strDay = null;
                        String strMonth = null;
                        strMonth = (month<10)?"0"+String.valueOf(month+1):String.valueOf(month+1);
                        strDay = (day<10)?"0"+String.valueOf(day):String.valueOf(day);
                        String date = strDay +"-"+strMonth +"-"+String.valueOf(year);
                        etMyLotteryDate.setText(date);
                    }
                }, yy, mm, dd);
                datePicker.show();
            }
        });

        adapter = new ArrayAdapter<String>(mView.getContext(),android.R.layout.simple_list_item_1,lotterycompanynames);
        tvMyLotteryCompany.setAdapter(adapter);
        tvMyLotteryCompany.setThreshold(1);

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
