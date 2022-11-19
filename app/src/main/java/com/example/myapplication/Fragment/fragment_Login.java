package com.example.myapplication.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.*;
import android.view.animation.AnimationUtils;
import android.widget.*;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.example.myapplication.Firebase.FbDao;
import com.example.myapplication.Model.*;
import com.example.myapplication.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.*;


public class fragment_Login extends Fragment implements View.OnClickListener {
    //  khai báo
    private LinearLayout layoutLogoWhite;
    private EditText ed_Username;
    private EditText ed_Password;

    private AppCompatButton btn_Login;
    private TextView tv_GoToRegister, tv_FogotPassword;
    //    khai báo biến username & password giá trị rỗng
    private String Usernameavali = "", passwordavali = "";
    private List<User> list;
    private final String TAG = fragment_Login.class.toString();
    private ImageView imgHidePassword;


    //khai báo view
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //gọi hàm ánh xạ(truyền view để tìm id trong view đó)
        Anhxa(view);
        //gọi hàm animation (truyền vào các tham số)
        animation(layoutLogoWhite, ed_Username, ed_Password, btn_Login, tv_GoToRegister, tv_FogotPassword);

        LoginWithoutbtn();
        //bắt sự kiện khi click
        btn_Login.setOnClickListener(this::onClick);
        tv_GoToRegister.setOnClickListener(this::onClick);
        tv_FogotPassword.setOnClickListener(this::onClick);
        imgHidePassword.setOnClickListener(this::onClick);
    }


    private void LoginWithoutbtn() {
        SharedPreferences s = getActivity().getSharedPreferences("account", Context.MODE_PRIVATE);
        String username = s.getString("Username", "");
        String password = s.getString("Password", "");


        if (username.equals("") || password.equals("")) {
            return;
        }

        for (User u : list
        ) {
            if (username.equals(u.getName()) && password.equals(u.getPassword())) {
                FbDao.UserLogin = u;
                saveAccount(username, password);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragment_Main()).commit();

                break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_Login:

                String username = ed_Username.getText().toString();
                String password = ed_Password.getText().toString();

                if (username.equals("") || password.equals("")) {
                    Snackbar.make(getView(), "Không được để trống tài khoản và mật khẩu", 2000).show();
                    break;
                }

                boolean dk = false;
                for (User u : list
                ) {
                    if (username.equals(u.getName()) && password.equals(u.getPassword())) {
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragment_Main()).commit();
                        dk = true;
                        FbDao.UserLogin = u;
                        saveAccount();
                    }
                }
                if (!dk) {
                    Snackbar.make(getView(), "Mật khẩu hoặc tài khoản không đúng", 2000).show();
                }


                break;

            case R.id.tv_GoToRegister:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragment_Regesiter(list)).addToBackStack("").commit();
                break;
            case R.id.tv_FogotPassword:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragment_Fogot_Password()).addToBackStack("").commit();
                break;
            case R.id.img_hidePassword:
                if (ed_Password.getInputType() != InputType.TYPE_TEXT_VARIATION_PASSWORD) {
                    ed_Password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    imgHidePassword.setImageResource(R.drawable.ic_baseline_remove_red_eye_24);
                } else {
                    ed_Password.setInputType(129);
                    imgHidePassword.setImageResource(R.drawable.ic_baseline_visibility_off_24);

                }
                break;
        }
    }

    //test dialog loading
    private void editPhieuMuonDiaLog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.CustomDialog);
        LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
        View viewDialog = inflater.inflate(R.layout.dialog_loading, null);

        builder.setView(viewDialog);
        AlertDialog dialog = builder.create();

        dialog.show();
    }

    // khai báo hàm animation
    private void animation(LinearLayout layoutLogoWhite, EditText edEmailLogin, EditText edPasswordLogin, AppCompatButton btnLogin, TextView btnGoToRegister, TextView tvFogotPassword) {
        layoutLogoWhite.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.floatin));
        edEmailLogin.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.fadein));
        edPasswordLogin.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.fadein));
        btnLogin.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.fadein));
        btnGoToRegister.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.fadein));
        tvFogotPassword.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.fadein));
        imgHidePassword.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.fadein));
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    public void saveAccount() {
        SharedPreferences s = getActivity().getSharedPreferences("account", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = s.edit();
        editor.clear();
        editor.putString("Username", ed_Username.getText().toString());
        editor.putString("Password", ed_Password.getText().toString());
        editor.commit();
    }

    public void saveAccount(String Username, String Password) {
        Log.d(TAG, "saveAccounted: " + Username + " " + Password);
        SharedPreferences s = getActivity().getSharedPreferences("account", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = s.edit();
        editor.clear();
        editor.putString("Username", Username);
        editor.putString("Password", Password);
        editor.commit();
    }

    //   khai báo constructor
    public fragment_Login() {
        this.list = FbDao.getList();
    }

    //  Phương thức khởi tạo có tham số username & password
    public fragment_Login(String usr, String pwd) {
        this.Usernameavali = usr;
        this.passwordavali = pwd;
    }

    public static fragment_Login newInstance() {
        fragment_Login fragment = new fragment_Login();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    //    khai báo hàm Anhxa
    private void Anhxa(View view) {
        layoutLogoWhite = view.findViewById(R.id.layout_logoWhite);
        ed_Username = view.findViewById(R.id.ed_Username);
        ed_Password = view.findViewById(R.id.ed_Password);

        btn_Login = view.findViewById(R.id.btn_Login);
        tv_GoToRegister = view.findViewById(R.id.tv_GoToRegister);
        tv_FogotPassword = view.findViewById(R.id.tv_FogotPassword);
        imgHidePassword = view.findViewById(R.id.img_hidePassword);

        ed_Username.setText(Usernameavali);
        ed_Password.setText(passwordavali);
    }


}