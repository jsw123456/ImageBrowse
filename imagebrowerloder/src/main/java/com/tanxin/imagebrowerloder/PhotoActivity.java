package com.tanxin.imagebrowerloder;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.tanxin.imagebrowerloder.adapter.PhotoPagerAdapter;
import com.tanxin.imagebrowerloder.bean.PhotoInfo;
import com.tanxin.imagebrowerloder.widget.PhotoViewPager;
import com.tanxin.imagebrowerloder.widget.SlidingImageView;

import java.util.ArrayList;
import java.util.List;

public class PhotoActivity extends FragmentActivity {
    private PhotoViewPager pager;
    private TextView tv_num,tv_save;
    private PhotoPagerAdapter adapter;

    private List<PhotoInfo> imgUrls;
    private List<PhotoFragment> fragments = new ArrayList<>();
    private boolean isTransformOut = false;
    private int currentIndex;

    public static void startActivity(Context context, ArrayList<PhotoInfo> imgDatas, int position) {
        // 图片的地址
        //获取图片的bitmap
        Intent intent = new Intent(context, PhotoActivity.class);
        intent.putParcelableArrayListExtra("imagePaths", imgDatas);
        intent.putExtra("position", position);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        initData();
        initView();
    }

    private void initData() {
        imgUrls = getIntent().getParcelableArrayListExtra("imagePaths");
        currentIndex = getIntent().getIntExtra("position",-1);
        if (imgUrls != null) {
            for (int i = 0; i < imgUrls.size(); i++) {
                PhotoFragment fragment = new PhotoFragment();
                Bundle bundle = new Bundle();
                bundle.putString(PhotoFragment.KEY_PATH, imgUrls.get(i).getUrl());
                bundle.putParcelable(PhotoFragment.KEY_START_RECT, imgUrls.get(i).getmRect());
                bundle.putBoolean(PhotoFragment.KEY_SHOWTRANS, currentIndex==i);
                fragment.setArguments(bundle);
                fragments.add(fragment);
            }
        } else {
            finish();
        }
    }

    private void initView() {
        pager = (PhotoViewPager) findViewById(R.id.pager);
        tv_num = (TextView) findViewById(R.id.tv_num);
        tv_save = (TextView) findViewById(R.id.tv_save);
        tv_num.setText(currentIndex+1+"/"+imgUrls.size());
        adapter = new PhotoPagerAdapter(getSupportFragmentManager(),fragments);
        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (tv_num != null) {
                    tv_num.setText((position + 1) + "/" + imgUrls.size());
                }
                currentIndex = position;
                pager.setCurrentItem(position, true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        pager.setCurrentItem(currentIndex);

        pager.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                pager.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                final PhotoFragment fragment = fragments.get(currentIndex);
                fragment.transformIn();
            }
        });
        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoFragment fragment = fragments.get(currentIndex);
                fragment.saveImage();
            }
        });
    }


    //退出预览的动画
    public void transformOut() {
        if (isTransformOut) {
            return;
        }
        isTransformOut = true;
        int currentItem = pager.getCurrentItem();
        if (currentItem < imgUrls.size()) {
            PhotoFragment fragment = fragments.get(currentItem);
            tv_num.setVisibility(View.GONE);
            fragment.changeBg(Color.TRANSPARENT);
            fragment.transformOut(new SlidingImageView.onTransformListener() {
                @Override
                public void onTransformCompleted(SlidingImageView.Status status) {
                    exit();
                }
            });
        } else {
            exit();
        }
    }

    /**
     * 关闭页面
     */
    private void exit() {
        finish();
        overridePendingTransition(0, 0);
    }

    @Override
    public void onBackPressed() {
        transformOut();
    }

}
