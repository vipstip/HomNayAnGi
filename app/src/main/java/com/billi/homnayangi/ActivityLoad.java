package com.billi.homnayangi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.ybq.android.spinkit.style.ThreeBounce;

public class ActivityLoad extends AppCompatActivity {
    ImageView imageViewLoad;
    Boolean checkLoadComplete = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        imageViewLoad = findViewById(R.id.gifLoad);

        ProgressBar progressBar = (ProgressBar)findViewById(R.id.waitload);
        ThreeBounce doubleBounce = new ThreeBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);
        Glide.with(this).load(R.drawable.gifload).into(imageViewLoad);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                checkLoadComplete = true;
                onBackPressed();
            }
        }, 3000);
    }

    @Override
    public void onBackPressed() {
        if (checkLoadComplete){
            super.onBackPressed();
        } else {
            Toast.makeText(ActivityLoad.this,"Đang tải vui lòng đợi trong giây lát",Toast.LENGTH_SHORT).show();
        }
    }
}
