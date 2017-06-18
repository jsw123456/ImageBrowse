package com.tanxin.imagebrowerloder.base;

import android.graphics.Bitmap;

/**
 * Created by TanXin on 2017/3/23.
 */

public interface ImageDownLoadCallBack {
    void onDownLoadSuccess(Bitmap bitmap);
    void onDownLoadFailed();
}
