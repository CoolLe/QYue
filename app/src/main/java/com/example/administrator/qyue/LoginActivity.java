package com.example.administrator.qyue;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Context;
import android.net.ConnectivityManager;
import android.view.Gravity;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.example.administrator.qyue.Web.WebService;


public class LoginActivity extends Activity implements OnClickListener{
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    //loginButton
    private  Button logbtn;
    //test register
    private  TextView regtv,infotv;
    //userInformation
    EditText username,password;
    //waiting dialog
    private ProgressDialog dialog;
    // 返回的数据
    private String info;
    //return thread
    private static Handler handler=new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //获取控件
        username = (EditText) findViewById(R.id.userTest);
        password = (EditText) findViewById(R.id.pwdTest);
        logbtn = (Button) findViewById(R.id.loginButtom);
        regtv = (TextView) findViewById(R.id.registButtom);
        infotv = (TextView) findViewById(R.id.info);

        //set listener
        logbtn.setOnClickListener(this);


        //init();

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginButtom:
                // 检测网络，无法检测wifi
                if (!checkNetwork()) {
                    Toast toast = Toast.makeText(LoginActivity.this,"网络未连接", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    break;
                }
                // 提示框
                dialog = new ProgressDialog(this);
                dialog.setTitle("提示");
                dialog.setMessage("正在登陆，请稍后...");
                dialog.setCancelable(false);
                dialog.show();
                // 创建子线程，分别进行Get和Post传输
                new Thread(new MyThread()).start();
                break;
            case R.id.registButtom:
                Intent regItn = new Intent(LoginActivity.this, RegistActivity.class);
                // overridePendingTransition(anim_enter);
                startActivity(regItn);
                break;
        }
        ;
    }
    // 子线程接收数据，主线程修改数据
    public class MyThread implements Runnable {
        @Override
        public void run() {
            info = WebService.executeHttpGet(username.getText().toString(), password.getText().toString());
            // info = WebServicePost.executeHttpPost(username.getText().toString(), password.getText().toString());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    infotv.setText(info);
                    dialog.dismiss();
                }
            });
        }
    }
    // 检测网络
    private boolean checkNetwork() {
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connManager.getActiveNetworkInfo() != null) {
            return connManager.getActiveNetworkInfo().isAvailable();
        }
        return false;
    }


    private void init() {
        TextView tv_register = (TextView) findViewById(R.id.registButtom);
        TextView tv_find_psw = (TextView) findViewById(R.id.forgetButtom);
        Button btn_login = (Button) findViewById(R.id.loginButtom);
        username = (EditText) findViewById(R.id.userTest);
        password = (EditText) findViewById(R.id.pwdTest);
        //立即注册控件的点击事件
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //为了跳转到注册界面，并实现注册功能
                Intent intent = new Intent(LoginActivity.this, RegistActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        //找回密码控件的点击事件
        tv_find_psw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgetActivity.class));
            }
        });

    }
}
