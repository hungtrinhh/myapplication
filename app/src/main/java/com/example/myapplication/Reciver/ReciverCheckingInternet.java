package com.example.myapplication.Reciver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

public class ReciverCheckingInternet extends BroadcastReceiver implements DialogInterface.OnClickListener {
    @Override
    public void onReceive(Context context, Intent intent) {
        boolean status = false;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                status = true;
            } else
                if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                status = true;

            } else {
                status = false;
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);


        if (MainActivity.alertDialog == null) {

            MainActivity.alertDialog = builder.create();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.dialog_lostconecttion, null);
            MainActivity.alertDialog.setView(view);
        }


        if (status) {
            MainActivity.alertDialog.cancel();

        } else {
            MainActivity.alertDialog.show();

        }


    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

    }
}