/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appgiaovan.DAO;

import appgiaovan.ConnectDB.ConnectionUtils;
import appgiaovan.Entity.BaoCaoKho;
import java.sql.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.CallableStatement;

/**
 *
 * @author ASUS
 */
public class BaoCaoDAO {

    public BaoCaoDAO() {
    }

    public void BaoCaoGiaoHang(int idtk, int dagiao, int thatbai, int cod) {
        String sql = "call ThemBaoCaoGiaoHang(?,?,?,?)";
        try {
            Connection conn = ConnectionUtils.getMyConnection();
            CallableStatement cs = conn.prepareCall(sql);
            cs.setInt(1, idtk);
            cs.setInt(2, dagiao);
            cs.setInt(3, thatbai);
            cs.setInt(4, cod);
            cs.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(BaoCaoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BaoCaoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    static public void GuiBaoCaoKho(BaoCaoKho baoCao, int idnvk) throws SQLException, ClassNotFoundException {
        String sql = "call ThemBaoCaoKho(?,?,?,?)";

        Connection conn = ConnectionUtils.getMyConnection();
        CallableStatement cs = conn.prepareCall(sql);
        cs.setInt(1, idnvk);
        cs.setDate(2, new java.sql.Date(baoCao.getKyBaoCao().getTime()));
        cs.setInt(3, baoCao.getSoGoiHangNhap());
        cs.setInt(4, baoCao.getSoGoiHangXuat());
        cs.executeQuery();

    }

    public void main(String[] args) {

    }
}
