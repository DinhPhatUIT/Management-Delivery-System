/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appgiaovan.DAO;

import appgiaovan.ConnectDB.ConnectionUtils;
import appgiaovan.Entity.GoiHang;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;

/**
 *
 * @author HP
 */
public class GoiHangDAO {

    public List<GoiHang> LayDSGoiHangTheoKho(int idKho) throws SQLException, ClassNotFoundException {
        List<GoiHang> list = new ArrayList<>();

        String sql = "SELECT * FROM GoiHang WHERE ID_KhoHangDen = " + idKho + " OR ID_KhoHangGui = " + idKho + " ORDER BY ID_GoiHang DESC";
        try (Connection conn = ConnectionUtils.getMyConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                GoiHang gh = new GoiHang();
                gh.setIdGoiHang(rs.getInt("ID_GoiHang"));
                gh.setIdKhoHangDen(rs.getInt("ID_KhoHangDen"));
                gh.setIdKhoHangGui(rs.getInt("ID_KhoHangGui"));
                gh.setIdNhanVien(rs.getInt("ID_NhanVien"));
                gh.setSoLuong(rs.getInt("SoLuong"));
                gh.setTrangThai(rs.getString("TrangThai"));

                list.add(gh);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<GoiHang> LayDSGoiHangTheoKho(GoiHang goiHang, int idKho) throws SQLException, ClassNotFoundException {
        List<GoiHang> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM GoiHang WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (null == goiHang.getIdGoiHang()) {
        } else {
            sql.append(" AND ID_GoiHang = ?");
            params.add(goiHang.getIdGoiHang());
        }

        if (goiHang.getTrangThai() != null && !goiHang.getTrangThai().isEmpty()) {
            System.out.println(goiHang.getTrangThai());
            sql.append(" AND TrangThai LIKE ?");
            params.add("%" + goiHang.getTrangThai() + "%");
        }

        sql.append(" AND (ID_KhoHangGui = ? OR ID_KhoHangDen = ?) ORDER BY ID_GoiHang");
        params.add(idKho);
        params.add(idKho);

        try (Connection conn = ConnectionUtils.getMyConnection(); PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                GoiHang gh = new GoiHang();
                gh.setIdGoiHang(rs.getInt("ID_GoiHang"));
                gh.setIdKhoHangDen(rs.getInt("ID_KhoHangDen"));
                gh.setIdKhoHangGui(rs.getInt("ID_KhoHangGui"));
                gh.setIdNhanVien(rs.getInt("ID_NhanVien"));
                gh.setSoLuong(rs.getInt("SoLuong"));
                gh.setTrangThai(rs.getString("TrangThai"));

                list.add(gh);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void ThemGoiHang(GoiHang goiHang, List<Integer> listDonHang) throws SQLException, ClassNotFoundException {
        String sql = "{call ThemGoiHang(?, ?, ?, ?)}";
        Integer[] donHangArray = listDonHang.toArray(Integer[]::new);
        try (Connection conn = ConnectionUtils.getMyConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            ArrayDescriptor descriptor = ArrayDescriptor.createDescriptor("DONHANGLIST", conn);
            ARRAY oracleArray = new ARRAY(descriptor, conn, donHangArray);

            stmt.setInt(1, goiHang.getIdKhoHangGui()); 
            stmt.setInt(2, goiHang.getIdKhoHangDen()); 
            stmt.setInt(3, goiHang.getIdNhanVien()); 
            stmt.setArray(4, oracleArray);
            stmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String[] DSTrangThai() throws Exception {
        List<String> result = new ArrayList<>();
        String sql = """
        SELECT search_condition
        FROM all_constraints
        WHERE constraint_name = 'CHK_GOIHANG_TRANGTHAI'
          AND table_name = 'GOIHANG'
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

    public void HoanThanhGoiHang(Integer maGoiHang) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE GOIHANG SET TRANGTHAI = 'Hoàn thành' WHERE ID_GOIHANG = ?";
        try (Connection conn = ConnectionUtils.getMyConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, maGoiHang);
            stmt.executeUpdate(); // executeUpdate dùng cho UPDATE, INSERT, DELETE
        }
    }

    public static void main(String[] agrs) {

    }
}
