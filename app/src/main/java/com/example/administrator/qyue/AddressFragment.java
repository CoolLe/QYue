package com.example.administrator.qyue;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

@SuppressLint("ValidFragment")
public class AddressFragment extends Fragment {
    private String context;
    private TextView mTextView;

    public AddressFragment(String context){
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.address_fragment,container,false);
        mTextView = (TextView)view.findViewById(R.id.txt_content);
        //mTextView = (TextView)getActivity().findViewById(R.id.txt_content);
        mTextView.setText(context);
        return view;
    }
}