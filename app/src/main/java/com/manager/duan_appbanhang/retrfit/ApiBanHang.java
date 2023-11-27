package com.manager.duan_appbanhang.retrfit;

import com.manager.duan_appbanhang.mode.DonHangModel;
import com.manager.duan_appbanhang.mode.LoaiSpModel;
import com.manager.duan_appbanhang.mode.MessageModel;
import com.manager.duan_appbanhang.mode.SanPhamMoiModel;
import com.manager.duan_appbanhang.mode.UserModel;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


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
  @POST("dangki.php")
  @FormUrlEncoded
  Observable<UserModel> dangKi(
          @Field("email") String email,
          @Field("pass") String pass,
          @Field("username") String username,
          @Field("mobile") String mobile
  );


  @POST("dangnhap.php")
  @FormUrlEncoded
  Observable<UserModel> dangNhap(
          @Field("email") String email,
          @Field("pass") String pass
  );


  @POST("donhang.php")
  @FormUrlEncoded
  Observable<UserModel> createOder(
          @Field("email") String email,
          @Field("sdt") String sdt,
          @Field("tongtien") String tongtien,
          @Field("iduser") int id,
          @Field("diachi") String diachi,
          @Field("soluong") int soluong,
          @Field("chitiet") String chitiet
  );

  @POST("xemdonhang.php")
  @FormUrlEncoded
  Observable<DonHangModel> xemDonHang(
          @Field("iduser") int id

  );
  @POST("timkiem.php")
  @FormUrlEncoded
  Observable<SanPhamMoiModel> search(
          @Field("search") String search

  );

  @POST("insertsp.php")
  @FormUrlEncoded
  Observable<MessageModel> insert(
          @Field("tensp") String email,
          @Field("gia") String gia,
          @Field("hinhanh") String hinhanh,
          @Field("mota") String mota,
          @Field("loai") int id

  );

  @Multipart
  @POST("upload.php")
  Call<MessageModel> uploadFile(@Part MultipartBody.Part file);


}
