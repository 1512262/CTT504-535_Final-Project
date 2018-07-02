package com.example.huykhoahuy.finalproject.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.widget.Toast;

import com.example.huykhoahuy.finalproject.OCR_Task.OCR_Activity;
import com.example.huykhoahuy.finalproject.R;
import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class Intro extends AppIntro {

    @Override
    public void onDonePressed() {
        startActivity(new Intent(this,OCR_Activity.class));
        finish();
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        startActivity(new Intent(this,OCR_Activity.class));
        finish();
    }

    @Override
    public void onSlideChanged() {
        Toast.makeText(this,"Tiếp nào",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(AppIntroFragment.newInstance(getString(R.string.app_name),
                "Tự động ghi nhận thông tin từ vé số",
                R.drawable.ic_camera_white_48dp,
                Color.parseColor("#ff6699")));

        addSlide(AppIntroFragment.newInstance(getString(R.string.app_name),
                "Kiểm tra thông tin tự động và nhanh chóng",
                R.drawable.ic_dvr_white_48dp,
                Color.parseColor("#ffcc00")));

        addSlide(AppIntroFragment.newInstance(getString(R.string.app_name),
                "Tìm được đi nhanh nhất tới nơi nhận thưởng",
                R.drawable.ic_map_white_48dp,
                Color.parseColor("#00ff00")));

        addSlide(AppIntroFragment.newInstance(getString(R.string.app_name),
                "Liên lạc với đại diện công ty xổ số  một cách tiện lợi",
                R.drawable.ic_call_white_48dp,
                Color.parseColor("#3399ff")));

        showStatusBar(false);
        setBarColor(Color.parseColor("#333639"));
        setSeparatorColor((Color.parseColor(("#2196f3"))));




    }
}
