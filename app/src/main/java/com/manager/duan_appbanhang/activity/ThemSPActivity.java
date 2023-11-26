package com.manager.duan_appbanhang.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.manager.duan_appbanhang.R;
import com.manager.duan_appbanhang.databinding.ActivityThemspBinding;
import com.manager.duan_appbanhang.retrfit.ApiBanHang;
import com.manager.duan_appbanhang.retrfit.RetrofitClient;
import com.manager.duan_appbanhang.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ThemSPActivity extends AppCompatActivity {
    Spinner spinner;
    Toolbar toolbar;
    int loai = 0;
    ActivityThemspBinding binding;
    ApiBanHang apiBanHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themsp);
        binding = ActivityThemspBinding.inflate(getLayoutInflater());
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        setContentView(binding.getRoot());

        initView();
        initData();
        ActionToolBar();
    }

    private void initData() {
        List<String> stringList = new ArrayList<>();
        stringList.add("vui lòng chọn loại");
        stringList.add("Loại 1");
        stringList.add("Loại 2");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, stringList);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                loai = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        binding.btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                themsanpham();
            }
        });

    }

    private void themsanpham() {
        String str_ten = binding.tensp.getText().toString().trim();
        String str_gia = binding.giasp.getText().toString().trim();
        String str_hinhanh = binding.hinhanh.getText().toString().trim();
        String str_mota = binding.mota.getText().toString().trim();

        if (TextUtils.isEmpty(str_ten) || TextUtils.isEmpty(str_gia) || TextUtils.isEmpty(str_hinhanh) || TextUtils.isEmpty(str_mota) || loai == 0) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin sản phẩm", Toast.LENGTH_SHORT).show();
        } else {
            compositeDisposable.add(apiBanHang.insert(str_ten, str_gia, str_hinhanh, str_mota, (loai))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            messageModel -> {
                                if(messageModel.isSuccess()){
                                    Toast.makeText(this, messageModel.getMessage(), Toast.LENGTH_SHORT).show();

                                }else {
                                    Toast.makeText(this, messageModel.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }, throwable -> {
                                Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                    )
            );

        }
    }

    private void initView() {
        spinner = findViewById(R.id.spinner_loai);
        toolbar = findViewById(R.id.toobbar_themsp);

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

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}