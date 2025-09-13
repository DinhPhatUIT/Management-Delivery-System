/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appgiaovan.ManagerGUI;

import appgiaovan.Entity.NhanVienKho;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author pc
 */
public class NhanVienKhoTableModel extends AbstractTableModel {
    private final List<NhanVienKho> data;
    private final String[] cols = {
        "Mã NV Kho", "Họ Tên", "SĐT", "Email", "CCCD", "Ngày sinh", "Giới tính", "Địa chỉ", "ID_Kho", "ID_QuanLy", "Mức lương"
    };
    private final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    public NhanVienKhoTableModel(List<NhanVienKho> data) {
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
        NhanVienKho nv = data.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> nv.getID_NguoiDung();
            case 1 -> nv.getHoTen();
            case 2 -> nv.getSDT();
            case 3 -> nv.getEmail();
            case 4 -> nv.getCCCD();
            case 5 -> nv.getNgaySinh() != null ? df.format(nv.getNgaySinh()) : "";
            case 6 -> nv.getGioiTinh();
            case 7 -> nv.getDiaChi();
            case 8 -> nv.getID_Kho();
            case 9 -> nv.getID_QuanLy();
            case 10 -> nv.getMucLuong();
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
