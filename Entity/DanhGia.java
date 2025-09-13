/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appgiaovan.Entity;

import java.util.Date;

/**
 *
 * @author nhant
 */
public class DanhGia {
    private int idDanhGia;
    private int idDonHang;
    private int idKhachHang;
    private String noiDungDanhGia;
    private int diemDanhGia;
    private Date ngayTao;

    // Constructors
    public DanhGia() {}

    public DanhGia(int idDanhGia, int idDonHang, int idKhachHang, String noiDungDanhGia, int diemDanhGia, Date ngayTao) {
        this.idDanhGia = idDanhGia;
        this.idDonHang = idDonHang;
        this.idKhachHang = idKhachHang;
        this.noiDungDanhGia = noiDungDanhGia;
        this.diemDanhGia = diemDanhGia;
        this.ngayTao = ngayTao;
    }

    // Getters and Setters
    public int getIdDanhGia() {
        return idDanhGia;
    }

    public void setIdDanhGia(int idDanhGia) {
        this.idDanhGia = idDanhGia;
    }

    public int getIdDonHang() {
        return idDonHang;
    }

    public void setIdDonHang(int idDonHang) {
        this.idDonHang = idDonHang;
    }

    public int getIdKhachHang() {
        return idKhachHang;
    }

    public void setIdKhachHang(int idKhachHang) {
        this.idKhachHang = idKhachHang;
    }

    public String getNoiDungDanhGia() {
        return noiDungDanhGia;
    }

    public void setNoiDungDanhGia(String noiDungDanhGia) {
        this.noiDungDanhGia = noiDungDanhGia;
    }

    public int getDiemDanhGia() {
        return diemDanhGia;
    }

    public void setDiemDanhGia(int diemDanhGia) {
        this.diemDanhGia = diemDanhGia;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    
}

