package com.example.huykhoahuy.finalproject.OCR_Task;

import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huykhoahuy.finalproject.Activity.MainActivity;
import com.example.huykhoahuy.finalproject.BuildConfig;
import com.example.huykhoahuy.finalproject.Class.Lottery;
import com.example.huykhoahuy.finalproject.Class.LotteryCompany;
import com.example.huykhoahuy.finalproject.OCR_Task.OCR_Pre_Processing.RetrieveLotteryInfo;
import com.example.huykhoahuy.finalproject.Other.ParseHostFile;
import com.example.huykhoahuy.finalproject.Other.RetrieveLotteryResult;
import com.example.huykhoahuy.finalproject.R;
import com.github.javiersantos.bottomdialogs.BottomDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;

public class OCR_Activity extends AppCompatActivity implements View.OnClickListener {

    private static final int CAMERA_REQUEST = 1997;
    private static final int RESULT_LOAD_IMG = 2018;
    Bitmap bitmap = null;
    List<Bitmap> bitmapList;
    Uri mImageUri = null;
    StringBuilder sb = new StringBuilder();
    File photo = null;
    private Lottery lottery;
    private RetrieveLotteryResult retrieveLotteryResult;
    private EditText etLotteryCode;
    private EditText etLotteryDate;
    private TextView tvLotteryCompany;
    private TextView tvTestSB;
    private ProgressBar progressBar;
    private Map<String,String> map_name_id=new HashMap<String,String>();
    private ArrayList<LotteryCompany> lotteryCompanies;
    private ArrayList<String> lotterycompanynames = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        setContentView(R.layout.activity_ocr);
        Button btnTakeImage = (Button)findViewById(R.id.btn_take_image);
        Button btnLoadImage = (Button)findViewById(R.id.btn_load_image);
        Button btnUseInfo = (Button)findViewById(R.id.btn_use_info);
        Button btnBackHome = (Button)findViewById(R.id.btn_back_home);


        etLotteryCode = (EditText)findViewById(R.id.et_lottery_code);
        etLotteryDate = (EditText) findViewById(R.id.et_lottery_date);
        tvLotteryCompany = (TextView)findViewById(R.id.tv_lottery_company);
        progressBar = (ProgressBar)findViewById(R.id.prb_loading_form_1);
//        tvTestSB = (TextView)findViewById(R.id.tv_test_sb);

