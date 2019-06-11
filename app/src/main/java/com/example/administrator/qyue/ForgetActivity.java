package com.example.administrator.qyue;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

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

        SharedPreferences preferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        String phone = preferences.getString("phone", "");
        Log.d("TAG", "phone: " + phone);

        if (Objects.equals(phone, this.phone.getText().toString())) {
            SharedPreferences.Editor edit = preferences.edit();
            edit.putString("password", this.password.getText().toString());
            Log.d("TAG", "passwprd: " + this.password.getText().toString());
            edit.apply();
            ToastUtils.toastShowe(this, "重置成功");
            finish();
        }
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

        if (password.getText().toString()!=repassword.getText().toString()){
            ToastUtils.toastShowe(this, "密码不一致");
            return false;
        }
        return true;
    }
}
