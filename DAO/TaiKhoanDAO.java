package appgiaovan.DAO;

import appgiaovan.ConnectDB.ConnectionUtils;
import appgiaovan.Entity.NguoiDung;
import appgiaovan.Entity.TaiKhoan;
import static appgiaovan.PasswordHashing.hashPassword;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TaiKhoanDAO {

    public TaiKhoan xacThucThongTin(String user, String pass) throws SQLException, ClassNotFoundException {
        String sql = "SELECT ID_TAIKHOAN, TenTaiKhoan, MatKhauMaHoa, VaiTro FROM TAIKHOAN WHERE TENTAIKHOAN = ?";
        System.out.println(user);

        try (Connection conn = ConnectionUtils.getMyConnection(); PreparedStatement st = conn.prepareStatement(sql)) {

            st.setString(1, user);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    System.out.println(pass);

                    String passH = rs.getString("MatKhauMaHoa");

                    if (passH.equals(hashPassword(pass))) {
                        int id = rs.getInt("ID_TAIKHOAN");
                        String vaiTro = rs.getString("VaiTro");
                        return new TaiKhoan(id, vaiTro);
                    }
                }
            }
        }

        return null;
    }

    public TaiKhoan LayThongTinTaiKhoan(int idtk) throws SQLException, ClassNotFoundException {

        String sql = "SELECT ID_TAIKHOAN, TenTaiKhoan, MatKhauMaHoa, VaiTro FROM TAIKHOAN WHERE ID_TaiKhoan = ?";
        TaiKhoan tk = null;

        try (Connection conn = ConnectionUtils.getMyConnection(); PreparedStatement st = conn.prepareStatement(sql)) {

            st.setInt(1, idtk);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    tk = new TaiKhoan();
                    tk.setIdTaiKhoan(rs.getInt("ID_TAIKHOAN"));
                    tk.setTenTaiKhoan(rs.getString("TenTaiKhoan"));

                    tk.setVaiTro(rs.getString("VaiTro"));
                } else {
                    return null; 
                }
            }

            System.out.println("Đây là " + tk.getVaiTro());

            String vaiTro = tk.getVaiTro();
            String hoTen = null;

            String tableName = null;
            if ("NVK".equals(vaiTro)) {
                tableName = "NhanVienKho";
                tk.setVaiTro("Nhân Viên Kho");
            } else if ("KH".equals(vaiTro)) {
                tableName = "KHACHHANG";
                tk.setVaiTro("Khách hàng");
            } else if ("QL".equals(vaiTro)) {
                tableName = "QUANLY";
                tk.setVaiTro("Quản lý");
            } else if ("NVGH".equals(vaiTro)) {
                tableName = "NHANVIENGIAOHANG";
                tk.setVaiTro("Nhân Viên giao hàng");
            }

            if (tableName != null) {
                String sql2 = "SELECT HoTen FROM " + tableName + " WHERE ID_TaiKhoan = ?";
                try (PreparedStatement st2 = conn.prepareStatement(sql2)) {
                    st2.setInt(1, idtk);
                    try (ResultSet rs2 = st2.executeQuery()) {
                        if (rs2.next()) {
                            hoTen = rs2.getString("HoTen");
                            tk.setTenNguoiDung(hoTen); // Giả sử class TaiKhoan có setHoTen
                        }
                    }
                }
            }
        }

        return tk;
    }

    public NguoiDung LayThongTinNguoiDung(TaiKhoan taiKhoan) throws SQLException, ClassNotFoundException {
        NguoiDung nd = new NguoiDung();
        String idName = null;
        String tableName = null;
        if ("NVK".equals(taiKhoan.getVaiTro())) {
            tableName = "NHANVIENKHO";
            idName = "NHANVIEN";
        } else if ("KH".equals(taiKhoan.getVaiTro())) {
            tableName = "KHACHHANG";
            idName = "KHACHHANG";
        } else if ("QL".equals(taiKhoan.getVaiTro())) {
            tableName = "QUANLY";
            idName = "QUANLY";

        } else if ("NVGH".equals(taiKhoan.getVaiTro())) {
            tableName = "NHANVIENGIAOHANG";
            idName = "NVGIAOHANG";
        }

        if (tableName != null) {
            String sql = "SELECT * FROM " + tableName + " WHERE ID_TaiKhoan = ?";
            try (Connection conn = ConnectionUtils.getMyConnection(); PreparedStatement st = conn.prepareStatement(sql)) {
                st.setInt(1, taiKhoan.getIdTaiKhoan());
                try (ResultSet rs = st.executeQuery()) {
                    if (rs.next()) {
                        nd.setID_NguoiDung(rs.getInt("ID_" + idName));
                        nd.setID_TaiKhoan(rs.getInt("ID_TaiKhoan"));
                        nd.setHoTen(rs.getString("HoTen"));
                        nd.setSDT(rs.getString("SDT"));
                        nd.setEmail(rs.getString("Email"));
                        nd.setCCCD(rs.getString("CCCD"));
                        nd.setNgaySinh(rs.getDate("NgaySinh"));
                        nd.setGioiTinh(rs.getString("GioiTinh"));
                    }
                }
            }
        }

        return nd;
    }

    public static boolean suaNguoiDung(NguoiDung nd, TaiKhoan taiKhoan) throws SQLException, ClassNotFoundException {

        String idName = null;
        String tableName = null;
        if ("NVK".equals(taiKhoan.getVaiTro())) {
            tableName = "NHANVIENKHO";
            idName = "NHANVIEN";
        } else if ("KH".equals(taiKhoan.getVaiTro())) {
            tableName = "KHACHHANG";
            idName = "KHACHHANG";
        } else if ("QL".equals(taiKhoan.getVaiTro())) {
            tableName = "QUANLY";
            idName = "QUANLY";

        } else if ("NVGH".equals(taiKhoan.getVaiTro())) {
            tableName = "NHANVIENGIAOHANG";
            idName = "NVGIAOHANG";
        }

        if (tableName != null) {
            String sql = "UPDATE " + tableName + " SET HoTen=?, SDT=?, Email=?, CCCD=?, NgaySinh=?, GioiTinh=? WHERE ID_" + idName+ "= ?";
            try (Connection conn = ConnectionUtils.getMyConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, nd.getHoTen());
                ps.setString(2, nd.getSDT());
                ps.setString(3, nd.getEmail());
                ps.setString(4, nd.getCCCD());
                ps.setDate(5, new java.sql.Date(nd.getNgaySinh().getTime()));
                ps.setString(6, nd.getGioiTinh());
                ps.setInt(7, nd.getID_NguoiDung());
                ps.execute();
                return true;
            }
        }
        return false;

    }
    
   
    static void main(String[] args) throws SQLException, ClassNotFoundException {
        TaiKhoan tk = new TaiKhoan();
        tk.setIdTaiKhoan(1);
        tk.setVaiTro("QL");
        NguoiDung nd = new TaiKhoanDAO().LayThongTinNguoiDung(tk);
        System.out.println(nd.getHoTen());
    }

    public void CapNhatMK(String hashPassword, int ID_TaiKhoan) throws SQLException, ClassNotFoundException {

        String sql = "UPDATE TAIKHOAN SET MATKHAUMAHOA = ? WHERE ID_TaiKhoan = ?";

        try (Connection conn = ConnectionUtils.getMyConnection(); PreparedStatement st = conn.prepareStatement(sql)) {

            st.setString(1, hashPassword);
            st.setInt(2, ID_TaiKhoan);

            int rowsUpdated = st.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Cập nhật mật khẩu thành công cho: ");
            } else {
                System.out.println("Không tìm thấy tài khoản với email: ");
            }

        } catch (SQLException e) {
            e.printStackTrace(); 
        }
    }

}
