package com.example.huykhoahuy.finalproject.Fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
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

import com.example.huykhoahuy.finalproject.Class.LotterStatus;
import com.example.huykhoahuy.finalproject.Class.LotteryCompany;
import com.example.huykhoahuy.finalproject.Other.ParseHostFile;
import com.example.huykhoahuy.finalproject.R;

import java.util.ArrayList;
import java.util.Calendar;


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
    private  ArrayList<String> lotterycompanynames = new ArrayList<String>();
    private LotterStatus lotterStatus = new LotterStatus();
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
        fabCreate.setOnClickListener(this);
        initData();
    }

    public void initData()
    {
        ParseHostFile parseHostFile = new ParseHostFile(getContext(),R.xml.main_host);
        lotteryCompanies = parseHostFile.lotteryCompanies;
        for(LotteryCompany company: lotteryCompanies)
            lotterycompanynames.add(company.getName());
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
        EditText etLotteryCode = (EditText)mView.findViewById(R.id.et_lottery_code);
        final EditText etLotteryDate = (EditText) mView.findViewById(R.id.et_lottery_date);
        AutoCompleteTextView tvLotteryCompany = (AutoCompleteTextView)mView.findViewById(R.id.tv_lottery_company);
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
                        strMonth = (month<10)?"0"+String.valueOf(month):String.valueOf(month);
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

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lotterStatus.setDate(etLotteryDate.getText().toString());
                lotterStatus.setProvice_id();
            }
        });

        AlertDialog dialog = mBuilder.create();
        dialog.show();
        doKeepDialog(dialog);
    }
    private static void doKeepDialog(Dialog dialog){
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);
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
}
