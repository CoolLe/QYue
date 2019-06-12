package com.example.administrator.qyue;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.administrator.qyue.Utils.ToastUtils;

import java.io.IOException;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ForgetActivity extends AppCompatActivity {


    @BindView(R.id.phoneNum)
    EditText phone;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.rePassword)
    EditText repassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);

        setTitle("忘记密码");
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

    @OnClick(R.id.resetButtom)
    public void onViewClicked() {
        if (!checkUserData()) {
            return;
        }


        //初始化okhttp客户端
        OkHttpClient client = new OkHttpClient.Builder().build();
        //创建POST表单，获取username和password
        RequestBody post = new FormBody.Builder()
                .add("phongNum", phone.getText().toString())
                .add("userPassword", password.getText().toString())
                .build();
        //开始请求，填入url和表单
        Request request = new Request.Builder()
                .url("http://47.101.176.1:8090/user/reset")
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
                    ToastUtils.toastShowe(ForgetActivity.this, "重置成功!");
                    finish();
                } else {
                    ToastUtils.toastShowe(ForgetActivity.this, "重置失败,该手机号不存在!");
                    finish();
                }
            }

        });
    }


    private boolean checkUserData() {
        if ("".equals(phone.getText().toString())) {
            ToastUtils.toastShowe(this, "手机号不能为空");
            return false;
        }

        if ("".equals(password.getText().toString())) {
            ToastUtils.toastShowe(this, "密码不能为空");
            return false;
        }

        if (password.getText().toString() != repassword.getText().toString()) {
            ToastUtils.toastShowe(this, "密码不一致");
            return false;
        }
        return true;
    }
}
