package com.fyp.womensafetyapp.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.TextView;

import com.fyp.womensafetyapp.R;

public class LoadingDialogBar {

    public Context context;
    public Dialog dialog;

    public LoadingDialogBar(Context context){
        this.context = context;
    }

    public void showDialog(String title){

        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView txtTitle = dialog.findViewById(R.id.tvDialogTitle);
        txtTitle.setText(title);
        dialog.create();
        dialog.show();
    }

    public void hideDialog(){
        dialog.dismiss();
    }
}