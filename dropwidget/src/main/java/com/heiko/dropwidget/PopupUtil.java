package com.heiko.dropwidget;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

/**
 * @Description PopupWindow的一个封装工具类
 * Created by EthanCo on 2016/3/30.
 */
class PopupUtil {
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
        //float anchorHeight = mScreenHeight - anchor.getRotationY();
        final PopupWindow popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT, (int) (mScreenHeight * heightScale), true); //(int) (mScreenHeight * heightScale)
        popupWindow.setTouchable(true);
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
