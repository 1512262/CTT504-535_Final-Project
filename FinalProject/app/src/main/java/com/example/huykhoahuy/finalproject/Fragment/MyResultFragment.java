package com.example.huykhoahuy.finalproject.Fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.arch.lifecycle.Observer;
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
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huykhoahuy.finalproject.Class.Lottery;
import com.example.huykhoahuy.finalproject.Class.LotteryCompany;
import com.example.huykhoahuy.finalproject.Class.LotteryViewModel;
import com.example.huykhoahuy.finalproject.Other.ParseHostFile;
import com.example.huykhoahuy.finalproject.Other.RetrieveLotteryResult;
import com.example.huykhoahuy.finalproject.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;


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
    private com.github.clans.fab.FloatingActionButton fabCreate;
    private FloatingActionButton fabDelete;
    private FloatingActionButton fabLookupinHistory;
    private com.github.clans.fab.FloatingActionButton fabLookup;
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
        fabCreate = (com.github.clans.fab.FloatingActionButton) view.findViewById(R.id.fab_new_checking);
        fabLookup = (com.github.clans.fab.FloatingActionButton) view.findViewById(R.id.fab_lookup);
        fabLookupinHistory = (FloatingActionButton)view.findViewById(R.id.fab_lookup_in_history);
        fabDelete = (FloatingActionButton)view.findViewById(R.id.fab_delete);
        final TextView tvMyResultLotteryComapany = (TextView)view.findViewById(R.id.tv_my_result_lottery_company);
        final TextView tvMyResultLotteryDate = (TextView)view.findViewById(R.id.tv_my_result_lottery_date);
        final TextView tvMyResultLotteryCode = (TextView)view.findViewById(R.id.tv_my_result_lottery_code);
        final TextView tvMyResultLotteryPrize = (TextView)view.findViewById(R.id.tv_my_result_lottery_prize);

        LotteryViewModel.getInstance().getAllLotteries().observe(this, new Observer<List<Lottery>>() {
            @Override
            public void onChanged(@Nullable List<Lottery> lotteries) {
                if (lotteries.size() < 1) return;
                Lottery bestLottery = lotteries.get(0);
                for (int i = 1; i < lotteries.size(); i++) {
                    Lottery tmpLottery = lotteries.get(i);
                    try {
                        if (new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
                                .parse(tmpLottery.getLottery_Check_Date()+" "+tmpLottery.getLottery_Check_Time())
                                .after(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
                                        .parse(bestLottery.getLottery_Check_Date()+" "+bestLottery.getLottery_Check_Time())))
                            bestLottery = tmpLottery;
                    } catch (ParseException e) {
                        return;
                    }
                }
                tvMyResultLotteryComapany.setText(bestLottery.getLottery_Company_Name());
                tvMyResultLotteryDate.setText(bestLottery.getLottery_Date());
                tvMyResultLotteryCode.setText(bestLottery.Lottery_Code);
                tvMyResultLotteryPrize.setText(bestLottery.getLottery_Prize());
            }
        });

        fabDelete.setVisibility(View.INVISIBLE);
        fabLookupinHistory.setVisibility(View.INVISIBLE);
        fabCreate.setOnClickListener(this);
        fabLookup.setOnClickListener(this);
        initData();
    }

    public void initData() {
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
        if(v.getId()==R.id.fab_new_checking){
            Checking();
        }
        else if(v.getId()==R.id.fab_lookup){
            TextView tvMyResultLotteryComapany = (TextView)view.findViewById(R.id.tv_my_result_lottery_company);
            TextView tvMyResultLotteryDate = (TextView)view.findViewById(R.id.tv_my_result_lottery_date);
            HomeFragment homeFragment = (HomeFragment)getParentFragment();
            homeFragment.LookUpMore(tvMyResultLotteryComapany.getText().toString(), tvMyResultLotteryDate.getText().toString());
        }

    }

    private void Checking() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(view.getContext());
        View mView = getLayoutInflater().inflate(R.layout.dialog_input_lottery_info,null);
        final ProgressBar progressBarForm = (ProgressBar)mView.findViewById(R.id.prb_loading_form);
        final EditText etLotteryCode = (EditText)mView.findViewById(R.id.et_lottery_code);
        final EditText etLotteryDate = (EditText) mView.findViewById(R.id.et_lottery_date);
        final TextView tvLotteryCompany = (TextView)mView.findViewById(R.id.tv_lottery_company);
        final SpinnerDialog spinnerDialog = new SpinnerDialog(getActivity(),lotterycompanynames,"Chọn Công ty Xổ số Kiến thiết",R.style.DialogAnimations_SmileWindow,"Đóng");
        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String s, int i) {
                tvLotteryCompany.setText(s);
            }
        });

        tvLotteryCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerDialog.showSpinerDialog();
            }
        });
        Button btnCheck = (Button)mView.findViewById(R.id.btn_check);
        Button btnAutoFill = (Button)mView.findViewById(R.id.btn_autofill);

        etLotteryDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker(etLotteryDate);
            }
        });

        mBuilder.setView(mView);

        btnAutoFill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder importBuilder = new AlertDialog.Builder(view.getContext());
                View importView = getLayoutInflater().inflate(R.layout.dialog_get_info_from_image,null);
                importBuilder.setView(importView);
                AlertDialog importdialog = importBuilder.create();
                importdialog.show();
            }
        });
        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckLotteryResult(v, etLotteryDate, tvLotteryCompany, etLotteryCode, progressBarForm);

            }
        });

        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }

    private void CheckLotteryResult(View v, EditText etLotteryDate, TextView tvLotteryCompany, EditText etLotteryCode, ProgressBar progressBarForm) {
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

    private void DatePicker(final EditText etLotteryDate) {
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

    private boolean hasInternetConnection(View v) {
        ConnectivityManager cm = (ConnectivityManager) v.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm != null
                && cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected();
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
