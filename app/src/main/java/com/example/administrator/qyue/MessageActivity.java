package com.example.administrator.qyue;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends AppCompatActivity {

    private List<Msg> msgList=new ArrayList<>();
    private EditText inputText;
    private Button send;
    private RecyclerView msgRecyclerView;
    private  MsgAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_message);

        initMsgs();//初始化消息数据
        inputText=(EditText) findViewById(R.id.inputFrame);
        send=(Button) findViewById(R.id.send);
        msgRecyclerView=(RecyclerView)findViewById(R.id.msg_recycler_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        msgRecyclerView.setLayoutManager(layoutManager);
        adapter=new MsgAdapter(msgList);
        msgRecyclerView.setAdapter(adapter);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content=inputText.getText().toString();
                if (!"".equals(content)) {
                    Msg msg=new Msg(content,Msg.TYPE_SEND);
                    msgList.add(msg);
                    adapter.notifyItemInserted(msgList.size()-1);
                    //当有新消息时，刷新RecyclerView中的显示
                    msgRecyclerView.scrollToPosition(msgList.size()-1);
                    //将RecyclerView定位到最后一行
                    inputText.setText("");  //清空输入框的内容
                }
            }
        });
    }
    private void initMsgs(){
        Msg msg1=new Msg("您好，请问有什么需要帮助的？",Msg.TYPE_RECEIVED);
        msgList.add(msg1);
        Msg msg2=new Msg("我想查询自己的成绩",Msg.TYPE_SEND);
        msgList.add(msg2);
        Msg msg3=new Msg("好的，请稍等",Msg.TYPE_RECEIVED);
        msgList.add(msg3);
    }

}
