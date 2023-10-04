package com.example.bluestar;

//聊天界面适配器
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter  {

    private Context context;
//    用于接受上下文对象？
    private ArrayList<ChatMsg> Msg;
    private static final int T_I = 0;
    private static final int T_He = 1;

    public ChatAdapter(Context context) {
        this.context = context;
//        在适配器中使用 context 访问应用程序的资源，例如字符串、颜色、尺寸等。
//        使用 context 获取系统服务，例如访问数据库、网络操作、共享偏好设置等。
//        将 context 传递给其他组件或工具类，以便它们能够使用上下文执行相应的操作。
    }

    public void setData(ArrayList<ChatMsg> Msg) {
        this.Msg = Msg;
        notifyDataSetChanged();
//        用于通知适配器数据已经发生变化，需要更新列表的显示。
//        适配器会重新计算列表项的数量，通过调用 getItemCount() 方法获取最新的列表项数量。
//        适配器会重新调用 onCreateViewHolder() 方法创建新的 ViewHolder 对象。
//        适配器会重新调用 onBindViewHolder() 方法将数据绑定到 ViewHolder 中的视图控件上。
//        列表的显示会更新，显示最新的数据。
    }
    class T_IViewHolder extends RecyclerView.ViewHolder{
        private TextView my_tv;
        public T_IViewHolder(View itemView) {
            super(itemView);
            my_tv = (TextView) itemView.findViewById(R.id.my_msg_tv);
        }
    }

    class T_HeViewHolder extends RecyclerView.ViewHolder{
        private TextView his_tv;
        public T_HeViewHolder(View itemView) {
            super(itemView);
            his_tv = (TextView) itemView.findViewById(R.id.his_msg_tv);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //用来创建ViewHolder实例，再将加载好的布局传入构造函数，最后返回ViewHolder实例
//        它表示每个列表项的视图容器，用于保存列表项中的视图控件引用。
        RecyclerView.ViewHolder holder = null;
        if (viewType==T_I){
            View view = LayoutInflater.from(context).inflate(R.layout.mymsg_layout,parent,false);
            holder = new T_IViewHolder(view);
//            根据指定的布局文件创建一个视图对象，以便在 RecyclerView 的列表项中使用。
//            通过调用 inflate() 方法，将布局文件转换为一个视图对象，并将其存储在 view 变量中供后续使用。
        }
        else{
            View view = LayoutInflater.from(context).inflate(R.layout.hismsg_layout,parent,false);
//            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.fruit_item,null);
            holder = new T_HeViewHolder(view);
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        int itemViewType = getItemViewType(position);
        if (itemViewType==T_I) {
            T_IViewHolder myViewHolder = (T_IViewHolder) holder;
            myViewHolder.my_tv.setText(Msg.get(position).getMsg());
        }
        else{
            T_HeViewHolder HisViewHolder = (T_HeViewHolder) holder;
            HisViewHolder.his_tv.setText(Msg.get(position).getMsg());
        }
    }

    @Override
    public int getItemCount() {
        int c=0;
        if(Msg != null&& Msg.size() > 0){
            c=Msg.size();
        }
        return c;
    }

    @Override
    public int getItemViewType(int position) {
        return Msg.get(position).getNumber();
    }
//    返回指定位置的列表项类型

}
