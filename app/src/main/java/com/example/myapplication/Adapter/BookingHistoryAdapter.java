package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Firebase.FbDao;
import com.example.myapplication.Model.HoaDonHenGio;
import com.example.myapplication.Model.Hoadonchoigame;
import com.example.myapplication.Model.User;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class BookingHistoryAdapter extends RecyclerView.Adapter<BookingHistoryAdapter.ViewBookingHoler> {
    private final Context context;
    private List<HoaDonHenGio> list = new ArrayList<>();

    public BookingHistoryAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<HoaDonHenGio> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewBookingHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_booking_history,parent,false);

        return new ViewBookingHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewBookingHoler holder, int position) {
        HoaDonHenGio game = list.get(position);
        if(game == null){
            return;
        }
        String ten =  FbDao.getNameUserFromID(game.getUserId());
        holder.name.setText("Tên khách hàng: " + ten);
        holder.dateStart.setText("Từ: "+game.getTimeStart());
        holder.dateEnd.setText("Đến: "+game.getTimeEnd());
    }

    @Override
    public int getItemCount() {
        if(list == null){
            return 0;
        }
        return list.size();
    }

    public  class ViewBookingHoler extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView dateStart;
        private final TextView dateEnd;
        private final ImageView img_AvatarUser;
        public ViewBookingHoler(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name_user_booking);
            dateStart = itemView.findViewById(R.id.tv_date_start);
            img_AvatarUser= itemView.findViewById(R.id.img_AvatarUser);
            dateEnd = itemView.findViewById(R.id.tv_date_end);
        }
    }
}
