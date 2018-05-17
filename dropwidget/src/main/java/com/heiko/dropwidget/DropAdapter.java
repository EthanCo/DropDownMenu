package com.heiko.dropwidget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author EthanCo
 * @since 2018/5/17
 */
public class DropAdapter<T extends DropBeanFlag> extends RecyclerView.Adapter<DropAdapter.DropViewHolder> {
    private Context mContext;
    private List<T> datas;

    public DropAdapter(Context context, List<T> drapBeans, int defCheckedPos) {
        this.mContext = context;
        this.datas = drapBeans;
        initChecked(defCheckedPos);
    }

    private void initChecked(int defCheckedPos) {
        setSingleCheckSilence(defCheckedPos);
    }

    public void setSingleCheck(int checkedPos) {
        setSingleCheckSilence(checkedPos);
        notifyDataSetChanged();
    }

    private void setSingleCheckSilence(int checkedPos) {
        if (datas.size() > checkedPos) {
            T data;
            for (int i = 0; i < datas.size(); i++) {
                data = datas.get(i);
                if (checkedPos == i) {
                    data.setDropChecked(true);
                } else {
                    data.setDropChecked(false);
                }
            }
        }else{
            for (T data : datas) {
                data.setDropChecked(false);
            }
            if (datas.size() > 0) {
                datas.get(0).setDropChecked(true);
            }
        }
    }

    @Override
    public DropViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        View root = mInflater.inflate(R.layout.item_drop, parent, false);
        return new DropViewHolder(root);
    }

    @Override
    public void onBindViewHolder(final DropViewHolder holder, final int position) {
        T data = datas.get(position);
        holder.tvTitle.setText(data.getDropName());
        if (data.isDropChecked()) {
            holder.imgCheck.setImageResource(data.getDropCheckedImageRes());
        }
        holder.layoutRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (OnItemClickListener itemClick : itemClicks) {
                    itemClick.onClick(holder.layoutRoot, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public interface OnItemClickListener {
        void onClick(View view, int pos);
    }

    private List<OnItemClickListener> itemClicks = new ArrayList<>();

    public void addOnItemClickListener(OnItemClickListener listener) {
        if (!itemClicks.contains(listener)) {
            itemClicks.add(listener);
        }
    }

    public void removeOnItemClickListener(OnItemClickListener listener) {
        itemClicks.remove(listener);
    }

    public static class DropViewHolder extends RecyclerView.ViewHolder {
        final TextView tvTitle;
        final ImageView imgCheck;
        final View layoutRoot;

        public DropViewHolder(View itemView) {
            super(itemView);
            layoutRoot = itemView.findViewById(R.id.layout_item_root);
            tvTitle = itemView.findViewById(R.id.tv_drop_item_title);
            imgCheck = itemView.findViewById(R.id.img_drop_item_check);
        }
    }
}
