package com.bbk.open.Utils;

import com.bbk.open.model.FileInfo;

/**
 * 转化文件的类型对应成数字
 * 2016.7.18
 * 杨尚臻
 */
public class ConvertUtil {


    public static int convertType(String name) {
       if (name.contains("txt")) {
            return FileInfo.TYPE_TXT;
        } else if (name.contains("doc")) {
            return FileInfo.TYPE_WORD;
        } else if (name.contains("pdf")) {
            return FileInfo.TYPE_PDF;
        } else if (name.contains("xls")) {
            return FileInfo.TYPE_XLS;
        }else if (name.contains("ppt")) {
            return FileInfo.TYPE_PPT;
        }
        return 0;
    }
}
