package com.example.huykhoahuy.finalproject.Fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.huykhoahuy.finalproject.R;

import java.util.Calendar;


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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_result_table, container, false);
//        final EditText etMyLotteryDate = (EditText)mView.findViewById(R.id.et_my_lottery_date);
//        etMyLotteryDate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final Calendar calendar = Calendar.getInstance();
//                int yy = calendar.get(Calendar.YEAR);
//                int mm = calendar.get(Calendar.MONTH);
//                int dd = calendar.get(Calendar.DAY_OF_MONTH);
//                DatePickerDialog datePicker = new DatePickerDialog(mView.getContext(),new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker view, int year, int month, int day) {
//                        String strDay = null;
//                        String strMonth = null;
//                        strMonth = (month<10)?"0"+String.valueOf(month+1):String.valueOf(month+1);
//                        strDay = (day<10)?"0"+String.valueOf(day):String.valueOf(day);
//                        String date = strDay +"-"+strMonth +"-"+String.valueOf(year);
//                        etMyLotteryDate.setText(date);
//                    }
//                }, yy, mm, dd);
//                datePicker.show();
//            }
//        });
//        mWebView = (WebView) view.findViewById(R.id.webview);
//        mWebView.loadUrl("https://www.minhngoc.net/");
//
//        // Enable Javascript
//        WebSettings webSettings = mWebView.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//
//        // Force links and redirects to open in the WebView instead of in a browser
//        mWebView.setWebViewClient(new WebViewClient());

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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
