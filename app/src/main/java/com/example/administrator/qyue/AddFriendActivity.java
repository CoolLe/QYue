package com.example.administrator.qyue;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AddFriendActivity extends AppCompatActivity {

    @BindView(R.id.friend_phone_number)
    EditText friendPhoneNumber;
    @BindView(R.id.add_buttom)
    Button addBtn;

    private String CurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.add_buttom)
    public void onViewClicked() {
        AddFriend();
    }

    public void AddFriend(){

        SharedPreferences login = getSharedPreferences("loginInformation", Context.MODE_PRIVATE);
        CurrentUser = login.getString("loginPhone","0");

        //初始化okhttp客户端
        OkHttpClient client = new OkHttpClient.Builder().build();
        //创建POST表单，获取username和password
        RequestBody post = new FormBody.Builder()
                .add("phoneNum",CurrentUser)
                .add("friendPhoneNum",friendPhoneNumber.getText().toString())
                .build();
        //开始请求，填入url和表单
        Request request = new Request.Builder()
                .url("http://47.101.176.1:8090/friend/addFriends")
                .post(post)
                .build();
        //Toast.makeText(this, "已经填入表单和url", Toast.LENGTH_SHORT).show();
        Call call = client.newCall(request);
        //客户端回调
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //请求失败的处理
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException{
                final String responseData = response.body().string();
                if(responseData.equals("no")){
                    if (Looper.myLooper() == null) {
                        Looper.prepare();
                    }
                    Toast.makeText(AddFriendActivity.this, "该用户不存在", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
                else if(responseData.equals("fail")){
                    if (Looper.myLooper() == null) {
                        Looper.prepare();
                    }
                    Toast.makeText(AddFriendActivity.this, "好友已存在", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
                if (Looper.myLooper() == null) {
                    Looper.prepare();
                }
                Toast.makeText(AddFriendActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                Looper.loop();
                finish();
            }
        });

    }
}
