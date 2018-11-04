package com.whut.mytvrecycleview;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.whut.mytvrecycleview.fragment.GridFragment;
import com.whut.mytvrecycleview.fragment.ListFragment;
import com.whut.mytvrecycleview.fragment.MetroFragment;
import com.whut.mytvrecycleview.fragment.SpannableFragment;
import com.whut.mytvrecycleview.fragment.StaggeredFragment;
import com.whut.mytvrecycleview.fragment.UpateDataFragment;
import com.whut.mytvrecycleview.fragment.V7GridFragment;
import com.whut.mytvrecycleview.view.TabLayout;
import com.whut.mytvrecycleview.view.TvTabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends FragmentActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private final int[] mTitleResIds = {
            R.string.tab_metro,
            R.string.tab_list,
            R.string.tab_grid,
            R.string.tab_v7_grid,
            R.string.tab_staggered,
            R.string.tab_spannable,
            R.string.tab_update_data,
    };

    @BindView(R.id.tab_layout)
    TvTabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mTabLayout.addOnTabSelectedListener(new TabSelectedListener());

        mTabLayout.setScaleValue(1.1f);
        setTabsTitles();

        setUpListener();
    }

    private void setUpListener() {
    }


    private class TabSelectedListener implements TabLayout.OnTabSelectedListener {

        private Fragment mFragment;
        private int mLayoutResIds[] = {
                R.layout.layout_metro_grid,
                R.layout.layout_list,
                R.layout.layout_grid,
                R.layout.layout_grid2,
                R.layout.layout_staggered_grid,
                R.layout.layout_spannable_grid,
                R.layout.layout_update_data_changed,
        };

        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            int pos = tab.getPosition();
            Log.d(TAG, "onTabSelected: postion--->" + pos);

            mFragment = getSupportFragmentManager().findFragmentByTag(String.valueOf(pos));
            FragmentTransaction fmt = getSupportFragmentManager().beginTransaction();

            if (mFragment == null) {
                switch (mLayoutResIds[pos]) {
                    case R.layout.layout_metro_grid:
                        mFragment = MetroFragment.newInstance();
                        break;
                    case R.layout.layout_list:
                        mFragment = ListFragment.newInstance();
                        break;
                    case R.layout.layout_grid:
                        mFragment = GridFragment.newInstance();
                        break;
                    case R.layout.layout_grid2:
                        mFragment = V7GridFragment.newInstance();
                        break;
                    case R.layout.layout_staggered_grid:
                        mFragment = StaggeredFragment.newInstance();
                        break;
                    case R.layout.layout_spannable_grid:
                        mFragment = SpannableFragment.newInstance();
                        break;
                    case R.layout.layout_update_data_changed:
                        mFragment = UpateDataFragment.newInstance();
                        break;
                }
                fmt.add(R.id.content, mFragment, String.valueOf(pos));
            } else {
                fmt.attach(mFragment);
            }
            fmt.commit();
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
            if (mFragment != null) {
                FragmentTransaction fmt = getSupportFragmentManager().beginTransaction();
                fmt.detach(mFragment);
                fmt.commit();
            }
        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    }

    /**
     * 设置标题栏
     */
    private void setTabsTitles() {
        for (int i = 0; i < mTitleResIds.length; i++) {
            if (i == 0) {
                mTabLayout.addTab(mTabLayout.newTab().
                                setText(mTitleResIds[i]),
                        true);
            } else {
                mTabLayout.addTab(mTabLayout.newTab().
                                setText(mTitleResIds[i]),
                        false);
            }
        }
    }
}
