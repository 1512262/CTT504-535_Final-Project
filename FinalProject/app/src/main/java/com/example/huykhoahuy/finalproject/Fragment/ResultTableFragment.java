package com.example.huykhoahuy.finalproject.Fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huykhoahuy.finalproject.Class.Lottery;
import com.example.huykhoahuy.finalproject.Class.LotteryCompany;
import com.example.huykhoahuy.finalproject.Other.ParseHostFile;
import com.example.huykhoahuy.finalproject.Other.RetrieveLotteryResult;
import com.example.huykhoahuy.finalproject.Other.RetrieveLotteryResultAndRenderToATable;
import com.example.huykhoahuy.finalproject.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;


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
    private LinearLayout linearLayout;
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

    private ArrayList<Integer> getListOfRow() {
        ArrayList<Integer> result = new ArrayList<Integer>();
        result.add(R.id.tv_lottery_special_prize);
        result.add(R.id.tv_lottery_1st_prize);
        result.add(R.id.tv_lottery_2nd_prize);
        result.add(R.id.tv_lottery_3rd_prize);
        result.add(R.id.tv_lottery_4th_prize);
        result.add(R.id.tv_lottery_5th_prize);
        result.add(R.id.tv_lottery_6th_prize);
        result.add(R.id.tv_lottery_7th_prize);
        result.add(R.id.tv_lottery_8th_prize);
        return result;
    }

    private int getProgressBarID() {
        return R.id.prb_loading;
    }

    private void btnQueryOnClick(View v) {
        linearLayout = (LinearLayout)mView.findViewById(R.id.lyt_table);
        final EditText etMyLotteryDate = (EditText)mView.findViewById(R.id.et_my_lottery_date);
        final TextView tvMyLotteryCompany = (TextView)mView.findViewById(R.id.tv_my_lottery_company);
        final ProgressBar progressBar = (ProgressBar)mView.findViewById(R.id.prb_loading);
        String lottery_date = etMyLotteryDate.getText().toString();
        String lottery_company = tvMyLotteryCompany.getText().toString();

        if(lottery_date.equals("") || lottery_company.equals(""))
        {
            Toast.makeText(v.getContext(),"Vui lòng nhập đầy đủ",Toast.LENGTH_SHORT).show();
        }
        else
        {
            String lottery_province_id = map_name_id.get(lottery_company);
            ArrayList<Integer> listOfRowViews = this.getListOfRow();
            int progressBarID = this.getProgressBarID();
            RetrieveLotteryResultAndRenderToATable retrieveLotteryResultAndRenderToATable
                    = new RetrieveLotteryResultAndRenderToATable(lottery_province_id, lottery_date,
                        getView(), listOfRowViews, progressBar,linearLayout);

            retrieveLotteryResultAndRenderToATable.execute();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ArrayAdapter<String> adapter;
        mView =inflater.inflate(R.layout.fragment_result_table, container, false);
        linearLayout = (LinearLayout)mView.findViewById(R.id.lyt_table);
        final EditText etMyLotteryDate = (EditText)mView.findViewById(R.id.et_my_lottery_date);
        final TextView tvMyLotteryCompany = (TextView)mView.findViewById(R.id.tv_my_lottery_company);

        linearLayout.setVisibility(View.INVISIBLE);

        final Button btnQuery = (Button)mView.findViewById(R.id.btn_query);
        btnQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasInternetConnection(v)) {
                    btnQueryOnClick(v);
                }
                else {
                    linearLayout.setVisibility(View.INVISIBLE);
                    Toast.makeText(v.getContext(), "Không có kết nối Internet!", Toast.LENGTH_SHORT).show();
                }

            }
        });

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
                        strMonth = (month<9)?"0"+String.valueOf(month+1):String.valueOf(month+1);
                        strDay = (day<10)?"0"+String.valueOf(day):String.valueOf(day);
                        String date = strDay +"-"+strMonth +"-"+String.valueOf(year);
                        etMyLotteryDate.setText(date);
                    }
                }, yy, mm, dd);
                datePicker.show();
            }
        });

        final SpinnerDialog spinnerDialog = new SpinnerDialog(getActivity(),lotterycompanynames,"Chọn Công ty Xổ số Kiến thiết",R.style.DialogAnimations_SmileWindow,"Đóng");
        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String s, int i) {
                tvMyLotteryCompany.setText(s);
            }
        });
//        ArrayAdapter<String> adapter;

        tvMyLotteryCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerDialog.showSpinerDialog();
            }
        });

        return mView;

    }

    private boolean hasInternetConnection(View v) {
        ConnectivityManager cm = (ConnectivityManager) v.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm != null
                && cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected();
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
