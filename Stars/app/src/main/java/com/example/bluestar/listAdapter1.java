package com.example.bluestar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//为RecyclerView设置点击事件，麻烦死了，吐了，就应该直接用ListView的
public class listAdapter1 extends RecyclerView.Adapter<listAdapter1.ItemViewHolder> {
//    listAdapter1继承自RecyclerView.Adapter<listAdapter1.ItemViewHolder>，
//    指定了适配器使用的ViewHolder类型为ItemViewHolder
    private Context context;
    private String[] object_list;
    private OnItemClickListener itemClickListener;


    public listAdapter1(Context context, String[] object_list) {
        this.context = context;
//        直接赋值
        this.object_list = object_list;
    }

    public void setData(String[] object_list) {
        this.object_list = object_list;
        notifyDataSetChanged();
    }

    //    ItemViewHolder是一个静态内部类，继承自RecyclerView.ViewHolder，用于保存列表项中的视图控件引用。
    //    在ItemViewHolder中，有一个item_tv成员变量用于显示文本。
    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView item_tv;

        public ItemViewHolder(View itemView) {
            super(itemView);
            item_tv = (TextView) itemView.findViewById(R.id.list_item_tv);
        }
    }

    @NonNull
    @Override
//    onCreateViewHolder()方法负责创建ViewHolder实例。
//    它使用LayoutInflater从指定的布局文件list_item_layout中创建一个视图对象，并将其传递给ItemViewHolder构造函数
//    来创建ViewHolder实例。
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_layout, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
//    onBindViewHolder()方法用于绑定数据到ViewHolder中的视图控件。
//    它通过position参数获取要显示的数据，并将其设置到对应的视图控件item_tv中。
//    此外，还设置了列表项的点击事件监听器，在点击时调用itemClickListener的onItemClick()方法，并传递点击的位置。
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.item_tv.setText(object_list[position]);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int clickedPosition = holder.getAdapterPosition();
                if (itemClickListener != null && clickedPosition != RecyclerView.NO_POSITION) {
                    itemClickListener.onItemClick(clickedPosition);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return object_list.length;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }
}
