package appgiaovan.DAO;

import appgiaovan.ConnectDB.ConnectionUtils;
import appgiaovan.Entity.DoanhThuLoiNhuan;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoanhThuLoiNhuanDAO {

    
    public List<DoanhThuLoiNhuan> getListDoanhThuLoiNhuan() throws SQLException, ClassNotFoundException {
        List<DoanhThuLoiNhuan> list = new ArrayList<>();

        String sql = """
            SELECT 
              LEVEL AS Thang,
              TongDoanhThu(LEVEL, EXTRACT(YEAR FROM SYSDATE)) AS DoanhThu
            FROM dual
            CONNECT BY LEVEL <= (SELECT EXTRACT(MONTH FROM SYSDATE) FROM dual)
            """;

        try (
            Connection conn = ConnectionUtils.getMyConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
        ) {
            while (rs.next()) {
                int thang = rs.getInt("THANG");
                Double doanhThu = rs.getDouble("DOANHTHU");

                DoanhThuLoiNhuan dtln = new DoanhThuLoiNhuan(thang, doanhThu);
                list.add(dtln);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
