package com.example.huykhoahuy.finalproject.OCR_Task.OCR_Pre_Processing;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huykhoahuy.finalproject.Class.LotteryCompany;
import com.example.huykhoahuy.finalproject.Other.ParseHostFile;
import com.example.huykhoahuy.finalproject.Other.RetrieveLotteryResult;
import com.example.huykhoahuy.finalproject.R;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RetrieveLotteryInfo extends AsyncTask<Void, Void, ArrayList<String> > {

    private View view;
    private Bitmap bitmap;
    private EditText etCode;
    private EditText etDate;
    private TextView tvName;
    private ProgressBar progressBar;


    private ArrayList<LotteryCompany> lotteryCompanies;
    private Map<String,String> map_id_name=new HashMap<String,String>();
    private RetrieveLotteryResult retrieveLotteryResult;

    public void initData() {
        ParseHostFile parseHostFile = new ParseHostFile(view.getContext(), R.xml.main_host);
        lotteryCompanies = parseHostFile.lotteryCompanies;
        for(LotteryCompany company: lotteryCompanies)
        {
            map_id_name.put(company.getProvince_id(),company.getName());
        }
    }

    public RetrieveLotteryInfo(View view, Bitmap bitmap, EditText etCode, EditText etDate, TextView tvName, ProgressBar progressBar) {
        this.view = view;
        this.bitmap = bitmap;
        this.etCode = etCode;
        this.etDate = etDate;
        this.tvName = tvName;
        this.progressBar = progressBar;
    }
//    private ArrayList<String> listResult = new ArrayList<>();

    private ArrayList<Bitmap> CropImage(Bitmap bitmap) {
        ArrayList<Bitmap> bitmapList = new ArrayList<>();
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        int h1 = h/2;
        int h2 = h/4;
        int h3 = h/8;
        int w1 = w/2;
        int w2 = w/4;
        int w3 = w/8;

        Matrix matrix = new Matrix();
        matrix.postRotate(90);

        Bitmap bmp1 = Bitmap.createBitmap(bitmap,0,0,w1,h2);
        bitmapList.add(bmp1);
        Bitmap bmp2 = Bitmap.createBitmap(bitmap,0,0,w3,h); //need rotate
        Bitmap bmp2r = Bitmap.createBitmap(bmp2,0,0,bmp2.getWidth(), bmp2.getHeight(),matrix,true);
        bitmapList.add(bmp2r);
        Bitmap bmp3 = Bitmap.createBitmap(bitmap,0,0,w2,h1);
        bitmapList.add(bmp3);
        Bitmap bmp4 = Bitmap.createBitmap(bitmap,w-w1,0,w1,h2);
        bitmapList.add(bmp4);
        Bitmap bmp5 = Bitmap.createBitmap(bitmap,w-w1,h2,w1,h2);
        bitmapList.add(bmp5);
        Bitmap bmp6 = Bitmap.createBitmap(bitmap,w1-50,h-h2-50,w1+50,h2+50);
        bitmapList.add(bmp6);
        Bitmap bmp7 = Bitmap.createBitmap(bitmap,0,h-h1,w1,h1);
        bitmapList.add(bmp7);
        Bitmap bmp8 = Bitmap.createBitmap(bitmap,w2,0,w2,h1);
        bitmapList.add(bmp8);
        Bitmap bmp9 = Bitmap.createBitmap(bitmap,0,0,w,h2);
        bitmapList.add(bmp9);
        Bitmap bmp10 = Bitmap.createBitmap(bitmap,0,h-h1,w,h1);
        bitmapList.add(bmp10);
        return bitmapList;
    }

    private ArrayList<String> getRawInfo(Bitmap bitmap)
    {
        ArrayList<String> listResult = new ArrayList<>();
        ArrayList<Bitmap> bitmapList = CropImage(bitmap);
        TextRecognizer textRecognizer = new TextRecognizer.Builder(view.getContext()).build();
        for(int i=0;i<bitmapList.size();i++) {
            Bitmap bmp = bitmapList.get(i);

            if(!textRecognizer.isOperational() || bmp == null)
            {
                Toast.makeText(view.getContext(),"No Text", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Frame frame = new Frame.Builder().setBitmap(bmp).build();
                SparseArray<TextBlock> items = textRecognizer.detect(frame);
                StringBuilder temp = new StringBuilder();
                for(int j= 0; j < items.size(); ++j)
                {
                    TextBlock myItems = items.valueAt(j);
                    temp.append(myItems.getValue());
                    temp.append("\n");
                }
                listResult.add(temp.toString());
            }

        }
        return listResult;
    }


    @Override
    protected ArrayList<String> doInBackground(Void... voids) {
        progressBar.setVisibility(View.VISIBLE);
        try{
            return getRawInfo(bitmap);

        }catch (Exception e){
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }
    }

    public void onPreExecute() {
        progressBar.setVisibility(View.VISIBLE);
        etCode.setText("");
        etDate.setText("");
        tvName.setText("");
        initData();
    }

    public void onPostExecute(ArrayList<String> listResult) {

        progressBar.setVisibility(View.GONE);

        ParsingListResultToGetInformation parsingList = new ParsingListResultToGetInformation();
        LotteryInfo lotteryInfo = parsingList.getLotteryInfo(listResult);

        //Phần đưa thông tin vào
        etCode.setText(lotteryInfo.getLotteryCode());
        etDate.setText(lotteryInfo.getLotteryDate());
        String name = map_id_name.get(lotteryInfo.getLotteryHost());
        tvName.setText(name);
    }

}
