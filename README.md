# DropDownMenu
下拉菜单，可独立分离使用

![DropDownMenu.gif](http://oqk78xit2.bkt.clouddn.com/DropDownMenu.gif)  

![DropDownMenu.jpg](http://oqk78xit2.bkt.clouddn.com/DropDownMenu_mini.jpg)  

## 添加依赖
### Step 1. Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:  

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}  

### Step 2. Add the dependency  

	dependencies {
        implementation 'com.github.EthanCo:DropDownMenu:1.0.5'
	}  

## 使用步骤
分为DropDownButton和DropDownMenu，两者可一起使用，亦可分离单独使用。
一起使用可详见Demo的MainActivity，DropDownMenu单独使用可详见CustomActivity。  

### 1.在XML中添加
在XML中添加如下代码，其中DropDownButton是下拉点击的按钮，layout_mask是半透明遮盖图层，用作下拉菜单弹出时显示。

	<?xml version="1.0" encoding="utf-8"?>
	<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
	    xmlns:app="http://schemas.android.com/apk/res-auto"
	    xmlns:tools="http://schemas.android.com/tools"
	    android:layout_width="match_parent"
	    android:layout_height="match_parent"
	    android:orientation="vertical"
	    tools:context="com.heiko.dropdownmenu.MainActivity">
	
	    <com.heiko.dropwidget.DropDownButton
	        android:id="@+id/drop_down_button"
	        android:layout_width="match_parent"
	        android:layout_height="56dp"
	        app:divider_visible="true"
			app:divider_color="@color/color_drop_divider"
	        app:drop_direction="@drawable/my_select_drop_direction"
			app:button_background="@color/color_def_drop_background"
	        app:title="@string/drop_title" />
	
	    <FrameLayout
	        android:id="@+id/layout_mask"
	        android:layout_width="match_parent"
	        android:layout_height="match_parent"
	        android:background="#88222222"
	        android:visibility="gone" />
	</LinearLayout>  

### 2.进行初始化  
	
	dropDownButton.attach(activity, datas,2, 0.5F, layoutMask);  

**运行，即可使用。**  

## 附录

### DropDownButton属性说明  
		app:divider_visible //底部分割线是否可见
		app:divider_color   //底部分割线颜色
		app:drop_direction //下拉图标，使用selector，详见demo中的my_select_drop_direction.xml
 		app:title //标题  
		app:button_background //背景色

### DropDownButton方法说明  

	 /**
     * 设置下拉状态，执行监听回调
     *
     * @param open
     */
    public void setDropState(boolean open);

	 /**
     * 设置下拉状态，不执行监听回调
     *
     * @param open
     */
    public void setDropStateSilence(boolean open);


	 /**
     * 进行关联，实现DropDownButton和DropDownMenu的联动
     *
     * @param activity
     * @param dropBeans   数据列表，Bean对象需实现DropBeanFlag接口
     * @param defCheckPos 默认选中位置
     * @param heightScale 高度占手机屏幕的比例 0-1
     * @param layoutMask  半透明阴影
     */
    public void attach(final Activity activity, final List<DropBeanFlag> dropBeans, final int defCheckPos,
                       @FloatRange(from = 0, to = 1) final float heightScale,
                       @Nullable final View layoutMask);  

	/**
     * 设置当前选中位置
     * @param currCheckPos
     */
    public void setCurrCheckPos(int currCheckPos);

	/**
     * 获取当前选中位置
     * @return
     */
    public int getCurrCheckPos();

	/**
     * 添加DropDownButton状态改变监听
     * @param listener
     */
    public void addDropStateChangeListener(DropStateChangeListener listener);

	/**
     * 移除DropDownButton状态改变监听
     * @param listener
     */
    public void removeDropStateChangeListener(DropStateChangeListener listener);

    /**
     * 设置下拉图标
     * @param resId 使用selector状态改变，图标会联动改变，详见select_drop_direction.xml
     */
    public void setDropDirectionImage(@DrawableRes int resId)

    /**
     * 设置标题
     * @param resId
     */
    public void setTitle(@StringRes int resId);

	/**
     * 获取标题
     * @return
     */
    public String getTitle();  

### DropDownMenu方法说明

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
                                   final DropDownButton dropDownButton);

	/**
     * 自定义show，不依赖DropDownButton
     *
     * @param activity
     * @param dropBeans             数据列表，Bean对象需实现DropBeanFlag接口
     * @param heightScale           高度占手机屏幕的比例 0-1
     * @param anchor                和PopupWindow同理
     * @param defCheckedPos         默认选中Position
     * @param dropAdapterItemClick  Item点击监听
     * @param onDropDismissListener dismiss监听
     * @return
     */
    public static PopupWindow showCustom(Activity activity, List<DropBeanFlag> dropBeans,
                                         float heightScale, View anchor, int defCheckedPos,
                                         DropAdapter.OnItemClickListener dropAdapterItemClick,
                                         final OnDropDismissListener onDropDismissListener);

	/**
     * dismiss下拉菜单
     */
    public static void dismiss();


### 不依赖DropDownButton，仅单独使用DropDownMenu
详见Demo的CustomActivity

	