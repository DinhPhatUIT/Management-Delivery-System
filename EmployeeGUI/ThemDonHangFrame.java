package appgiaovan.EmployeeGUI;

import appgiaovan.Controller.QLDonHangController;
import appgiaovan.DAO.DonHangDAO;
import appgiaovan.Entity.DonHang;
import appgiaovan.Entity.KhoHang;
import appgiaovan.GUI.Components.DiaChiPanel;
import appgiaovan.GUI.Components.RoundedButton;
import appgiaovan.GUI.Components.RoundedComboBox;

import appgiaovan.GUI.Components.RoundedTextField;
import appgiaovan.GUI.Components.TimeWeather;
import appgiaovan.report.HoaDonKH;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class ThemDonHangFrame extends JFrame {

    private JTextField txtMaDon = new JTextField("");
    private DonHangDAO donHangDAO;
    private QLDonHangController controller = new QLDonHangController();
    private DiaChiPanel diaChiPanel;

    public ThemDonHangFrame(Runnable onSucces, int idKho) throws SQLException, ClassNotFoundException, Exception {
        donHangDAO = new DonHangDAO();

        setTitle("Tạo Đơn Hàng");
        setSize(920, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center on screen

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.WHITE);

        // Bên gửi
        JLabel lblBenGui = new JLabel("Bên gửi");
        lblBenGui.setFont(new Font("Arial", Font.BOLD, 14));
        lblBenGui.setBounds(20, 20, 100, 25);
        mainPanel.add(lblBenGui);

        //SDT
        RoundedTextField txtSDTNguoiGui = new RoundedTextField("Nhập số điện thoại người gửi");
        txtSDTNguoiGui.setBorder(BorderFactory.createTitledBorder("SĐT Người Gửi *"));
        txtSDTNguoiGui.setBounds(20, 50, 200, 50);
        mainPanel.add(txtSDTNguoiGui);
        //Ho ten
        RoundedTextField txtTenNguoiGui = new RoundedTextField("Nhập tên người gửi");
        txtTenNguoiGui.setBorder(BorderFactory.createTitledBorder("Tên Người Gửi *"));
        txtTenNguoiGui.setBounds(240, 50, 200, 50);
        mainPanel.add(txtTenNguoiGui);

        List<KhoHang> listKho = controller.LayThongTinKho();
        JTextField txtKhoTiepNhan = new JTextField();
        String tenKho = null;

        for(KhoHang kho : listKho){
            if(kho.getIdKho() == idKho){
                tenKho = kho.getTenKho();
                break;
            }
        }

        txtKhoTiepNhan.setText(tenKho);
        txtKhoTiepNhan.setBorder(BorderFactory.createTitledBorder("Kho tiếp nhận"));
        txtKhoTiepNhan.setBounds(460, 50, 200, 50);
        txtKhoTiepNhan.setFocusable(false);
        mainPanel.add(txtKhoTiepNhan);

        JSeparator separator = new JSeparator();
        separator.setBounds(20, 120, 820, 10);
        mainPanel.add(separator);

        // Bên nhận
        JLabel lblBenNhan = new JLabel("Bên nhận");
        lblBenNhan.setFont(new Font("Arial", Font.BOLD, 14));
        lblBenNhan.setBounds(20, 130, 100, 25);
        mainPanel.add(lblBenNhan);

        RoundedTextField txtSDTNguoiNhan = new RoundedTextField("Nhập số điện thoại người nhận");
        txtSDTNguoiNhan.setBorder(BorderFactory.createTitledBorder("SĐT Người Nhận *"));
        txtSDTNguoiNhan.setBounds(20, 160, 200, 50);
        mainPanel.add(txtSDTNguoiNhan);

        RoundedTextField txtTenNguoiNhan = new RoundedTextField("Nhập tên người nhận");
        txtTenNguoiNhan.setBorder(BorderFactory.createTitledBorder("Tên Người Nhận *"));
        txtTenNguoiNhan.setBounds(240, 160, 200, 50);
        mainPanel.add(txtTenNguoiNhan);

        RoundedTextField txtDiaChiNhan = new RoundedTextField("Nhập địa chỉ người nhận");
        txtDiaChiNhan.setBorder(BorderFactory.createTitledBorder("Địa Chỉ Nhận *"));
        txtDiaChiNhan.setBounds(500, 230, 300, 50);
        mainPanel.add(txtDiaChiNhan);

         diaChiPanel = new DiaChiPanel();
        diaChiPanel.setBounds(20, 230, 500, 50);

        mainPanel.add(this.diaChiPanel);
        String[] dsDichVu = donHangDAO.DSDichVu();
        JComboBox cbLoaiDichVu = new JComboBox(dsDichVu);
        cbLoaiDichVu.setBorder(BorderFactory.createTitledBorder("Loại Dịch Vụ *"));
        cbLoaiDichVu.setBounds(20, 300, 150, 50);
        mainPanel.add(cbLoaiDichVu);

        //Loai Hang
        String[] dsLoaiHang = donHangDAO.DSLoaiHang();
        JComboBox cbLoaiHang = new JComboBox(dsLoaiHang);
        cbLoaiHang.setBorder(BorderFactory.createTitledBorder("Loại Hàng *"));
        cbLoaiHang.setBounds(200, 300, 300, 50);
        mainPanel.add(cbLoaiHang);

        // Nút Xác nhận
        RoundedButton btnTaoDon = new RoundedButton("Tạo đơn hàng");
        btnTaoDon.setBounds((880 - 200 - 150) / 2, 440, 150, 45); // Trừ chiều rộng của menubar
        btnTaoDon.setBackground(new Color(0x007BFF)); // Flat Blue
        mainPanel.add(btnTaoDon);
        //Thêm hình thức thanh toán
        RoundedComboBox cbHinhThucThanhToan = new RoundedComboBox(new String[]{
            "Chọn hình thức thanh toán", "Tiền mặt", "Thanh toán online", "Thanh toán COD"
        });
        cbHinhThucThanhToan.setBorder(BorderFactory.createTitledBorder("Hình Thức Thanh Toán *"));
        cbHinhThucThanhToan.setBounds(20, 370, 300, 50);
        mainPanel.add(cbHinhThucThanhToan);

        add(mainPanel, BorderLayout.CENTER);
        setVisible(true);
        //Thanh Weather
        TimeWeather CustomerTimeWeather = new TimeWeather("Ho Chi Minh 30 độ");
        mainPanel.add(CustomerTimeWeather, BorderLayout.NORTH);

        btnTaoDon.addActionListener(e -> {

            try {

                String sdtNguoiGui = txtSDTNguoiGui.getText().trim();
                String tenNguoiGui = txtTenNguoiGui.getText().trim();

                String sdtNguoiNhan = txtSDTNguoiNhan.getText().trim();
                String tenNguoiNhan = txtTenNguoiNhan.getText().trim();
                String diaChiNhan = txtDiaChiNhan.getText().trim();
                String tinhThanh = (String) diaChiPanel.getSelectedTinh();
                String quanHuyen = (String) diaChiPanel.getSelectedHuyen();
                String phuongXa = (String) diaChiPanel.getSelectedXa();

                String loaiDichVu = (String) cbLoaiDichVu.getSelectedItem();
                String loaiHang = (String) cbLoaiHang.getSelectedItem();
                String hinhThucThanhToan = (String) cbHinhThucThanhToan.getSelectedItem();

                String diaChiDayDu = diaChiNhan + ", " + phuongXa + ", " + quanHuyen + ", " + tinhThanh;

                // Tạo đối tượng DonHang
                DonHang dh = new DonHang();
                dh.setSdtNguoiGui(sdtNguoiGui);
                dh.setSdtNguoiNhan(sdtNguoiNhan);
                dh.setTenNguoiGui(tenNguoiGui);
                dh.setTenNguoiNhan(tenNguoiNhan);
                dh.setDiaChiNhan(diaChiDayDu);
                dh.setDichVu(loaiDichVu);
                dh.setLoaiHangHoa(loaiHang);
                dh.setIdKhoTiepNhan(idKho);
                if (!controller.KiemTraDinhDang(dh)) {
                    JOptionPane.showMessageDialog(this, "Định dạng đơn hàng không hợp lệ. Vui lòng kiểm tra lại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return; 
                }
                int id_dh = controller.ThemDonHang(dh);
                HoaDonKH hd = new HoaDonKH();
                JOptionPane.showMessageDialog(this, "Tạo đơn hàng thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                onSucces.run();
                hd.XuatHD(id_dh);

                dispose();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }

        });

    }

    public void ThemDonHang() {

    }

    public void HienThiMaDonHang() throws SQLException, ClassNotFoundException {
        DonHangDAO donHangDAO = new DonHangDAO();
        int maDon = donHangDAO.LayMaDon();
        this.txtMaDon.setText(String.valueOf(maDon)); 
        System.out.println(maDon);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            ThemDonHangFrame frame = null;
            try {
                frame = new ThemDonHangFrame(() -> System.out.println("Cập nhật danh sách!"), 1);
            } catch (SQLException ex) {
                Logger.getLogger(ThemDonHangFrame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ThemDonHangFrame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(ThemDonHangFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
}
