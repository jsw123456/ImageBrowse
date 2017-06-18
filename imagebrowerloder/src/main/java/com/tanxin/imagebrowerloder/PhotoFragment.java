package com.tanxin.imagebrowerloder;


import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.tanxin.imagebrowerloder.base.BaseFragment;
import com.tanxin.imagebrowerloder.base.ImageDownLoadCallBack;
import com.tanxin.imagebrowerloder.loading.LoadingView;
import com.tanxin.imagebrowerloder.utils.Utils;
import com.tanxin.imagebrowerloder.widget.SlidingImageView;

import uk.co.senab.photoview.PhotoViewAttacher;


public class PhotoFragment extends BaseFragment {
    private SlidingImageView photoView;
    private LoadingView viewLoad;
    private View rootView;

    private boolean isShowTrans;
    private String imgUrl;

    public static final String KEY_START_RECT = "IMG_RECT";
    public static final String KEY_SHOWTRANS= "isShowTrans";
    public static final String KEY_PATH = "PATN";

    @Override
    public int getContentViewId() {
        return R.layout.fragment_photo;
    }

    @Override
    protected void initView(View view) {
        photoView = (SlidingImageView) view.findViewById(R.id.photoView);
        viewLoad = (LoadingView) view.findViewById(R.id.view_load);
        rootView = view.findViewById(R.id.rootView);
    }
    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        if (bundle != null){
            imgUrl = getArguments().getString(KEY_PATH);
            Rect imgRect = getArguments().getParcelable(KEY_START_RECT);
            isShowTrans = getArguments().getBoolean(KEY_SHOWTRANS);

            if (imgRect!=null) photoView.setThumbRect(imgRect);

            Glide.with(this).load(imgUrl).asBitmap().fitCenter()
                    .placeholder(R.mipmap.ic_head_load_detail).error(R.mipmap.ic_head_load_detail)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            photoView.setImageBitmap(resource);
                            viewLoad.setVisibility(View.GONE);
                        }
                        @Override
                        public void onLoadStarted(Drawable placeholder) {
                            super.onLoadStarted(placeholder);
                           photoView.setImageDrawable(placeholder);
                    }

                        @Override
                        public void onLoadFailed(Exception e, Drawable errorDrawable) {
                            super.onLoadFailed(e, errorDrawable);
                            photoView.setImageDrawable(errorDrawable);
                        }

                    });
        }
        if (!isShowTrans)  rootView.setBackgroundColor(Color.BLACK);
        photoView.setMinimumScale(1f);

        photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                if (photoView.checkMinScale()) {
                    ((PhotoActivity) getActivity()).transformOut();
                }
            }
        });

        photoView.setAlphaChangeListener(new SlidingImageView.OnAlphaChangeListener() {
            @Override
            public void onAlphaChange(int alpha) {
                rootView.setBackgroundColor(getColorWithAlpha(alpha / 255f, Color.BLACK));
            }
        });

        photoView.setTransformOutListener(new SlidingImageView.OnTransformOutListener() {
            @Override
            public void onTransformOut() {
                if (photoView.checkMinScale()) {
                    ((PhotoActivity) getActivity()).transformOut();
                }
            }
        });
    }

    @Override
    protected void onLazyData() {
        if (isLoaded)return;
        isLoaded = true;
        //加载原图
        Glide.with(this).load(imgUrl).asBitmap().placeholder(R.mipmap.ic_head_load_detail).error(R.mipmap.ic_head_load_detail)
                .fitCenter().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                isLoadFinish = true;
                viewLoad.setVisibility(View.GONE);
                photoView.setImageBitmap(resource);
            }

            @Override
            public void onLoadStarted(Drawable placeholder) {
                super.onLoadStarted(placeholder);
                viewLoad.setVisibility(isLoadFinish?View.GONE:View.VISIBLE);
                photoView.setImageDrawable(placeholder);
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                super.onLoadFailed(e, errorDrawable);
                Toast.makeText(context, "加载失败", Toast.LENGTH_SHORT).show();
                photoView.setImageDrawable(errorDrawable);
            }
        });
    }

    public void saveImage(){
        if (Utils.isNull(imgUrl)){
            Toast.makeText(context, "没有获取到图片信息", Toast.LENGTH_SHORT).show();
        }else {
            //利用Picasso加载图片
            DownLoadImageService service = new DownLoadImageService(context, imgUrl, new ImageDownLoadCallBack() {

                @Override
                public void onDownLoadSuccess(Bitmap bitmap) {
                    DownHandle.sendEmptyMessage(1);
                }

                @Override
                public void onDownLoadFailed() {
                    DownHandle.sendEmptyMessage(2);
                }
            });
            new Thread(service).start();
        }

    }

    public Handler DownHandle = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    Toast.makeText(context, "保存成功", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(context, "保存失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


    public static int getColorWithAlpha(float alpha, int baseColor) {
        int a = Math.min(255, Math.max(0, (int) (alpha * 255))) << 24;
        int rgb = 0x00ffffff & baseColor;
        return a + rgb;
    }

    private boolean isLoaded = false;
    private boolean isLoadFinish = false;

    public void transformIn() {
        photoView.transformIn(new SlidingImageView.onTransformListener() {
            @Override
            public void onTransformCompleted(SlidingImageView.Status status) {
                rootView.setBackgroundColor(Color.BLACK);
            }
        });
    }

    public void transformOut(SlidingImageView.onTransformListener listener) {
        photoView.transformOut(listener);
    }

    public void changeBg(int color) {
        rootView.setBackgroundColor(color);
    }



}
