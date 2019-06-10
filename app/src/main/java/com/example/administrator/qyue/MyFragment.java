package com.example.administrator.qyue;

import android.annotation.SuppressLint;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

@SuppressLint("ValidFragment")
public class MyFragment extends Fragment {
    private TextView mTextView;

    private Button exitButtom;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_fragment,container,false);
        mTextView = (TextView)view.findViewById(R.id.my_top);
        initView(view);
        return view;
    }

    private void initView(View view) {
        exitButtom=(Button)view.findViewById(R.id.exitButtom);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initData();
    }

    private void initData() {
        //退出登录的路基处理
        exitButtom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




            }
        });
    }
}