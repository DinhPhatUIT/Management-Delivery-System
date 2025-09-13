package appgiaovan.EmployeeGUI;

import appgiaovan.ConnectDB.ConnectionUtils;
import appgiaovan.Controller.QLDonHangController;
import appgiaovan.DAO.DonHangDAO;
import appgiaovan.Entity.DonHang;
import appgiaovan.Entity.KhoHang;
import appgiaovan.GUI.Components.DiaChiPanel;
import appgiaovan.GUI.Components.RoundedButton;

import appgiaovan.GUI.Components.TimeWeather;
import java.sql.Connection;

import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;



public class SuaDonHangFrame extends JFrame {

    // --- Fields ---
    private final QLDonHangController controller = new QLDonHangController();
    private final DonHangDAO donHangDAO = new DonHangDAO();

    private JTextField txtMaDon = new JTextField("");
    private JTextField txtSDTNguoiGui = new JTextField("");
    private JTextField txtTenNguoiGui = new JTextField("");
    private JTextField txtKhoTiepNhan;
    private JTextField txtSDTNguoiNhan = new JTextField("");
    private JTextField txtTenNguoiNhan = new JTextField("");
    private JTextField txtDiaChiNhan = new JTextField("");

    private JComboBox cbLoaiDichVu;
    private JComboBox cbLoaiHang;

    private RoundedButton btnSuaDonHang = new RoundedButton("Sửa đơn hàng");
    private RoundedButton btnCommit = new RoundedButton("Commit");
    private DiaChiPanel diaChiPanel;

    private int idDonHang;
    private Connection conn = null;
    private Runnable onSuccess;

