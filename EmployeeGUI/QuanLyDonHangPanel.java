package appgiaovan.EmployeeGUI;

import appgiaovan.ConnectDB.ConnectionUtils;
import appgiaovan.Controller.QLDonHangController;
import appgiaovan.Entity.DonHang;
import appgiaovan.Entity.NhanVienKho;
import com.formdev.flatlaf.FlatLightLaf;
import java.sql.Connection;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class QuanLyDonHangPanel extends JPanel {

    private final QLDonHangController controller = new QLDonHangController();
    private TableDonHang listOrder;
    private TopPanelQLDH topPanel;
    private NhanVienKho nhanVienKho;
    private Connection conn = null;

    public QuanLyDonHangPanel(NhanVienKho nvKho) throws SQLException, ClassNotFoundException {
        
        this.nhanVienKho = nvKho;

        setLayout(new BorderLayout());

        // Panel chính
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Panel lọc (filter)
        topPanel = new TopPanelQLDH();
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Khởi tạo bảng rỗng ban đầu
        String[] columns = DonHang.getTableHeaders();
        Object[][] data = new Object[0][columns.length];

        listOrder = new TableDonHang(columns, data);
        mainPanel.add(listOrder, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);

        //Gán sự kiện thêm đơn hàng
        topPanel.getaddButton().addActionListener(e -> {

            try {
                ThemDonHang();
            } catch (SQLException ex) {
                Logger.getLogger(QuanLyDonHangPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(QuanLyDonHangPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(QuanLyDonHangPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        //Gán sự kiện tìm kiếm đơn hàng
        topPanel.getfilterButton().addActionListener(e -> {
            try {
                DonHang dh = topPanel.getDonHang();
                dh.setIdKhoTiepNhan(nhanVienKho.getID_Kho());
                HienThiDanhSach(dh);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(QuanLyDonHangPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        topPanel.getupdateButton().addActionListener(e -> {
            try {
                XuLySuaDonHang();
            } catch (SQLException ex) {
                Logger.getLogger(QuanLyDonHangPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(QuanLyDonHangPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(QuanLyDonHangPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        topPanel.getPhanCongButton().addActionListener(e -> {
            try {
                PhanCongGiaoHang();
            } catch (SQLException ex) {
                Logger.getLogger(QuanLyDonHangPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(QuanLyDonHangPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        topPanel.getDeleteButton().addActionListener(e -> {
            try {
                XuLyHuyDonHang();
            } catch (SQLException ex) {
                Logger.getLogger(QuanLyDonHangPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(QuanLyDonHangPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        topPanel.getRefreshButton().addActionListener(e -> {
            try {
                conn.commit();
                conn.close();
                Connect();
                HienThiDanhSach();
            } catch (SQLException ex) {
                Logger.getLogger(QuanLyDonHangPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(QuanLyDonHangPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        Connect();
        HienThiDanhSach();

    }

public void Connect() throws SQLException, ClassNotFoundException{
    if (conn == null || conn.isClosed()) {
        conn = ConnectionUtils.getMyConnection();
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(java.sql.Connection.TRANSACTION_READ_COMMITTED); // để mô phỏng lỗi
//        conn.setTransactionIsolation(java.sql.Connection.TRANSACTION_SERIALIZABLE);
    }
}
    
    public void XuLyHuyDonHang() throws SQLException, ClassNotFoundException {
        for (int i = 0; i < listOrder.getRowCount(); i++) {
            Boolean isChecked = (Boolean) listOrder.getValueAt(i, 0);
            if (Boolean.TRUE.equals(isChecked)) {
                // Lấy thông tin dòng được chọn
                Integer maDonHang = (Integer) listOrder.getValueAt(i, 1);
                System.out.println(maDonHang);

                controller.HuyDonHang(maDonHang);
                JOptionPane.showMessageDialog(this, "Hủy đơn hàng thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                HienThiDanhSach();
            }
        }
    }

    public void PhanCongGiaoHang() throws SQLException, ClassNotFoundException {
        new PhanCongGiaoHangFrame(nhanVienKho.getID_Kho(), () -> {
            try {
                HienThiDanhSach();
            } catch (SQLException ex) {
                Logger.getLogger(QuanLyDonHangPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(QuanLyDonHangPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).setVisible(true);
    }

    public void XuLySuaDonHang() throws SQLException, ClassNotFoundException, Exception {
        for (int i = 0; i < listOrder.getRowCount(); i++) {
            Boolean isChecked = (Boolean) listOrder.getValueAt(i, 0); // Cột 0 là checkbox
            if (Boolean.TRUE.equals(isChecked)) {
                // Lấy thông tin dòng được chọn
                Integer maDonHang = (Integer) listOrder.getValueAt(i, 1); // cột 1: mã ĐH
                System.out.println(maDonHang);

                SuaDonHang(maDonHang);

            }
        }
    }

    public void SuaDonHang(int idDonHang) throws SQLException, ClassNotFoundException, Exception {
        SuaDonHangFrame suaDonHangFrame = new SuaDonHangFrame(idDonHang, () -> {
            try {
                HienThiDanhSach();
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(QuanLyDonHangPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void ThemDonHang() throws SQLException, ClassNotFoundException, Exception {
        ThemDonHangFrame themDH = new ThemDonHangFrame(() -> {
            try {
                
                HienThiDanhSach();
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(QuanLyDonHangPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }, nhanVienKho.getID_Kho());

    }

    public final void HienThiDanhSach() throws SQLException, ClassNotFoundException {
        DonHang dh = new DonHang();
        dh.setIdKhoTiepNhan(nhanVienKho.getID_Kho());
        HienThiDanhSach(dh);
    }

    public final void HienThiDanhSach(DonHang dh) throws SQLException, ClassNotFoundException {
        List<DonHang> dsDonHang = controller.LayDSDonHang(dh, this.conn);
        String[] columns = DonHang.getTableHeaders();
        Object[][] data = new Object[dsDonHang.size()][columns.length];

        for (int i = 0; i < dsDonHang.size(); i++) {
            data[i] = dsDonHang.get(i).toTableRow();
        }

        listOrder.setTableDataDonHang(dsDonHang);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException ex) {
            System.err.println("Không thể cài đặt FlatLaf");
        }
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Test Quản Lý Đơn Hàng Panel");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1300, 600);
            frame.setLocationRelativeTo(null);
            frame.setLayout(new BorderLayout());

            try {
                NhanVienKho nvkho = new NhanVienKho();

                frame.add(new QuanLyDonHangPanel(new NhanVienKho()), BorderLayout.CENTER);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Không thể kết nối cơ sở dữ liệu!");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(QuanLyDonHangPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            frame.setVisible(true);
        });
    }
}