        btnTakeImage.setOnClickListener(this);
        btnLoadImage.setOnClickListener(this);
        btnUseInfo.setOnClickListener(this);
        btnBackHome.setOnClickListener(this);
        etLotteryCode.setOnClickListener(this);
        tvLotteryCompany.setOnClickListener(this);
        etLotteryDate.setOnClickListener(this);


    }
    public void initData()
    {
        ParseHostFile parseHostFile = new ParseHostFile(this,R.xml.main_host);
        lotteryCompanies = parseHostFile.lotteryCompanies;
        for(LotteryCompany company: lotteryCompanies)
        {
            lotterycompanynames.add(company.getName());
            map_name_id.put(company.getName(),company.getProvince_id());
        }


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode==RESULT_OK) {
            getContentResolver().notifyChange(mImageUri, null);
            ContentResolver cr = getContentResolver();
            try {
                bitmap = android.provider.MediaStore.Images.Media.getBitmap(cr, mImageUri);
                ImageView imgView = (ImageView) findViewById(R.id.iv_my_lottery_image);
                imgView.setImageBitmap(bitmap);
                RetrieveLotteryInfo lotteryInfo = new RetrieveLotteryInfo(getWindow().getDecorView().getRootView(),
                        bitmap,
                        etLotteryCode,etLotteryDate,tvLotteryCompany,
                        progressBar);
                lotteryInfo.execute();
                photo.delete();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(),"Failed to load",Toast.LENGTH_SHORT).show();
            }
        }
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK && data != null) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String imgDecodableString = cursor.getString(columnIndex);
                cursor.close();

                ImageView imgView = (ImageView) findViewById(R.id.iv_my_lottery_image);
                // Set the Image in ImageView after decoding the String
                bitmap = BitmapFactory.decodeFile(imgDecodableString);
                imgView.setImageBitmap(bitmap);
                RetrieveLotteryInfo lotteryInfo = new RetrieveLotteryInfo(getWindow().getDecorView().getRootView(),
                        bitmap,
                        etLotteryCode,etLotteryDate,tvLotteryCompany,
                        progressBar);
                lotteryInfo.execute();


            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_take_image)
        {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            photo = null;
            try {
                File tempDir = Environment.getExternalStorageDirectory();
                tempDir = new File(tempDir.getAbsolutePath()+"/.temp/");
                if (!tempDir.exists()) {
                    tempDir.mkdirs();
                }
                photo = File.createTempFile("picture",".jpg",tempDir);
                photo.delete();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(),"Can't create file to take picture",Toast.LENGTH_SHORT).show();
                return;
            }
            if (photo == null) return;
            mImageUri = FileProvider.getUriForFile(OCR_Activity.this, BuildConfig.APPLICATION_ID + ".provider",photo);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);

        }
        if(v.getId()==R.id.btn_load_image)
        {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, RESULT_LOAD_IMG);


        }
        if(v.getId()==R.id.btn_use_info)
        {
            if (hasInternetConnection(v)) {
                String lottery_date = etLotteryDate.getText().toString();
                String lottery_company = tvLotteryCompany.getText().toString();
                String lottery_code = etLotteryCode.getText().toString();
                String lottery_province_id;
                if(lottery_code.length()!=6 || lottery_date.equals("") || lottery_company.equals(""))
                {
                    Toast.makeText(v.getContext(),"Vui lòng nhập đầy đủ",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    lottery_province_id = map_name_id.get(lottery_company);
                    lottery_province_id = map_name_id.get(lottery_company);
                    lottery = new Lottery(lottery_company,lottery_date,lottery_province_id,lottery_code);
                    retrieveLotteryResult = new RetrieveLotteryResult(lottery, getWindow().getDecorView().getRootView(),progressBar);
                    retrieveLotteryResult.execute();
                }
            }
            else {
//            Toast.makeText(v.getContext(), "Không có kết nối Internet!", Toast.LENGTH_SHORT).show();
                BottomDialog bottomDialog =new BottomDialog.Builder(v.getContext())
                        .setTitle("Thông báo")
                        .setIcon(R.drawable.red_dice_128)
                        .setContent("Không có kết nối Internet!")
                        .setNegativeText("Vào Cài Đặt")
                        .onNegative(new BottomDialog.ButtonCallback() {
                            @Override
                            public void onClick(@NonNull BottomDialog bottomDialog) {
                                startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                            }
                        })
                        .build();
                bottomDialog.show();
            }

        }
        if(v.getId()==R.id.btn_back_home)
        {
            Intent BackHomeIntent = new Intent(OCR_Activity.this,MainActivity.class);
            startActivity(BackHomeIntent);
        }
        if(v.getId()==R.id.et_lottery_date)
        {
            DatePicker(etLotteryDate);
        }
        if(v.getId()==R.id.tv_lottery_company)
        {
            final SpinnerDialog spinnerDialog = new SpinnerDialog(this,lotterycompanynames,"Chọn Công ty Xổ số Kiến thiết",R.style.DialogAnimations_SmileWindow,"Đóng");
            spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
                @Override
                public void onClick(String s, int i) {
                    tvLotteryCompany.setText(s);
                }
            });
            spinnerDialog.showSpinerDialog();
        }
    }

    private void DatePicker(final EditText etLotteryDate) {
        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePicker = new DatePickerDialog(this,new DatePickerDialog.OnDateSetListener() {
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
}