    public SuaDonHangFrame(int idDonHang, Runnable onSuccess) throws SQLException, ClassNotFoundException, Exception {
        this.idDonHang = idDonHang;
        this.onSuccess = onSuccess;

        setTitle("Sửa Đơn Hàng");
        setSize(920, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center on screen

        initComponents(); 

        HienThiDonHang(idDonHang);

        btnSuaDonHang.addActionListener(e -> {
            try {   
               // if(conn == null || conn.isClosed()){
                    conn = (Connection) ConnectionUtils.getMyConnection();
                    conn.setAutoCommit(false); 
//                    conn.setTransactionIsolation(java.sql.Connection.TRANSACTION_SERIALIZABLE); // để mô phỏng lỗi
                    conn.setTransactionIsolation(java.sql.Connection.TRANSACTION_READ_COMMITTED); // để mô phỏng lỗi

                //}
                SuaDonHang(idDonHang, onSuccess);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCommit.addActionListener(e -> Commit());

        setVisible(true);
    }

    
    private void initComponents() throws SQLException, ClassNotFoundException, Exception {
        JPanel mainPanel = new JPanel(null);
        mainPanel.setBackground(Color.WHITE);

        JLabel lblBenGui = new JLabel("Bên gửi");
        lblBenGui.setFont(new Font("Arial", Font.BOLD, 14));
        lblBenGui.setBounds(20, 20, 100, 25);
        mainPanel.add(lblBenGui);

        txtMaDon.setFocusable(false);
        txtMaDon.setBorder(BorderFactory.createTitledBorder("Mã đơn hàng"));
        txtMaDon.setBounds(20, 50, 200, 50);
        txtMaDon.setFont(new Font("Arial", Font.BOLD, 16));
        mainPanel.add(txtMaDon);

        txtSDTNguoiGui.setBorder(BorderFactory.createTitledBorder("SĐT Người Gửi *"));
        txtSDTNguoiGui.setBounds(240, 50, 200, 50);
        mainPanel.add(txtSDTNguoiGui);

        txtTenNguoiGui.setBorder(BorderFactory.createTitledBorder("Tên Người Gửi *"));
        txtTenNguoiGui.setBounds(460, 50, 200, 50);
        mainPanel.add(txtTenNguoiGui);

        txtKhoTiepNhan = new JTextField();
        txtKhoTiepNhan.setBorder(BorderFactory.createTitledBorder("Kho tiếp nhận"));
        txtKhoTiepNhan.setBounds(680, 50, 200, 50);
        txtKhoTiepNhan.setFocusable(false);
        mainPanel.add(txtKhoTiepNhan);

        JSeparator separator = new JSeparator();
        separator.setBounds(20, 120, 820, 10);
        mainPanel.add(separator);

        JLabel lblBenNhan = new JLabel("Bên nhận");
        lblBenNhan.setFont(new Font("Arial", Font.BOLD, 14));
        lblBenNhan.setBounds(20, 130, 100, 25);
        mainPanel.add(lblBenNhan);

        txtSDTNguoiNhan.setBorder(BorderFactory.createTitledBorder("SĐT Người Nhận *"));
        txtSDTNguoiNhan.setBounds(20, 160, 200, 50);
        mainPanel.add(txtSDTNguoiNhan);

        txtTenNguoiNhan.setBorder(BorderFactory.createTitledBorder("Tên Người Nhận *"));
        txtTenNguoiNhan.setBounds(240, 160, 200, 50);
        mainPanel.add(txtTenNguoiNhan);

        txtDiaChiNhan.setBorder(BorderFactory.createTitledBorder("Địa Chỉ Nhận *"));
        txtDiaChiNhan.setBounds(460, 160, 300, 50);
        mainPanel.add(txtDiaChiNhan);

        diaChiPanel = new DiaChiPanel();
        diaChiPanel.setBounds(20, 230, 500, 50);
        mainPanel.add(diaChiPanel);

        String[] dsDichVu = donHangDAO.DSDichVu();
        cbLoaiDichVu = new JComboBox(dsDichVu);
        cbLoaiDichVu.setBorder(BorderFactory.createTitledBorder("Loại Dịch Vụ *"));
        cbLoaiDichVu.setBounds(20, 300, 150, 50);
        mainPanel.add(cbLoaiDichVu);

        String[] dsLoaiHang = donHangDAO.DSLoaiHang();
        cbLoaiHang = new JComboBox(dsLoaiHang);
        cbLoaiHang.setBorder(BorderFactory.createTitledBorder("Loại Hàng Hóa *"));
        cbLoaiHang.setBounds(200, 300, 300, 50);
        mainPanel.add(cbLoaiHang);

        btnSuaDonHang.setBounds((880 - 200 - 150) / 2, 440, 150, 45);
        btnSuaDonHang.setBackground(new Color(0x007BFF));
        mainPanel.add(btnSuaDonHang);
        btnCommit.setBounds(500, 440, 150, 45);
        btnCommit.setBackground(new Color(0x28A745));
        mainPanel.add(btnCommit);

        TimeWeather CustomerTimeWeather = new TimeWeather("Ho Chi Minh 30 độ");
        mainPanel.add(CustomerTimeWeather, BorderLayout.NORTH);

        add(mainPanel, BorderLayout.CENTER);
    }

    public void HienThiDonHang(int idDonHang) throws SQLException, ClassNotFoundException {
        DonHang dh = donHangDAO.LayThongTinDonHang(idDonHang);
        txtMaDon.setText(String.valueOf(idDonHang));
        txtSDTNguoiGui.setText(dh.getSdtNguoiGui());
        txtTenNguoiGui.setText(dh.getTenNguoiGui());
        txtSDTNguoiNhan.setText(dh.getSdtNguoiNhan());
        txtTenNguoiNhan.setText(dh.getTenNguoiNhan());
        txtDiaChiNhan.setText(dh.getDiaChiNhan());

        List<KhoHang> listKho = controller.LayThongTinKho();

        String tenKho = null;
        for (KhoHang kho : listKho) {
            if (kho.getIdKho() == dh.getIdKhoTiepNhan()) {
                tenKho = kho.getTenKho();
                break;
            }
        }
        txtKhoTiepNhan.setText(tenKho);

    }

    public void HienThiMaDonHang() throws SQLException, ClassNotFoundException {
        DonHangDAO donHangDAO = new DonHangDAO();
        int maDon = donHangDAO.LayMaDon();
        this.txtMaDon.setText(String.valueOf(maDon)); // chuyển int sang String

    }

    public void SuaDonHang(int idDonHang, Runnable onSuccess) throws SQLException, ClassNotFoundException {
        // Lấy dữ liệu từ các trường

        String sdtNguoiGui = txtSDTNguoiGui.getText().trim();
        String tenNguoiGui = txtTenNguoiGui.getText().trim();

        String tenKho = txtKhoTiepNhan.getText().trim();
        List<KhoHang> listKho = controller.LayThongTinKho();

        Integer idKho = null;
        for (KhoHang kho : listKho) {
            if (kho.getTenKho().equals(tenKho)) {
                idKho = kho.getIdKho();
                break;
            }
        }

        String sdtNguoiNhan = txtSDTNguoiNhan.getText().trim();
        String tenNguoiNhan = txtTenNguoiNhan.getText().trim();
        String diaChiNhan = txtDiaChiNhan.getText().trim();
//        String quanHuyen = (String) cbQuanHuyen.getSelectedItem();
//        String phuongXa = (String) cbPhuongXa.getSelectedItem();

        String loaiDichVu = (String) cbLoaiDichVu.getSelectedItem();
        String loaiHang = (String) cbLoaiHang.getSelectedItem();

        // Gộp địa chỉ chi tiết
//        String diaChiDayDu = diaChiNhan + ", " + phuongXa + ", " + quanHuyen;
        // Tạo đối tượng DonHang
        DonHang dh = new DonHang();
        dh.setIdDonHang(idDonHang);
        dh.setSdtNguoiGui(sdtNguoiGui);
        dh.setSdtNguoiNhan(sdtNguoiNhan);
        dh.setTenNguoiGui(tenNguoiGui);
        dh.setTenNguoiNhan(tenNguoiNhan);
        dh.setDiaChiNhan(diaChiNhan);
        dh.setDichVu(loaiDichVu);
        dh.setLoaiHangHoa(loaiHang);
        dh.setIdKhoTiepNhan(idKho);
        if (!controller.KiemTraDinhDang(dh)) {
            JOptionPane.showMessageDialog(this, "Định dạng đơn hàng không hợp lệ."
                    + " Vui lòng kiểm tra lại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return; // Dừng lại, không thực hiện thêm
        }
        // Gọi controller để thêm đơn hàng

        controller.SuaDonHang(dh, conn);
        // Gọi callback
//        JOptionPane.showMessageDialog(this, "Sửa đơn hàng thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
//        onSuccess.run();
//
//        dispose();
    }

    public void Commit() {
        try {
            conn.commit();
            conn.close();
            JOptionPane.showMessageDialog(this, "Cập nhật thành công!");

            onSuccess.run();
            dispose();
        } catch (SQLException ex) {
            if (ex.getErrorCode() == 8177) { // ORA-08177: can't serialize access
                JOptionPane.showMessageDialog(this, "Xung đột dữ liệu (Serializable)! Vui lòng tải lại đơn hàng.");
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi khi commit: " + ex.getMessage());
            }
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ignored) {
            }
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            SuaDonHangFrame frame = null;
            try {
                frame = new SuaDonHangFrame(63, () -> System.out.println("Cập nhật danh sách!"));
            } catch (SQLException ex) {
                Logger.getLogger(SuaDonHangFrame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(SuaDonHangFrame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(SuaDonHangFrame.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
    }
}
