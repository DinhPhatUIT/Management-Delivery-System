/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appgiaovan.Controller;
import appgiaovan.DAO.NhanVienGiaoHangDAO;
import appgiaovan.Entity.NhanVienGiaoHang;
import appgiaovan.Entity.TaiKhoan;
import java.util.List;
/**
 *
 * @author pc
 */
public class QLShipperController {
    private NhanVienGiaoHangDAO dao;
    public QLShipperController() throws ClassNotFoundException {
        dao = new NhanVienGiaoHangDAO();
    }

    public List<NhanVienGiaoHang> layTatCaNhanVienGiaoHang() throws ClassNotFoundException {
        try {
            return dao.LayDSNhanVienGiaoHang();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public List<NhanVienGiaoHang> timKiemNhanVienGiaoHang(String kw) throws ClassNotFoundException {
        try {
            return dao.timKiemNhanVienGiaoHang(kw);
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public int layMaNhanVienGiaoHangMoi() throws ClassNotFoundException {
        try {
            return dao.layMaNhanVienGiaoHangMoi();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public NhanVienGiaoHang layThongTinNhanVienGiaoHang(int id) {
        try {
            return dao.layThongTinNhanVienGiaoHang(id);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public boolean taoNhanVienGiaoHang(NhanVienGiaoHang sh, TaiKhoan tk) {
        try {
            return dao.themNhanVienGiaoHang(sh, tk);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean xoaNhanVienGiaoHang(int id) {
        try {
            return dao.xoaNhanVienGiaoHang(id);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean suaNhanVienGiaoHang(NhanVienGiaoHang sh) {
        try {
            return dao.suaNhanVienGiaoHang(sh);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public int getIdTaiKhoanByNhanVienGiaoHang(int idNhanVien) throws Exception {
        return dao.getIdTaiKhoanByNhanVienGiaoHang(idNhanVien);
    }
    
    public List<Integer> layTatCaIDKho() throws Exception {
        return dao.layTatCaIDKho();                
    }

    public Integer layIDQuanLyTheoKho(int idKho) throws Exception {
        return dao.layIDQuanLyTheoKho(idKho);      
    }
}
