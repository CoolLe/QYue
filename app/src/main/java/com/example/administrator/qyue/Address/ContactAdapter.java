package com.example.administrator.qyue.Address;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.qyue.LoginActivity;
import com.example.administrator.qyue.Message.MessageActivity;
import com.example.administrator.qyue.R;
import com.example.administrator.qyue.Utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;

public class ContactAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private String[] mContactNames; // 联系人名称字符串数组
    private List<String> mContactList; // 联系人名称List（转换成拼音）
    private List<Contact> resultList; // 最终结果（包含分组的字母）
    private List<String> characterList; // 字母List


    public enum ITEM_TYPE {

        ITEM_TYPE_CHARACTER,//字母
        ITEM_TYPE_CONTACT//联系人

    }

    public ContactAdapter(Context context, String[] contactNames) {

        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mContactNames = contactNames;

        handleContact();

    }

    private void handleContact() {

        mContactList = new ArrayList<>();
        Map<String, String> map = new HashMap<>();


//实现联系人排序
        for (int i = 0; i < mContactNames.length; i++) {
            String pinyin = Utils.getPingYin(mContactNames[i]);
            map.put(pinyin, mContactNames[i]);
            mContactList.add(pinyin);
        }

        Collections.sort(mContactList, new ContactComparator());

        resultList = new ArrayList<>();
        characterList = new ArrayList<>();


//按照字母表写入联系人名称
        for (int i = 0; i < mContactList.size(); i++) {
            String name = mContactList.get(i);
            String character = (name.charAt(0) + "").toUpperCase(Locale.ENGLISH);
            if (!characterList.contains(character)) {
                if (character.hashCode() >= "A".hashCode() && character.hashCode() <= "Z".hashCode()) { // 是字母
                    characterList.add(character);
                    resultList.add(new Contact(character, ITEM_TYPE.ITEM_TYPE_CHARACTER.ordinal()));
                } else {

                    if (!characterList.contains("#")) {
                        characterList.add("#");
                        resultList.add(new Contact("#", ITEM_TYPE.ITEM_TYPE_CHARACTER.ordinal()));
                    }
                }
            }
            resultList.add(new Contact(map.get(name), ITEM_TYPE.ITEM_TYPE_CONTACT.ordinal()));
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE.ITEM_TYPE_CHARACTER.ordinal()) {
            return new CharacterHolder(mLayoutInflater.inflate(R.layout.address_item, parent, false));
        } else {
            return new ContactHolder(mLayoutInflater.inflate(R.layout.contact_item, parent, false));
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CharacterHolder) {
            ((CharacterHolder) holder).mTextView.setText(resultList.get(position).getmName());
        } else if (holder instanceof ContactHolder) {
            ((ContactHolder) holder).mTextView.setText(resultList.get(position).getmName());
        }
    }

    @Override
    public int getItemViewType(int position) {
        return resultList.get(position).getmType();
    }

    @Override
    public int getItemCount() {
        return resultList == null ? 0 : resultList.size();
    }

    public class CharacterHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        CharacterHolder(View view) {
            super(view);
            mTextView = (TextView) view.findViewById(R.id.character);
        }
    }

    public class ContactHolder extends RecyclerView.ViewHolder {
        TextView mTextView;


        ContactHolder(View view) {
            super(view);
            mTextView = view.findViewById(R.id.contact_name);
            view.setOnClickListener(v -> {

                Log.d("TAG", "onResponse: ====================================" + mTextView.getText().toString());
                findPhone(mTextView.getText().toString());

            });
        }

    }


    public void findPhone(String name){

        //初始化okhttp客户端
        OkHttpClient client = new OkHttpClient.Builder().build();
        //创建POST表单，获取username和password
        RequestBody post = new FormBody.Builder()
                .add("friendName",name)
                .build();
        //开始请求，填入url和表单
        Request request = new Request.Builder()
                .url("http://47.101.176.1:8090/friend/find")
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
                Log.d("TAG", "MessageActivity: ====================================" + responseData);
                Intent intent = new Intent(mContext, MessageActivity.class);
                intent.putExtra("friendPhone",responseData);
                intent.putExtra("friendName",name);
                mContext.startActivity(intent);
            }
        });
    }

    public int getScrollPosition(String character) {

        if (characterList.contains(character)) {
            for (int i = 0; i < resultList.size(); i++) {
                if (resultList.get(i).getmName().equals(character)) {
                    return i;
                }
            }
        }
        return -1; // -1不会滑动

    }

}
