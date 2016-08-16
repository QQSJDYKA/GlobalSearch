package com.bbk.open.Utils;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

import com.bbk.open.model.FileInfo;

import java.io.File;

/**
 * 打開文件方式工具類
 * 2016.6.18
 * 楊尚臻
 */
public class OpenUtils {


    public static Intent openFile(int type, String filePath, Context context) {
        if (type>3){
            File file = new File(filePath);
            if (!file.exists()){
                return null;
            }
        }
        switch (type) {
            case FileInfo.TYPE_APP:
                return getAppFileIntent(context, filePath);
            case FileInfo.TYPE_AUDIO:
                return getAudioFileIntent(filePath);
            case FileInfo.TYPE_CONTACT:
                return getContactFileIntent(filePath);
            case FileInfo.TYPE_IMAGE:
                return getImageFileIntent(filePath);
            case FileInfo.TYPE_INSTALL:
                return getApkFileIntent(filePath);
            case FileInfo.TYPE_PDF:
                return getPdfFileIntent(filePath);
            case FileInfo.TYPE_PPT:
                return getPptFileIntent(filePath);
            case FileInfo.TYPE_SMS:
                return getSmsFileIntent(filePath);
            case FileInfo.TYPE_TXT:
                return getTextFileIntent(filePath, false);
            case FileInfo.TYPE_VIDEO:
                return getVideoFileIntent(filePath);
            case FileInfo.TYPE_WORD:
                return getWordFileIntent(filePath);
            case FileInfo.TYPE_XLS:
                return getExcelFileIntent(filePath);
        }
        return getAllIntent(filePath);
    }

    public static Intent getAllIntent(String param) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "*/*");
        return intent;
    }

    //Android获取一个用于打开APP应用的intent
    public static Intent getAppFileIntent(Context context, String param) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent = context.getPackageManager().getLaunchIntentForPackage(param);
        return intent;
    }

    //Android获取一个用于打开联系人的intent
    public static Intent getContactFileIntent(String param) {
        Uri personUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, Long.parseLong(param));
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(personUri);
        return intent;
    }

    //Android获取一个用于打开短信的intent
    public static Intent getSmsFileIntent(String param) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("smsto:" + param));
        return intent;
    }

    //Android获取一个用于打开VIDEO文件的intent
    public static Intent getVideoFileIntent(String param) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(param), "video/mp4");
        return intent;
    }

    //Android获取一个用于打开AUDIO文件的intent
    public static Intent getAudioFileIntent(String param) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse("file://" + param), "audio/mp3");
        return intent;
    }

    //Android获取一个用于打开图片文件的intent
    public static Intent getImageFileIntent(String param) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse("file://" + param), "image/*");
        return intent;
    }

    //Android获取一个用于打开APK文件的intent
    public static Intent getApkFileIntent(String param) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        return intent;
    }

    //Android获取一个用于打开PPT文件的intent
    public static Intent getPptFileIntent(String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
        return intent;
    }

    //Android获取一个用于打开Excel文件的intent
    public static Intent getExcelFileIntent(String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/vnd.ms-excel");
        return intent;
    }

    //Android获取一个用于打开Word文件的intent
    public static Intent getWordFileIntent(String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/msword");
        return intent;
    }

    //Android获取一个用于打开文本文件的intent
    public static Intent getTextFileIntent(String param, boolean paramBoolean) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (paramBoolean) {
            Uri uri1 = Uri.parse(param);
            intent.setDataAndType(uri1, "text/plain");
        } else {
            Uri uri2 = Uri.fromFile(new File(param));
            intent.setDataAndType(uri2, "text/plain");
        }
        return intent;
    }

    //Android获取一个用于打开PDF文件的intent
    public static Intent getPdfFileIntent(String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/pdf");
        return intent;
    }


}
