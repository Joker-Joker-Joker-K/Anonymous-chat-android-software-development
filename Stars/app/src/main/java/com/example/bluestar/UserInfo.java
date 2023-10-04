package com.example.bluestar;




import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class UserInfo extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);

        TextView nameTextView = findViewById(R.id.name_text_view);
        TextView bioTextView = findViewById(R.id.bio_text_view);
        Button editButton = findViewById(R.id.edit_button);

        // 设置初始的个人信息文本
        String name = "名字:";
        String user_say = "个人简介:";
        // 用UserDao中写好的函数获取名字和个人简介append到name和usersay中

        nameTextView.setText(name);
        bioTextView.setText(user_say);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到修改信息界面
                Intent intent = new Intent(UserInfo.this, EditActivity.class);
                startActivity(intent);
            }
        });
    }


}

