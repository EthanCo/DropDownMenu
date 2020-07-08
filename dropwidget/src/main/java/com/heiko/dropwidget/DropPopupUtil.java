package com.heiko.dropwidget;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
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
     * @param anchor
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
        int allHeight = mScreenHeight - location[1] - height;//总体高度

        final View viewById = contentView.findViewById(R.id.drop_menu_list);
        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) viewById.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
        linearParams.height = (int) (allHeight * heightScale);
        viewById.setLayoutParams(linearParams);

        final PopupWindow popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT, allHeight, true); //(int) (mScreenHeight * heightScale)
//        final PopupWindow popupWindow = new PopupWindow(contentView,
//                ViewGroup.LayoutParams.MATCH_PARENT, (int) (mScreenHeight * heightScale), true); //(int) (mScreenHeight * heightScale)
        popupWindow.setTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        //popupWindow.setAnimationStyle(R.style.anim_popup_dir);
        popupWindow.showAsDropDown(anchor, xoff, yoff);
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
