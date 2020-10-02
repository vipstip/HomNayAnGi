package com.billi.homnayangi.View.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.billi.homnayangi.R;
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
                checkLoadComplete = true;
                Intent intent = new Intent(ActivityLoad.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }

    @Override
    protected void onPause() {
        super.onPause();

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
