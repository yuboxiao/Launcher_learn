package com.whut.mytvrecycleview.view;

import android.content.Context;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.ViewPropertyAnimator;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

/**
 * 作者: x00378851
 * 日期: 2018/10/21 11:14
 */
public class TvTabLayout extends TabLayout {

    private static final String TAG = TvTabLayout.class.getSimpleName();
    private float mScaleValue = 1f;

    public TvTabLayout(Context context) {
        super(context);
    }

    public TvTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 提供接口给外界设置放大倍数
     * @param scaleValue
     */
    public void setScaleValue(@FloatRange(from = 1.0) float scaleValue) {
        this.mScaleValue = scaleValue;
    }

    /**
     * 当tab被选中后更改动画效果
     *
     * @param tab
     */
    @Override
    protected void onTabSelected(@NonNull Tab tab) {
        ViewPropertyAnimator animation = tab.getView().animate();
        if (mScaleValue > 1) {
            animation.scaleX(mScaleValue).scaleY(mScaleValue).
                    setDuration(700).
                    setInterpolator(new DecelerateInterpolator()).
                    start();
            return;
        }

        animation.scaleX(1.2f).scaleY(1.2f)
                .translationY((getHeight() - tab.getView().getHeight()) / 2)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .setDuration(700)
                .start();

    }

    /**
     * 没有被选中后的效果
     * @param tab
     */
    @Override
    protected void onTabUnselected(@NonNull Tab tab) {
        ViewPropertyAnimator animator = tab.getView().animate();
        if (mScaleValue > 1) {
            animator.scaleX(1).scaleY(1)
                    .setDuration(500)
                    .start();
            return;
        }
        animator.scaleX(1f).scaleY(1f)
                .translationY(0)
                .setInterpolator(new DecelerateInterpolator())
                .setDuration(500)
                .start();
    }
}
