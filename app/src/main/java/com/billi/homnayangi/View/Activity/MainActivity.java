package com.billi.homnayangi.View.Activity;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.billi.homnayangi.View.Fragment.FragmentTaiKhoan;
import com.billi.homnayangi.View.Fragment.FragmentTimKiem;
import com.billi.homnayangi.View.Fragment.TextFragment;
import com.billi.homnayangi.View.Fragment.ThucDonMoiNgay;
import com.billi.homnayangi.R;
import com.yinglan.alphatabs.AlphaTabsIndicator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AlphaTabsIndicator alphaTabsIndicator;
    private EditText edtSearch;
    private ViewPager mViewPger;
    private LinearLayout lineSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this,ActivityLoad.class);
        startActivity(intent);
        init();
        MainAdapter mainAdapter = new MainAdapter(getSupportFragmentManager());
        mViewPger.setAdapter(mainAdapter);
        hideKeyboard();
        mViewPger.addOnPageChangeListener(mainAdapter);
        alphaTabsIndicator.setViewPager(mViewPger);
        mViewPger.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                hideKeyboard();
                edtSearch.clearFocus();
                return false;
            }
        });

        edtSearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                alphaTabsIndicator.setTabCurrenItem(2);
                return false;
            }
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        this.adBanner.destroy();
    }

    public void hideKeyboard(){
        View view = this.getCurrentFocus();
        if (view != null){
            InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public void init(){
        lineSearch = findViewById(R.id.lineSearch);
        alphaTabsIndicator = findViewById(R.id.alphaIndicator);
        mViewPger = findViewById(R.id.mViewPager);
        edtSearch = findViewById(R.id.edt_search);
    }

    private class MainAdapter extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener {

        private List<Fragment> fragments = new ArrayList<>();
        private String[] titles = {"Gợi ý thực đơn", "Công thức", "Tìm kiếm", "Thông tin"};

        public MainAdapter(FragmentManager fm) {
            super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

            fragments.add(ThucDonMoiNgay.newInstance());
            fragments.add(TextFragment.newInstance(titles[1]));
            fragments.add(FragmentTimKiem.newInstance());
            fragments.add(FragmentTaiKhoan.newInstance());
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (0 == position) {
                lineSearch.setVisibility(View.VISIBLE);
            } else if (1 == position) {
                lineSearch.setVisibility(View.VISIBLE);
            } else if (2 == position) {
                lineSearch.setVisibility(View.GONE);
            } else if (3 == position) {
                lineSearch.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
