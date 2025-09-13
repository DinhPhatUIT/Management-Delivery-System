package appgiaovan.ManagerGUI;

import appgiaovan.Entity.KhachHang;
import javax.swing.table.AbstractTableModel;
import java.text.SimpleDateFormat;
import java.util.List;

public class KhachHangTableModel extends AbstractTableModel {
    private final List<KhachHang> data;
    private final String[] cols = {
        "Mã KH", "Họ Tên", "SĐT", "Email", "CCCD", "Ngày sinh", "Giới tính"
    };
    private final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    public KhachHangTableModel(List<KhachHang> data) {
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
        KhachHang kh = data.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> kh.getID_NguoiDung();
            case 1 -> kh.getHoTen();
            case 2 -> kh.getSDT();
            case 3 -> kh.getEmail();
            case 4 -> kh.getCCCD();
            case 5 -> kh.getNgaySinh() != null ? df.format(kh.getNgaySinh()) : "";
            case 6 -> kh.getGioiTinh();
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
