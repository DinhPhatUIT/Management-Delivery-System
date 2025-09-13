/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appgiaovan.ManagerGUI;

import appgiaovan.Entity.NhanVienGiaoHang;
import java.util.List;
import java.text.SimpleDateFormat;
import javax.swing.table.AbstractTableModel;
/**
 *
 * @author pc
 */



public class NhanVienGiaoHangTableModel extends AbstractTableModel {
    private final List<NhanVienGiaoHang> data;
    private final String[] cols = {
        "Mã Shipper", "Họ Tên", "SĐT", "Email", "CCCD", "Ngày sinh", "Giới tính", "Địa chỉ", "ID_Kho", "ID_QuanLy", "Điểm đánh giá"
    };
    private final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    public NhanVienGiaoHangTableModel(List<NhanVienGiaoHang> data) {
        this.data = data;
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return cols.length;
    }

    @Override
    public String getColumnName(int column) {
        return cols[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        NhanVienGiaoHang sh = data.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> sh.getID_NguoiDung();
            case 1 -> sh.getHoTen();
            case 2 -> sh.getSDT();
            case 3 -> sh.getEmail();
            case 4 -> sh.getCCCD();
            case 5 -> sh.getNgaySinh() != null ? df.format(sh.getNgaySinh()) : "";
            case 6 -> sh.getGioiTinh();
            case 7 -> sh.getDiaChi();
            case 8 -> sh.getID_Kho();
            case 9 -> sh.getID_QuanLy();
            case 10 -> sh.getDiemDanhGia();
            default -> null;
        };
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == 0) return Integer.class;
        if (columnIndex == 6) return Character.class;
        return String.class;
    }
}
