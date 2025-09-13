/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appgiaovan.DAO;

/**
 *
 * @author pc
 */
import appgiaovan.ConnectDB.ConnectionUtils;
import appgiaovan.Entity.NhanVienKho;
import appgiaovan.Entity.TaiKhoan;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class NhanVienKhoDAO {
    public NhanVienKhoDAO(){
        
    }
    
    public NhanVienKho layThongTinNhanVienKho(int id) throws SQLException, ClassNotFoundException{
        String sql = "SELECT * FROM NhanVienKho WHERE ID_NhanVien = ?";
        try (Connection conn = ConnectionUtils.getMyConnection();
                PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()){
                    NhanVienKho nv = new NhanVienKho();
                    nv.setID_NguoiDung(rs.getInt("ID_NhanVien"));
                    nv.setID_TaiKhoan(rs.getInt("ID_TaiKhoan"));
                    nv.setHoTen(rs.getString("HoTen"));
                    nv.setSDT(rs.getString("SDT"));
                    nv.setEmail(rs.getString("Email"));
                    nv.setCCCD(rs.getString("CCCD"));
                    nv.setNgaySinh(rs.getDate("NgaySinh"));
                    nv.setGioiTinh(rs.getString("GioiTinh"));
                    nv.setDiaChi(rs.getString("DiaChi"));
                    nv.setID_Kho(rs.getInt("ID_Kho"));
                    nv.setID_QuanLy(rs.getInt("ID_QuanLy"));
                    nv.setMucLuong(rs.getDouble("MucLuong"));
                    return nv;
                }
            }
        }
        return null;
    }
    
    public boolean kiemTraTonTaiThongTin(NhanVienKho nv) throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(*) FROM NhanVienKho WHERE CCCD = ? OR Email = ? OR SDT = ?";
        try (Connection conn = ConnectionUtils.getMyConnection(); 
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nv.getCCCD());
            ps.setString(2, nv.getEmail());
            ps.setString(3, nv.getSDT());
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1) > 0;
            }
        }
    }
    
    public boolean themNhanVienKho(NhanVienKho nv, TaiKhoan tk) throws SQLException, ClassNotFoundException {
        String sql = "{ call ThemNhanVienKho(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }";
        System.out.println(tk.getTenTaiKhoan());
        try (Connection conn = ConnectionUtils.getMyConnection(); 
            CallableStatement cs = conn.prepareCall(sql)) 
        {
            cs.setString(1, tk.getTenTaiKhoan());
            cs.setString(2, tk.getMatKhauMaHoa());
            cs.setString(3, nv.getHoTen());
            cs.setString(4, nv.getSDT());
            cs.setString(5, nv.getEmail());
            cs.setString(6, nv.getCCCD());
            java.util.Date utilDate = nv.getNgaySinh();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dateStr = sdf.format(utilDate);
            System.out.println(dateStr);
            cs.setDate(7, Date.valueOf(dateStr));
            cs.setString(8, nv.getGioiTinh());
            cs.setString(9, nv.getDiaChi());
            cs.setInt(10, nv.getID_Kho());
            cs.setInt(11, nv.getID_QuanLy());
            cs.setDouble(12, nv.getMucLuong());
            cs.execute();
        } catch (SQLException e) {

            System.err.println("Lỗi khi gọi function TaoTaiKhoanNVK_Func: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public int layMaNhanVienKhoMoi() throws SQLException, ClassNotFoundException {
        String sql = "SELECT COALESCE(MAX(ID_NhanVien),0) AS maxId FROM NhanVienKho";
        try (Connection conn = ConnectionUtils.getMyConnection(); 
                Statement stmt = conn.createStatement(); 
                ResultSet rs = stmt.executeQuery(sql)) {
            rs.next();
            return rs.getInt("maxId") + 1;
        }
    }
    public boolean xoaNhanVienKho(int idNguoiDung) throws SQLException, ClassNotFoundException {

        String callSql = "{ CALL XoaTaiKhoan(?) }";
        try (Connection conn = ConnectionUtils.getMyConnection();
             CallableStatement cs = conn.prepareCall(callSql)) {
            cs.setInt(1, idNguoiDung);
            cs.execute();
            return true;
        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa nhân viên kho: " + e.getMessage());
            throw e; 
        }
    }
        
    public boolean suaNhanVienKho(NhanVienKho nv) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE NhanVienKho SET HoTen=?, SDT=?, Email=?, CCCD=?, NgaySinh=?, GioiTinh=?, DiaChi=?, ID_Kho =?, ID_QuanLy=?, MucLuong = ? WHERE ID_NhanVien = ?";
        try (Connection conn = ConnectionUtils.getMyConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nv.getHoTen());
            ps.setString(2, nv.getSDT());
            ps.setString(3, nv.getEmail());
            ps.setString(4, nv.getCCCD());
            ps.setDate(5, new java.sql.Date(nv.getNgaySinh().getTime()));
            ps.setString(6, nv.getGioiTinh());
            ps.setString(7, nv.getDiaChi());
            ps.setInt(8, nv.getID_Kho());
            ps.setInt(9, nv.getID_QuanLy());
            ps.setDouble(10, nv.getMucLuong());
            ps.setInt(11, nv.getID_NguoiDung());


            return ps.executeUpdate() > 0;
        }
    }
    
    public List<NhanVienKho> layTatCaNhanVienKho() throws SQLException, ClassNotFoundException {
    String sql = "SELECT * FROM NhanVienKho n JOIN TaiKhoan t ON n.ID_TaiKhoan = t.ID_TaiKhoan WHERE TrangThaiXoa = 0 ORDER BY ID_NhanVien";
    try (Connection conn = ConnectionUtils.getMyConnection(); 
            PreparedStatement ps = conn.prepareStatement(sql); 
            ResultSet rs = ps.executeQuery()) {
        List<NhanVienKho> results = new ArrayList<>();
        while (rs.next()) {
            NhanVienKho nv = new NhanVienKho();
            nv.setID_NguoiDung(rs.getInt("ID_NhanVien"));
            nv.setID_TaiKhoan(rs.getInt("ID_TaiKhoan"));
            nv.setHoTen(rs.getString("HoTen"));
            nv.setSDT(rs.getString("SDT"));
            nv.setEmail(rs.getString("Email"));
            nv.setCCCD(rs.getString("CCCD"));
            nv.setNgaySinh(rs.getDate("NgaySinh"));
            nv.setGioiTinh(rs.getString("GioiTinh"));
            nv.setDiaChi(rs.getString("DiaChi"));
            nv.setID_Kho(rs.getInt("ID_Kho"));
            nv.setID_QuanLy(rs.getInt("ID_QuanLy"));
            nv.setMucLuong(rs.getDouble("MucLuong"));         
            results.add(nv);
        }
        return results;
        }
    }
    
    public List<NhanVienKho> timKiemNhanVienKho(String keyword) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM NhanVienKho n JOIN TaiKhoan t ON n.ID_TaiKhoan = t.ID_TaiKhoan WHERE ((HoTen LIKE ?) OR (SDT LIKE ?)) AND TrangThaiXoa = 0";
        try (Connection conn = ConnectionUtils.getMyConnection(); 
                PreparedStatement ps = conn.prepareStatement(sql)) {
            String pattern = "%" + keyword + "%";
            ps.setString(1, pattern);
            ps.setString(2, pattern);
            try (ResultSet rs = ps.executeQuery()) {
                List<NhanVienKho> results = new ArrayList<>();
                while (rs.next()) {
                    NhanVienKho nv = new NhanVienKho();
                    nv.setID_NguoiDung(rs.getInt("ID_NhanVien"));
                    nv.setID_TaiKhoan(rs.getInt("ID_TaiKhoan"));
                    nv.setHoTen(rs.getString("HoTen"));
                    nv.setSDT(rs.getString("SDT"));
                    nv.setEmail(rs.getString("Email"));
                    nv.setCCCD(rs.getString("CCCD"));
                    nv.setNgaySinh(rs.getDate("NgaySinh"));
                    nv.setGioiTinh(rs.getString("GioiTinh"));
                    nv.setDiaChi(rs.getString("DiaChi"));
                    nv.setID_Kho(rs.getInt("ID_Kho"));
                    nv.setID_QuanLy(rs.getInt("ID_QuanLy"));
                    nv.setMucLuong(rs.getDouble("MucLuong"));  
                    results.add(nv);
                }
                return results;
            }
        }
    }
    
    public void ThemTaiKhoan(TaiKhoan tk) throws SQLException, ClassNotFoundException {
        try (Connection conn = ConnectionUtils.getMyConnection()) {

            String sql = "{ ? = call DOANGIAOVAN.TaoTaiKhoan_Func(?, ?) }";
            try (CallableStatement stmt = conn.prepareCall(sql)) {

              
                stmt.registerOutParameter(1, Types.INTEGER); 

                
                stmt.setString(2, tk.getTenTaiKhoan());          
                stmt.setString(3, tk.getMatKhauMaHoa());        

                stmt.execute();

                int idTaiKhoan = stmt.getInt(1); // Lấy kết quả trả về
                System.out.println("Tạo tài khoản thành công với ID: " + idTaiKhoan);
            }

        } catch (SQLException e) {
            if (e.getErrorCode() == 20011) {
                System.err.println("Tên tài khoản đã tồn tại.");
            } else {
                System.err.println("Lỗi SQL: " + e.getMessage());
            }
        }
    }
    
    public int layID_NhanVienKho(int ID_TaiKhoan) throws SQLException, ClassNotFoundException {
        String sql = "SELECT ID_NhanVien FROM NhanVienKho WHERE ID_TaiKhoan=?";
        try (Connection conn = ConnectionUtils.getMyConnection(); 
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ID_TaiKhoan);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int ID_NhanVien=rs.getInt("ID_NhanVien");
                    return ID_NhanVien;
                }
            }
        }
        return -1;
    }
    
    public int getIdTaiKhoanByNhanVienKho(int idNhanVien) 
        throws SQLException, ClassNotFoundException {
    String sql = "SELECT ID_TaiKhoan FROM NhanVienKho WHERE ID_NhanVien = ?";
    try (Connection conn = ConnectionUtils.getMyConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, idNhanVien);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("ID_TaiKhoan");
            } else {
                throw new SQLException("Không tìm thấy nhân viên kho với ID=" + idNhanVien);
            }
        }
    }
}

    
    
    public List<Integer> layTatCaIDKho() throws SQLException, ClassNotFoundException {
        String sql = "SELECT ID_Kho FROM KhoHang";
        try (Connection conn = ConnectionUtils.getMyConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<Integer> ids = new ArrayList<>();
            while (rs.next()) {
                ids.add(rs.getInt("ID_Kho"));
            }
            return ids;
        }
    }

    public Integer layIDQuanLyTheoKho(int idKho) throws SQLException, ClassNotFoundException {
        String sql = "SELECT ID_QuanLy FROM QuanLy WHERE ID_Kho = ?";
        try (Connection conn = ConnectionUtils.getMyConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idKho);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("ID_QuanLy");
                else return null;
            }
        }
    }

}


