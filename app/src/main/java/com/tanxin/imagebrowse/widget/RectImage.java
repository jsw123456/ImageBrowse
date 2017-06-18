package com.tanxin.imagebrowse.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by TANXIN on 2017/6/13.
 */

public class RectImage extends ImageView {
    public RectImage(Context context) {
        super(context);
    }

    public RectImage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RectImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
