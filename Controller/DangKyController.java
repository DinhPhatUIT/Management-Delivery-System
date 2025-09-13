/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appgiaovan.Controller;

import appgiaovan.DAO.KhachHangDAO;
import appgiaovan.DAO.TaiKhoanDAO;
import appgiaovan.Entity.KhachHang;
import appgiaovan.Entity.NguoiDung;
import appgiaovan.Entity.TaiKhoan;
import java.sql.SQLException;
import javax.swing.JPasswordField;

/**
 *
 * @author nhant
 */
public class DangKyController {

    private KhachHangDAO khachHangDAO = new KhachHangDAO();
    private TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO();

    public boolean KiemTraDinhDang(KhachHang khachHang, String matKhau, String matKhauNL, String tenDangNhap) {

        //1. họ tên không được để trống
        if (khachHang.getHoTen() == null || khachHang.getHoTen().trim().isEmpty()) {
            return false;
        }
        //2. tên đăng nhập không được để trống
        if (tenDangNhap == null) {
            return false;
        }
        //3. Mật khẩu không được để trống
        if (matKhau == null || matKhauNL == null) {
            return false;
        }
        //4. Nhập lại mật khẩu phải trùng với mật khẩu
        if (!matKhau.equals(matKhauNL)) {
            return false;
        }
        //5. CCCD không được để trống
        if (khachHang.getCCCD() == null || khachHang.getCCCD().trim().isEmpty()) {
            return false;
        }
        //6. Gmail không được để trống
        if (khachHang.getEmail() == null || khachHang.getEmail().trim().isEmpty()) {
            return false;
        }
        //7. Số điện thoại khách hàng phải bắt đầu bằng '0' và đủ 10 chữ số
        if (khachHang.getSDT() == null || !khachHang.getSDT().matches("0\\d{9}")) {
            return false;
        }
        return true; 
    }

    public boolean KiemTraDinhDangCapNhat(KhachHang khachHang) {

        //1. họ tên không được để trống
        if (khachHang.getHoTen() == null || khachHang.getHoTen().trim().isEmpty()) {
            return false;
        }
        //5. CCCD không được để trống
        if (khachHang.getCCCD() == null || khachHang.getCCCD().trim().isEmpty()) {
            return false;
        }
        //6. Gmail không được để trống
        if (khachHang.getEmail() == null || khachHang.getEmail().trim().isEmpty()) {
            return false;
        }
        //7. Số điện thoại khách hàng phải bắt đầu bằng '0' và đủ 10 chữ số
        if (khachHang.getSDT() == null || !khachHang.getSDT().matches("0\\d{9}")) {
            return false;
        }
        return true; 
    }

    public boolean KiemTraDinhDangCapNhat(NguoiDung nguoiDung) {

        //1. họ tên không được để trống
        if (nguoiDung.getHoTen() == null || nguoiDung.getHoTen().trim().isEmpty()) {
            return false;
        }
        //5. CCCD không được để trống
        if (nguoiDung.getCCCD() == null || nguoiDung.getCCCD().trim().isEmpty()) {
            return false;
        }
        //6. Gmail không được để trống
        if (nguoiDung.getEmail() == null || nguoiDung.getEmail().trim().isEmpty()) {
            return false;
        }
        //7. Số điện thoại khách hàng phải bắt đầu bằng '0' và đủ 10 chữ số
        if (nguoiDung.getSDT() == null || !nguoiDung.getSDT().matches("0\\d{9}")) {
            return false;
        }
        return true; 
    }

    public boolean themKhachHang(KhachHang kh, TaiKhoan tk) {
        try {
            khachHangDAO.themKhachHang(kh, tk);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public int layID_KhachHang(int ID_TaiKhoan) throws SQLException, ClassNotFoundException {
        return khachHangDAO.layID_KhachHang(ID_TaiKhoan);
    }

    public int layID_TaiKhoan(String email) throws SQLException, ClassNotFoundException {
        return khachHangDAO.layID_TaiKhoan(email);
    }

    public void CapNhatMK(String hashPassword, String email) throws SQLException, ClassNotFoundException {
        int ID_TaiKhoan = this.layID_TaiKhoan(email);

        taiKhoanDAO.CapNhatMK(hashPassword, ID_TaiKhoan);
    }

}
