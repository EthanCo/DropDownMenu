package com.heiko.dropdownmenu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.heiko.dropwidget.DropBeanFlag;
import com.heiko.dropwidget.DropDownButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DropDownButton dropDownButton;
    private FrameLayout layoutMask;
    private int currCheckedPos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dropDownButton = findViewById(R.id.drop_down_button);
        layoutMask = findViewById(R.id.layout_mask);

        final List<DropBeanFlag> dropBeans = initDatas();

        //方法一
        dropDownButton.attach(this, dropBeans,2, 0.5F, layoutMask);

        //方法二
        /*dropDownButton.addDropStateChangeListener(new DropDownButton.DropStateChangeListener() {
            @Override
            public void onDropStateChange(View view, boolean isOpen) {
                if (isOpen) {
                    DropDownMenu.show(MainActivity.this, dropBeans, 0.5F, dropDownButton);
                }
                layoutMask.setVisibility(isOpen ? View.VISIBLE : View.GONE);
            }
        });
        dropDownButton.setCurrCheckPos(2);
        dropDownButton.setTitle(dropBeans.get(2).getDropName());*/

        //方法三
//        dropDownButton.addDropStateChangeListener(new DropDownButton.DropStateChangeListener() {
//            @Override
//            public void onDropStateChange(View view, boolean isOpen) {
//                if (isOpen) {
//                    LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
//                    View root = inflater.inflate(R.layout.layout_drop_popup, null);
//                    RecyclerView listDropMenu = root.findViewById(R.id.drop_menu_list);
//                    listDropMenu.setLayoutManager(new LinearLayoutManager(MainActivity.this));
//                    DividerItemDecoration itemDecoration = new DividerItemDecoration(
//                            MainActivity.this, DividerItemDecoration.VERTICAL);
//                    itemDecoration.setDrawable(getResources().getDrawable(R.drawable.shape_divider));
//                    listDropMenu.addItemDecoration(itemDecoration);
//                    final DropAdapter dropAdapter = new DropAdapter(getApplicationContext(), dropBeans, currCheckedPos);
//                    listDropMenu.setAdapter(dropAdapter);
//
//                    final PopupWindow popupWindow = DropPopupUtil.showAsDropDown(
//                            MainActivity.this, root, 0.5F, dropDownButton);
//
//                    dropAdapter.addOnItemClickListener(new DropAdapter.OnItemClickListener() {
//                        @Override
//                        public void onClick(View view, int pos) {
//                            currCheckedPos = pos;
//                            dropDownButton.setTitle(dropBeans.get(pos).getDropName());
//                            dropAdapter.setSingleCheck(pos);
//                            Toast.makeText(MainActivity.this, "pos:" + pos, Toast.LENGTH_SHORT).show();
//                            popupWindow.dismiss();
//                        }
//                    });
//
//                    popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
//                        @Override
//                        public void onDismiss() {
//                            dropDownButton.setDropState(false);
//                            popupWindow.setOnDismissListener(null);
//
//                            /*dropDownButton.setDropStateSilence(false);
//                            layoutMask.setVisibility(View.GONE);
//                            popupWindow.setOnDismissListener(null);*/
//                        }
//                    });
//                }
//                layoutMask.setVisibility(isOpen ? View.VISIBLE : View.GONE);
//            }
//        });
//        dropDownButton.setTitle(dropBeans.get(currCheckedPos).getDropName());
    }

    @NonNull
    private List<DropBeanFlag> initDatas() {
        List<DropBeanFlag> dropBeans = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            dropBeans.add(new DropBean(i, "item" + i));
        }
        return dropBeans;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_drop, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_custom) {
            Intent intent = new Intent(MainActivity.this, CustomActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
