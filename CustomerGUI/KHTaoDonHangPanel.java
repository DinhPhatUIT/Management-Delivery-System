package appgiaovan.CustomerGUI;

import appgiaovan.Controller.QLDonHangController;
import appgiaovan.DAO.DonHangDAO;
import appgiaovan.DAO.KhachHangDAO;
import appgiaovan.EmailSender;
import appgiaovan.Entity.DonHang;
import appgiaovan.Entity.KhachHang;
import appgiaovan.Entity.KhoHang;
import appgiaovan.GUI.Components.DiaChiPanel;
import appgiaovan.GUI.Components.RoundedButton;
import appgiaovan.GUI.Components.RoundedComboBox;
import appgiaovan.GUI.Components.RoundedTextField;
import appgiaovan.report.HoaDonKH;
import java.awt.*;
import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;


public class KHTaoDonHangPanel extends JPanel {
        private DonHangDAO donHangDAO= new DonHangDAO();
        private KhachHangDAO khachHangDAO= new KhachHangDAO();
        private QLDonHangController qLDonHangController = new QLDonHangController();
        private DiaChiPanel diaChiPanel;
    public KHTaoDonHangPanel(int ID_KhachHang) throws SQLException, ClassNotFoundException, Exception {

        setLayout(new BorderLayout());
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.WHITE);
        KhachHang kh=khachHangDAO.layThongTinKhachHang(ID_KhachHang);
        // Bên gửi
        JLabel lblBenGui = new JLabel("Bên gửi");
        lblBenGui.setFont(new Font("Arial", Font.BOLD, 14));
        lblBenGui.setBounds(20, 20, 100, 25);
        mainPanel.add(lblBenGui);
        //SDT
        RoundedTextField txtSDTNguoiGui = new RoundedTextField(kh.getSDT());
        txtSDTNguoiGui.setBorder(BorderFactory.createTitledBorder("SĐT Người Gửi *"));
        txtSDTNguoiGui.setFocusable(false);
        txtSDTNguoiGui.setBounds(20, 50, 200, 50);
        mainPanel.add(txtSDTNguoiGui);
        //Ho ten
        RoundedTextField txtTenNguoiGui = new RoundedTextField(kh.getHoTen());
        txtTenNguoiGui.setBorder(BorderFactory.createTitledBorder("Tên Người Gửi *"));
        txtTenNguoiGui.setFocusable(false);
        txtTenNguoiGui.setBounds(240, 50, 200, 50);
        mainPanel.add(txtTenNguoiGui);

        List<KhoHang> listKho  = qLDonHangController.LayThongTinKho(); 
        JComboBox cbKhoTiepNhan = new RoundedComboBox();

        for (KhoHang kho : listKho) {
            cbKhoTiepNhan.addItem(kho);
        }

        cbKhoTiepNhan.setBorder(BorderFactory.createTitledBorder("Kho tiếp nhận"));
        cbKhoTiepNhan.setBounds(680, 50, 200, 50);
        mainPanel.add(cbKhoTiepNhan);
        
        cbKhoTiepNhan.setBorder(BorderFactory.createTitledBorder("Kho tiếp nhận"));
        cbKhoTiepNhan.setBounds(460, 50, 200, 50);
        mainPanel.add(cbKhoTiepNhan);

        JSeparator separator = new JSeparator();
        separator.setBounds(20, 120, 1800, 10);
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
        diaChiPanel.setBounds(20, 230, 500, 50); // Điều chỉnh lại vị trí và kích thước phù hợp
        mainPanel.add(this.diaChiPanel);

        String[] dsDichVu = donHangDAO.DSDichVu();
        RoundedComboBox cbLoaiDichVu = new RoundedComboBox(dsDichVu);
        cbLoaiDichVu.setBorder(BorderFactory.createTitledBorder("Loại Dịch Vụ *"));
        cbLoaiDichVu.setBounds(20, 300, 170, 50);
        mainPanel.add(cbLoaiDichVu);

