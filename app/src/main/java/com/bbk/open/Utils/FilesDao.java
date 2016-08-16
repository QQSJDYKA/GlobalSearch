package com.bbk.open.Utils;


import android.content.Context;
import com.bbk.open.model.FileInfo;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by administration on 2016/7/15.
 * files DAO
 */
public class FilesDao {
    private Context context;
    private Dao<FileInfo, Integer> userDaoOpe;
    private DatabaseHelper helper;

    public FilesDao(Context context)
    {
        this.context = context;
        try
        {
            helper = DatabaseHelper.getHelper(context);
            userDaoOpe = helper.getDao(FileInfo.class);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 增加一个文件
     * @param file
     */
    public void add(FileInfo file)
    {
        try
        {
            userDaoOpe.createIfNotExists(file);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    /**
     * TODO 查询操作
     */
    public List<FileInfo> qurry(String key,int type) throws SQLException {
        List<FileInfo> files=new ArrayList<FileInfo>();
        QueryBuilder builder = userDaoOpe.queryBuilder();
        builder.orderBy("count",false).where().like("name","%"+key+"%").or().like("searchInfo","%"+key+"%").and().eq("type",type);
        files=builder.query();
        return files;
    }

    public List<FileInfo> qurryHideFiles() throws SQLException {
        List<FileInfo> files=new ArrayList<FileInfo>();
        QueryBuilder builder = userDaoOpe.queryBuilder();
        builder.orderBy("type",true).where().eq("tag",2);
        files=builder.query();
        return files;
    }


    public List<FileInfo> qurryDoc(String key) throws SQLException {
        List<FileInfo> files=new ArrayList<FileInfo>();
        QueryBuilder builder = userDaoOpe.queryBuilder();
        builder.orderBy("type",false).where().like("name","%"+key+"%").or().like("searchInfo","%"+key+"%").and().in("type",8,9,10,11,12);
        files=builder.query();
        return files;
    }

    public void update(FileInfo file) {
        try {
            userDaoOpe.update(file);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
