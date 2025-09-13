package appgiaovan.DAO;

import appgiaovan.ConnectDB.ConnectionUtils;
import appgiaovan.Entity.TK_DanhGia;
import appgiaovan.Entity.TK_DonHang;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ThongKeDAO {

    public static List<TK_DonHang> getListTKDonHang() throws SQLException, ClassNotFoundException {
        List<TK_DonHang> list = new ArrayList<>();
        String sql = "SELECT "
                + "d.THOIGIANTAO AS NGAY, "
                + "SUM(CASE WHEN D.TRANGTHAI = N'Đã giao' THEN 1 ELSE 0 END) AS SoLuongDaGiao, "
                + "SUM(CASE WHEN D.TRANGTHAI = N'Giao thất bại' THEN 1 ELSE 0 END) AS SoLuongThatBai, "
                + "SUM(CASE WHEN D.TRANGTHAI = N'Hủy' THEN 1 ELSE 0 END) AS SoLuongDaHuy "
                + "FROM DONHANG d "
                + "GROUP BY d.THOIGIANTAO "
                + "ORDER BY d.THOIGIANTAO";

        Connection conn = ConnectionUtils.getMyConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            java.sql.Date ngay = rs.getDate("NGAY");
            int soLuongDaGiao = rs.getInt("SOLUONGDAGIAO");
            int soLuongThatBai = rs.getInt("SOLUONGTHATBAI");
            int soLuongDaHuy = rs.getInt("SOLUONGDAHUY");

            int tongSoDonHang = soLuongDaGiao + soLuongThatBai + soLuongDaHuy;

            TK_DonHang donhang = new TK_DonHang(
                    ngay,
                    tongSoDonHang,
                    soLuongDaGiao,
                    soLuongThatBai,
                    soLuongDaHuy
            );

            list.add(donhang);
        }

        return list;
    }

    public static List<TK_DanhGia> getListTKDanhGia() throws SQLException, ClassNotFoundException {
        List<TK_DanhGia> list = new ArrayList<>();
       String sql = "SELECT"
        
        + " SUM(CASE WHEN D.DIEMDANHGIA = 1 THEN 1 ELSE 0 END) AS SoLuong1Sao,"
        + " SUM(CASE WHEN D.DIEMDANHGIA = 2 THEN 1 ELSE 0 END) AS SoLuong2Sao,"
        + " SUM(CASE WHEN D.DIEMDANHGIA = 3 THEN 1 ELSE 0 END) AS SoLuong3Sao,"
        + " SUM(CASE WHEN D.DIEMDANHGIA = 4 THEN 1 ELSE 0 END) AS SoLuong4Sao,"
        + " SUM(CASE WHEN D.DIEMDANHGIA = 5 THEN 1 ELSE 0 END) AS SoLuong5Sao"
        + " FROM DANHGIA d"
        
        + " ORDER BY d.NGAYTAO";


        try (
                Connection conn = ConnectionUtils.getMyConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery();) {
             java.sql.Date ngay = new java.sql.Date(System.currentTimeMillis());
            while (rs.next()) {
                
                int soLuong1Sao = rs.getInt("SOLUONG1SAO");
                int soLuong2Sao = rs.getInt("SOLUONG2SAO");
                int soLuong3Sao = rs.getInt("SOLUONG3SAO");
                int soLuong4Sao = rs.getInt("SOLUONG4SAO");
                int soLuong5Sao = rs.getInt("SOLUONG5SAO");

                int tongLuotDanhGia = soLuong1Sao + soLuong2Sao + soLuong3Sao + soLuong4Sao + soLuong5Sao;

                TK_DanhGia danhgia = new TK_DanhGia(
                        ngay,
                        tongLuotDanhGia,
                        soLuong1Sao,
                        soLuong2Sao,
                        soLuong3Sao,
                        soLuong4Sao,
                        soLuong5Sao
                );

                list.add(danhgia);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    static public void main(String[] args) {
        try {
            List<TK_DonHang> list = getListTKDonHang();
            for (TK_DonHang item : list) {
                System.out.println("Ngày: " + item.getNgay());
                System.out.println("Tổng số đơn hàng: " + item.getTongSoDonHang());
                System.out.println("Số lượng đã giao: " + item.getSoLuongDaGiao());
                System.out.println("Số lượng giao thất bại: " + item.getSoLuongThatBai());
                System.out.println("Số lượng đã hủy: " + item.getSoLuongDaHuy());
                System.out.println("----------------------------------------");
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ThongKeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
