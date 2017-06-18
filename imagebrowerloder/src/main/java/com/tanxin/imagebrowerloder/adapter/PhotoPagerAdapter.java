package com.tanxin.imagebrowerloder.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tanxin.imagebrowerloder.PhotoFragment;

import java.util.List;

/**
 * Created by TANXIN on 2017/6/14.
 */

public class PhotoPagerAdapter extends FragmentPagerAdapter {
    private List<PhotoFragment> fragments;

    public PhotoPagerAdapter(FragmentManager fm,List<PhotoFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }



    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
