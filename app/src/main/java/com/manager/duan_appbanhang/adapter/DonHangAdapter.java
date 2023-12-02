package com.manager.duan_appbanhang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.manager.duan_appbanhang.Interface.ItemClickListener;
import com.manager.duan_appbanhang.R;
import com.manager.duan_appbanhang.mode.DonHang;
import com.manager.duan_appbanhang.mode.EventBus.DonHangEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class DonHangAdapter extends RecyclerView.Adapter<DonHangAdapter.MyViewHolder> {
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    Context context;
    List<DonHang> listDonhang;

    public DonHangAdapter(Context context, List<DonHang> listDonhang) {
        this.context = context;
        this.listDonhang = listDonhang;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_donhang, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DonHang donHang = listDonhang.get(position);
        holder.txtDonhang.setText("Đơn hàng: " + donHang.getId());
        holder.diachi.setText("Địa chỉ:"+donHang.getDiachi());
        holder.trangthai.setText(trangThaiDon(donHang.getTrangthai()));

        LinearLayoutManager layoutManager = new LinearLayoutManager(
                holder.reChitiet.getContext(),
                LinearLayoutManager.VERTICAL, false

        );
        layoutManager.setInitialPrefetchItemCount(donHang.getItem().size());
        //adapte chi tiet
        ChitietAdapter chitietAdapter = new ChitietAdapter(context, donHang.getItem());
        holder.reChitiet.setLayoutManager(layoutManager);
        holder.reChitiet.setAdapter(chitietAdapter);
        holder.reChitiet.setRecycledViewPool(viewPool);
        holder.setListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int pos, boolean isLongClick) {
                if(isLongClick){
                    EventBus.getDefault().postSticky(new DonHangEvent(donHang));
                }
            }
        });



    }

    private String trangThaiDon(int status) {
        String result  = "";
        switch (status){
            case 0 :
                result = "Đơn hàng đang được xử lí";
                break;
            case 1 :
                result = "Đơn hàng đã được xác nhận";
                break;
            case 2 :
                result = "Đơn hàng đã được giao cho đơn vị vận chuyển";
                break;
            case 3:
                result = "Thành công";
                break;
            case 4:
                result = "Đơn hàng đã hủy";
                break;
        }
        return  result;
    }

    @Override
    public int getItemCount() {
        return listDonhang.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        TextView txtDonhang, trangthai,diachi;
        RecyclerView reChitiet;
        ItemClickListener listener;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDonhang = itemView.findViewById(R.id.idDonhang);
            trangthai = itemView.findViewById(R.id.tinhtrang);
            diachi = itemView.findViewById(R.id.diachi_donhang);
            reChitiet = itemView.findViewById(R.id.recycleview_chitiet);
            itemView.setOnLongClickListener(this);
        }

        public void setListener(ItemClickListener listener) {
            this.listener = listener;
        }

        @Override
        public boolean onLongClick(View view) {
            listener.onClick(view,getAdapterPosition(),true);
            return false;
        }
    }
}
