/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appgiaovan.Controller;
import appgiaovan.DAO.NhanVienKhoDAO;
import appgiaovan.Entity.NhanVienKho;
import appgiaovan.Entity.TaiKhoan;
import java.util.List;
/**
 *
 * @author pc
 */
public class QLNVKhoController {
    private NhanVienKhoDAO dao;
    public QLNVKhoController() throws ClassNotFoundException {
        dao = new NhanVienKhoDAO();
    }

    public List<NhanVienKho> layTatCaNhanVienKho() throws ClassNotFoundException {
        try {
                return dao.layTatCaNhanVienKho();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public List<NhanVienKho> timKiemNhanVienKho(String kw) throws ClassNotFoundException {
        try {
            return dao.timKiemNhanVienKho(kw);
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public int layMaNhanVienKhoMoi() throws ClassNotFoundException {
        try {
            return dao.layMaNhanVienKhoMoi();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public NhanVienKho layThongTinNhanVienKho(int id) {
        try {
            return dao.layThongTinNhanVienKho(id);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public boolean taoNhanVienKho(NhanVienKho nv, TaiKhoan tk) {
        try {
            return dao.themNhanVienKho(nv, tk);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean xoaNhanVienKho(int id) {
        try {
            return dao.xoaNhanVienKho(id);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean suaNhanVienKho(NhanVienKho nv) {
        try {
            return dao.suaNhanVienKho(nv);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
  
    public List<Integer> layTatCaIDKho() throws Exception {
        return dao.layTatCaIDKho();                
    }

    public Integer layIDQuanLyTheoKho(int idKho) throws Exception {
        return dao.layIDQuanLyTheoKho(idKho);      
    }

    public int getIdTaiKhoanByNhanVienKho(int idNhanVien) throws Exception {
        return dao.getIdTaiKhoanByNhanVienKho(idNhanVien);
    }
    
    public static void main(String[] args){
        System.out.println("nothing");
    }

}