        //Loai Hang
        String[] dsLoaiHang = donHangDAO.DSLoaiHang();
        RoundedComboBox cbLoaiHang = new RoundedComboBox(dsLoaiHang);
        cbLoaiHang.setBorder(BorderFactory.createTitledBorder("Loại Hàng Hóa *"));
        cbLoaiHang.setBounds(220, 300, 300, 50);
        mainPanel.add(cbLoaiHang);
        
        // Nút Tạo đơn hàng
        RoundedButton btnTaoDon = new RoundedButton("Tạo đơn hàng");
        btnTaoDon.setBounds((880 - 200 - 150) / 2, 440, 150, 45); 
        btnTaoDon.setBackground(new Color(0x007BFF)); 
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
        
        btnTaoDon.addActionListener(e->{
            String sdtNguoiGui = txtSDTNguoiGui.getText().trim();
                String tenNguoiGui = txtTenNguoiGui.getText().trim();

                KhoHang selectedKho = (KhoHang) cbKhoTiepNhan.getSelectedItem();
                int idKho = selectedKho.getIdKho();
                System.out.print(idKho);

                String sdtNguoiNhan = txtSDTNguoiNhan.getText().trim();
                String tenNguoiNhan = txtTenNguoiNhan.getText().trim();
                String diaChiNhan = txtDiaChiNhan.getText().trim();
                String tinhThanh = (String) diaChiPanel.getSelectedTinh();
                String quanHuyen = (String) diaChiPanel.getSelectedHuyen();
                String phuongXa = (String) diaChiPanel.getSelectedXa();

                String loaiDichVu = (String) cbLoaiDichVu.getSelectedItem();
                String loaiHang = (String) cbLoaiHang.getSelectedItem();
                String hinhThucThanhToan = (String) cbHinhThucThanhToan.getSelectedItem();
                double tienCOD=0;
                
                String diaChiDayDu = diaChiNhan + ", " + phuongXa + ", " + quanHuyen + ", " + tinhThanh;
                if(hinhThucThanhToan.equals("Thanh toán COD")){
                    String input = JOptionPane.showInputDialog(null, "Nhập số tiền COD:", "Thông tin COD", JOptionPane.PLAIN_MESSAGE);

                    if (input != null && !input.trim().isEmpty()) {
                        try {
                            tienCOD = Double.parseDouble(input.trim());
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Vui lòng nhập đúng định dạng số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Bạn chưa nhập số tiền COD!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                    }    
                }
                else if(hinhThucThanhToan.equals("Thanh toán online")){
                    double phiDichVu = 0;
                    double phiLoaiHang = 0;
                    double phi=0;
                    // Tính phí dịch vụ
                    if (loaiDichVu == null) {
                        loaiDichVu = "";
                    }
                    switch (loaiDichVu.toLowerCase()) {
                        case "tiết kiệm":
                            phiDichVu = 10000;
                            break;
                        case "nhanh":
                            phiDichVu = 15000;
                            break;
                        case "hỏa tốc":
                            phiDichVu = 30000;
                            break;
                        default:
                            phiDichVu = 0;
                            break;
                    }

                    // Tính phí loại hàng hóa
                    if (loaiHang == null) {
                        loaiHang = "";
                    }
                    switch (loaiHang.toLowerCase()) {
                        case "bình thường":
                            phiLoaiHang = 25000;
                            break;
                        case "dễ vỡ":
                            phiLoaiHang = 30000;
                            break;
                        case "cồng kềnh":
                            phiLoaiHang = 50000;
                            break;
                        default:
                            phiLoaiHang = 0;
                            break;
                    }
                    phi=phiDichVu+phiLoaiHang;
                    // Tạo dialog
                    JDialog qrDialog = new JDialog((Frame) null, "Quét mã QR để thanh toán", true);
                    qrDialog.setLayout(new BorderLayout());
                    //Thêm label phí
                    JLabel phiLabel = new JLabel("Số tiền cần thanh toán: " + String.format("%,.0f", phi) + " VND");
                    phiLabel.setHorizontalAlignment(JLabel.CENTER);
                    phiLabel.setFont(new Font("Arial", Font.BOLD, 16));
                    phiLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
                    qrDialog.add(phiLabel, BorderLayout.NORTH);
                    // Thêm ảnh mã QR
                    URL qrUrl = getClass().getResource("/images/QRCODE.jpg");
                    System.out.println("QR URL: " + qrUrl);

                    if (qrUrl != null) {
                        ImageIcon originalIcon = new ImageIcon(qrUrl);
                        Image scaledImage = originalIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                        ImageIcon scaledIcon = new ImageIcon(scaledImage);

                        JLabel qrLabel = new JLabel(scaledIcon);
                        qrLabel.setHorizontalAlignment(JLabel.CENTER);
                        qrDialog.add(qrLabel, BorderLayout.CENTER);
                    } else {
                        JLabel qrLabel = new JLabel("QR CODE");
                        qrLabel.setHorizontalAlignment(JLabel.CENTER);
                        qrLabel.setForeground(Color.RED);
                        qrLabel.setFont(new Font("Arial", Font.BOLD, 16));
                        qrDialog.add(qrLabel, BorderLayout.CENTER);
                    }


                    // Thêm nút OK
                    JButton okButton = new JButton("Đã chuyển khoản");
                    okButton.addActionListener(ex -> {
                        qrDialog.dispose();

                        JOptionPane.showMessageDialog(null, "Xác nhận đã chuyển khoản thành công!");
                    });

                    JPanel bottomPanel = new JPanel();
                    bottomPanel.add(okButton);
                    qrDialog.add(bottomPanel, BorderLayout.SOUTH);

                    qrDialog.setSize(300, 350);
                    qrDialog.setLocationRelativeTo(null); 
                    qrDialog.setVisible(true);
                }
                // Tạo đối tượng DonHang
                DonHang dh = new DonHang();
                dh.setIdKhachHang(ID_KhachHang);
                dh.setSdtNguoiGui(kh.getSDT());
                dh.setSdtNguoiNhan(sdtNguoiNhan);
                dh.setTenNguoiGui(kh.getHoTen());
                dh.setTenNguoiNhan(tenNguoiNhan);
                dh.setDiaChiNhan(diaChiDayDu);
                dh.setDichVu(loaiDichVu);
                dh.setLoaiHangHoa(loaiHang);
                dh.setIdKhoTiepNhan(idKho);
                dh.setTienCOD(tienCOD);
                
                if (!qLDonHangController.KiemTraDinhDang(dh)) {
                    JOptionPane.showMessageDialog(this, "Định dạng đơn hàng không hợp lệ. Vui lòng kiểm tra lại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return; // Dừng lại, không thực hiện thêm
                }
            try {
                 int id_dh = qLDonHangController.ThemDonHang(dh);
                JOptionPane.showMessageDialog(this, "Tạo đơn hàng thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);

                HoaDonKH hd = new HoaDonKH();
                hd.XuatHD(id_dh);
                try {
                    new EmailSender().sendMail(new KhachHangDAO().layEmailKH(id_dh),new File(hd.getFilePath()),"GỬI HÓA ĐƠN");
                } catch (Exception ex) {
                    Logger.getLogger(KHTaoDonHangPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(KHTaoDonHangPanel.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(this, "Tạo đơn hàng thất bại. Vui lòng thử lại.\nChi tiết: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(KHTaoDonHangPanel.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(this, "Tạo đơn hàng thất bại. Vui lòng thử lại.\nChi tiết: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
                
                
        });
    }
    

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Test Employee Main Panel");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1000, 600);
            frame.setLocationRelativeTo(null);
            frame.setLayout(new BorderLayout());

            try {
                frame.add(new KHTaoDonHangPanel(21), BorderLayout.CENTER);
            } catch (SQLException ex) {
                Logger.getLogger(KHTaoDonHangPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(KHTaoDonHangPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(KHTaoDonHangPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            frame.setVisible(true);
        });
    }
}
