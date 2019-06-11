package com.example.administrator.qyue;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.administrator.qyue.Utils.LoadingUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Map;

public class AddFriendActivity extends BaseActivity implements View.OnClickListener {


    private String TAG = "friend";
    private SearchView mSvSearchFriends;
    private LinearLayout mLlReturn;
    private TextView mTvResult;

    private LoadingUtils loadingUtils;

    final ArrayList<Map<String, Object>> listdata = new ArrayList<>();
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
    }

    @Override
    public void onClick(View v) {

    }
}
