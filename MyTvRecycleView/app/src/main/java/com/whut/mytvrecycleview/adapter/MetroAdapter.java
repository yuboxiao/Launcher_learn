package com.whut.mytvrecycleview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.owen.tvrecyclerview.widget.MetroGridLayoutManager;
import com.owen.tvrecyclerview.widget.MetroTitleItemDecoration;
import com.whut.mytvrecycleview.R;
import com.whut.mytvrecycleview.data.ItemBean;

import java.util.List;
/**
 * 作者: x00378851
 * 日期: 2018/10/21 16:44
 */

public class MetroAdapter extends RecyclerView.Adapter implements MetroTitleItemDecoration.Adapter {

    private static final String TAG = MetroAdapter.class.getSimpleName();
    private Context mContext;
    private List<ItemBean> mDatas;

    public MetroAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.item, null);
        return new MetroHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemBean itemBean = mDatas.get(position);
        if (itemBean != null) {
            final MetroHolder metroHolder = (MetroHolder) holder;
            if (itemBean.imgUrl != null) {
                Log.d(TAG, "onBindViewHolder: setImage--->" + itemBean.imgUrl);
                //TODO 这里一定要增加网络访问的权限，否则死活加载不出来图片
                Glide.with(mContext)
                        .load(itemBean.imgUrl)
                        .into(metroHolder.mImgView);
            }
            if (itemBean.title != null) {
                Log.d(TAG, "onBindViewHolder: setText--->" + itemBean.title);
                metroHolder.mTitle.setText(String.valueOf(position));
            }

            //这个itemView就是R.layout.item的根节点RoundFrameLayout
            View itemView = metroHolder.itemView;
            MetroGridLayoutManager.LayoutParams lp = new MetroGridLayoutManager.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            //TODO 为什么人家这里可以获取到layoutParams
            //MetroGridLayoutManager.LayoutParams lp = (MetroGridLayoutManager.LayoutParams) itemView.getLayoutParams();


            if (position > 18) {
                //在这里设置了区域索引,这是标题栏2内容
                lp.sectionIndex = 2;
                lp.rowSpan = 3;
                lp.colSpan = 2;
            } else if (position > 6) {
                //在这里设置了区域索引,这是标题栏1内容
                lp.sectionIndex = 1;
                lp.isSuportIntelligentScrollEnd = false;
                lp.isSuportIntelligentScrollStart = true;
                if (position < 10) {
                    lp.rowSpan = 15;
                    lp.colSpan = 20;
                } else if (position < 14) {
                    lp.rowSpan = 9;
                    lp.colSpan = 15;
                } else {
                    lp.rowSpan = 7;
                    lp.colSpan = 12;
                }
            } else {
                //在这里设置了区域索引,这是标题栏0内容
                lp.sectionIndex = 0;
                if (position == 0 || position == 6) {
                    lp.rowSpan = 3;
                    lp.colSpan = 8;
                } else {
                    lp.rowSpan = 6;
                    lp.colSpan = 8;
                }
            }
            itemView.setLayoutParams(lp);
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public void setDatas(List<ItemBean> datas) {
        this.mDatas = datas;
    }

    /**
     * 在这里去实现每一行去增加一个标题栏
     * @param index
     * @param parent
     * @return
     */
    @Override
    public View getTitleView(int index, RecyclerView parent) {
        Log.d(TAG, "getTitleView: " + index);
        switch (index) {
            case 1:
                return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_title2, parent, false);
            case 2:
                return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_title3, parent, false);
        }
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_title, parent, false);
    }

    class MetroHolder extends RecyclerView.ViewHolder {

        ImageView mImgView;
        TextView mTitle;

        public MetroHolder(View itemView) {
            super(itemView);
            mImgView = itemView.findViewById(R.id.image);
            mTitle = itemView.findViewById(R.id.title);
        }
    }


}
