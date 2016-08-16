package com.bbk.open.activity;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import com.bbk.open.fragment.BaseFragment;
import com.bbk.open.globlesearch.R;
import com.bbk.open.model.FileInfo;
import com.bbk.open.view.CleanableEditText;
import com.bbk.open.view.PagerSlidingTabStripNumber;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements TextWatcher, View.OnClickListener{

    private BaseFragment appFragment;
    private BaseFragment contactFragment;
    private BaseFragment smsFragment;
    private BaseFragment videoFragment;
    private BaseFragment imageFragment;
    private BaseFragment audioFragment;
    private BaseFragment docFragment;
    private BaseFragment apkFragment;
    private ViewPager viewPager;
    private FragmentAdapter adapter;
    private PagerSlidingTabStripNumber tabStrip;
    private CleanableEditText auto_searchkey;
    private Button bt_search_web;

    private FloatingActionMenu menuYellow;
    private FloatingActionButton fabHide;
    private List<FloatingActionMenu> menus = new ArrayList<>();
    private List<Integer> lengthList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        init();
        sendNotification();
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewpage1r);
        tabStrip = (PagerSlidingTabStripNumber) findViewById(R.id.tab);
        auto_searchkey = (CleanableEditText) findViewById(R.id.tv_searchkey);
        bt_search_web = (Button) findViewById(R.id.bt_search_web);

        menuYellow = (FloatingActionMenu) findViewById(R.id.menu_yellow);
        fabHide = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab_hide);
    }

    private void init() {
        lengthList = new ArrayList<>();
        viewPager.setOffscreenPageLimit(8);
        adapter = new FragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabStrip.setIndicatorColorResource(R.color.home_bar_text_push);
        tabStrip.setIndicatorHeight(8);
        tabStrip.setUnderlineHeight(0);
        tabStrip.setViewPager(viewPager);
        auto_searchkey.addTextChangedListener(this);
        bt_search_web.setOnClickListener(this);

        fabHide.setOnClickListener(this);
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
            adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_search_web:
                Uri uri = Uri.parse("http://www.baidu.com.cn/s?wd=" + auto_searchkey.getText().toString() + "&cl=3");
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
                break;
            case R.id.fab_hide:
               startActivity(new Intent(this, HideActivity.class));
                break;
        }

    }




    public class FragmentAdapter extends FragmentPagerAdapter {

        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();

            //每次有新的搜索都必须清空
            lengthList.clear();

            //按序添加
            lengthList.add(appFragment.getResultListLength());
            lengthList.add(contactFragment.getResultListLength());
            lengthList.add(smsFragment.getResultListLength());
            lengthList.add(imageFragment.getResultListLength());
            lengthList.add(audioFragment.getResultListLength());
            lengthList.add(videoFragment.getResultListLength());
            lengthList.add(docFragment.getResultListLength());
            lengthList.add(apkFragment.getResultListLength());


            //设置并更新
            tabStrip.setLengthList(lengthList);
            tabStrip.notifyDataSetChanged();
        }

        @Override
        public Fragment getItem(int position) {
            if (position >= 0 && position < 8) {
                switch (position) {
                    case 0:
                        appFragment = BaseFragment.getInstance(FileInfo.TYPE_APP);
                        return appFragment;
                    case 1:
                        contactFragment = BaseFragment.getInstance(FileInfo.TYPE_CONTACT);
                        return contactFragment;
                    case 2:
                        smsFragment = BaseFragment.getInstance(FileInfo.TYPE_SMS);
                        return smsFragment;
                    case 3:
                        imageFragment = BaseFragment.getInstance(FileInfo.TYPE_IMAGE);
                        return imageFragment;
                    case 4:
                        audioFragment = BaseFragment.getInstance(FileInfo.TYPE_AUDIO);
                        return audioFragment;
                    case 5:
                        videoFragment = BaseFragment.getInstance(FileInfo.TYPE_VIDEO);
                        return videoFragment;
                    case 6:
                        docFragment = BaseFragment.getInstance(FileInfo.TYPE_TXT);
                        return docFragment;
                    case 7:
                        apkFragment = BaseFragment.getInstance(FileInfo.TYPE_INSTALL);
                        return apkFragment;
                    default:
                        break;
                }
            }
            return null;
        }

        @Override
        public int getCount() {
            return 8;
        }

        @Override
        public int getItemPosition(Object object) {
            if (object instanceof BaseFragment) {
                ((BaseFragment) object).updateContent(auto_searchkey.getText().toString());
            }
            return super.getItemPosition(object);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position >= 0 && position < 8) {
                switch (position) {
                    case 0:
                        return "应用";
                    case 1:
                        return "联系人";
                    case 2:
                        return "短信";
                    case 3:
                        return "图片";
                    case 4:
                        return "音乐";
                    case 5:
                        return "视频";
                    case 6:
                        return "文档";
                    case 7:
                        return "安装包";
                    default:
                        break;
                }
            }
            return null;
        }
    }


    public static final int NOTIFY_ID = 8080;
//
    public void sendNotification() {
        Resources res = getResources();
        Bitmap bmp= BitmapFactory.decodeResource(res, R.drawable.search_notification);
        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(
                this);
        notifyBuilder.setContentTitle("GlobalSearch");
        notifyBuilder.setContentText("点击进入GlobalSearch！");
        notifyBuilder.setSmallIcon(R.drawable.search_notification);
        notifyBuilder.setWhen(System.currentTimeMillis());
        notifyBuilder.setOngoing(true);//不能滑动删除
        notifyBuilder.setLargeIcon(bmp);
        notifyBuilder.setPriority(NotificationCompat.PRIORITY_MAX);//通知栏优先级设置为最高
        notifyBuilder.setTicker("Hi,Notification is here");
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //点击通知跳转
        Intent i = new Intent(this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        PendingIntent contentIntent = PendingIntent.getActivity(
                MainActivity.this,
                R.string.app_name,
                i,
                PendingIntent.FLAG_UPDATE_CURRENT);
        notifyBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFY_ID, notifyBuilder.build());

    }
}