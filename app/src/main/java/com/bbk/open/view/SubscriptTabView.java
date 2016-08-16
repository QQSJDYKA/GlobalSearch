package com.bbk.open.view;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bbk.open.Utils.DensityUtil;
import com.bbk.open.globlesearch.R;

/**
 * Created by Administrator on 2016/8/6.
 *
 * 组合View=Tab+数字角标
 *
 */

public class SubscriptTabView extends LinearLayout {

    private String numberStr;
    private int numberBackgroundColor;
    private int numberFontSize;

    private String titleStr;
    private int titleFontSize;
    private int titleBackgroundColor;

    private TextView titleTv;
    private TextView numberTv;

    private boolean isShowNumber;
    private boolean isHasReadGone;


    public SubscriptTabView(Context context) {
        super(context);
        initView(context);
    }

    public SubscriptTabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public SubscriptTabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    /**
     * 初始化
     * @param context
     */
    private void initView(Context context) {

        View view = LayoutInflater.from(context).inflate(R.layout.view_subscript_tab, this);

        titleTv = (TextView) view.findViewById(R.id.view_subscript_tab_title);
        numberTv = (TextView) view.findViewById(R.id.view_subscript_tab_number);

    }


    /**-- getter and setter --**/

    public boolean isHasReadGone() {
        return isHasReadGone;
    }

    public void setHasReadGone(boolean hasReadGone) {
        isHasReadGone = hasReadGone;
    }

    public boolean isShowNumber() {
        return isShowNumber;
    }

    public void setShowNumber(boolean showNumber) {
        isShowNumber = showNumber;
    }

    public String getNumberStr() {
        return numberStr;
    }

    public void setNumberStr(int numberStr) {

        this.numberStr=String.valueOf(numberStr);

        if(numberStr==0) {
            //如果数字等于0隐藏Number
            numberTv.setVisibility(View.INVISIBLE);
            return;
        }

        RelativeLayout.LayoutParams paramNumber = (RelativeLayout.LayoutParams)numberTv.getLayoutParams();
        RelativeLayout.LayoutParams paramTitle = (RelativeLayout.LayoutParams)titleTv.getLayoutParams();


        if (isShowNumber) {

            if(numberStr<0){
                numberStr=0-numberStr;
            }
            numberTv.setText(String.valueOf(numberStr));
            paramNumber.width = DensityUtil.dip2px(20);
            paramNumber.height= DensityUtil.dip2px(20);
            paramNumber.leftMargin=DensityUtil.dip2px(-15);

            paramTitle.topMargin=DensityUtil.dip2px(10);
            paramTitle.rightMargin=DensityUtil.dip2px(10);

        }
        else{
            paramNumber.width = DensityUtil.dip2px(5);
            paramNumber.height=DensityUtil.dip2px(5);
            paramNumber.leftMargin=DensityUtil.dip2px(-4);

            paramTitle.topMargin=DensityUtil.dip2px(1);
            paramTitle.rightMargin=DensityUtil.dip2px(3);
        }
        numberTv.setLayoutParams(paramNumber);
        numberTv.setVisibility(View.VISIBLE);

        titleTv.setLayoutParams(paramTitle);



    }

    public int getNumberBackgroundColor() {
        return numberBackgroundColor;
    }

    public void setNumberBackgroundColor(int numberBackgroundColor) {

        int numberStr=Integer.valueOf(getNumberStr());

        if(numberStr==0) {
            //如果数字等于0隐藏Number
            numberTv.setVisibility(View.INVISIBLE);
            return;
        }

        if(numberStr<0&&isHasReadGone){
            numberTv.setVisibility(View.INVISIBLE);
            return;
        }

        numberTv.setVisibility(View.VISIBLE);

        this.numberBackgroundColor = numberBackgroundColor;
        //更新shape.xml的填充颜色
        GradientDrawable myGrad = (GradientDrawable)numberTv.getBackground();
        myGrad.setColor(this.numberBackgroundColor);
    }

    public int getNumberFontSize() {
        return numberFontSize;
    }

    public void setNumberFontSize(int numberFontSize) {
        this.numberFontSize = numberFontSize;
    }

    public String getTitleStr() {
        return titleStr;
    }

    public void setTitleStr(String titleStr) {
        this.titleStr = titleStr;
        titleTv.setText(this.titleStr);
    }

    public int getTitleFontSize() {
        return titleFontSize;
    }

    public void setTitleFontSize(int titleFontSize) {
        this.titleFontSize = titleFontSize;
    }

    public int getTitleBackgroundColor() {
        return titleBackgroundColor;
    }

    public void setTitleBackgroundColor(int titleBackgroundColor) {
        this.titleBackgroundColor = titleBackgroundColor;
    }

}