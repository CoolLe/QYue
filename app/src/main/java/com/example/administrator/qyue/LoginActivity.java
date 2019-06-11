package com.example.administrator.qyue;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.phoneNum)
    EditText phoneNum;
    @BindView(R.id.password)
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);


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
                startActivity(new Intent(this, MajorActivity.class));
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
