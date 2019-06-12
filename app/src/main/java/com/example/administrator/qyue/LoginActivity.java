package com.example.administrator.qyue;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.qyue.Utils.ToastUtils;

import java.io.IOException;
import java.util.Objects;

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

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.phonenumber)
    EditText phoneNum;
    @BindView(R.id.password)
    EditText password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    public void login(){

        //初始化okhttp客户端
        OkHttpClient client = new OkHttpClient.Builder().build();
        //创建POST表单，获取username和password
        RequestBody post = new FormBody.Builder()
                .add("phoneNum",phoneNum.getText().toString())
                .add("password",password.getText().toString())
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
            public void onResponse(Call call, Response response) throws IOException{
                final String responseData = response.body().string();
                Log.d("TAG", "onResponse: ====================================" + responseData);
                if(responseData.equals("no")){
                    if (Looper.myLooper() == null) {
                        Looper.prepare();
                    }
                    Toast.makeText(LoginActivity.this, "用户名密码不一致", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }else {
                    startActivity(new Intent(LoginActivity.this,MajorActivity.class));
                    finish();
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private boolean checkPassword(String phoneNum, String password) {

        return Objects.equals(phoneNum, this.phoneNum.getText().toString())
                && Objects.equals(password, this.password.getText().toString());
    }

    private boolean checkUserData() {

        if ("".equals(phoneNum.getText().toString())) {
            ToastUtils.toastShowe(this, "用户名不能为空");
            return false;
        }

        if ("".equals(password.getText().toString())) {
            ToastUtils.toastShowe(this, "密码不能为空");
            return false;
        }

        return true;
    }

    @OnClick({R.id.loginButtom, R.id.registButtom, R.id.forgetButtom})
    public void onViewClicked(View view) {
        Log.d("TAG", view.getId() + "");
        switch (view.getId()) {
            case R.id.loginButtom:
                login();
                break;
            case R.id.registButtom:
                startActivity(new Intent(this, RegistActivity.class));
                break;
            case R.id.forgetButtom:
                startActivity(new Intent(this, ForgetActivity.class));
                break;
            default:
                Log.d("TAG", "default");
        }
    }
}
