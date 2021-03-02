package com.heiko.dropwidget;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

/**
 * @Description PopupWindow的一个封装工具类
 * Created by EthanCo on 2016/3/30.
 */
public class DropPopupUtil {
    private static final String TAG = "DropPopupUtil";

    /**
     * @param activity
     * @param contentView 自定义的view
     * @param heightScale 高度比例 0-1
     * @param anchor      锚
     * @param xoff
     * @param yoff
     */
    public static PopupWindow showAsDropDown(Activity activity, View contentView, float heightScale, View anchor, int xoff, int yoff) {
        DisplayMetrics outMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        int mScreenHeight = outMetrics.heightPixels;
        float anchorHeight = mScreenHeight - anchor.getRotationY();
        Log.d(TAG, "showAsDropDown: mScreenHeight =" + mScreenHeight);
        Log.d(TAG, "showAsDropDown: anchorHeight =" + anchorHeight);
        int[] location = new int[2];
        anchor.getLocationInWindow(location);
        Log.d(TAG, location[0] + "  " + location[1]);
        final int height = anchor.getHeight();

        Log.d(TAG, " location[1] = " + location[1]);
        Log.d(TAG, "anchor.getHeight() height = " + height);
//        int allHeight = mScreenHeight - height;//总体高度
//        int allHeight = mScreenHeight - location[1];//总体高度
        int allHeight = mScreenHeight - location[1] - height;//总体高度

        final View viewById = contentView.findViewById(R.id.ll_other);

//        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) viewById.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
//        int afterHeight = (int) (allHeight * heightScale);
//        linearParams.height = afterHeight;
//        viewById.setLayoutParams(linearParams);

        final PopupWindow popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT, allHeight + 80, true); //(int) (mScreenHeight * heightScale)
//        final PopupWindow popupWindow = new PopupWindow(contentView,
//                ViewGroup.LayoutParams.MATCH_PARENT, (int) (mScreenHeight * heightScale), true); //(int) (mScreenHeight * heightScale)
//        popupWindow.setHeight(mScreenHeight);
        popupWindow.setTouchable(true);
//        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));    //要为popWindow设置一个背景才有效
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        //popupWindow.setAnimationStyle(R.style.anim_popup_dir);
//        popupWindow.showAsDropDown(anchor, xoff, height);
        popupWindow.showAsDropDown(anchor, xoff, yoff);
        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        return popupWindow;
    }

    /**
     * @param activity
     * @param contentView 自定义的view
     * @param heightScale 高度比例 0-1
     * @param anchor
     */
    public static PopupWindow showAsDropDown(Activity activity, View contentView, float heightScale, View anchor) {
        return showAsDropDown(activity, contentView, heightScale, anchor, 0, 0);
    }
}
