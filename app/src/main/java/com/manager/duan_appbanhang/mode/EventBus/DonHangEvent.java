package com.manager.duan_appbanhang.mode.EventBus;

import com.manager.duan_appbanhang.mode.DonHang;

public class DonHangEvent {
    DonHang donHang;

    public DonHangEvent(DonHang donHang) {
        this.donHang = donHang;
    }

    public DonHang getDonHang() {
        return donHang;
    }

    public void setDonHang(DonHang donHang) {
        this.donHang = donHang;
    }
}
