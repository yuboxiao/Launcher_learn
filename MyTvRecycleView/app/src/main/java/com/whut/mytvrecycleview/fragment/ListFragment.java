package com.whut.mytvrecycleview.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.whut.mytvrecycleview.R;

import butterknife.ButterKnife;

/**
 * 作者: x00378851
 * 日期: 2018/10/21 15:47
 */

public class ListFragment extends Fragment {
    public static ListFragment newInstance() {
        ListFragment fragment = new ListFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.layout_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
}
