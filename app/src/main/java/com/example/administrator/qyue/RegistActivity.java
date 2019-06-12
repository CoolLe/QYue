package com.example.administrator.qyue;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.administrator.qyue.Utils.ToastUtils;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class RegistActivity extends AppCompatActivity {
    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.phoneNum)
    EditText phoneNum;
    @BindView(R.id.password)
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);


        setTitle("注册");
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.register})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.register:
                register();
                break;
            default:
        }
    }

    private void register() {
        if (checkUserData()) {
            //初始化okhttp客户端
            OkHttpClient client = new OkHttpClient.Builder().build();
            //创建POST表单，获取username和password
            RequestBody post = new FormBody.Builder()
                    .add("phongNum", phoneNum.getText().toString())
                    .add("userPassword", password.getText().toString())
                    .add("userName", username.getText().toString())
                    .build();
            //开始请求，填入url和表单
            Request request = new Request.Builder()
                    .url("http://47.101.176.1:8090/user/login")
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
                public void onResponse(Call call, Response response) throws IOException {
                    //请求成功的处理
                    final String responseData = response.body().string();
                    if (responseData == "ture") {
                        ToastUtils.toastShowe(RegistActivity.this, "注册成功!");
                        finish();
                    } else {
                        ToastUtils.toastShowe(RegistActivity.this, "注册失败,该手机号已被使用!");
                        finish();
                    }
                }

            });
        }

    }

    private boolean checkUserData() {
        // todo 账号是否重复应该连网检查
        if ("".equals(phoneNum.getText().toString())) {
            ToastUtils.toastShowe(this, "手机号不能为空");
            return false;
        }

        if (!checkusername()) {
            return false;
        }

        if ("".equals(password.getText().toString())) {
            ToastUtils.toastShowe(this, "密码不能为空");
            return false;
        }

        return true;
    }

    private boolean checkusername() {
        if ("".equals(username.getText().toString())) {
            ToastUtils.toastShowe(this, "用户名不能为空");
            return false;
        }
        return true;

    }
}

