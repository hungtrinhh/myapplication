package com.example.myapplication.Fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;


public class fragment_Main extends Fragment implements NavigationView.OnNavigationItemSelectedListener {


    private int itemselectID = R.id.bottomNav_Home;
    private FragmentManager fragmentManager = null;


    private BottomNavigationView bottomNav;

    public fragment_Main() {
    }

    public static fragment_Main newInstance() {
        fragment_Main fragment = new fragment_Main();
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("TAG", "onStart: ");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        Log.d("TAG", "onCreateView: ");
        View view = inflater.inflate(R.layout.fragment_main, null);
        Anhxa(view);
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    private View viewcontainer;

    private void Anhxa(View v) {
        this.viewcontainer = v;
        Log.d("TAG", "Anhxa: ");
        bottomNav = (BottomNavigationView) v.findViewById(R.id.bottomNav);

        fragmentManager = getActivity().getSupportFragmentManager();
        bottomNav.getMenu().findItem(R.id.bottomNav_Home).setChecked(true);
        bottomNav.setOnNavigationItemSelectedListener(this::onNavigationItemSelected);
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(broadcastReceiver, filter);
        ReplaceFragment(new fragment_Trangchu());
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String status = "";
            Log.d("TAG", "onReceive: " + status);
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (activeNetwork != null) {
                if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                    status = "Wifi enabled";
                } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                    status = "Mobile data enabled";
                } else {
                    status = "";
                }
            }

            if (status.equals("")) {
                //        SnackBar hien thi ket noi wifi
                Snackbar snackbar = Snackbar.make(viewcontainer, "Không có kết nối mạng", Snackbar.LENGTH_LONG);
                snackbar.show();
            }


        }
    };

    @Override
    public void onPause() {
        super.onPause();
        Log.d("TAG", "onPause: ");
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(broadcastReceiver);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        //nếu item chọn trùng với item đã chọn thì không thay đổi
        if (item.getItemId() == itemselectID) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.bottomNav_Home:
                ReplaceFragment(new fragment_Trangchu());
                break;
            case R.id.bottomNav_User:
                ReplaceFragment(new fragment_User());
                break;
            case R.id.bottomNav_Doter:
                ReplaceFragment(new fragment_Uudai());
                break;
        }

        itemselectID = item.getItemId();
        return true;
    }

    private void ReplaceFragment(Fragment fragment) {
        fragmentManager.beginTransaction().replace(R.id.frameContainer, fragment).commit();
    }
}