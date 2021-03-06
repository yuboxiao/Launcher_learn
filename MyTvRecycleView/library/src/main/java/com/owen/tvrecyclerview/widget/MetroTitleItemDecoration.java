package com.owen.tvrecyclerview.widget;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.owen.tvrecyclerview.utils.Loger;

/**
 * Created by owen on 2017/6/16.
 */

public class MetroTitleItemDecoration extends RecyclerView.ItemDecoration {
    /**
     * 用来保存标题栏
     */
    private final SparseArray<View> mTitleViews = new SparseArray<>();
    private final Rect mTempRect = new Rect();
    private Adapter mAdapter;
    private MetroGridLayoutManager.LayoutParams mTempItemLp;
    private View mTempTitleView;
    private int mTempSectionIndex = -1;
    
    public MetroTitleItemDecoration(Adapter adapter) {
        this.mAdapter = adapter;
    }
    
    private void createAndMeasureTitleView(RecyclerView parent) {
        if(mAdapter == null)
            return;
        //获取标题栏的视图，通过view.inflater进行填充后的view
        mTempTitleView = mAdapter.getTitleView(mTempItemLp.sectionIndex, parent);
        if(null != mTempTitleView) {
            ViewGroup.LayoutParams lp = mTempTitleView.getLayoutParams();
            if (null == lp) {
                lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                mTempTitleView.setLayoutParams(lp);
            }

            final int left = parent.getPaddingLeft() + mTempItemLp.leftMargin;
            final int right = parent.getPaddingRight() + mTempItemLp.rightMargin;
            final int top = parent.getPaddingTop() + mTempItemLp.topMargin;
            final int bottom = parent.getPaddingBottom() + mTempItemLp.bottomMargin;

            final int width = lp.width < 0 ? parent.getMeasuredWidth() - left - right : lp.width;
            final int height = lp.height < 0 ? parent.getMeasuredHeight() - top - bottom : lp.height;

            final int widthSpec = View.MeasureSpec.makeMeasureSpec(width,
                    lp.width == ViewGroup.LayoutParams.WRAP_CONTENT ? View.MeasureSpec.AT_MOST : View.MeasureSpec.EXACTLY);
            final int heightSpec = View.MeasureSpec.makeMeasureSpec(height,
                    lp.width == ViewGroup.LayoutParams.WRAP_CONTENT ? View.MeasureSpec.AT_MOST : View.MeasureSpec.EXACTLY);

        /*//测量处理
        int widthSpec = View.MeasureSpec.makeMeasureSpec(parent.getWidth(), View.MeasureSpec.EXACTLY);
         int heightSpec = View.MeasureSpec.makeMeasureSpec(parent.getHeight(),View.MeasureSpec.UNSPECIFIED);
        //计算宽高
        int childWidth = ViewGroup.getChildMeasureSpec(widthSpec, parent.getPaddingLeft() + parent.getPaddingRight(),lp.width);
        int childHeight = ViewGroup.getChildMeasureSpec(heightSpec, parent.getPaddingTop() + parent.getPaddingBottom(), lp.height);*/

            //设置宽高
            mTempTitleView.measure(widthSpec, heightSpec);
            mTitleViews.put(mTempItemLp.sectionIndex, mTempTitleView);
        }
    }

    /**
     * 重写recycleView的方法, getItemOffsets 撑开了 ItemView 的上下左右间隔区域
     *   outRect代表的是RecycleView其中一个itemView的外围矩形 默认值都为0
     *   view代表的就是itemView本身
     *   parent代表的就是recycleView
     *   state状态值
     * @param outRect
     * @param view
     * @param parent
     * @param state
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //获取itemView的布局
        mTempItemLp = (MetroGridLayoutManager.LayoutParams) view.getLayoutParams();
        /**
         * 这里的viewAdapterPos代表的是adapter数据里的第几个
         * isSecStart代表的是是否是这个区域开始的第一行
         * getTopDecorationHeight目前都为0
         */
        Loger.i("ViewAdapterPosition="+mTempItemLp.getViewAdapterPosition()+" isSectionStart="+mTempItemLp.isSectionStart 
                + " Decorated Top="+parent.getLayoutManager().getTopDecorationHeight(view));
        //标题下的第一行
        if(mTempItemLp.isSectionStart) {
            mTempTitleView = mTitleViews.get(mTempItemLp.sectionIndex);

            if(null == mTempTitleView) {
                //创建并且测量标题栏
                createAndMeasureTitleView(parent);
            }

            if(null != mTempTitleView) {
                final boolean isVertical = ((MetroGridLayoutManager) parent.getLayoutManager()).isVertical();
                //左、上、右、下
                outRect.set(isVertical ? 0 : mTempTitleView.getMeasuredWidth(), isVertical ? mTempTitleView.getMeasuredHeight() : 0, 0, 0);
            }
        }
    }

    /**
     * getItemOffsets 是针对每一个 ItemView，
     * 而 onDraw 方法却是针对 RecyclerView 本身，
     * 所以在 onDraw 方法中需要遍历屏幕上可见的 ItemView，
     * 分别获取它们的位置信息，
     * 这里的onDraw就是要绘制标题栏的内容
     * @param c
     * @param parent
     * @param state
     */
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        final int count = parent.getChildCount();
        for(int i = 0; i < count; i++) {
            //获取每一个itemView的layoutParams
            mTempItemLp = (MetroGridLayoutManager.LayoutParams) parent.getChildAt(i).getLayoutParams();
            //如果当前就是第一行的时候
            if(mTempSectionIndex != mTempItemLp.sectionIndex && mTempItemLp.isSectionStart) {
                //这里mTitleViews存储的是第一行view的信息
                mTempTitleView = mTitleViews.get(mTempItemLp.sectionIndex);
                if(null != mTempTitleView) {
                    final View itemView = parent.getChildAt(i);
                    parent.getLayoutManager().getDecoratedBoundsWithMargins(itemView, mTempRect);
                    //标题栏坐标的区域就是item的getLeft
                    final int left = itemView.getLeft();
                    //标题栏的头部的区域就是撑开的包裹itemview的矩形头部
                    final int top = mTempRect.top;
                    //标题栏的右方区域就是left+标题栏的宽度
                    final int right = left + mTempTitleView.getMeasuredWidth();
                    //标题栏的下方就是top+标题栏的高度
                    final int bottom = top + mTempTitleView.getMeasuredHeight();
                    c.save();
                    //重新布局标题栏
                    mTempTitleView.layout(left, top, right, bottom);
                    //平移到最上角
                    c.translate(left, top);
                    //开始绘制标题栏
                    mTempTitleView.draw(c);
                    //恢复画布，画下一个标题栏
                    c.restore();
                    Loger.i("mTitleView.draw ... sectionIndex="+mTempItemLp.sectionIndex);
                }
                mTempSectionIndex = mTempItemLp.sectionIndex;
            }
        }
        mTempSectionIndex = -1;
        mTempItemLp = null;
        mTempTitleView = null;
    }
    
    public static interface Adapter {
        View getTitleView(int index, RecyclerView parent);
    }
}
