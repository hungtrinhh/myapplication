package com.example.myapplication.Fragment.fragDifferent;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.myapplication.Adapter.HistoryAdapter;
import com.example.myapplication.Firebase.FbDao;
import com.example.myapplication.Model.Hoadon;
import com.example.myapplication.Model.Hoadonchoigame;
import com.example.myapplication.Model.Hoadonnaptien;
import com.example.myapplication.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class FragmentLichSuGiaoDich extends Fragment implements View.OnClickListener {
    private Toolbar toolbarDanhMuc;
    private ImageView btnBackNotify;
    private RecyclerView recyclerviewHistory;
    private HistoryAdapter historyAdapter;
    private List<Hoadon> list;
    private static List<Hoadon> hoadonList;
    String TAG = "LichSugiadich";
    Comparator<Hoadon> comparator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lich_su_giao_dich, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        anhXa(view);
        comparator = (o2, o1) -> getDate(o1).compareTo(getDate(o2));

        if (hoadonList!=null){
            list=hoadonList;
            FillHoaDonAgain();
            hoadonList=null;
        }else {
            fillRecycleView();
        }
        System.out.println("xin chao");
        btnBackNotify.setOnClickListener(this::onClick);
    }


    private Date getDate(Hoadon hoadon) {
        Date date = null;
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        try {
            if (hoadon.getClass().toString().equals(Hoadonnaptien.class.toString())) {
                Hoadonnaptien hoadonnaptien = (Hoadonnaptien) hoadon;
                date = format.parse(hoadonnaptien.getDate());
            } else {
                Hoadonchoigame hoadonchoigame = (Hoadonchoigame) hoadon;
                date = format.parse(hoadonchoigame.getDateStart());
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }



    private void fillRecycleView() {
        list = FbDao.hoadonList;
        Log.e("BUG", "fillRecycleView: "+list.size(),null );
        Collections.sort(list, comparator);
        historyAdapter = new HistoryAdapter(list);
        recyclerviewHistory.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerviewHistory.setAdapter(historyAdapter);
    }

    private void FillHoaDonAgain(){
        Collections.sort(list, comparator);
        historyAdapter = new HistoryAdapter(list);
        recyclerviewHistory.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerviewHistory.setAdapter(historyAdapter);
    }

    private void anhXa(View view) {
        toolbarDanhMuc = (Toolbar) view.findViewById(R.id.toolbar_DanhMuc);
        btnBackNotify = (ImageView) view.findViewById(R.id.btn_backNotify);
        recyclerviewHistory = (RecyclerView) view.findViewById(R.id.recyclerview_history);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_backNotify:
                getActivity().getSupportFragmentManager().popBackStack();
                break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        hoadonList=list;
    }
}