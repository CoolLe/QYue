package com.example.administrator.qyue;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class LoginActivity extends Activity {
    private Button login,regist,forget;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);
            Button regist=findViewById(R.id.registButtom);
            Button forget=findViewById(R.id.forgetButtom);

            forget.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(LoginActivity.this,ForgetActivity.class);
                    startActivity(intent);
                }
            });
            regist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(LoginActivity.this,RegistActivity.class);
                    startActivity(intent);
                }
            });
//        Button login=findViewById(R.id.loginButtom);
//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(LoginActivity.this,)
//            }
//        });
    }
}
