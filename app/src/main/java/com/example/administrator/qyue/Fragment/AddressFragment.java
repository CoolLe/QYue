package com.example.administrator.qyue.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.administrator.qyue.AddFriendActivity;
import com.example.administrator.qyue.Address.ContactAdapter;
import com.example.administrator.qyue.Address.DividerItemDecoration;
import com.example.administrator.qyue.Address.LetterView;
import com.example.administrator.qyue.R;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import static com.netease.nimlib.sdk.media.player.AudioPlayer.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddressFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddressFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddressFragment extends Fragment {

    private RecyclerView contactList;
    private String[] contactNames;
    private String[] contactPhoneNum;
    private LinearLayoutManager layoutManager;
    private LetterView letterView;
    private ContactAdapter adapter;
    private String CurrentUser;
    private ImageView iv_add;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            JSONArray jsonArray = (JSONArray) msg.obj;
            contactPhoneNum = new String[jsonArray.length()];
            contactNames = new String[jsonArray.length()];
            try{
                for (int i = 0;i<jsonArray.length();i++){
                    contactPhoneNum[i] = jsonArray.getJSONObject(i).getString("friendPhoneNum");
                    contactNames[i] = jsonArray.getJSONObject(i).getString("friendName");

                    SharedPreferences preferences = getActivity().getSharedPreferences("loginInformation", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = preferences.edit();
                    edit.putString(jsonArray.getJSONObject(i).getString("friendName"),jsonArray.getJSONObject(i).getString("friendPhoneNum"));
                    edit.apply();

                }
            }catch (JSONException e){
                Log.d(TAG, "handleMessage: "+e.getMessage());
            }
            //这里添加创建通讯录界面的方法
            showAddress();
        }
    };

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AddressFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddressFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddressFragment newInstance(String param1, String param2) {
        AddressFragment fragment = new AddressFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_address, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        SharedPreferences login = getActivity().getSharedPreferences("loginInformation", Context.MODE_PRIVATE);
        CurrentUser = login.getString("loginPhone","0");
        //初始化okhttp客户端
        OkHttpClient client = new OkHttpClient.Builder().build();
        //创建POST表单，获取username和password
        RequestBody post = new FormBody.Builder()
                .add("phoneNum",CurrentUser)
                .build();
        //开始请求，填入url和表单
        Request request = new Request.Builder()
                .url("http://47.101.176.1:8090/friend/findFriends")
                .post(post)
                .build();
        //Toast.makeText(this, "已经填入表单和url", Toast.LENGTH_SHORT).show();
        Call call = client.newCall(request);
        //客户端回调
        call.enqueue(new Callback(){

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                final String responseData = response.body().string();
                try{
                    JSONArray jsonArray = new JSONArray(responseData);
                    //Log.d(TAG, "onResponse: ===================================" + jsonArray);
                    Message msg = new Message();
                    msg.obj = jsonArray;
                    handler.sendMessage(msg);
                }catch (JSONException e){
                    Log.d(TAG, "run: "+e.getMessage());
                }
            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }
        });
    }

    //增加好友按钮的监听事件
    private void initListener() {
        iv_add = getActivity().findViewById(R.id.iv_add);
        iv_add.setOnClickListener(new ImageView.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), AddFriendActivity.class);
                startActivity(intent);
            }
        });
    }


    private void showAddress(){
        contactList =  getActivity().findViewById(R.id.contact_list);
        letterView = getActivity().findViewById(R.id.letter_view);

        //address_invite=getActivity().findViewById(R.id.address_invite);

        layoutManager = new LinearLayoutManager(getActivity());
        adapter = new ContactAdapter(getActivity(),contactNames);

        contactList.setLayoutManager(layoutManager);
        contactList.addItemDecoration(new android.support.v7.widget.DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        contactList.setAdapter(adapter);


        //实现字母排序好友列表
        letterView.setCharacterListener(new LetterView.CharacterClickListener() {

            @Override
            public void clickCharacter(String character) {
                layoutManager.scrollToPositionWithOffset(adapter.getScrollPosition(character), 0);
            }

            @Override
            public void clickArrow() {
                layoutManager.scrollToPositionWithOffset(0, 0);
            }
        });
        initListener();
    }
}

