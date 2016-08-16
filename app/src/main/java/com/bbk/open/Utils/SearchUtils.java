package com.bbk.open.Utils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.provider.MediaStore;
import android.util.Log;

import com.bbk.open.model.FileInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import opensource.jpinyin.PinyinFormat;
import opensource.jpinyin.PinyinHelper;

/**
 * 搜索工具类
 * Created by Administrator on 2016/7/13.
 */
public class SearchUtils {

    private Context context;
    private List<FileInfo> apkList = new ArrayList<>();
    private List<FileInfo> piecemealList = new ArrayList<>();

    public SearchUtils(Context context) {
        this.context = context;
    }

    public List<FileInfo> getApkList() {
        return apkList;
    }

    public List<FileInfo> getPiecemealList() {
        return piecemealList;
    }


    public List<FileInfo> SearchByType(int type) {
        switch (type) {
            case FileInfo.TYPE_APP:
                return SearchApps();
            case FileInfo.TYPE_CONTACT:
                return SearchContacts();
            case FileInfo.TYPE_SMS:
                return SearchSms();
            case FileInfo.TYPE_IMAGE:
                return SearchImages();
            case FileInfo.TYPE_AUDIO:
                return SearchAudio();
            case FileInfo.TYPE_VIDEO:
                return SearchVedio();
            case FileInfo.TYPE_TXT:
                Log.i("qq", "txtxtxtxt");
                SearchPiecemealInfo(Environment.getExternalStorageDirectory());
                return getPiecemealList();
            case FileInfo.TYPE_INSTALL:
                SearchApks(Environment.getExternalStorageDirectory());
                return getApkList();
            default:
                return null;
        }
    }

    /**
     * 搜索结果返回所有应用
     */
    public List<FileInfo> SearchApps() {
        List<FileInfo> appList = new ArrayList<>();
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> resolveInfos = context.getPackageManager().queryIntentActivities(intent, 0);
        for (ResolveInfo resolveInfo : resolveInfos) {
            Drawable icon = resolveInfo.loadIcon(context.getPackageManager());
            String iconstr = (PhotoHandle.drawableToByte(icon));
            String name = resolveInfo.loadLabel(context.getPackageManager()).toString();
            String path = resolveInfo.activityInfo.packageName;
            String nameToPinyin = PinyinHelper.convertToPinyinString(name, "", PinyinFormat.WITHOUT_TONE);
            FileInfo info = new FileInfo(name, path, nameToPinyin, iconstr, FileInfo.TYPE_APP);
            appList.add(info);
        }
        return appList;
    }

