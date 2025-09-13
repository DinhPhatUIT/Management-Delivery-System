package appgiaovan.EmployeeGUI;
import appgiaovan.Controller.QLGHController;
import appgiaovan.Entity.*;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ThemGoiHangFrame extends JFrame {

    
    private QLGHController controller = new QLGHController();
    private TableDonHang listOrder;
    private TopPanelTGH topPanel;
    private NhanVienKho nhanVienKho;

    public ThemGoiHangFrame(Runnable onSuccess, NhanVienKho nvkho) throws SQLException, ClassNotFoundException {
        this.nhanVienKho = nvkho;
        setTitle("Thêm Gói Hàng");
        setSize(1300, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI(onSuccess);
    }

    private void initUI(Runnable onSuccess) throws SQLException, ClassNotFoundException {

        //main
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        //thanh filter
        topPanel = new TopPanelTGH();
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Panel danh sách
        String[] columns = DonHang.getTableHeaders();
        Object[][] data = new Object[0][columns.length];

        listOrder = new TableDonHang(columns, data);
        mainPanel.add(listOrder, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);
        topPanel.getAddButton().addActionListener(e -> {
            try {
                ThemGoiHang(onSuccess);
            } catch (SQLException ex) {
                Logger.getLogger(QuanLyDonHangPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(QuanLyDonHangPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(QuanLyDonHangPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        HienThiDSDonHangDangXuLy();
    }

    public void ThemGoiHang(Runnable onSuccess) throws SQLException, ClassNotFoundException {
        List<Integer> listDonHang = new ArrayList<>();
        System.out.println("hello");
        for (int i = 0; i < listOrder.getRowCount(); i++) {
            Boolean isChecked = (Boolean) listOrder.getValueAt(i, 0); // Cột 0 là checkbox
            if (Boolean.TRUE.equals(isChecked)) {
                // Lấy thông tin dòng được chọn
                Integer maDonHang = (Integer) listOrder.getValueAt(i, 1); // cột 1: mã ĐH
                System.out.println(maDonHang);
                listDonHang.add(maDonHang);

            }
        }

        GoiHang goiHang = new GoiHang();
        KhoHang khoHang = topPanel.getKhoHangDen();
        goiHang.setIdKhoHangDen(khoHang.getIdKho());
        goiHang.setIdKhoHangGui(nhanVienKho.getID_Kho());
        goiHang.setIdNhanVien(nhanVienKho.getID_NguoiDung());
        controller.ThemGoiHang(goiHang, listDonHang);
        JOptionPane.showMessageDialog(this, "Thêm gói hàng thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
        onSuccess.run();
        this.dispose();
    }

    public void HienThiDSDonHangDangXuLy() throws SQLException, ClassNotFoundException {
        DonHang donHang = new DonHang();
        donHang.setIdKhoTiepNhan(nhanVienKho.getID_Kho());
        donHang.setTrangThai("Đang xử lý");
        List<DonHang> dsDonHang = controller.LayDSDonHang(donHang);
        String[] columns = DonHang.getTableHeaders();
        Object[][] data = new Object[dsDonHang.size()][columns.length];

        for (int i = 0; i < dsDonHang.size(); i++) {
            data[i] = dsDonHang.get(i).toTableRow();
        }

        listOrder.setTableData(data);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException ex) {
            System.err.println("Không thể cài đặt FlatLaf");
        }
        SwingUtilities.invokeLater(() -> {
            try {
                NhanVienKho nvkho = new NhanVienKho();
                nvkho.setID_NguoiDung(1);
                new ThemGoiHangFrame(() -> System.out.println("Thêm gói hàng!"), nvkho).setVisible(true);
            } catch (SQLException ex) {
                Logger.getLogger(ThemGoiHangFrame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ThemGoiHangFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
}
