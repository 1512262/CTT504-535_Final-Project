package com.example.huykhoahuy.finalproject.Other;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huykhoahuy.finalproject.Class.Lottery;
import com.example.huykhoahuy.finalproject.Class.LotteryResult;
import com.example.huykhoahuy.finalproject.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class RetrieveLotteryResultAndRenderToATable extends AsyncTask<Void, Void, String> {

    private Exception exception;
    private ProgressBar progressBar;
    private String lottery_province_id;
    private String lottery_date;
    private View view;
    private LinearLayout linearLayout;
    private ArrayList<Integer> listOfRowViews;
    private ArrayList<Integer> numberOfLotteryCodeInAPrize;

    private void initNumberOfLotteryCodeInAPrize() {
        numberOfLotteryCodeInAPrize = new ArrayList<Integer>();
        numberOfLotteryCodeInAPrize.add(1);
        numberOfLotteryCodeInAPrize.add(1);
        numberOfLotteryCodeInAPrize.add(1);
        numberOfLotteryCodeInAPrize.add(2);
        numberOfLotteryCodeInAPrize.add(7);
        numberOfLotteryCodeInAPrize.add(1);
        numberOfLotteryCodeInAPrize.add(3);
        numberOfLotteryCodeInAPrize.add(1);
        numberOfLotteryCodeInAPrize.add(1);
    }

    private String minorStringProcessing(String date) {
        return date.replace("/", "-");
    }

    public RetrieveLotteryResultAndRenderToATable(String lottery_province_id, String lottery_date,
                                                  View view, ArrayList<Integer> listOfRowviews,
                                                    ProgressBar progressBar,LinearLayout linearLayout) {
        this.lottery_province_id = lottery_province_id;
        this.lottery_date = minorStringProcessing(lottery_date);
        this.progressBar = progressBar;
        this.listOfRowViews = listOfRowviews;
        this.view = view;
        this.initNumberOfLotteryCodeInAPrize();
        this.linearLayout = linearLayout;
    }

    private static final String API_KEY = "5b0cf5d828e03";
    private static final String API_URL = "https://laythongtin.net/mini-content/lottery-all-api.php?type=json";

    public void onPreExecute() {
        this.progressBar.setVisibility(View.VISIBLE);
        this.linearLayout.setVisibility(View.INVISIBLE);
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

    private JSONObject getJSONTokener(String JSONResponse) {
        JSONObject reader = null;
        try {
            reader = (JSONObject) new JSONTokener(JSONResponse).nextValue();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return reader;
    }

    private JSONObject getTheIthJSON(JSONObject reader, int i) {
        JSONObject temp = null;
        if (reader != null) {
            try {
                temp = reader.getJSONObject(String.valueOf(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return temp;
    }

    private String getTheIthResult(JSONObject reader, int i) {
        JSONObject jsonObject = getTheIthJSON(reader, i);
        String result = null;
        if (jsonObject != null) {
            try {
                result = jsonObject.getString("result");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private ArrayList<String> extractResultListFromJSON(String JSONResponse) {
        // Precondition: Suppose that JSONResponse != null

        ArrayList<String> listResults = new ArrayList<String>();
        JSONObject reader = this.getJSONTokener(JSONResponse);

        int nPrizes = 18;
        for (int i = 0; i < nPrizes; i++) {
            String result = this.getTheIthResult(reader, i);
            if (result != null) {
                listResults.add(result);
            }
        }
        return listResults;
    }

    public void onPostExecute(String response) {
        progressBar.setVisibility(View.GONE);
        linearLayout.setVisibility(View.VISIBLE);
        ArrayList<String> listResults = new ArrayList<String>();
        if (response == null) {
            response = "Có lỗi";
            Log.i("NOINFO", response);

            Toast.makeText(this.view.getContext(), R.string.toast_error_info, Toast.LENGTH_SHORT).show();
        }
        else {
            Log.i("INFO", response);
            listResults = this.extractResultListFromJSON(response);
            if (listResults != null) {
                if (listResults.size() == 18) {
                    renderToATable(listResults);
                } else {
                    linearLayout.setVisibility(View.INVISIBLE);
                    Toast.makeText(this.view.getContext(), R.string.toast_error_info, Toast.LENGTH_SHORT).show();
                }
            }
            else{
                linearLayout.setVisibility(View.INVISIBLE);
                Toast.makeText(this.view.getContext(),R.string.toast_error_info, Toast.LENGTH_SHORT).show();
            }


        }
    }

    private void renderToATable(ArrayList<String> listResults) {
        // Do nothing for now
        for (int i = 0, k = 0; i < this.listOfRowViews.size(); ++i) {
            final TextView rowPrize = (TextView)(this.view.findViewById(this.listOfRowViews.get(i)));
            StringBuilder prizeContent = new StringBuilder();
            for (int j = 0; j < this.numberOfLotteryCodeInAPrize.get(i); ++j, ++k) {
                if (j > 0 && j%4 == 0)
                    prizeContent.append("\n");

                prizeContent.append(listResults.get(17-k));
                prizeContent.append("     ");
            }
            prizeContent.deleteCharAt(prizeContent.length()-1);
            rowPrize.setText(prizeContent.toString());
        }
    }
}
