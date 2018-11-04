package com.whut.mytvrecycleview.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.owen.tvrecyclerview.widget.MetroTitleItemDecoration;
import com.owen.tvrecyclerview.widget.TvRecyclerView;
import com.whut.mytvrecycleview.R;
import com.whut.mytvrecycleview.adapter.MetroAdapter;
import com.whut.mytvrecycleview.data.ItemDatas;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者: x00378851
 * 日期: 2018/10/21 15:47
 */

public class MetroFragment extends Fragment {

    private static final String TAG = MetroFragment.class.getSimpleName();
    private static final int ITEM_COUNT = 60;
    private MetroAdapter mAdapter;

    @BindView(R.id.list)
    TvRecyclerView mTvRecycleView;

    public static MetroFragment newInstance() {
        MetroFragment fragment = new MetroFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_metro_grid, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAdapter = new MetroAdapter(getContext());
        mAdapter.setDatas(ItemDatas.getDatas(ITEM_COUNT));

        mTvRecycleView.addItemDecoration(new MetroTitleItemDecoration(mAdapter));
        mTvRecycleView.setSpacingWithMargins(10, 10);//设置行列间距
        mTvRecycleView.setAdapter(mAdapter);
    }
}
