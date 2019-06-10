package com.example.administrator.qyue;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.widget.RadioGroup;

public class MajorActivity extends FragmentActivity implements AddressFragment.OnFragmentInteractionListener {

    private RadioGroup rg_major;
    private MessageFragment messageFragment;
    private AddressFragment addressFragment;
    private MyFragment myFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();
        setContentView(R.layout.activity_major);


        initView();
        initData();
        initLister();

    }

    private void initLister() {
        //RadioGroup的选择事件
        rg_major.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Fragment fragment=null;
                switch (checkedId){
                    //消息列表页面
                    case R.id.messageButton:
                        fragment=messageFragment;
                    break;

                    //通讯录列表界面
                    case R.id.addressButton:
                        fragment=addressFragment;
                        break;

                    //我的界面
                    case R.id.myButton:
                        fragment=myFragment;
                        break;
                }
                //实现fragment切换的方法
                switchFragment(fragment);
            }
        });
      //默认选择消息列表页面
        rg_major.check(R.id.messageButton);
    }
    //实现fragment切换的方法
    private void switchFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fl_main,fragment).commit();

    }

    private void initView() {
        rg_major=(RadioGroup) findViewById(R.id.rg_major);

    }

    private void initData() {
        //创建三个fragment对象
        messageFragment = new MessageFragment();
        addressFragment = new AddressFragment();
        myFragment = new MyFragment();
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

