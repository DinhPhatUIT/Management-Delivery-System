package appgiaovan.DAO;
import appgiaovan.ConnectDB.ConnectionUtils;
import appgiaovan.Entity.KhoHang;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KhoHangDAO {

    public List<KhoHang> LayThongTinKho() throws SQLException, ClassNotFoundException {
        List<KhoHang> khoList = new ArrayList<>();

        String sql = "SELECT * FROM KhoHang";
        try (Connection conn = ConnectionUtils.getMyConnection(); 
                PreparedStatement stmt = conn.prepareStatement(sql); 
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                KhoHang kho = new KhoHang(
                        rs.getInt("ID_Kho"),
                        rs.getString("TenKho"),
                        rs.getInt("SLHangToiDa"),
                        rs.getInt("SLHangTon"),
                        rs.getString("DiaChi")
                );
                khoList.add(kho);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return khoList;
    }
    
    public static KhoHang LayThongTinKho(int idKho) throws SQLException, ClassNotFoundException {
        
        KhoHang kho = null;
        String sql = "SELECT * FROM KhoHang where ID_Kho = " + idKho;
        try (Connection conn = ConnectionUtils.getMyConnection(); 
                PreparedStatement stmt = conn.prepareStatement(sql); 
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                kho = new KhoHang(
                        rs.getInt("ID_Kho"),
                        rs.getString("TenKho"),
                        rs.getInt("SLHangToiDa"),
                        rs.getInt("SLHangTon"),
                        rs.getString("DiaChi")
                );
                
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return kho;
    }
    
    public String LayTenKho(int idKho) throws SQLException, ClassNotFoundException {
        String tenKho = "";
        String sql = "SELECT TenKho FROM KhoHang WHERE ID_Kho = ?";
        try (Connection conn = ConnectionUtils.getMyConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idKho);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                tenKho = rs.getString("TenKho");
            }
        }
        return tenKho;
    }
    
    public static void main(String[] args) {
        try {
            KhoHangDAO khoHangDAO = new KhoHangDAO();

            List<KhoHang> khoList = khoHangDAO.LayThongTinKho();
            System.out.println("DANH SÁCH KHO:");
            for (KhoHang kho : khoList) {
                System.out.println("ID: " + kho.getIdKho()
                        + " | Tên: " + kho.getTenKho()
                        + " | Quản lý: " + kho.getIdQuanLy()
                        + " | Sức chứa: " + kho.getSlHangToiDa()
                        + " | Tồn kho: " + kho.getSlHangTon()
                        + " | Địa chỉ: " + kho.getDiaChi());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
