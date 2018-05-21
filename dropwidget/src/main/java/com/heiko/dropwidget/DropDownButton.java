package com.heiko.dropwidget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * 下拉对话框
 *
 * @author EthanCo
 * @since 2018/5/17
 */
public class DropDownButton extends FrameLayout {
    private TextView tvDropTitle;
    private ImageView imgDropDirection;
    private View viewDropDivider;
    private int currCheckPos; //用作记录DropDownMenu当前的选中位置
    private ViewGroup layoutDropRoot;

    public DropDownButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public DropDownButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View root = inflater.inflate(R.layout.layout_drop_down_button, this, true);
        tvDropTitle = root.findViewById(R.id.tv_drop_title);
        imgDropDirection = root.findViewById(R.id.img_drop_direction);
        viewDropDivider = root.findViewById(R.id.view_drop_divider);
        layoutDropRoot = root.findViewById(R.id.layout_drop_root);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isOpen = imgDropDirection.isSelected();
                setDropState(!isOpen);
            }
        });

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.DropDownButton);
        String dropTitle = ta.getString(R.styleable.DropDownButton_title);
        boolean dividerVisible = ta.getBoolean(R.styleable.DropDownButton_divider_visible, true);
        @ColorInt int dividerColor = ta.getColor(R.styleable.DropDownButton_divider_color,
                getResources().getColor(R.color.color_drop_divider));
        @ColorInt int buttonBg = ta.getColor(R.styleable.DropDownButton_button_background,
                getResources().getColor(R.color.color_def_drop_background));
        if (TextUtils.isEmpty(dropTitle)) {
            dropTitle = "Title";
        }
        Drawable dropDirectionDrawable = ta.getDrawable(R.styleable.DropDownButton_drop_direction);
        ta.recycle();

        tvDropTitle.setText(dropTitle);
        if (dropDirectionDrawable != null) {
            setDropDirectionImage(dropDirectionDrawable);
        }

        if (dividerVisible) {
            viewDropDivider.setVisibility(View.VISIBLE);
            viewDropDivider.setBackgroundColor(dividerColor);
        } else {
            viewDropDivider.setVisibility(View.GONE);
        }
        layoutDropRoot.setBackgroundColor(buttonBg);
    }

    public void setTitle(CharSequence text) {
        tvDropTitle.setText(text);
    }

    /**
     * 设置标题
     *
     * @param resId
     */
    public void setTitle(@StringRes int resId) {
        tvDropTitle.setText(resId);
    }

    /**
     * 获取标题
     *
     * @return
     */
    public String getTitle() {
        return tvDropTitle.getText().toString();
    }

    /**
     * 设置下拉图标
     *
     * @param resId 使用selector状态改变，图标会联动改变，详见select_drop_direction.xml
     */
    public void setDropDirectionImage(@DrawableRes int resId) {
        imgDropDirection.setImageResource(resId);
    }

    public void setDropDirectionImage(Bitmap bm) {
        imgDropDirection.setImageBitmap(bm);
    }

    public void setDropDirectionImage(Drawable drawable) {
        imgDropDirection.setImageDrawable(drawable);
    }

    /**
     * 设置下拉状态，执行监听回调
     *
     * @param open
     */
    public void setDropState(boolean open) {
        imgDropDirection.setSelected(open);
        for (DropStateChangeListener stateChangeListener : stateChangeListeners) {
            stateChangeListener.onDropStateChange(DropDownButton.this, open);
        }
    }

    /**
     * 设置下拉状态，不执行监听回调
     *
     * @param open
     */
    public void setDropStateSilence(boolean open) {
        imgDropDirection.setSelected(open);
    }

    /**
     * 进行关联，实现DropDownButton和DropDownMenu的联动
     *
     * @param activity
     * @param dropBeans               数据列表，Bean对象需实现DropBeanFlag接口
     * @param defCheckPos             默认选中位置
     * @param heightScale             高度占手机屏幕的比例 0-1
     * @param layoutMask              半透明阴影
     * @param dropStateChangeListener 状态改变监听
     */
    public void attach(final Activity activity, final List<DropBeanFlag> dropBeans, final int defCheckPos,
                       @FloatRange(from = 0, to = 1) final float heightScale,
                       @Nullable final View layoutMask, final DropStateChangeListener dropStateChangeListener) {
        addDropStateChangeListener(new DropStateChangeListener() {
            @Override
            public void onDropStateChange(View view, boolean isOpen) {
                if (isOpen) {
                    DropDownMenu.show(activity, dropBeans, heightScale, DropDownButton.this);
                }
                if (layoutMask != null) {
                    layoutMask.setVisibility(isOpen ? View.VISIBLE : View.GONE);
                }
                if (dropStateChangeListener != null) {
                    dropStateChangeListener.onDropStateChange(view, isOpen);
                }
            }
        });
        if (defCheckPos < dropBeans.size()) {
            setCurrCheckPos(defCheckPos);
            setTitle(dropBeans.get(defCheckPos).getDropName());
        } else {
            if (dropBeans.size() > 0) {
                setCurrCheckPos(0);
                setTitle(dropBeans.get(0).getDropName());
            }
        }
    }

    /**
     * 获取当前选中位置
     *
     * @return
     */
    public int getCurrCheckPos() {
        return currCheckPos;
    }

    /**
     * 设置当前选中位置
     *
     * @param currCheckPos
     */
    public void setCurrCheckPos(int currCheckPos) {
        this.currCheckPos = currCheckPos;
    }

    public interface DropStateChangeListener {
        void onDropStateChange(View view, boolean isOpen);
    }

    private List<DropStateChangeListener> stateChangeListeners = new ArrayList<>();

    /**
     * 添加DropDownButton状态改变监听
     *
     * @param listener
     */
    public void addDropStateChangeListener(DropStateChangeListener listener) {
        if (!stateChangeListeners.contains(listener)) {
            stateChangeListeners.add(listener);
        }
    }

    /**
     * 移除DropDownButton状态改变监听
     *
     * @param listener
     */
    public void removeDropStateChangeListener(DropStateChangeListener listener) {
        stateChangeListeners.remove(listener);
    }
}
