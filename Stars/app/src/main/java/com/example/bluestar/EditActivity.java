package com.example.bluestar;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;

public class EditActivity extends AppCompatActivity {

    private EditText nameEditText;
    private EditText bioEditText;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editinfo);

        nameEditText = findViewById(R.id.name_edit_text);
        bioEditText = findViewById(R.id.bio_edit_text);
        saveButton = findViewById(R.id.save_button);
        new Thread(new Runnable() {
            @Override
            public void run() {
                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = nameEditText.getText().toString();
                        String bio = bioEditText.getText().toString();

                        Connection con = DBUtil.getConnection();
                        if(con == null){
                            System.out.println("!!!!!!!!!!!!!!!!!!!###########################$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
                        }
                        // 获取用户id或其他全局变量来修改用户名和个人简介
                        int userid = 1;
                        UserDao.updateUserName(userid, name);
                        UserDao.updateUserBio(userid, bio);

                        Toast.makeText(EditActivity.this, "个人信息已保存", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(EditActivity.this, UserInfo.class);
                        startActivity(intent);
                    }
                });
            }
        }).start();
    }
}