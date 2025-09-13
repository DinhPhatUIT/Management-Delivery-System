/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appgiaovan.DAO;

import appgiaovan.ConnectDB.ConnectionUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;

/**
 *
 * @author ASUS
 */
public class TokenDAO {

    private int idTK;
    private int idToken;
    public int taoToken(String username) throws SQLException, ClassNotFoundException {
        String sqlID = "SELECT ID_TaiKhoan FROM TAIKHOAN WHERE TENTAIKHOAN = ?";
        String sqlToken = "{call sp_TaoTokenChoTaiKhoan(?,?)}";

        try (Connection conn = ConnectionUtils.getMyConnection();
             PreparedStatement st = conn.prepareStatement(sqlID)) {

            conn.setAutoCommit(false);
            st.setString(1, username);

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                     idTK = rs.getInt("ID_TaiKhoan");

                    try (CallableStatement cs = conn.prepareCall(sqlToken)) {
                        cs.setInt(1, idTK);
                        cs.registerOutParameter(2, java.sql.Types.INTEGER);
                        cs.execute();
                        idToken = cs.getInt(2); // Nhận ID_Token được trả về
                        conn.commit();
                        return idToken;
                    }

                } else {
                    throw new SQLException("Không tìm thấy tài khoản: " + username);
                }
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
      
    }
    public void capNhatToken(int idToken) throws SQLException, ClassNotFoundException{
        String sqlToken = "{call ThuHoiTokenChoTaiKhoan (?)}";

        try (Connection conn = ConnectionUtils.getMyConnection();
            CallableStatement st = conn.prepareCall(sqlToken)) {
            conn.setAutoCommit(false);
            st.setInt(1, idToken);
            st.executeQuery();
            
        }
    }

    /*CREATE TABLE TaiKhoan_Token (
  ID_Token NUMBER CONSTRAINT PK_TaiKhoan_Token PRIMARY KEY,
  ID_TaiKhoan NUMBER,
  GiaTriToken NUMBER,
  ThoiGianHetHan DATE,
  ThoiGianCapPhat DATE,
  BiThuHoi NUMBER,
  CONSTRAINT FK_Token_TaiKhoan FOREIGN KEY (ID_TaiKhoan)
    REFERENCES TaiKhoan(ID_TaiKhoan)
);*/

}
