package com.example.duan_appbanhang.retrfit;

import com.example.duan_appbanhang.mode.LoaiSpModel;
import com.example.duan_appbanhang.mode.SanPhamMoiModel;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface ApiBanHang {
  @GET("getloaisp.php")
    Observable<LoaiSpModel> getLoaiSp();

  @GET("getspmoi.php")
  Observable<SanPhamMoiModel> getSpMoi();


  @POST("chitiet.php")
  @FormUrlEncoded
  Observable<SanPhamMoiModel> getSanPham(
          @Field("page") int page,
          @Field("loai") int loai
  );

}
