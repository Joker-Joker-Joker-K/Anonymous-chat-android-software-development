package com.example.bluestar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;
//控制导航栏
public class FragmentActivity extends AppCompatActivity implements View.OnClickListener {
    //顶部导航栏
    private TextView fg_topbar;
//    四个导航项
    private TextView fglist;
//    private TextView fg_1;
//    private TextView fg_2;
    private TextView fguser;
//
    private FrameLayout fg;

    private MyFragment fg1,fg2,fg3,fg4;
    private FragmentManager fManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        fManager = getSupportFragmentManager();
        bindViews();

        fglist.performClick();
        //模拟一次点击，既进去后选择第一项
    }
    private void bindViews() {
        fg_topbar = (TextView) findViewById(R.id.fg_top_bar_tv);
        fglist = (TextView) findViewById(R.id.fg_list);
//        待定
//        fg_1 = (TextView) findViewById(R.id.fg_1);
//        fg_2 = (TextView) findViewById(R.id.fg_2);
        fguser = (TextView) findViewById(R.id.fg_user);
        fg = (FrameLayout) findViewById(R.id.fg);

        fglist.setOnClickListener(this);
//        fg_1.setOnClickListener(this);
//        fg_2.setOnClickListener(this);
        fguser.setOnClickListener(this);
    }

    //重置所有文本的选中状态
    private void setSelected(){
        fglist.setSelected(false);
//        fg_1.setSelected(false);
//        fg_2.setSelected(false);
        fguser.setSelected(false);
    }

    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction){
        if(fg1 != null)fragmentTransaction.hide(fg1);
        if(fg2 != null)fragmentTransaction.hide(fg2);
        if(fg3 != null)fragmentTransaction.hide(fg3);
        if(fg4 != null)fragmentTransaction.hide(fg4);
    }
    @Override
    public void onClick(View v) {
//        FragmentTransaction 实例，用于处理 Fragment 相关的操作。
//        之后可以使用该实例执行添加、显示和隐藏 Fragment 的操作，并通过调用 commit() 方法提交事务。
        FragmentTransaction fTransaction = getSupportFragmentManager().beginTransaction();
        hideAllFragment(fTransaction);
        if(v.getId()==R.id.fg_list){
            setSelected();
            fglist.setSelected(true);
            if(fg1 == null){
                fg1 = new MyFragment("第一个Fragment");
                fTransaction.add(R.id.fg,fg1);
            }else{
                fTransaction.show(fg1);
            }
        }
//        else if (v.getId()==R.id.fg_1) {
//            setSelected();
//            fg_1.setSelected(true);
//            if(fg2 == null){
//                fg2 = new MyFragment("第二个Fragment");
//                fTransaction.add(R.id.fg,fg2);
//            }else{
//                fTransaction.show(fg2);
//            }
//        } else if (v.getId()==R.id.fg_2) {
//            setSelected();
//            fg_2.setSelected(true);
//            if(fg3 == null){
//                fg3 = new MyFragment("第三个Fragment");
//                fTransaction.add(R.id.fg,fg3);
//            }else{
//                fTransaction.show(fg3);
//            }
//        }
        else if (v.getId()==R.id.fg_user) {
            setSelected();
            fguser.setSelected(true);
            if(fg4 == null){
                fg4 = new MyFragment("第四个Fragment");
                fTransaction.add(R.id.fg,fg4);
            }else{
                fTransaction.show(fg4);
            }
        }
        fTransaction.commit();
    }
}