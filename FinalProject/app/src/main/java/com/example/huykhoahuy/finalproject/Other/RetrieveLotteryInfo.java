package com.example.huykhoahuy.finalproject.Other;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.SparseArray;
import android.view.View;
import android.widget.Toast;

import com.example.huykhoahuy.finalproject.Class.Lottery;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.util.ArrayList;
import java.util.List;

public class RetrieveLotteryInfo {
    private View view;
    private Bitmap bitmap;
    private Lottery lottery;
    private List<Bitmap> bitmapList;
    private StringBuilder sb = new StringBuilder();

    public RetrieveLotteryInfo(Bitmap bitmap,View view) {
        this.bitmap = bitmap;
        this.view = view;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Lottery getLottery() {
        return lottery;
    }

    public void setLottery(Lottery lottery) {
        this.lottery = lottery;
    }

    private void CropImage() {

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
    }

    private void getRawInfo()
    {
        TextRecognizer textRecognizer = new TextRecognizer.Builder(view.getContext()).build();
        sb = new StringBuilder();
        for(int i=0;i<bitmapList.size();i++) {
            Bitmap bmp = bitmapList.get(i);

            sb.append("["+String.valueOf(i+1)+"]");
            sb.append("          ");
            if(!textRecognizer.isOperational() || bmp == null)
            {
                Toast.makeText(view.getContext(),"No Text",Toast.LENGTH_SHORT).show();
            }
            else
            {
                Frame frame = new Frame.Builder().setBitmap(bmp).build();
                SparseArray<TextBlock> items = textRecognizer.detect(frame);

                for(int j= 0;j<items.size();++j)
                {
                    TextBlock myItems = items.valueAt(j);
                    sb.append(myItems.getValue());
                    sb.append("          ");
                }
            }
        }
    }






}
