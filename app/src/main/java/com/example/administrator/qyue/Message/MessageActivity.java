package com.example.administrator.qyue.Message;

import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.qyue.R;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.constant.MsgTypeEnum;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends AppCompatActivity {

    private List<Msg> msgList=new ArrayList<>();
    //消息发送框
    private EditText inputText;
    //发送按钮
    private Button send;
    //消息视图
    private RecyclerView msgRecyclerView;

    private  MsgAdapter adapter;

    private TextView friendName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_message);

        NIMClient.getService(MsgServiceObserve.class)
                .observeReceiveMessage(incomingMessageObserver, true);

        friendName=(TextView) findViewById(R.id.friend_name);
        Intent intent = getIntent();
        friendName.setText(intent.getStringExtra("friendName"));
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
                Intent intent = getIntent();
                if (!"".equals(content)) {
                    sendMsg(intent.getStringExtra("friendPhone"),content);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NIMClient.getService(MsgServiceObserve.class)
                .observeReceiveMessage(incomingMessageObserver, false);
    }

    Observer<List<IMMessage>> incomingMessageObserver =
        new Observer<List<IMMessage>>() {
            @Override
            public void onEvent(List<IMMessage> messages) {
                // 处理新收到的消息，为了上传处理方便，SDK 保证参数 messages 全部来自同一个聊天对象。
                for (IMMessage message : messages){
                    if(message.getMsgType() == MsgTypeEnum.text){

                        Msg msg = new Msg(message.getContent(), Msg.TYPE_RECEIVED);

                        msgList.add(msg);
                        // 当有新消息时，刷新ListView中的显示
                        adapter.notifyItemInserted(msgList.size() - 1);
                        // 将ListView定位到最后一行
                        msgRecyclerView.scrollToPosition(msgList.size() - 1);
                    }
                }
            }
        };

    private void sendMsg (String sendAccount,String sendContent){

        String account = sendAccount;
        SessionTypeEnum sessionType = SessionTypeEnum.P2P;
        String content = sendContent;

        IMMessage textMessage = MessageBuilder.createTextMessage(account, sessionType, content);

        NIMClient.getService(MsgService.class).sendMessage(textMessage, false).setCallback(new RequestCallback<Void>() {
            @Override
            public void onSuccess(Void param) {
                Msg msg = new Msg(content, Msg.TYPE_SEND);
                msgList.add(msg);
                // 当有新消息时，刷新ListView中的显示
                adapter.notifyItemInserted(msgList.size()-1);
                //当有新消息时，刷新RecyclerView中的显示
                msgRecyclerView.scrollToPosition(msgList.size()-1);
                //将RecyclerView定位到最后一行
                inputText.setText("");  //清空输入框的内容
                Toast.makeText(MessageActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed(int code) {
                Toast.makeText(MessageActivity.this, "发送失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onException(Throwable exception) {
                Toast.makeText(MessageActivity.this, "服务器异常", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
