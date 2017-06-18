package com.tanxin.imagebrowerloder.utils;

import android.content.Context;
import android.view.WindowManager;

/**
 * Created by TANXIN on 2017/6/15.
 */

public class Utils {
    public static boolean isNull(String str){
        if (str==null|| str.length() <= 0){
            return true;
        }
        return false;
    }


    /**
     * 获取屏幕宽度
     * @param mContext context
     * */
    public static int getScreenWidth(Context mContext){
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }


    /**
     * 获取屏幕高度
     * @param mContext context
     * */
    public static int getScreenHeight(Context mContext){
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getHeight();
    }
}
