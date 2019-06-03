package com.example.administrator.qyue;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;

public class MajorActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView topBar;
    private TextView tabMessage;
    private TextView tabAddress;

    private TextView tabUser;

    private FrameLayout ly_content;

    private FirstFragment f1,f2,f3;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_major);

        bindView();

    }

    //UI组件初始化与事件绑定
    private void bindView() {
        topBar = (TextView)this.findViewById(R.id.txt_top);
        tabMessage = (TextView)this.findViewById(R.id.txt_message);
        tabAddress = (TextView)this.findViewById(R.id.txt_address);
        tabUser = (TextView)this.findViewById(R.id.txt_my);
        ly_content = (FrameLayout) findViewById(R.id.fragment_container);

        tabMessage.setOnClickListener(this);
        tabUser.setOnClickListener(this);
        tabAddress.setOnClickListener(this);

    }

    //重置所有文本的选中状态
    public void selected(){
        tabMessage.setSelected(false);
        tabAddress.setSelected(false);
        tabUser.setSelected(false);
    }

    //隐藏所有Fragment
    public void hideAllFragment(FragmentTransaction transaction){
        if(f1!=null){
            transaction.hide(f1);
        }
        if(f2!=null){
            transaction.hide(f2);
        }
        if(f3!=null){
            transaction.hide(f3);
        }
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        hideAllFragment(transaction);
        switch(v.getId()){
            case R.id.txt_message:
                selected();
                tabMessage.setSelected(true);
                if(f1==null){
                    f1 = new FirstFragment("聊天框框");
                    transaction.add(R.id.fragment_container,f1);
                }else{
                    transaction.show(f1);
                }
                break;


            case R.id.txt_address:
                selected();
                tabAddress.setSelected(true);
                if(f2==null){
                    f2 = new FirstFragment("好友列表框框");
                    transaction.add(R.id.fragment_container,f2);
                }else{
                    transaction.show(f2);
                }
                break;

            case R.id.txt_my:
                selected();
                tabUser.setSelected(true);
                if(f3==null){
                    f3 = new FirstFragment("我的信息框框");
                    transaction.add(R.id.fragment_container,f3);
                }else{
                    transaction.show(f3);
                }
                break;
        }

        transaction.commit();
    }
}