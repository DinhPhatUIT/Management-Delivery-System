/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appgiaovan.Entity;

import appgiaovan.DAO.KhoHangDAO;
import java.sql.SQLException;
import java.util.Date;

public class GoiHang {
    private Integer idGoiHang;
    private int idKhoHangGui;
    private int idKhoHangDen;
    private Date ngayGui;
    private int idNhanVien;
    private int soLuong;
    private String trangThai;

    public GoiHang() {}

    public GoiHang(int idGoiHang, int idKhoHangGui, int idKhoHangDen, Date ngayGui, int idNhanVien, int soLuong, String trangThai) {
        this.idGoiHang = idGoiHang;
        this.idKhoHangGui = idKhoHangGui;
        this.idKhoHangDen = idKhoHangDen;
        this.ngayGui = ngayGui;
        this.idNhanVien = idNhanVien;
        this.soLuong = soLuong;
        this.trangThai = trangThai;
    }
    
    public static String[] getTableHeaders() {
        return new String[]{" ", "ID", "Nhân viên",  "Kho gửi", "Kho đến", "Trạng thái", "Số lượng đơn hàng"};
    }

    public Object[] toTableRow() throws SQLException, ClassNotFoundException {
        KhoHangDAO khoHangDAO = new KhoHangDAO();
        String KhoGui = khoHangDAO.LayTenKho(idKhoHangGui);
        String KhoDen = khoHangDAO.LayTenKho(idKhoHangDen);
        return new Object[]{"",idGoiHang,  "<html>" + idNhanVien + "<br/>" +"</html>"
                , KhoGui ,KhoDen, trangThai,soLuong};
    }

    public Integer getIdGoiHang() {
        return idGoiHang;
    }

    public void setIdGoiHang(Integer idGoiHang) {
        this.idGoiHang = idGoiHang;
    }

    public int getIdKhoHangGui() {
        return idKhoHangGui;
    }

    public void setIdKhoHangGui(int idKhoHangGui) {
        this.idKhoHangGui = idKhoHangGui;
    }

    public int getIdKhoHangDen() {
        return idKhoHangDen;
    }

    public void setIdKhoHangDen(int idKhoHangDen) {
        this.idKhoHangDen = idKhoHangDen;
    }

    public Date getNgayGui() {
        return ngayGui;
    }

    public void setNgayGui(Date ngayGui) {
        this.ngayGui = ngayGui;
    }

    public int getIdNhanVien() {
        return idNhanVien;
    }

    public void setIdNhanVien(int idNhanVien) {
        this.idNhanVien = idNhanVien;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return "GoiHang{" +
                "idGoiHang=" + idGoiHang +
                ", idKhoHangGui=" + idKhoHangGui +
                ", idKhoHangDen=" + idKhoHangDen +
                ", ngayGui=" + ngayGui +
                ", idNhanVien=" + idNhanVien +
                ", soLuong=" + soLuong +
                ", trangThai='" + trangThai + '\'' +
                '}';
    }
}

