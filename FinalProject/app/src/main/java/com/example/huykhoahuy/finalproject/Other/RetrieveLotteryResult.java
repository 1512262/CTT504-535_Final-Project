package com.example.huykhoahuy.finalproject.Other;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.huykhoahuy.finalproject.Class.Lottery;
import com.example.huykhoahuy.finalproject.Class.LotteryResult;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RetrieveLotteryResult extends AsyncTask<Void, Void, String> {

    private Exception exception;
//    private ProgressBar progressBar;
    private String lottery_province_id;
    private String lottery_date;
    private Lottery lottery;
    private View view;

//    public RetrieveLotteryResult(String lottery_province_id, String lottery_date) {
//        this.lottery_province_id = lottery_province_id;
//        this.lottery_date = lottery_date;
//    }

    private String minorStringProcessing(String date) {
        return date.replace("/", "-");
    }

    private static final String API_KEY = "5b0cf5d828e03";
    private static final String API_URL = "https://laythongtin.net/mini-content/lottery-all-api.php?type=json";

    public RetrieveLotteryResult(Lottery lottery, View view) {
        this.lottery_province_id = lottery.getLoterry_Province_ID();
        this.lottery_date = this.minorStringProcessing(lottery.getLottery_Date());
        this.lottery = lottery;
        this.view = view;
    }

    public void onPreExecute() {
//        progressBar.setVisibility(View.VISIBLE);
//        responseView.setText("");
    }

    public String doInBackground(Void... urls) {

        // Do some validation here
        try {
            URL url = new URL(String.format(API_URL + "&key=" + API_KEY + "&location="
                    + lottery_province_id + "&date=" + lottery_date));
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
            } finally {
                urlConnection.disconnect();
            }
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }
    }

    public void onPostExecute(String response) {
        ArrayList<String> listResults = new ArrayList<String>();
        if (response == null) {
            response = "THERE WAS AN ERROR";
            Log.i("NOINFO", response);
        }
        else {
            Log.i("INFO", response);
            JSONObject reader = null;
            try {
                reader = (JSONObject) new JSONTokener(response).nextValue();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < 18; i++) {
                JSONObject temp = null;
                String result = null;
                try {
                    temp = reader.getJSONObject(String.valueOf(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    result = temp.getString("result");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                listResults.add(result);
            }
        }

        lottery.checkResult(listResults);

        Context context = this.view.getContext();
        String prize = lottery.getLottery_Prize();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, prize, duration);
        toast.show();
    }
}