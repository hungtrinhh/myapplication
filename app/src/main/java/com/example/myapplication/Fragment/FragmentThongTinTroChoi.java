package com.example.myapplication.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.Adapter.DanhSachGameAdapter;
import com.example.myapplication.Model.Game;
import com.example.myapplication.R;
import com.example.myapplication.SetOnClickItemIterface.OnclickItemGame;

public class FragmentThongTinTroChoi extends Fragment {
    private TextView tvTemGameFragThongTin;
    private TextView tvMoTa;
    private TextView tvTrangThaiFragThongTin;
    private TextView tvLoaTroChoi;
    private Button btnChoi;
    private Toolbar toolbarDanhSachGame;
    private ImageView btnBackToDanhSachGame;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_thong_tin_tro_choi, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        anhXa(view);
        thongTinGame();
        backToDanhSach();
    }

    public void anhXa(View view) {
        tvTemGameFragThongTin = (TextView) view.findViewById(R.id.tv_temGame_frag_thong_tin);
        tvMoTa = (TextView) view.findViewById(R.id.tv_mo_ta);
        tvTrangThaiFragThongTin = (TextView) view.findViewById(R.id.tv_trang_thai_frag_thong_tin);
        tvLoaTroChoi = (TextView) view.findViewById(R.id.tv_loa_tro_choi);
        btnChoi = (Button) view.findViewById(R.id.btn_choi);
        toolbarDanhSachGame = (Toolbar) view.findViewById(R.id.toolbar_DanhSachGame);
        btnBackToDanhSachGame = (ImageView) view.findViewById(R.id.btn_backToDanhSachGame);
    }
    public void backToDanhSach(){
        btnBackToDanhSachGame.setOnClickListener(view -> {
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_frame,new Fragment_ListDanhSachTroChoi()).commit();
        });
    }
    public void thongTinGame() {
        Bundle bundle = getArguments();
        Game game = (Game) bundle.get("obj_game");
        tvMoTa.setText(game.getMoTa());
        tvTemGameFragThongTin.setText(game.getTenGame());
        tvLoaTroChoi.setText(game.getKieu());
        tvTrangThaiFragThongTin.setText(game.getTrangThai());
    }
}