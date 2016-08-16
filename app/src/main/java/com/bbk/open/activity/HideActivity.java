package com.bbk.open.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.bbk.open.Utils.FilesDao;
import com.bbk.open.adapter.MyAdapter;
import com.bbk.open.globlesearch.R;
import com.bbk.open.model.FileInfo;


import java.sql.SQLException;
import java.util.List;

public class HideActivity extends AppCompatActivity implements SwipeMenuListView.OnMenuItemClickListener {

    private List<FileInfo> list;
    private SwipeMenuListView listView;
    private MyAdapter adapter;
    private int width;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hide);
        initView();
        width = this.getResources().getDisplayMetrics().widthPixels;
        FilesDao dao = new FilesDao(this);
        try {
            list = dao.qurryHideFiles();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        adapter = new MyAdapter(this);
        adapter.setData(list);
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem restoreItem = new SwipeMenuItem(HideActivity.this);
                restoreItem.setBackground(new ColorDrawable(Color.rgb(255,131,69)));
                restoreItem.setTitle("还原");
                restoreItem.setWidth(width/4);
                restoreItem.setTitleSize(18);
                restoreItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(restoreItem);

                SwipeMenuItem deleteItem = new SwipeMenuItem(HideActivity.this);
                deleteItem.setBackground(new ColorDrawable(Color.rgb(1, 159, 222)));
                deleteItem.setTitle("删除");
                deleteItem.setTitleSize(18);
                deleteItem.setTitleColor(Color.WHITE);
                deleteItem.setWidth(width/4);
                menu.addMenuItem(deleteItem);
            }
        };
        listView.setMenuCreator(creator);
        listView.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
        listView.setAdapter(adapter);
        listView.setOnMenuItemClickListener(this);


    }

    private void initView() {
        listView = (SwipeMenuListView) findViewById(R.id.listview_hide);
    }


    @Override
    public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
        FilesDao dao = new FilesDao(HideActivity.this);
        FileInfo info = list.get(position);
        switch (index){
            case 0:
                info.setTag(FileInfo.TAG_DEFAULT);
                dao.update(info);
                list.remove(position);
                adapter.notifyDataSetChanged();
                Toast.makeText(HideActivity.this, "文件已恢复", Toast.LENGTH_LONG).show();
                break;
            case 1:
                info.setTag(FileInfo.TAG_DELETE);
                dao.update(info);
                list.remove(position);
                adapter.notifyDataSetChanged();
                Toast.makeText(HideActivity.this, "文件已删除", Toast.LENGTH_LONG).show();
                break;
        }
        return true;
    }
}
