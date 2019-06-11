package com.example.administrator.qyue;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.administrator.qyue.Utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;


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
                ToastUtils.toastShowe(this, "注册成功");
                finish();
                break;
            default:
        }
    }

    private void register() {
        if (checkUserData()) {

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

