// File: XemBaoCaoDAO.java
// Package: appgiaovan.DAO

package appgiaovan.DAO;

import appgiaovan.ConnectDB.ConnectionUtils;
import appgiaovan.Entity.BaoCaoGiaoHang;
import appgiaovan.Entity.BaoCaoKho;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class XemBaoCaoDAO {

    public List<BaoCaoKho> getBaoCaoKho(String keyword) throws SQLException, ClassNotFoundException {
        List<BaoCaoKho> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
            "SELECT ID_BaoCaoKho, ID_NhanVien, NgayTaoBaoCao, KyBaoCao, SoGoiHangNhap, SoGoiHangXuat " +
            "FROM BaoCaoKho"
        );
        if (keyword != null && !keyword.isEmpty()) {
            sql.append(" WHERE TO_CHAR(KyBaoCao, 'YYYY-MM-DD') LIKE ? OR TO_CHAR(NgayTaoBaoCao, 'YYYY-MM-DD') LIKE ?");
        }
        sql.append(" ORDER BY KyBaoCao DESC");

        try (Connection conn = ConnectionUtils.getMyConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            if (keyword != null && !keyword.isEmpty()) {
                String kw = "%" + keyword + "%";
                ps.setString(1, kw);
                ps.setString(2, kw);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    BaoCaoKho bc = new BaoCaoKho();
                    bc.setIdBaoCao(rs.getInt("ID_BaoCaoKho"));
                    bc.setIdNhanVien(rs.getInt("ID_NhanVien"));
                    bc.setNgayKhoiTao(rs.getDate("NgayTaoBaoCao"));
                    bc.setKyBaoCao(rs.getDate("KyBaoCao"));
                    bc.setSoGoiHangNhap(rs.getInt("SoGoiHangNhap"));
                    bc.setSoGoiHangXuat(rs.getInt("SoGoiHangXuat"));
                    list.add(bc);
                }
            }
        }

        return list;
    }

    public List<BaoCaoGiaoHang> getBaoCaoGiaoHang(String keyword) throws SQLException, ClassNotFoundException {
        List<BaoCaoGiaoHang> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
            "SELECT ID_BaoCaoGiaoHang, ID_NVGiaoHang, NgayKhoiTao, TongDonHangDaGiao, TongDHGiaoThatBai, TongTienCOD " +
            "FROM BaoCaoGiaoHang"
        );
        if (keyword != null && !keyword.isEmpty()) {
            sql.append(" WHERE TO_CHAR(NgayKhoiTao, 'YYYY-MM-DD') LIKE ?");
        }
        sql.append(" ORDER BY NgayKhoiTao DESC");

        try (Connection conn = ConnectionUtils.getMyConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            if (keyword != null && !keyword.isEmpty()) {
                ps.setString(1, "%" + keyword + "%");
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    BaoCaoGiaoHang bc = new BaoCaoGiaoHang();
                    bc.setIdBaoCao(rs.getInt("ID_BaoCaoGiaoHang"));
                    bc.setIdNhanVien(rs.getInt("ID_NVGiaoHang"));
                    bc.setNgayKhoiTao(rs.getDate("NgayKhoiTao"));
                    bc.setTongDonHangDaGiao(rs.getInt("TongDonHangDaGiao"));
                    bc.setTongDHGiaoThatBai(rs.getInt("TongDHGiaoThatBai"));
                    bc.setTongTienCOD(rs.getInt("TongTienCOD"));
                    list.add(bc);
                }
            }
        }

        return list;
    }
}
