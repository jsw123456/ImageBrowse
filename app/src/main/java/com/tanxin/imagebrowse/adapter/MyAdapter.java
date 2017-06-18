package com.tanxin.imagebrowse.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tanxin.imagebrowerloder.bean.PhotoInfo;
import com.tanxin.imagebrowse.R;

import java.util.List;

/**
 * Created by TANXIN on 2017/6/13.
 */

public class MyAdapter extends BaseQuickAdapter<PhotoInfo,BaseViewHolder> {
    public MyAdapter(@Nullable List<PhotoInfo> data) {
        super(R.layout.item_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PhotoInfo item) {
        ImageView img = helper.getView(R.id.img);
        Glide.with(mContext).load(item.getUrl()).centerCrop().into(img);

    }
}
