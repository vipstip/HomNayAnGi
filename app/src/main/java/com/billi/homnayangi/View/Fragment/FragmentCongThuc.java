package com.billi.homnayangi.View.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.billi.homnayangi.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentCongThuc#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentCongThuc extends Fragment {

    public FragmentCongThuc() {
        // Required empty public constructor
    }

    public static FragmentCongThuc newInstance() {
        FragmentCongThuc fragment = new FragmentCongThuc();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cong_thuc, container, false) ;

        return view;
    }
}