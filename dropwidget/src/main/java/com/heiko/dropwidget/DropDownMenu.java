package com.heiko.dropwidget;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * DropDownMenu
 *
 * @author EthanCo
 * @since 2018/5/17
 */
public class DropDownMenu {
    private static WeakReference<PopupWindow> popupWindowWeakRef;

    /**
     * 默认show，依赖DropDownButton
     *
     * @param activity
     * @param dropBeans      数据列表，Bean对象需实现DropBeanFlag接口
     * @param heightScale    高度占手机屏幕的比例 0-1
     * @param dropDownButton DropDownButton
     * @return
     */
    public static PopupWindow show(Activity activity, final List<DropBeanFlag> dropBeans,
                                   @FloatRange(from = 0, to = 1) float heightScale,
                                   final DropDownButton dropDownButton) {
        dismiss();
        View root = findRootView(activity);
        final DropAdapter dropAdapter = initRecyclerView(activity,
                dropBeans, dropDownButton.getCurrCheckPos(), root);
        final PopupWindow popupWindow = DropPopupUtil.showAsDropDown(
                activity, root, heightScale, dropDownButton);

        dropAdapter.addOnItemClickListener(new DropAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int pos) {
                dropDownButton.setCurrCheckPos(pos);
                dropDownButton.setTitle(dropBeans.get(pos).getDropName());
                dropAdapter.setSingleCheck(pos);
                popupWindow.dismiss();
            }
        });

        ViewGroup layoutRoot = root.findViewById(R.id.layout_root);
        ViewGroup llOther = root.findViewById(R.id.ll_other);
        llOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        layoutRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                dropDownButton.setDropState(false);
                popupWindow.setOnDismissListener(null);

                /*dropDownButton.setDropStateSilence(false);
                if (layoutMask != null) {
                    layoutMask.setVisibility(View.GONE);
                }
                popupWindow.setOnDismissListener(null);*/
            }
        });
        popupWindowWeakRef = new WeakReference<>(popupWindow);
        return popupWindow;
    }

    /**
     * 自定义show，不依赖DropDownButton
     *
     * @param activity
     * @param dropBeans             数据列表，Bean对象需实现DropBeanFlag接口
     * @param heightScale           高度占手机屏幕的比例 0-1
     * @param anchor                和PopupWindow同理 锚
     * @param defCheckedPos         默认选中Position
     * @param dropAdapterItemClick  Item点击监听
     * @param onDropDismissListener dismiss监听
     * @return
     */
    public static PopupWindow showCustom(Activity activity, List<DropBeanFlag> dropBeans,
                                         float heightScale, View anchor, int defCheckedPos,
                                         DropAdapter.OnItemClickListener dropAdapterItemClick,
                                         final OnDropDismissListener onDropDismissListener) {
        dismiss();
        View root = findRootView(activity);
        final DropAdapter dropAdapter = initRecyclerView(activity, dropBeans, defCheckedPos, root);
        final PopupWindow popupWindow = DropPopupUtil.showAsDropDown(
                activity, root, heightScale, anchor);

        if (dropAdapterItemClick != null) {
            dropAdapter.addOnItemClickListener(dropAdapterItemClick);
        }
        dropAdapter.addOnItemClickListener(new DropAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int pos) {
                //dropDownButton.setCurrCheckPos(pos);
                dropAdapter.setSingleCheck(pos);
                popupWindow.dismiss();
            }
        });

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (onDropDismissListener != null) {
                    onDropDismissListener.onDismiss();
                }
                popupWindow.setOnDismissListener(null);
            }
        });
        popupWindowWeakRef = new WeakReference<>(popupWindow);
        return popupWindow;
    }



    @NonNull
    private static DropAdapter initRecyclerView(Context context, List<DropBeanFlag> dropBeans, int defCheckedPos, View root) {
        MaxHeightRecyclerView listDropMenu = root.findViewById(R.id.drop_menu_list);
        listDropMenu.setLayoutManager(new LinearLayoutManager(context));
        DividerItemDecoration itemDecoration = new DividerItemDecoration(
                context, DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(context.getResources().getDrawable(R.drawable.shape_divider));
        listDropMenu.addItemDecoration(itemDecoration);
        final DropAdapter dropAdapter = new DropAdapter(context, dropBeans, defCheckedPos);
        listDropMenu.setAdapter(dropAdapter);
        return dropAdapter;
    }

    @NonNull
    private static View findRootView(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate(R.layout.layout_drop_popup, null);
    }

    /**
     * dismiss下拉菜单
     */
    public static void dismiss() {
        if (popupWindowWeakRef == null) return;

        PopupWindow popupWindow = popupWindowWeakRef.get();
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            popupWindowWeakRef = null;
        }
    }

    public interface OnDropDismissListener {
        void onDismiss();
    }
}
