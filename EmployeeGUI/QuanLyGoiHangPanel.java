package appgiaovan.EmployeeGUI;

import appgiaovan.Controller.QLGHController;
import appgiaovan.Entity.GoiHang;
import appgiaovan.Entity.NhanVienKho;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class QuanLyGoiHangPanel extends JPanel {

    private TopPanelQLGH topPanel;
    private TableDonHang listOrder;
    private NhanVienKho nhanVienKho;

    private QLGHController controller = new QLGHController();

    public QuanLyGoiHangPanel(NhanVienKho nvKho) throws SQLException, ClassNotFoundException, Exception {
        this.nhanVienKho = nvKho;
        this.setLayout(new BorderLayout());
        initUI();
    }

    private void initUI() throws SQLException, ClassNotFoundException, Exception {
        // Panel Menu
        topPanel = new TopPanelQLGH();
        // main
        JPanel mainPanel = new JPanel(new BorderLayout());

        // thanh filter
        mainPanel.add(topPanel, BorderLayout.NORTH);

        String[] columns = GoiHang.getTableHeaders();
        Object[][] data = new Object[0][columns.length];
        listOrder = new TableDonHang(columns, data);
        mainPanel.add(listOrder, BorderLayout.CENTER);

        this.add(mainPanel, BorderLayout.CENTER);

        topPanel.getaddButton().addActionListener(e -> {
            try {
                ThemGoiHang();
            } catch (SQLException ex) {
                Logger.getLogger(QuanLyDonHangPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(QuanLyDonHangPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(QuanLyDonHangPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        topPanel.getfilterButton().addActionListener(e -> {
            try {
                GoiHang goiHang = topPanel.getGoiHang();
                HienThiDSGoiHang(goiHang);
            } catch (SQLException ex) {
                Logger.getLogger(QuanLyDonHangPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(QuanLyDonHangPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(QuanLyDonHangPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        topPanel.getUpdateButton().addActionListener(e -> {
            try {
                XuLyHoanThanhGoiHang();
            } catch (SQLException ex) {
                Logger.getLogger(QuanLyDonHangPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(QuanLyDonHangPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(QuanLyDonHangPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        HienThiDSGoiHangTheoKho();
    }

    void HienThiDSGoiHangTheoKho() throws SQLException, ClassNotFoundException {
        java.util.List<GoiHang> dsGoiHang = controller.LayDSGoiHangTheoKho(nhanVienKho.getID_Kho());
        String[] columns = GoiHang.getTableHeaders();
        Object[][] data = new Object[dsGoiHang.size()][columns.length];

        for (int i = 0; i < dsGoiHang.size(); i++) {
            data[i] = dsGoiHang.get(i).toTableRow();
        }

        listOrder.setTableData(data);
    }

    void HienThiDSGoiHang(GoiHang goiHang) throws SQLException, ClassNotFoundException {
        java.util.List<GoiHang> dsGoiHang = controller.LayDSGoiHangTheoKho(goiHang, nhanVienKho.getID_Kho());
        String[] columns = GoiHang.getTableHeaders();
        Object[][] data = new Object[dsGoiHang.size()][columns.length];

        for (int i = 0; i < dsGoiHang.size(); i++) {
            data[i] = dsGoiHang.get(i).toTableRow();
        }

        listOrder.setTableData(data);
    }

    public void ThemGoiHang() throws SQLException, ClassNotFoundException {
        ThemGoiHangFrame themGoiHang = new ThemGoiHangFrame(() -> {
            try {
                HienThiDSGoiHangTheoKho();
            } catch (SQLException ex) {
                Logger.getLogger(QuanLyGoiHangPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(QuanLyGoiHangPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }, nhanVienKho);
        themGoiHang.setVisible(true);
    }
    
    public void XuLyHoanThanhGoiHang() throws SQLException, ClassNotFoundException{
        for (int i = 0; i < listOrder.getRowCount(); i++) {
            Boolean isChecked = (Boolean) listOrder.getValueAt(i, 0); // Cột 0 là checkbox
            if (Boolean.TRUE.equals(isChecked)) {
                // Lấy thông tin dòng được chọn
                Integer maGoiHang = (Integer) listOrder.getValueAt(i, 1); // cột 1: mã ĐH
                System.out.println(maGoiHang);

                // Gọi hàm xử lý
                controller.HoanThanhGoiHang(maGoiHang);
                JOptionPane.showMessageDialog(this, "Xác nhận gói hàng thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                HienThiDSGoiHangTheoKho();
            }
        }
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException ex) {
            System.err.println("Không thể cài đặt FlatLaf");
        }
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Quản Lý Gói Hàng");
            try {
                frame.setContentPane(new QuanLyGoiHangPanel(new NhanVienKho()));
            } catch (SQLException ex) {
                Logger.getLogger(QuanLyGoiHangPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(QuanLyGoiHangPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(QuanLyGoiHangPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            frame.setSize(1300, 600);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
