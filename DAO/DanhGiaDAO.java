/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package appgiaovan.DAO;

import appgiaovan.ConnectDB.ConnectionUtils;
import appgiaovan.Entity.DanhGia;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
/**
 *
 * @author nhant
 */
public class DanhGiaDAO {

    public void ThemDanhGia(DanhGia dg) throws SQLException, ClassNotFoundException {
        String sql = "{call SP_ThemDanhGia(?, ?, ?)}";

        try (Connection conn = ConnectionUtils.getMyConnection();CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, dg.getIdDonHang());
            cs.setString(2, dg.getNoiDungDanhGia());
            cs.setInt(3,dg.getDiemDanhGia());
           
            
            cs.execute();

        } catch (SQLException e) {
            System.err.println("Lỗi khi gọi procedure ThemDonHang: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    static public void main(String[] args){
        
    }
}
