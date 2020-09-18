package com.billi.homnayangi.Models;

public class DataCongThuc {
    private int congthucID;
    private String tenCongThuc;
    private int thoigianNau;
    private int luongThich;
    private int luongNguoiXem;
    private String HinhAnh;

    public DataCongThuc(int congthucID, String tenCongThuc, int thoigianNau, int luongThich, int luongNguoiXem, String hinhAnh) {
        this.congthucID = congthucID;
        this.tenCongThuc = tenCongThuc;
        this.thoigianNau = thoigianNau;
        this.luongThich = luongThich;
        this.luongNguoiXem = luongNguoiXem;
        this.HinhAnh = hinhAnh;
    }

    public String getHinhAnh() {
        return HinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        HinhAnh = hinhAnh;
    }

    public int getCongthudID() {
        return congthucID;
    }

    public void setCongthudID(int congthudID) {
        this.congthucID = congthudID;
    }

    public String getTenCongThuc() {
        return tenCongThuc;
    }

    public void setTenCongThuc(String tenCongThuc) {
        this.tenCongThuc = tenCongThuc;
    }

    public int getThoigianNau() {
        return thoigianNau;
    }

    public void setThoigianNau(int thoigianNau) {
        this.thoigianNau = thoigianNau;
    }

    public int getLuongThich() {
        return luongThich;
    }

    public void setLuongThich(int luongThich) {
        this.luongThich = luongThich;
    }

    public int getLuongNguoiXem() {
        return luongNguoiXem;
    }

    public void setLuongNguoiXem(int luongNguoiXem) {
        this.luongNguoiXem = luongNguoiXem;
    }
}
