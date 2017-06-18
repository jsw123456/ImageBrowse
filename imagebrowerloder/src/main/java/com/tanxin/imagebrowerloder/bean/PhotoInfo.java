package com.tanxin.imagebrowerloder.bean;

import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by TANXIN on 2017/6/13.
 */

public class PhotoInfo implements Parcelable {
    private String url;
    private Rect mRect;

    public PhotoInfo() {
    }
    public PhotoInfo(String url) {
        this.url = url;
    }

    public PhotoInfo(String url, Rect mRect) {
        this.url = url;
        this.mRect = mRect;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Rect getmRect() {
        return mRect;
    }

    public void setmRect(Rect mRect) {
        this.mRect = mRect;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeParcelable(this.mRect, flags);
    }

    protected PhotoInfo(Parcel in) {
        this.url = in.readString();
        this.mRect = in.readParcelable(Rect.class.getClassLoader());
    }

    public static final Parcelable.Creator<PhotoInfo> CREATOR = new Parcelable.Creator<PhotoInfo>() {
        @Override
        public PhotoInfo createFromParcel(Parcel source) {
            return new PhotoInfo(source);
        }

        @Override
        public PhotoInfo[] newArray(int size) {
            return new PhotoInfo[size];
        }
    };
}
