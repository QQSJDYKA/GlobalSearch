package com.bbk.open.model;


import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Administrator on 2016/7/19.
 * 文件files
 */
@DatabaseTable(tableName = "tb_infos")
public class FileInfo {

    public static final int TYPE_APP = 1;
    public static final int TYPE_CONTACT = 2;
    public static final int TYPE_SMS = 3;
    public static final int TYPE_AUDIO = 4;
    public static final int TYPE_IMAGE = 5;
    public static final int TYPE_VIDEO = 6;
    public static final int TYPE_INSTALL = 7;
    public static final int TYPE_TXT = 8;
    public static final int TYPE_XLS = 9;
    public static final int TYPE_PDF = 10;
    public static final int TYPE_WORD = 11;
    public static final int TYPE_PPT = 12;
    public static final int TAG_DEFAULT = 0;
    public static final int TAG_DELETE = 1;
    public static final int TAG_HIDE = 2;





    @DatabaseField
    private String name;//名称
    @DatabaseField(id = true)
    private String path;//路径
    @DatabaseField
    private String searchInfo;
    @DatabaseField
    private String noSearchInfo;
    @DatabaseField
    private int type;//类型
    @DatabaseField
    private int count;
    @DatabaseField
    private int tag;

    //空的构造方法
    public FileInfo() {
    }

    public FileInfo(String name, String path, String searchInfo, String noSearchInfo, int type, int count) {
        this.name = name;
        this.path = path;
        this.searchInfo = searchInfo;
        this.noSearchInfo = noSearchInfo;
        this.type = type;
        this.count = count;
    }
    public FileInfo(String name, String path, String searchInfo, String noSearchInfo, int type) {
        this.name = name;
        this.path = path;
        this.searchInfo = searchInfo;
        this.noSearchInfo = noSearchInfo;
        this.type = type;
    }

    @Override
    public String toString() {
        return "fileInfo{" +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", searchInfo='" + searchInfo + '\'' +
                ", noSearchInfo='" + noSearchInfo + '\'' +
                ", type=" + type + '\'' +
                ", count=" + count +
                '}';
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getSearchInfo() {
        return searchInfo;
    }

    public void setSearchInfo(String searchInfo) {
        this.searchInfo = searchInfo;
    }

    public String getNoSearchInfo() {
        return noSearchInfo;
    }

    public void setNoSearchInfo(String noSearchInfo) {
        this.noSearchInfo = noSearchInfo;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void addCount() {
        this.count = this.count + 1;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public int getTag() {
        return tag;
    }
}
