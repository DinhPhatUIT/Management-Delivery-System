package appgiaovan.Controller;

import appgiaovan.DAO.KhachHangDAO;
import appgiaovan.Entity.KhachHang;
import appgiaovan.Entity.TaiKhoan;
import java.util.List;

public class QLKHController {
    private KhachHangDAO dao;

    public QLKHController() throws ClassNotFoundException {
        // Khởi tạo DAO, các kết nối sẽ được thực hiện trong DAO khi cần
        dao = new KhachHangDAO();
    }

    public List<KhachHang> layTatCaKhachHang() throws ClassNotFoundException {
        try {
            return dao.layTatCaKhachHang();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public List<KhachHang> timKiemKhachHang(String kw) throws ClassNotFoundException {
        try {
            return dao.timKiemKhachHang(kw);
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public int layMaKhachHangMoi() throws ClassNotFoundException {
        try {
            return dao.layMaKhachHangMoi();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public KhachHang layThongTinKhachHang(int id) {
        try {
            return dao.layThongTinKhachHang(id);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public boolean taoKhachHang(KhachHang kh, TaiKhoan tk) {
        try {
            return dao.themKhachHang(kh, tk);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean xoaKhachHang(int id) {
        try {
            return dao.xoaKhachHang(id);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean suaKhachHang(KhachHang kh) {
        try {
            return dao.suaKhachHang(kh);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public int getIdTaiKhoanByKhachHang(int idKhachHang) throws Exception {
        return dao.getIdTaiKhoanByKhachHang(idKhachHang);
    }
}