    /**
     * 搜索结果返回所有联系人
     */
    public List<FileInfo> SearchContacts() {
        List<FileInfo> contactList = new ArrayList<>();
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(Contacts.CONTENT_URI, new String[]{Contacts._ID, Contacts.DISPLAY_NAME}, null, null, null);
        while (cursor.moveToNext()) {
            String phoneNumber = "";
            int _id = cursor.getInt(0);
            String name = cursor.getString(1);
            name = name + "|" + PinyinHelper.convertToPinyinString(name, "", PinyinFormat.WITHOUT_TONE);
            Cursor phoneNumberCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, new String[]{"data1"}, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "==" + _id, null, null);
            while (phoneNumberCursor.moveToNext()) {
                phoneNumber = phoneNumber + " " + phoneNumberCursor.getString(0);
            }
            FileInfo info = new FileInfo(name, _id + "", phoneNumber.trim(), "", FileInfo.TYPE_CONTACT);
            contactList.add(info);
            phoneNumberCursor.close();
        }
        cursor.close();
        return contactList;
    }

    /**
     * 搜索结果返回所有短信
     */
    public List<FileInfo> SearchSms() {
        Uri uri = Uri.parse("content://sms/");
        List<FileInfo> smsList = new ArrayList<>();
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(uri, new String[]{
                " a.date,a.snippet",
                " b.address from threads a",
                " sms b where a._id = b.thread_id  group by b.address-- ",
        }, null, null, null);
        while (cursor.moveToNext()) {
            String path = cursor.getString(2);
            String body = cursor.getString(1);
            String name = "";
            Uri personUri = Uri.withAppendedPath(
                    ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
                    Uri.encode(path));
            Cursor cur = context.getContentResolver().query(personUri, new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME}, null, null, null);
            if (cur.moveToFirst()) {
                name = cur.getString(cur.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
            }
            FileInfo info = new FileInfo(name, path, path, body, FileInfo.TYPE_SMS);
            smsList.add(info);
            cur.close();
        }
        cursor.close();
        return smsList;
    }


    /**
     * 搜索结果返回所有图片
     */
    public List<FileInfo> SearchImages() {
        List<FileInfo> imageList = new ArrayList<>();
        ContentResolver resolver = context.getContentResolver();
        Cursor imageCursor = resolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA, MediaStore.Images.Media.DISPLAY_NAME}, null, null, null);
        while (imageCursor.moveToNext()) {
            int _id = imageCursor.getInt(0);
            String imagePath = imageCursor.getString(1);
            String name = imageCursor.getString(2);
            FileInfo info = new FileInfo(name, imagePath, "", "", FileInfo.TYPE_IMAGE);
            imageList.add(info);
        }
        imageCursor.close();
        return imageList;
    }


    /**
     * 搜索结果返回所有视频
     */
    public List<FileInfo> SearchVedio() {
        List<FileInfo> videoList = new ArrayList<>();
        ContentResolver resolver = context.getContentResolver();
        Cursor videoCursor = resolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, new String[]{MediaStore.Video.Media._ID, MediaStore.Video.Media.DATA, MediaStore.Video.Media.DISPLAY_NAME}, null, null, null);
        while (videoCursor.moveToNext()) {
            int _id = videoCursor.getInt(0);
            String videoPath = videoCursor.getString(1);
            String name = videoCursor.getString(2);
            FileInfo info = new FileInfo(name, videoPath, "", "", FileInfo.TYPE_VIDEO);
            videoList.add(info);
        }
        videoCursor.close();
        return videoList;
    }

    /**
     * 搜索结果返回所有音频
     */
    public List<FileInfo> SearchAudio() {
        List<FileInfo> audioList = new ArrayList<>();
        ContentResolver resolver = context.getContentResolver();
        Cursor audioCursor = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, new String[]{MediaStore.Audio.Media.ALBUM_ID, MediaStore.Audio.Media.DISPLAY_NAME, MediaStore.Audio.Media.DATA}, null, null, null);
        while (audioCursor.moveToNext()) {
            int _id = audioCursor.getInt(0);
            String name = audioCursor.getString(1);
            String path = audioCursor.getString(2);
            String nameToPinyin = "";
            String albumPath = "";
            if (name != null) {
                Cursor albumCursor = resolver.query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, new String[]{MediaStore.Audio.Albums.ALBUM_ART}, MediaStore.Audio.Albums._ID + "=" + _id, null, null);
                if (albumCursor != null) {
                    albumCursor.moveToFirst();
                    albumPath = albumCursor.getString(0);
                    albumCursor.close();
                }
            }
            nameToPinyin = PinyinHelper.convertToPinyinString(name, "", PinyinFormat.WITHOUT_TONE);
            FileInfo info = new FileInfo(name, path, nameToPinyin, albumPath, FileInfo.TYPE_AUDIO);
            audioList.add(info);
        }
        audioCursor.close();
        return audioList;
    }

    /**
     * 搜索结果返回后缀名为zip，apk，txt，pdf，word，html，html文件的集合，跟据type区分不同
     */
    public void SearchPiecemealInfo(File file) {
        if (file.isFile()) {
            String name = file.getName();
            String path = null;
            if (name.toLowerCase().endsWith(".txt") || name.toLowerCase().endsWith(".xlsx") || name.toLowerCase().endsWith(".xls") || name.toLowerCase().endsWith(".docx") || name.toLowerCase().endsWith(".doc") || name.toLowerCase().endsWith(".pdf") || name.toLowerCase().endsWith(".ppt")) {
                path = file.getAbsolutePath();
                String nameToPinyin = PinyinHelper.convertToPinyinString(name, "", PinyinFormat.WITHOUT_TONE);
                FileInfo fileInfo = new FileInfo(name, path, nameToPinyin, "", ConvertUtil.convertType(name));
                piecemealList.add(fileInfo);

            }
        } else {
            File[] files = file.listFiles();
            if (files != null && files.length > 0) {
                for (File file_str : files) {
                    SearchPiecemealInfo(file_str);
                }
            }
        }

    }


    public void SearchApks(File file) {
        if (file.isFile()) {
            String name_s = file.getName();
            String apk_path = null;
            if (name_s.toLowerCase().endsWith(".apk")) {
                apk_path = file.getAbsolutePath();// apk文件的绝对路劲
                PackageManager pm = context.getPackageManager();
                PackageInfo packageInfo = pm.getPackageArchiveInfo(apk_path, PackageManager.GET_ACTIVITIES);
                if (packageInfo != null) {
                    ApplicationInfo appInfo = packageInfo.applicationInfo;
                    appInfo.sourceDir = apk_path;
                    appInfo.publicSourceDir = apk_path;
                    Drawable apk_icon = appInfo.loadIcon(pm);
                    String name = appInfo.loadLabel(pm).toString();
                    String nameToPinyin = PinyinHelper.convertToPinyinString(name, "", PinyinFormat.WITHOUT_TONE);
                    FileInfo fileInfo = new FileInfo(name, apk_path, nameToPinyin, PhotoHandle.drawableToByte(apk_icon), FileInfo.TYPE_INSTALL);
                    apkList.add(fileInfo);
                }
            }
        } else {
            File[] files = file.listFiles();
            if (files != null && files.length > 0) {
                for (File file_str : files) {
                    SearchApks(file_str);
                }
            }
        }
    }

}
