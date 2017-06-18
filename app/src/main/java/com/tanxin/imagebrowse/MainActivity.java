package com.tanxin.imagebrowse;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tanxin.imagebrowerloder.PhotoActivity;
import com.tanxin.imagebrowerloder.bean.PhotoInfo;
import com.tanxin.imagebrowse.adapter.MyAdapter;
import com.tanxin.imagebrowse.data.MyData;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private ArrayList<PhotoInfo> photoList = new ArrayList<>();
    GridLayoutManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycle);
        manager = new GridLayoutManager(this,4);
        recyclerView.setLayoutManager(manager);
        final List<String> list = MyData.getPhoto();
        for (int i = 0; i <list.size() ; i++) {
            photoList.add(new PhotoInfo(list.get(i)));
        }
        adapter = new MyAdapter(photoList);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                assembleDataList();
                PhotoActivity.startActivity(MainActivity.this, photoList,position);
            }
        });
    }


    /**
     * 从第一个完整可见item逆序遍历，如果初始位置为0，则不执行方法内循环
     */
    private void computeBoundsBackward(int firstCompletelyVisiblePos) {
        for (int i = firstCompletelyVisiblePos;i < photoList.size(); i++) {
            View itemView = manager.findViewByPosition(i);
            Rect bounds = new Rect();
            if (itemView != null) {
                ImageView thumbView = (ImageView) itemView.findViewById(R.id.img);
                thumbView.getGlobalVisibleRect(bounds);
            }
            photoList.get(i).setmRect(bounds);
        }

    }
    private void assembleDataList() {
        computeBoundsBackward(manager.findFirstVisibleItemPosition());

    }
}
