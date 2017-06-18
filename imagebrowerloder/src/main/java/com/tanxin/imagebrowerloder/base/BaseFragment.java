package com.tanxin.imagebrowerloder.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by TANXIN on 2017/6/14.
 */

public abstract class BaseFragment extends Fragment {
    private boolean isReady;
    private boolean isFirst = true;
    private boolean isVisible;
    protected Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getContentViewId(),container,false);
        this.context = getActivity();
        initView(view);
        initData();
        return view;
    }

    public abstract int getContentViewId();
    protected abstract void initView(View view);
    protected abstract void initData();


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            onVisible();
        } else {
            onInvisible();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            onVisible();
        } else {
            onInvisible();
        }
    }

    protected void onVisible() {
        isVisible = true;
        lazyLoad();
    }

    protected void onInvisible() {
        isVisible = false;
    }

    protected void lazyLoad() {
        if (isReady && isVisible) {
            if (isFirst) {
                isFirst = false;
                onLazyData();
            }
        }
    }

    protected abstract void onLazyData();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isReady = false;
    }
}
