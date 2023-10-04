package com.example.bluestar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class listAdapter extends RecyclerView.Adapter  {

    private Context context;
    //    用于接受上下文对象？


    public listAdapter(Context context) {
        this.context = context;
//        在适配器中使用 context 访问应用程序的资源，例如字符串、颜色、尺寸等。
//        使用 context 获取系统服务，例如访问数据库、网络操作、共享偏好设置等。
//        将 context 传递给其他组件或工具类，以便它们能够使用上下文执行相应的操作。
    }

    //    可以自己写一个，自动刷新页面
    public void setData(ArrayList<ChatMsg> Msg) {
//        this.Msg = Msg;
        notifyDataSetChanged();
//        用于通知适配器数据已经发生变化，需要更新列表的显示。
//        适配器会重新计算列表项的数量，通过调用 getItemCount() 方法获取最新的列表项数量。
//        适配器会重新调用 onCreateViewHolder() 方法创建新的 ViewHolder 对象。
//        适配器会重新调用 onBindViewHolder() 方法将数据绑定到 ViewHolder 中的视图控件上。
//        列表的显示会更新，显示最新的数据。
    }
    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        // 内部类的定义
//    class  ItemViewHolder extends RecyclerView.ViewHolder{
//        会报错
        private TextView item_tv;
        public ItemViewHolder(View itemView) {
            super(itemView);
            item_tv = (TextView) itemView.findViewById(R.id.list_item_tv);
        }

        //        添加接口定义事件点击
//        public interface OnItemClickListener {
//            void onItemClick(String item);
//        }
//        private OnItemClickListener itemClickListener;
//        public void setOnItemClickListener(OnItemClickListener listener) {
//            this.itemClickListener = listener;
//        }



    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //用来创建ViewHolder实例，再将加载好的布局传入构造函数，最后返回ViewHolder实例
//        它表示每个列表项的视图容器，用于保存列表项中的视图控件引用。
        RecyclerView.ViewHolder holder = null;

        View view = LayoutInflater.from(context).inflate(R.layout.list_item_layout,parent,false);
        holder = new ItemViewHolder(view);
//            根据指定的布局文件创建一个视图对象，以便在 RecyclerView 的列表项中使用。
//            通过调用 inflate() 方法，将布局文件转换为一个视图对象，并将其存储在 view 变量中供后续使用。


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //没用
        int itemViewType = getItemViewType(position);
        String[] object_list=GlobalVariables.getInstance().getChatobject();

        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        itemViewHolder.item_tv.setText(object_list[position]);



    }

    //    返回总数
    @Override
    public int getItemCount() {
        int c=0;

//        通过单例模式得到全局变量
        String[] object_list=GlobalVariables.getInstance().getChatobject();
//        借助数据库找到对于用户昵称
        if(object_list != null&& object_list.length > 0){
            c=object_list.length;
        }
        return c;
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }
//    返回指定位置的列表项类型

}


