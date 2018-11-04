package com.whut.mytvrecycleview.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.owen.tvrecyclerview.widget.MetroTitleItemDecoration;
import com.owen.tvrecyclerview.widget.SimpleOnItemListener;
import com.owen.tvrecyclerview.widget.TvRecyclerView;
import com.whut.mytvrecycleview.MainActivity;
import com.whut.mytvrecycleview.R;
import com.whut.mytvrecycleview.adapter.MetroAdapter;
import com.whut.mytvrecycleview.data.ItemDatas;
import com.whut.mytvrecycleview.focus.FocusBorder;

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
    /**
     * 移动飞框
     */
    private FocusBorder mFocusBorder;

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

    protected void onMoveFocusBorder(View focusedView, float scale, float roundRadius) {
        if(null != mFocusBorder) {
            mFocusBorder.onFocus(focusedView,
                    FocusBorder.OptionsFactory.get(scale, scale, roundRadius));//ScaleX.ScaleY
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mFocusBorder = new FocusBorder.Builder()
                .asColor()
                .borderColor(getResources().getColor(R.color.actionbar_color))
                .borderWidth(TypedValue.COMPLEX_UNIT_DIP, 2)
                .shadowColor(getResources().getColor(R.color.green_bright))
                .shadowWidth(TypedValue.COMPLEX_UNIT_DIP, 18)
                .build(getActivity());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAdapter = new MetroAdapter(getContext());
        mAdapter.setDatas(ItemDatas.getDatas(ITEM_COUNT));

        mTvRecycleView.addItemDecoration(new MetroTitleItemDecoration(mAdapter));
        mTvRecycleView.setSpacingWithMargins(10, 10);//设置行列间距
        mTvRecycleView.setAdapter(mAdapter);

        setUpListener();
    }

    private void setUpListener() {
        if (mTvRecycleView != null) {
            mTvRecycleView.setOnItemListener(new SimpleOnItemListener() {
                @Override
                public void onItemPreSelected(TvRecyclerView parent, View itemView, int position) {

                }

                /**
                 * 这个就是当itemView被选中时候的回调的函数，用作移动飞框
                 * @param parent
                 * @param itemView
                 * @param position
                 */
                @Override
                public void onItemSelected(TvRecyclerView parent, View itemView, int position) {
                    float radius = 10.0f;
                    onMoveFocusBorder(itemView, 1.1f, radius);
                }

                @Override
                public void onItemClick(TvRecyclerView parent, View itemView, int position) {
                    Toast.makeText(parent.getContext(), "onItemClick:: " + position,
                            Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}
