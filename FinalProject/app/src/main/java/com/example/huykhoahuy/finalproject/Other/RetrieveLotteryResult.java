package com.example.huykhoahuy.finalproject.Other;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.huykhoahuy.finalproject.Class.LotteryResult;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RetrieveLotteryResult extends AsyncTask<Void, Void, String> {

    private Exception exception;
    private ProgressBar progressBar;
    private String lottery_province_id;
    private String lottery_date;
    private LotteryResult lotteryResult;

    public RetrieveLotteryResult(String lottery_province_id, String lottery_date) {
        this.lottery_province_id = lottery_province_id;
        this.lottery_date = lottery_date;
    }

    static final String API_KEY = "5b0cf5d828e03";
    static final String API_URL = "https://laythongtin.net/mini-content/lottery-all-api.php?type=json";

    public void onPreExecute() {
        progressBar.setVisibility(View.VISIBLE);
//        responseView.setText("");
    }

    protected String doInBackground(Void... urls) {

        // Do some validation here

        try {
            URL url = new URL(String.format(API_URL + "&key=" + API_KEY + "&location="
                    + lottery_province_id+ "&date=" + lottery_date));
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                return stringBuilder.toString();
            }
            finally{
                urlConnection.disconnect();
            }
        }
        catch(Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }
    }

    protected void onPostExecute(String response) {
        if(response == null) {
            response = "THERE WAS AN ERROR";
        }
        progressBar.setVisibility(View.GONE);
        Log.i("INFO", response);
        // TODO: check this.exception
        // TODO: do something with the feed

    }
}
