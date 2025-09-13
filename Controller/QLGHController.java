/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appgiaovan.Controller;

import appgiaovan.DAO.DonHangDAO;
import appgiaovan.DAO.GoiHangDAO;
import appgiaovan.Entity.DonHang;
import appgiaovan.Entity.GoiHang;
import java.sql.SQLException;
import java.util.List;


public class QLGHController {
    private GoiHangDAO goiHangDAO = new GoiHangDAO();
    private DonHangDAO donHangDAO = new DonHangDAO();
    public List<GoiHang> LayDSGoiHangTheoKho(int idKho) throws SQLException, ClassNotFoundException{
        return goiHangDAO.LayDSGoiHangTheoKho(idKho);
    }
    
    public List<GoiHang> LayDSGoiHangTheoKho(GoiHang goiHang, int idKho) throws SQLException, ClassNotFoundException{
        return goiHangDAO.LayDSGoiHangTheoKho(goiHang, idKho);
    }
    
    public List<DonHang> LayDSDonHang(DonHang donHang) throws SQLException, ClassNotFoundException{
        return donHangDAO.LayDSDonHang(donHang);
    }
    
    public void HoanThanhGoiHang(Integer maGoiHang) throws SQLException, ClassNotFoundException{
        goiHangDAO.HoanThanhGoiHang(maGoiHang);
    }
    
    public void ThemGoiHang(GoiHang goiHang, List<Integer> listDonHang) throws SQLException, ClassNotFoundException{
        goiHangDAO.ThemGoiHang(goiHang, listDonHang);
    }
    static public void main(String[] args){
        
    }
}
