package com.manager.duan_appbanhang.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.manager.duan_appbanhang.R;
import com.manager.duan_appbanhang.adapter.SanPhamMoiAdapter;
import com.manager.duan_appbanhang.mode.EventBus.SuaXoaEvent;
import com.manager.duan_appbanhang.mode.SanPhamMoi;
import com.manager.duan_appbanhang.retrfit.ApiBanHang;
import com.manager.duan_appbanhang.retrfit.RetrofitClient;
import com.manager.duan_appbanhang.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import soup.neumorphism.NeumorphButton;
import soup.neumorphism.NeumorphCardView;

public class QuanLyActivity extends AppCompatActivity {
    ImageView img_them;
    RecyclerView recycleview;
    ApiBanHang apiBanHang;
    List<SanPhamMoi> list;
    SanPhamMoiAdapter adapter;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    SanPhamMoi sanphamSuaXoa;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly);
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        initView();
        initControl();
        getSpMoi();
        ActionToolBar();
    }
    private void ActionToolBar() {

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void initControl() {
        img_them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ThemSPActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getSpMoi() {
        compositeDisposable.add(apiBanHang.getSpMoi()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        sanPhamMoiModel -> {
                            if (sanPhamMoiModel.isSuccess()) {
                                list = sanPhamMoiModel.getResult();

                                adapter = new SanPhamMoiAdapter(getApplicationContext(), list);
                                recycleview.setAdapter(adapter);


                            }

                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), "Không kết nối được với sever " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                ));
    }

    private void initView() {
        img_them = findViewById(R.id.img_them);
        recycleview = findViewById(R.id.recycleview_ql);
        toolbar = findViewById(R.id.toobbar_quanly);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        recycleview.setLayoutManager(layoutManager);
        recycleview.setHasFixedSize(true);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getTitle().equals("Sửa")) {
            suaSanPham();

        } else if (item.getTitle().equals("Xóa")) {
            xoaSanpham();
        }
        return super.onContextItemSelected(item);
    }

    private void xoaSanpham() {
        compositeDisposable.add(apiBanHang.xoaSanPham(sanphamSuaXoa.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        messageModel -> {
                            if (messageModel.isSuccess()){
                                Toast.makeText(this, messageModel.getMessage(), Toast.LENGTH_SHORT).show();
                                getSpMoi();



                            }else {
                                Toast.makeText(this, messageModel.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }, throwable -> {
                            Log.d("log",throwable.getMessage());
                        }
                ));
    }

    private void suaSanPham() {
        Intent intent = new Intent(getApplicationContext(), ThemSPActivity.class);
        intent.putExtra("sua",sanphamSuaXoa);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void evenSuaXoa(SuaXoaEvent event) {
        if (event != null) {
            sanphamSuaXoa = event.getSanPhamMoi();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);

    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}