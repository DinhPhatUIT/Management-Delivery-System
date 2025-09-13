package appgiaovan.DAO;

import appgiaovan.ConnectDB.ConnectionUtils;
import appgiaovan.Entity.DonHang;
import appgiaovan.Entity.NhanVienGiaoHang;
import appgiaovan.Entity.NhanVienKho;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class DonHangDAO {

    public int ThemDonHang(DonHang donHang) throws SQLException, ClassNotFoundException {
        String sql = "{call ThemDonHang( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";

        try (Connection conn = ConnectionUtils.getMyConnection(); CallableStatement cs = conn.prepareCall(sql)) {

            if (donHang.getIdKhachHang() != null) {
                cs.setInt(1, donHang.getIdKhachHang());
            } else {
                cs.setNull(1, Types.INTEGER);
            }

            cs.setString(2, donHang.getSdtNguoiGui());
            cs.setString(3, donHang.getSdtNguoiNhan());
            cs.setInt(4, donHang.getIdKhoTiepNhan());
            cs.setString(5, donHang.getTenNguoiGui());
            cs.setString(6, donHang.getTenNguoiNhan());
            cs.setString(7, donHang.getDiaChiNhan());
            if (donHang.getTienCOD() != null) {
                cs.setDouble(8, donHang.getTienCOD());
            } else {
                cs.setNull(8, Types.INTEGER);
            }

            cs.setString(9, donHang.getDichVu());
            cs.setString(10, donHang.getLoaiHangHoa());
            cs.registerOutParameter(11, Types.INTEGER);

            cs.execute();
            return cs.getInt(11);

        } catch (SQLException e) {
            System.err.println("Lỗi khi gọi procedure ThemDonHang: "
                    + e.getMessage());
            JOptionPane.showMessageDialog(null,
                    "Tạo đơn hàng thất bại.\nChi tiết: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return -1;
    }

    public void SuaDonHang(DonHang donHang) throws SQLException, ClassNotFoundException {
        String sql = "{call CapNhatDonHang(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";

        try (Connection conn = ConnectionUtils.getMyConnection(); CallableStatement stmt = conn.prepareCall(sql)) {

            // 1. ID đơn hàng (bắt buộc)
            stmt.setInt(1, donHang.getIdDonHang());

            // 2. ID Khách hàng
            if (donHang.getIdKhachHang() != null) {
                stmt.setInt(2, donHang.getIdKhachHang());
            } else {
                stmt.setNull(2, Types.INTEGER);
            }

            // 3. ID Nhân viên giao hàng
            if (donHang.getIdNVGiaoHang() != null) {
                stmt.setInt(3, donHang.getIdNVGiaoHang());
            } else {
                stmt.setNull(3, Types.INTEGER);
            }

            // 4. SĐT người gửi
            if (donHang.getSdtNguoiGui() != null) {
                stmt.setString(4, donHang.getSdtNguoiGui());
            } else {
                stmt.setNull(4, Types.VARCHAR);
            }

            // 5. SĐT người nhận
            if (donHang.getSdtNguoiNhan() != null) {
                stmt.setString(5, donHang.getSdtNguoiNhan());
            } else {
                stmt.setNull(5, Types.VARCHAR);
            }

            // 6. ID kho tiếp nhận
            if (donHang.getIdKhoTiepNhan() != null) {
                stmt.setInt(6, donHang.getIdKhoTiepNhan());
            } else {
                stmt.setNull(6, Types.INTEGER);
            }

            // 7. Tên người gửi
            if (donHang.getTenNguoiGui() != null) {
                stmt.setString(7, donHang.getTenNguoiGui());
            } else {
                stmt.setNull(7, Types.VARCHAR);
            }

            // 8. Tên người nhận
            if (donHang.getTenNguoiNhan() != null) {
                stmt.setString(8, donHang.getTenNguoiNhan());
            } else {
                stmt.setNull(8, Types.VARCHAR);
            }

            // 9. Địa chỉ nhận
            if (donHang.getDiaChiNhan() != null) {
                stmt.setString(9, donHang.getDiaChiNhan());
            } else {
                stmt.setNull(9, Types.VARCHAR);
            }

            // 10. Tiền COD
            if (donHang.getTienCOD() != null) {
                stmt.setDouble(10, donHang.getTienCOD());
            } else {
                stmt.setNull(10, Types.DOUBLE);
            }

            // 11. Thời gian nhận
            if (donHang.getThoiGianNhan() != null) {
                stmt.setTimestamp(11, (Timestamp) donHang.getThoiGianNhan());
            } else {
                stmt.setNull(11, Types.TIMESTAMP);
            }

            // 12. Thời gian dự kiến
            if (donHang.getThoiGianDuKien() != null) {
                stmt.setTimestamp(12, (Timestamp) donHang.getThoiGianDuKien());
            } else {
                stmt.setNull(12, Types.TIMESTAMP);
            }

            // 13. Dịch vụ
            if (donHang.getDichVu() != null) {
                stmt.setString(13, donHang.getDichVu());
            } else {
                stmt.setNull(13, Types.VARCHAR);
            }

            // 14. Loại hàng hóa
            if (donHang.getLoaiHangHoa() != null) {
                stmt.setString(14, donHang.getLoaiHangHoa());
            } else {
                stmt.setNull(14, Types.VARCHAR);
            }

            stmt.execute();

        }
    }

    public void SuaDonHang(DonHang donHang, Connection conn) throws SQLException, ClassNotFoundException {
        if (conn == null) {
            throw new SQLException("Connection is null. DAO cần một kết nối mở từ GUI.");
        }

//        conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
//        conn.setAutoCommit(false);

        String sql = "{call CapNhatDonHang(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
        CallableStatement stmt = conn.prepareCall(sql);

        // 1. ID đơn hàng (bắt buộc)
        stmt.setInt(1, donHang.getIdDonHang());

        // 2. ID Khách hàng
        if (donHang.getIdKhachHang() != null) {
            stmt.setInt(2, donHang.getIdKhachHang());
        } else {
            stmt.setNull(2, Types.INTEGER);
        }

        // 3. ID Nhân viên giao hàng
        if (donHang.getIdNVGiaoHang() != null) {
            stmt.setInt(3, donHang.getIdNVGiaoHang());
        } else {
            stmt.setNull(3, Types.INTEGER);
        }

        // 4. SĐT người gửi
        if (donHang.getSdtNguoiGui() != null) {
            stmt.setString(4, donHang.getSdtNguoiGui());
        } else {
            stmt.setNull(4, Types.VARCHAR);
        }

        // 5. SĐT người nhận
        if (donHang.getSdtNguoiNhan() != null) {
            stmt.setString(5, donHang.getSdtNguoiNhan());
        } else {
            stmt.setNull(5, Types.VARCHAR);
        }

        // 6. ID kho tiếp nhận
        if (donHang.getIdKhoTiepNhan() != null) {
            stmt.setInt(6, donHang.getIdKhoTiepNhan());
        } else {
            stmt.setNull(6, Types.INTEGER);
        }

        // 7. Tên người gửi
        if (donHang.getTenNguoiGui() != null) {
            stmt.setString(7, donHang.getTenNguoiGui());
        } else {
            stmt.setNull(7, Types.VARCHAR);
        }

        // 8. Tên người nhận
        if (donHang.getTenNguoiNhan() != null) {
            stmt.setString(8, donHang.getTenNguoiNhan());
        } else {
            stmt.setNull(8, Types.VARCHAR);
        }

        // 9. Địa chỉ nhận
        if (donHang.getDiaChiNhan() != null) {
            stmt.setString(9, donHang.getDiaChiNhan());
        } else {
            stmt.setNull(9, Types.VARCHAR);
        }

        // 10. Tiền COD
        if (donHang.getTienCOD() != null) {
            stmt.setDouble(10, donHang.getTienCOD());
        } else {
            stmt.setNull(10, Types.DOUBLE);
        }

        // 11. Thời gian nhận
        if (donHang.getThoiGianNhan() != null) {
            stmt.setTimestamp(11, (Timestamp) donHang.getThoiGianNhan());
        } else {
            stmt.setNull(11, Types.TIMESTAMP);
        }

        // 12. Thời gian dự kiến
        if (donHang.getThoiGianDuKien() != null) {
            stmt.setTimestamp(12, (Timestamp) donHang.getThoiGianDuKien());
        } else {
            stmt.setNull(12, Types.TIMESTAMP);
        }

        // 13. Dịch vụ
        if (donHang.getDichVu() != null) {
            stmt.setString(13, donHang.getDichVu());
        } else {
            stmt.setNull(13, Types.VARCHAR);
        }

        // 14. Loại hàng hóa
        if (donHang.getLoaiHangHoa() != null) {
            stmt.setString(14, donHang.getLoaiHangHoa());
        } else {
            stmt.setNull(14, Types.VARCHAR);
        }
        System.out.println("here1");
        stmt.execute();
        System.out.println("here2");

    }

    public List<DonHang> LayDSDonHang(DonHang donHang) throws SQLException, ClassNotFoundException {
        List<DonHang> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM DonHang WHERE 1=1 ");
        List<Object> params = new ArrayList<>();

        if (null == donHang.getIdDonHang()) {
        } else {
            sql.append(" AND ID_DonHang = ?");
            params.add(donHang.getIdDonHang());
        }

        if (donHang.getTrangThai() != null
                && !donHang.getTrangThai().isEmpty()
                && !donHang.getTrangThai().equals("Tất cả")) {

            System.out.println(donHang.getTrangThai());
            sql.append(" AND TrangThai LIKE ?");
            params.add("%" + donHang.getTrangThai() + "%");
        }

        if (donHang.getTenNguoiGui() != null && !donHang.getTenNguoiGui().isEmpty()) {
            sql.append(" AND TenNguoiGui LIKE ?");
            params.add("%" + donHang.getTenNguoiGui() + "%");
        }

        if (donHang.getIdKhoTiepNhan() != null) {
            sql.append(" AND ID_KhoTiepNhan = ?");
            params.add(donHang.getIdKhoTiepNhan());
        }

        sql.append(" ORDER BY ID_DonHang DESC");

        try (Connection conn = ConnectionUtils.getMyConnection(); PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                DonHang dh = new DonHang();
                dh.setIdDonHang(rs.getInt("ID_DonHang"));
                dh.setIdKhachHang(rs.getInt("ID_KhachHang"));
                dh.setIdNVGiaoHang(rs.getInt("ID_NVGiaoHang"));
                dh.setSdtNguoiGui(rs.getString("SDTNguoiGui"));
                dh.setSdtNguoiNhan(rs.getString("SDTNguoiNhan"));
                dh.setIdKhoTiepNhan(rs.getInt("ID_KhoTiepNhan"));
                dh.setTenNguoiGui(rs.getString("TenNguoiGui"));
                dh.setTenNguoiNhan(rs.getString("TenNguoiNhan"));
                dh.setDiaChiNhan(rs.getString("DiaChiNhan"));
                dh.setTienCOD(rs.getDouble("TienCOD"));
                dh.setPhi(rs.getDouble("Phi"));
                dh.setThoiGianNhan(rs.getTimestamp("ThoiGianNhan"));
                dh.setThoiGianTao(rs.getTimestamp("ThoiGianTao"));
                dh.setThoiGianDuKien(rs.getTimestamp("ThoiGianDuKien"));
                dh.setTrangThai(rs.getString("TrangThai"));
                dh.setDichVu(rs.getString("DichVu"));
                dh.setLoaiHangHoa(rs.getString("LoaiHangHoa"));

                list.add(dh);
            }
        }

        return list;
    }

    public List<DonHang> LayDSDonHang(DonHang donHang, Connection conn) throws SQLException, ClassNotFoundException {
        List<DonHang> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM DonHang WHERE 1=1 ");
        List<Object> params = new ArrayList<>();

        if (null == donHang.getIdDonHang()) {
        } else {
            sql.append(" AND ID_DonHang = ?");
            params.add(donHang.getIdDonHang());
        }

        if (donHang.getTrangThai() != null
                && !donHang.getTrangThai().isEmpty()
                && !donHang.getTrangThai().equals("Tất cả")) {

            System.out.println(donHang.getTrangThai());
            sql.append(" AND TrangThai LIKE ?");
            params.add("%" + donHang.getTrangThai() + "%");
        }

        if (donHang.getTenNguoiGui() != null && !donHang.getTenNguoiGui().isEmpty()) {
            sql.append(" AND TenNguoiGui LIKE ?");
            params.add("%" + donHang.getTenNguoiGui() + "%");
        }

        if (donHang.getIdKhoTiepNhan() != null) {
            sql.append(" AND ID_KhoTiepNhan = ?");
            params.add(donHang.getIdKhoTiepNhan());
        }

        sql.append(" ORDER BY ID_DonHang DESC");

        PreparedStatement stmt = conn.prepareStatement(sql.toString());
        
            for (int i = 0; i < params.size(); i++) {
            stmt.setObject(i + 1, params.get(i));

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                DonHang dh = new DonHang();
                dh.setIdDonHang(rs.getInt("ID_DonHang"));
                dh.setIdKhachHang(rs.getInt("ID_KhachHang"));
                dh.setIdNVGiaoHang(rs.getInt("ID_NVGiaoHang"));
                dh.setSdtNguoiGui(rs.getString("SDTNguoiGui"));
                dh.setSdtNguoiNhan(rs.getString("SDTNguoiNhan"));
                dh.setIdKhoTiepNhan(rs.getInt("ID_KhoTiepNhan"));
                dh.setTenNguoiGui(rs.getString("TenNguoiGui"));
                dh.setTenNguoiNhan(rs.getString("TenNguoiNhan"));
                dh.setDiaChiNhan(rs.getString("DiaChiNhan"));
                dh.setTienCOD(rs.getDouble("TienCOD"));
                dh.setPhi(rs.getDouble("Phi"));
                dh.setThoiGianNhan(rs.getTimestamp("ThoiGianNhan"));
                dh.setThoiGianTao(rs.getTimestamp("ThoiGianTao"));
                dh.setThoiGianDuKien(rs.getTimestamp("ThoiGianDuKien"));
                dh.setTrangThai(rs.getString("TrangThai"));
                dh.setDichVu(rs.getString("DichVu"));
                dh.setLoaiHangHoa(rs.getString("LoaiHangHoa"));

                list.add(dh);
            }
        }

        return list;
    }

    public List<DonHang> LayDSDonHangPhanCong(DonHang donHang, int idKho) throws SQLException, ClassNotFoundException {
        List<DonHang> list = new ArrayList<>();
        System.out.println("here");
        StringBuilder sql = new StringBuilder("SELECT  DISTINCT d.* FROM DONHANG d"
                + " JOIN CHITIETGOIHANG c ON D.ID_DONHANG = c.ID_DONHANG"
                + " JOIN GOIHANG g ON C.ID_GOIHANG = G.ID_GOIHANG"
                + " WHERE G.ID_KHOHANGDEN = " + idKho);
        List<Object> params = new ArrayList<>();

        if (null == donHang.getIdDonHang()) {
        } else {
            sql.append(" AND ID_DonHang = ?");
            params.add(donHang.getIdDonHang());
        }

        if (donHang.getTrangThai() != null && !donHang.getTrangThai().isEmpty()) {
            sql.append(" AND D.TrangThai LIKE ?");
            params.add("%" + donHang.getTrangThai() + "%");
        }

        if (donHang.getTenNguoiGui() != null && !donHang.getTenNguoiGui().isEmpty()) {
            sql.append(" AND TenNguoiGui LIKE ?");
            params.add("%" + donHang.getTenNguoiGui() + "%");
        }

        sql.append(" ORDER BY D.ID_DonHang DESC");

        try (Connection conn = ConnectionUtils.getMyConnection(); PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                DonHang dh = new DonHang();
                dh.setIdDonHang(rs.getInt("ID_DonHang"));
                dh.setIdKhachHang(rs.getInt("ID_KhachHang"));
                dh.setIdNVGiaoHang(rs.getInt("ID_NVGiaoHang"));
                dh.setSdtNguoiGui(rs.getString("SDTNguoiGui"));
                dh.setSdtNguoiNhan(rs.getString("SDTNguoiNhan"));
                dh.setIdKhoTiepNhan(rs.getInt("ID_KhoTiepNhan"));
                dh.setTenNguoiGui(rs.getString("TenNguoiGui"));
                dh.setTenNguoiNhan(rs.getString("TenNguoiNhan"));
                dh.setDiaChiNhan(rs.getString("DiaChiNhan"));
                dh.setTienCOD(rs.getDouble("TienCOD"));
                dh.setPhi(rs.getDouble("Phi"));
                dh.setThoiGianNhan(rs.getTimestamp("ThoiGianNhan"));
                dh.setThoiGianTao(rs.getTimestamp("ThoiGianTao"));
                dh.setThoiGianDuKien(rs.getTimestamp("ThoiGianDuKien"));
                dh.setTrangThai(rs.getString("TrangThai"));
                dh.setDichVu(rs.getString("DichVu"));
                dh.setLoaiHangHoa(rs.getString("LoaiHangHoa"));

                list.add(dh);
            }
        }

        return list;
    }

    public List<DonHang> LayDSDonHangCuaKH(DonHang donHang, int ID_KhachHang) throws SQLException, ClassNotFoundException {
        List<DonHang> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM DonHang WHERE 1=1 AND ID_KhachHang=? ");

        List<Object> params = new ArrayList<>();
        params.add(ID_KhachHang);
        if (null == donHang.getIdDonHang()) {
        } else {
            sql.append(" AND ID_DonHang = ?");
            params.add(donHang.getIdDonHang());
        }

        if (donHang.getTrangThai() != null && !donHang.getTrangThai().isEmpty()) {

            sql.append(" AND TrangThai LIKE ?");
            params.add("%" + donHang.getTrangThai() + "%");
        }

        if (donHang.getTenNguoiGui() != null && !donHang.getTenNguoiGui().isEmpty()) {
            sql.append(" AND TenNguoiGui LIKE ?");
            params.add("%" + donHang.getTenNguoiGui() + "%");
        }

        sql.append(" ORDER BY ID_DonHang DESC");

        try (Connection conn = ConnectionUtils.getMyConnection(); PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                DonHang dh = new DonHang();
                dh.setIdDonHang(rs.getInt("ID_DonHang"));
                dh.setIdKhachHang(rs.getInt("ID_KhachHang"));
                dh.setIdNVGiaoHang(rs.getInt("ID_NVGiaoHang"));
                dh.setSdtNguoiGui(rs.getString("SDTNguoiGui"));
                dh.setSdtNguoiNhan(rs.getString("SDTNguoiNhan"));
                dh.setIdKhoTiepNhan(rs.getInt("ID_KhoTiepNhan"));
                dh.setTenNguoiGui(rs.getString("TenNguoiGui"));
                dh.setTenNguoiNhan(rs.getString("TenNguoiNhan"));
                dh.setDiaChiNhan(rs.getString("DiaChiNhan"));
                dh.setTienCOD(rs.getDouble("TienCOD"));
                dh.setPhi(rs.getDouble("Phi"));
                dh.setThoiGianNhan(rs.getTimestamp("ThoiGianNhan"));
                dh.setThoiGianTao(rs.getTimestamp("ThoiGianTao"));
                dh.setThoiGianDuKien(rs.getTimestamp("ThoiGianDuKien"));
                dh.setTrangThai(rs.getString("TrangThai"));
                dh.setDichVu(rs.getString("DichVu"));
                dh.setLoaiHangHoa(rs.getString("LoaiHangHoa"));

                list.add(dh);
            }
        }

        return list;
    }

    public List<DonHang> LayDSDonHangCuaKH(int ID_KhachHang) throws SQLException, ClassNotFoundException {
        List<DonHang> list = new ArrayList<>();

        String sql = "SELECT * FROM DonHang WHERE ID_KhachHang=" + Integer.toString(ID_KhachHang) + " ORDER BY ID_DonHang DESC";
        try (Connection conn = ConnectionUtils.getMyConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                DonHang dh = new DonHang();
                dh.setIdDonHang(rs.getInt("ID_DonHang"));
                dh.setIdKhachHang(rs.getInt("ID_KhachHang"));
                dh.setIdNVGiaoHang(rs.getInt("ID_NVGiaoHang"));
                dh.setSdtNguoiGui(rs.getString("SDTNguoiGui"));
                dh.setSdtNguoiNhan(rs.getString("SDTNguoiNhan"));
                dh.setIdKhoTiepNhan(rs.getInt("ID_KhoTiepNhan"));
                dh.setTenNguoiGui(rs.getString("TenNguoiGui"));
                dh.setTenNguoiNhan(rs.getString("TenNguoiNhan"));
                dh.setDiaChiNhan(rs.getString("DiaChiNhan"));
                dh.setTienCOD(rs.getDouble("TienCOD"));
                dh.setPhi(rs.getDouble("Phi"));
                dh.setThoiGianNhan(rs.getTimestamp("ThoiGianNhan"));
                dh.setThoiGianTao(rs.getTimestamp("ThoiGianTao"));
                dh.setThoiGianDuKien(rs.getTimestamp("ThoiGianDuKien"));
                dh.setTrangThai(rs.getString("TrangThai"));
                dh.setDichVu(rs.getString("DichVu"));
                dh.setLoaiHangHoa(rs.getString("LoaiHangHoa"));

                list.add(dh);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<DonHang> LayDSDonHang() throws SQLException, ClassNotFoundException {
        List<DonHang> list = new ArrayList<>();

        String sql = "SELECT * FROM DonHang ORDER BY ID_DONHANG desc";
        try (Connection conn = ConnectionUtils.getMyConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                DonHang dh = new DonHang();
                dh.setIdDonHang(rs.getInt("ID_DonHang"));
                dh.setIdKhachHang(rs.getInt("ID_KhachHang"));
                dh.setIdNVGiaoHang(rs.getInt("ID_NVGiaoHang"));
                dh.setSdtNguoiGui(rs.getString("SDTNguoiGui"));
                dh.setSdtNguoiNhan(rs.getString("SDTNguoiNhan"));
                dh.setIdKhoTiepNhan(rs.getInt("ID_KhoTiepNhan"));
                dh.setTenNguoiGui(rs.getString("TenNguoiGui"));
                dh.setTenNguoiNhan(rs.getString("TenNguoiNhan"));
                dh.setDiaChiNhan(rs.getString("DiaChiNhan"));
                dh.setTienCOD(rs.getDouble("TienCOD"));
                dh.setPhi(rs.getDouble("Phi"));
                dh.setThoiGianNhan(rs.getTimestamp("ThoiGianNhan"));
                dh.setThoiGianTao(rs.getTimestamp("ThoiGianTao"));
                dh.setThoiGianDuKien(rs.getTimestamp("ThoiGianDuKien"));
                dh.setTrangThai(rs.getString("TrangThai"));
                dh.setDichVu(rs.getString("DichVu"));
                dh.setLoaiHangHoa(rs.getString("LoaiHangHoa"));

                list.add(dh);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<DonHang> layDSDonHangCuaNVGH(int idTaiKhoan) throws SQLException, ClassNotFoundException {
        List<DonHang> danhSachDonHang = new ArrayList<>();

        try (Connection conn = ConnectionUtils.getMyConnection(); CallableStatement cs = conn.prepareCall("{call LayDSDHCuaNVGH(?, ?)}")) {

            cs.setInt(1, idTaiKhoan);
            cs.registerOutParameter(2, Types.REF_CURSOR);

            cs.execute();

            try (ResultSet rs = (ResultSet) cs.getObject(2)) {
                if (rs != null) {
                    while (rs.next()) {
                        DonHang dh = new DonHang();
                        dh.setIdDonHang(rs.getInt("ID_DONHANG"));
                        dh.setTenNguoiNhan(rs.getString("TENNGUOINHAN"));
                        dh.setDiaChiNhan(rs.getString("DIACHINHAN"));
                        dh.setSdtNguoiNhan(rs.getString("SDTNGUOINHAN"));
                        dh.setTrangThai(rs.getString("TRANGTHAI"));
                        dh.setTienCOD(rs.getDouble("TIENCOD"));
                        dh.setThoiGianTao(rs.getTimestamp("THOIGIANTAO"));
                        dh.setSdtNguoiGui(rs.getString("SDTNGUOIGUI"));
                        dh.setTenNguoiGui(rs.getString("TENNGUOIGUI"));
                        dh.setIdKhoTiepNhan(rs.getInt("ID_KHOTIEPNHAN"));
                        danhSachDonHang.add(dh);
                    }
                } else {
                    System.err.println("ResultSet trả về null từ procedure.");
                }
            }

        } catch (SQLException e) {
            System.err.println("Lỗi SQL khi gọi procedure LayDSDHCuaNVGH: " + e.getMessage());
            throw e;  // ném lại lỗi để lớp gọi xử lý
        }

        return danhSachDonHang;
    }

    public List<DonHang> LayDSDonHangCuaNVGH(DonHang donHang, int idtk) throws SQLException, ClassNotFoundException {
        List<DonHang> list = new ArrayList<>();
        int idnvgh;
        String sql1 = "SELECT ID_NVGIAOHANG FROM NHANVIENGIAOHANG "
                + "WHERE ID_TAIKHOAN = ?";
        try (Connection conn = ConnectionUtils.getMyConnection()) {
            PreparedStatement st = conn.prepareStatement(sql1);
            st.setInt(1, idtk);
            ResultSet rs1 = st.executeQuery();
            rs1.next();
            idnvgh = rs1.getInt("ID_NVGIAOHANG");
            StringBuilder sql = new StringBuilder("SELECT * FROM DonHang WHERE ID_NVGiaoHang = ?");

            List<Object> params = new ArrayList<>();
            params.add(idnvgh);
            if (null == donHang.getIdDonHang()) {
            } else {
                sql.append(" AND ID_DonHang = ?");
                params.add(donHang.getIdDonHang());
            }

            if (donHang.getTrangThai() != null && !donHang.getTrangThai().isEmpty()) {
                System.out.println(donHang.getTrangThai());

                sql.append(" AND TrangThai LIKE ?");
                params.add("%" + donHang.getTrangThai() + "%");
            }

            if (donHang.getTenNguoiGui() != null && !donHang.getTenNguoiGui().isEmpty()) {
                sql.append(" AND TenNguoiGui LIKE ?");
                params.add("%" + donHang.getTenNguoiGui() + "%");
            }

            sql.append(" ORDER BY ID_DonHang DESC");

            PreparedStatement stmt = conn.prepareStatement(sql.toString());
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                DonHang dh = new DonHang();
                dh.setIdDonHang(rs.getInt("ID_DonHang"));
                dh.setIdKhachHang(rs.getInt("ID_KhachHang"));
                dh.setIdNVGiaoHang(rs.getInt("ID_NVGiaoHang"));
                dh.setSdtNguoiGui(rs.getString("SDTNguoiGui"));
                dh.setSdtNguoiNhan(rs.getString("SDTNguoiNhan"));
                dh.setIdKhoTiepNhan(rs.getInt("ID_KhoTiepNhan"));
                dh.setTenNguoiGui(rs.getString("TenNguoiGui"));
                dh.setTenNguoiNhan(rs.getString("TenNguoiNhan"));
                dh.setDiaChiNhan(rs.getString("DiaChiNhan"));
                dh.setTienCOD(rs.getDouble("TienCOD"));
                dh.setPhi(rs.getDouble("Phi"));
                dh.setThoiGianNhan(rs.getTimestamp("ThoiGianNhan"));
                dh.setThoiGianTao(rs.getTimestamp("ThoiGianTao"));
                dh.setThoiGianDuKien(rs.getTimestamp("ThoiGianDuKien"));
                dh.setTrangThai(rs.getString("TrangThai"));
                dh.setDichVu(rs.getString("DichVu"));
                dh.setLoaiHangHoa(rs.getString("LoaiHangHoa"));

                list.add(dh);
            }
        }

        return list;
    }

    public DonHang LayThongTinDonHang(int idDonHang) throws SQLException, ClassNotFoundException {
        String sql = "Select * from DonHang where id_donhang =" + idDonHang;
        Connection conn = ConnectionUtils.getMyConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        rs.next();

        DonHang dh = new DonHang();
        dh.setIdDonHang(rs.getInt("ID_DonHang"));
        dh.setIdKhachHang(rs.getInt("ID_KhachHang"));
        dh.setIdNVGiaoHang(rs.getInt("ID_NVGiaoHang"));
        dh.setSdtNguoiGui(rs.getString("SDTNguoiGui"));
        dh.setSdtNguoiNhan(rs.getString("SDTNguoiNhan"));
        dh.setIdKhoTiepNhan(rs.getInt("ID_KhoTiepNhan"));
        dh.setTenNguoiGui(rs.getString("TenNguoiGui"));
        dh.setTenNguoiNhan(rs.getString("TenNguoiNhan"));
        dh.setDiaChiNhan(rs.getString("DiaChiNhan"));
        dh.setTienCOD(rs.getDouble("TienCOD"));
        dh.setPhi(rs.getDouble("Phi"));
        dh.setThoiGianNhan(rs.getTimestamp("ThoiGianNhan"));
        dh.setThoiGianTao(rs.getTimestamp("ThoiGianTao"));
        dh.setThoiGianDuKien(rs.getTimestamp("ThoiGianDuKien"));
        dh.setTrangThai(rs.getString("TrangThai"));
        dh.setDichVu(rs.getString("DichVu"));
        dh.setLoaiHangHoa(rs.getString("LoaiHangHoa"));
        return dh;
    }

    public List<DonHang> LayDSDonHangCuaNVK(DonHang donHang, NhanVienKho nvk) throws SQLException, ClassNotFoundException {
        List<DonHang> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("""
        SELECT 
            ID_DonHang, ID_KhachHang, ID_NVGiaoHang, SDTNguoiGui, SDTNguoiNhan, 
            ID_KhoTiepNhan, TenNguoiGui, TenNguoiNhan, DiaChiNhan, TienCOD, Phi, 
            ThoiGianNhan, ThoiGianTao, ThoiGianDuKien, TrangThai, DichVu, LoaiHangHoa 
        FROM DonHang 
        WHERE 1=1
    """);

        List<Object> params = new ArrayList<>();

        if (donHang.getIdDonHang() != null) {
            sql.append(" AND ID_DonHang = ?");
            params.add(donHang.getIdDonHang());
        }

        if (donHang.getTrangThai() != null && !donHang.getTrangThai().isEmpty()) {
            sql.append(" AND TrangThai LIKE ?");
            params.add("%" + donHang.getTrangThai() + "%");
        }

        if (donHang.getTenNguoiGui() != null && !donHang.getTenNguoiGui().isEmpty()) {
            sql.append(" AND TenNguoiGui LIKE ?");
            params.add("%" + donHang.getTenNguoiGui() + "%");
        }

        if (donHang.getIdKhoTiepNhan() != null) {
            sql.append(" AND ID_KhoTiepNhan = ?");
            params.add(donHang.getIdKhoTiepNhan());
        }

        if (nvk.getID_Kho() != null) {
            sql.append("""
            UNION
            SELECT 
                d.ID_DonHang, d.ID_KhachHang, d.ID_NVGiaoHang, d.SDTNguoiGui, d.SDTNguoiNhan, 
                d.ID_KhoTiepNhan, d.TenNguoiGui, d.TenNguoiNhan, d.DiaChiNhan, d.TienCOD, d.Phi, 
                d.ThoiGianNhan, d.ThoiGianTao, d.ThoiGianDuKien, d.TrangThai, d.DichVu, d.LoaiHangHoa 
            FROM DONHANG d
            JOIN CHITIETGOIHANG c ON d.ID_DonHang = c.ID_DonHang
            JOIN GOIHANG g ON c.ID_GOIHANG = g.ID_GOIHANG
            WHERE g.ID_KHOHANGDEN = ?
        """);
            params.add(nvk.getID_Kho());
        }

        sql.append(" ORDER BY ID_DonHang DESC");

        try (Connection conn = ConnectionUtils.getMyConnection(); PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                DonHang dh = new DonHang();
                dh.setIdDonHang(rs.getInt("ID_DonHang"));
                dh.setIdKhachHang(rs.getInt("ID_KhachHang"));
                dh.setIdNVGiaoHang(rs.getInt("ID_NVGiaoHang"));
                dh.setSdtNguoiGui(rs.getString("SDTNguoiGui"));
                dh.setSdtNguoiNhan(rs.getString("SDTNguoiNhan"));
                dh.setIdKhoTiepNhan(rs.getInt("ID_KhoTiepNhan"));
                dh.setTenNguoiGui(rs.getString("TenNguoiGui"));
                dh.setTenNguoiNhan(rs.getString("TenNguoiNhan"));
                dh.setDiaChiNhan(rs.getString("DiaChiNhan"));
                dh.setTienCOD(rs.getDouble("TienCOD"));
                dh.setPhi(rs.getDouble("Phi"));
                dh.setThoiGianNhan(rs.getTimestamp("ThoiGianNhan"));
                dh.setThoiGianTao(rs.getTimestamp("ThoiGianTao"));
                dh.setThoiGianDuKien(rs.getTimestamp("ThoiGianDuKien"));
                dh.setTrangThai(rs.getString("TrangThai"));
                dh.setDichVu(rs.getString("DichVu"));
                dh.setLoaiHangHoa(rs.getString("LoaiHangHoa"));
                list.add(dh);
            }
        }

        return list;
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        DonHangDAO dao = new DonHangDAO();
        try {
            List<DonHang> ds = dao.LayDSDonHang();
            for (DonHang dh : ds) {
                System.out.println("ID: " + dh.getIdDonHang()
                        + ", Khách hàng: " + dh.getIdKhachHang()
                        + ", Người gửi: " + dh.getTenNguoiGui()
                        + ", Người nhận: " + dh.getTenNguoiNhan()
                        + ", Địa chỉ nhận: " + dh.getDiaChiNhan()
                        + ", Tiền COD: " + dh.getTienCOD()
                        + ", Trạng thái: " + dh.getTrangThai());
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public int LayMaDon() throws SQLException, ClassNotFoundException {
        String sql = "SELECT seq_DonHang.NEXTVAL FROM dual";
        try (Connection conn = ConnectionUtils.getMyConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1); // lấy cột đầu tiên
            } else {
                throw new SQLException("Không thể lấy giá trị từ sequence seq_DonHang.");
            }
        }
    }

    public void CapNhatDH(int iddh, String trangthai) throws SQLException, ClassNotFoundException {
        String sql = "call CapNhatTrangThaiDonHang(?,?)";
        try (Connection conn = ConnectionUtils.getMyConnection()) {
            CallableStatement cs = conn.prepareCall(sql);
            cs.setInt(1, iddh);
            cs.setString(2, trangthai);
            cs.execute();
//            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String[] DSDichVu() throws Exception {
        List<String> result = new ArrayList<>();
        String sql = """
        SELECT search_condition
        FROM all_constraints
        WHERE constraint_name = 'CHK_DONHANG_DICHVU'
          AND table_name = 'DONHANG'
          AND constraint_type = 'C'
    """;

        try (Connection conn = ConnectionUtils.getMyConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                String condition = rs.getString(1); // ví dụ: "LOAIGIAOHANG" IN ('Nhanh', 'Tiết kiệm', 'Hỏa tốc')
                int start = condition.indexOf('(');
                int end = condition.lastIndexOf(')');
                if (start >= 0 && end > start) {
                    String inClause = condition.substring(start + 1, end); // 'Nhanh', 'Tiết kiệm', 'Hỏa tốc'
                    String[] values = inClause.split(",");
                    for (String val : values) {
                        result.add(val.trim().replaceAll("'", ""));
                    }
                }
            }
        }

        return result.toArray(String[]::new);
    }

    public String[] DSLoaiHang() throws Exception {
        List<String> result = new ArrayList<>();
        String sql = """
        SELECT search_condition
        FROM all_constraints
        WHERE constraint_name = 'CHK_DONHANG_LOAIHANGHOA'
          AND table_name = 'DONHANG'
          AND constraint_type = 'C'
    """;

        try (Connection conn = ConnectionUtils.getMyConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                String condition = rs.getString(1);
                int start = condition.indexOf('(');
                int end = condition.lastIndexOf(')');
                if (start >= 0 && end > start) {
                    String inClause = condition.substring(start + 1, end);
                    String[] values = inClause.split(",");
                    for (String val : values) {
                        result.add(val.trim().replaceAll("'", ""));
                    }
                }
            }
        }

        return result.toArray(String[]::new);
    }

    public String[] DSTrangThai() throws SQLException, ClassNotFoundException {
        List<String> result = new ArrayList<>();
        String sql = """
        SELECT search_condition
        FROM all_constraints
        WHERE constraint_name = 'CHK_DONHANG_TRANGTHAI'
          AND table_name = 'DONHANG'
          AND constraint_type = 'C'
    """;

        try (Connection conn = ConnectionUtils.getMyConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                String condition = rs.getString(1);
                int start = condition.indexOf('(');
                int end = condition.lastIndexOf(')');
                if (start >= 0 && end > start) {
                    String inClause = condition.substring(start + 1, end);
                    String[] values = inClause.split(",");
                    for (String val : values) {
                        result.add(val.trim().replaceAll("'", ""));
                    }
                }
            }
        }

        return result.toArray(String[]::new);
    }

    public void PhanCongGiaoHang(NhanVienGiaoHang nv, List<Integer> listIdDonHang) throws SQLException, ClassNotFoundException {
        if (listIdDonHang == null || listIdDonHang.isEmpty()) {
            return;
        }

        StringBuilder sql = new StringBuilder("UPDATE DonHang SET ID_NVGiaoHang = ? WHERE ID_DonHang IN (");

        for (int i = 0; i < listIdDonHang.size(); i++) {
            sql.append("?");
            if (i < listIdDonHang.size() - 1) {
                sql.append(",");
            }
        }
        sql.append(")");

        try (Connection conn = ConnectionUtils.getMyConnection(); PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            // Set ID của nhân viên giao hàng
            stmt.setInt(1, nv.getID_NguoiDung());

            // Set các ID đơn hàng
            for (int i = 0; i < listIdDonHang.size(); i++) {
                stmt.setInt(i + 2, listIdDonHang.get(i)); // +2 vì vị trí đầu tiên là ID_NVGiaoHang
            }

            int affectedRows = stmt.executeUpdate();
            System.out.println("Đã phân công " + affectedRows + " đơn hàng cho shipper ID: " + nv.getID_NguoiDung());

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void HuyDonHang(int ID_DonHang) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE DONHANG SET TRANGTHAI = 'Hủy' WHERE id_donhang = ?";

        try (Connection conn = ConnectionUtils.getMyConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, ID_DonHang);
            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Đơn hàng đã được hủy thành công.");
            } else {
                System.out.println("Không tìm thấy đơn hàng với ID: " + ID_DonHang);
            }
        }
    }

    public int LayTongSoDon(int ID_KhachHang) throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(*) FROM DONHANG WHERE ID_KhachHang = ?";
        try (Connection conn = ConnectionUtils.getMyConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, ID_KhachHang); // Gán tham số trước
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1); // Lấy giá trị COUNT(*)
                } else {
                    throw new SQLException("Không thể lấy số lượng đơn hàng.");
                }
            }

        }
    }

    public int LayTongSoDonDaGiao(int ID_KhachHang) throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(*) FROM DONHANG WHERE ID_KhachHang = ? AND TRANGTHAI='Đã giao'";
        try (Connection conn = ConnectionUtils.getMyConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, ID_KhachHang); // Gán tham số trước
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1); // Lấy giá trị COUNT(*)
                } else {
                    throw new SQLException("Không thể lấy số lượng đơn hàng.");
                }
            }

        }
    }

    public int layIDDHMoiNhat(int id_kh) throws SQLException, ClassNotFoundException {
        String sql = "SELECT ID_DONHANG FROM DONHANG WHERE ID_KHACHHANG = ? ORDER BY THOIGIANTAO DESC";
        try (Connection conn = ConnectionUtils.getMyConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id_kh);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("ID_DONHANG");
                } else {
                    throw new SQLException("Không thể lấy mã đơn hàng.");
                }
            }

        }
    }

    public static int TongDonHang() throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(ID_DONHANG) FROM DONHANG";
        try (Connection conn = ConnectionUtils.getMyConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1); // ✅ Lấy giá trị từ cột đầu tiên
                } else {
                    throw new SQLException("Không thể lấy tổng đơn hàng.");
                }
            }
        }

    }

}
