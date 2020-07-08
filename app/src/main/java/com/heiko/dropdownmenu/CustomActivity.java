package com.heiko.dropdownmenu;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.heiko.dropwidget.DropAdapter;
import com.heiko.dropwidget.DropBeanFlag;
import com.heiko.dropwidget.DropDownMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义 DropDownMenu show，不依赖 DropDownButton
 *
 * @author EthanCo
 * @since 2018/5/17
 */

public class CustomActivity extends AppCompatActivity {
    private TextView tvCustomShow;
    private View layoutMask;
    private int currCheckedPos = 2;
    private View layoutCustomShow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_custom);

        tvCustomShow = findViewById(R.id.tv_custom_show);
        layoutCustomShow = findViewById(R.id.layout_custom_show);
        layoutMask = findViewById(R.id.layout_mask);

        final List<DropBeanFlag> dropBeans = initDatas();

        layoutCustomShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                layoutMask.setVisibility(View.VISIBLE);
//                layoutMask.getBackground().setAlpha(100);
//                layoutMask.getBackground().setAlpha(100);;
                DropDownMenu.showCustom(CustomActivity.this, dropBeans, 0.7F, layoutCustomShow, currCheckedPos, new DropAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(View view, int pos) {
                        currCheckedPos = pos;
                    }
                }, new DropDownMenu.OnDropDismissListener() {
                    @Override
                    public void onDismiss() {
                        tvCustomShow.setText(dropBeans.get(currCheckedPos).getDropName());
//                        layoutMask.setVisibility(View.GONE);
                    }
                });
            }
        });
        tvCustomShow.setText(dropBeans.get(currCheckedPos).getDropName());
    }

    @NonNull
    private List<DropBeanFlag> initDatas() {
        List<DropBeanFlag> dropBeans = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            dropBeans.add(new DropBean(i, "item" + i));
        }
        return dropBeans;
    }
}
