package com.bbk.open.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.bbk.open.Utils.PhotoHandle;
import com.bbk.open.globlesearch.R;
import com.bbk.open.model.FileInfo;
import com.bumptech.glide.Glide;

import java.util.List;


/**
 * Created by Administrator on 2016/7/20.
 */
public class MyAdapter extends BaseAdapter {

    private Context context;
    private List<FileInfo> list;


    public MyAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<FileInfo> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        Bitmap bitmap;
        if (view == null) {
            holder = new ViewHolder();
            if (list.get(i).getType() == FileInfo.TYPE_IMAGE || list.get(i).getType() == FileInfo.TYPE_VIDEO) {
                view = LayoutInflater.from(context).inflate(R.layout.image_item_view, null);
            } else {
                view = LayoutInflater.from(context).inflate(R.layout.item_view, null);
            }
            holder.tvName = (TextView) view.findViewById(R.id.name);
            holder.ivIcon = (ImageView) view.findViewById(R.id.imageview1);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        if (TextUtils.isEmpty(list.get(i).getName())) {
            holder.tvName.setText(list.get(i).getSearchInfo());
        } else {
            holder.tvName.setText(list.get(i).getName());
        }
        switch (list.get(i).getType()) {
            case FileInfo.TYPE_APP:
                bitmap = PhotoHandle.byteToBitmap(list.get(i).getNoSearchInfo());
                holder.ivIcon.setImageBitmap(bitmap);
                break;
            case FileInfo.TYPE_IMAGE:
            case FileInfo.TYPE_VIDEO:
                Glide.with(context)
                        .load(list.get(i).getPath())
                        .centerCrop()
                        .placeholder(R.drawable.default_icon)
                        .crossFade()
                        .into(holder.ivIcon);
                break;
            case FileInfo.TYPE_AUDIO:
                    Glide.with(context)
                            .load(list.get(i).getNoSearchInfo())
                            .centerCrop()
                            .placeholder(R.drawable.default_icon)
                            .error(R.drawable.audio)
                            .crossFade()
                            .into(holder.ivIcon);
                break;
            case FileInfo.TYPE_PDF:
                holder.ivIcon.setImageResource(R.drawable.pdf);
                break;
            case FileInfo.TYPE_WORD:
                holder.ivIcon.setImageResource(R.drawable.doc);
                break;
            case FileInfo.TYPE_XLS:
                holder.ivIcon.setImageResource(R.drawable.xls);
                break;
            case FileInfo.TYPE_TXT:
                holder.ivIcon.setImageResource(R.drawable.txt);
                break;
            case FileInfo.TYPE_PPT:
                holder.ivIcon.setImageResource(R.drawable.ppt);
                break;
            case FileInfo.TYPE_CONTACT:
                int index = list.get(i).getName().indexOf("|");
                holder.tvName.setText(list.get(i).getName().substring(0, index));
                TextDrawable textDrawable = TextDrawable.builder().buildRound(list.get(i).getName().substring(0, 1), R.color.btn_bg_pressed_color);
                holder.ivIcon.setImageDrawable(textDrawable);
                break;
            case FileInfo.TYPE_SMS:
                holder.ivIcon.setImageResource(R.drawable.sms);
                break;
            case FileInfo.TYPE_INSTALL:
                bitmap = PhotoHandle.byteToBitmap(list.get(i).getNoSearchInfo());
                holder.ivIcon.setImageBitmap(bitmap);
                break;
            default:
                holder.ivIcon.setImageResource(R.drawable.default_icon);
        }
        return view;
    }

    class ViewHolder {
        TextView tvName;
        ImageView ivIcon;
    }


}

