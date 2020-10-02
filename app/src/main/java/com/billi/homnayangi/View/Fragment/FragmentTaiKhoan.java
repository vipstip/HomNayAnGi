package com.billi.homnayangi.View.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.billi.homnayangi.R;
import java.util.List;

public class FragmentTaiKhoan extends Fragment {
    List<Integer> lstI;
    public FragmentTaiKhoan() {

    }

    public static FragmentTaiKhoan newInstance() {
        FragmentTaiKhoan fragment = new FragmentTaiKhoan();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tai_khoan, container, false);

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();

    }
}