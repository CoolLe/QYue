package com.example.administrator.qyue;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public abstract class ToastUtils {

    public static void toastShowe(Context context, CharSequence text) {
        Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
