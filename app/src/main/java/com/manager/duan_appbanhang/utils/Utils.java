package com.manager.duan_appbanhang.utils;

import com.manager.duan_appbanhang.mode.GioHang;
import com.manager.duan_appbanhang.mode.User;

import java.util.ArrayList;
import java.util.List;

public class Utils {
  public static final String BASE_URL="http://10.0.2.2/banhang/";
//  public static final String BASE_URL="http://192.168.1.162/banhang/";
  public  static List<GioHang> manggiohang;
  public  static List<GioHang> mangmuahang = new ArrayList<>();
  public  static User user_current =  new User();
}
