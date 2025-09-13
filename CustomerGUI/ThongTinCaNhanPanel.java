/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package appgiaovan.CustomerGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import appgiaovan.Controller.DangKyController;
import appgiaovan.Controller.QLKHController;
import appgiaovan.DAO.TaiKhoanDAO;
import appgiaovan.Entity.NguoiDung;
import appgiaovan.Entity.TaiKhoan;

/**
 *
 * @author nhant
 */
public class ThongTinCaNhanPanel extends JPanel {

    private JTextField txtHoTen, txtSDT, txtEmail, txtCCCD, txtNgaySinh, txtGioiTinh;
    private JButton btnCapNhat;
    private NguoiDung nd = null;
//    private KhachHang kh = new KhachHang();
    private DangKyController controller = new DangKyController();

    public ThongTinCaNhanPanel(TaiKhoan taiKhoan) throws ClassNotFoundException {
        QLKHController qLKHController = new QLKHController();
       
        try {
            nd = new TaiKhoanDAO().LayThongTinNguoiDung(taiKhoan);
        } catch (SQLException ex) {
            Logger.getLogger(ThongTinCaNhanPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        setLayout(new BorderLayout());
        //Khu vuc trung tâm
        JPanel mainPanel = new JPanel(new BorderLayout());
        // Thêm vào JFrame
        add(mainPanel, BorderLayout.CENTER);
        // Panel thông tin cá nhân ở giữa
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(null); // Dùng layout tự do
        infoPanel.setBackground(Color.WHITE);

        // Tiêu đề
        JLabel lblTitle = new JLabel("Thông Tin Cá Nhân");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setBounds(20, 20, 300, 30);
        infoPanel.add(lblTitle);

        // Họ tên
        JLabel lblHoTen = new JLabel("Họ và tên:");
        lblHoTen.setBounds(20, 70, 100, 25);
        infoPanel.add(lblHoTen);

        JTextField txtHoTen = new JTextField(nd.getHoTen());
        txtHoTen.setBounds(130, 70, 200, 30);
        infoPanel.add(txtHoTen);

        // Số điện thoại
        JLabel lblSDT = new JLabel("SDT:");
        lblSDT.setBounds(20, 120, 100, 25);
        infoPanel.add(lblSDT);

        JTextField txtSDT = new JTextField(nd.getSDT());
        txtSDT.setBounds(130, 120, 200, 30);
        infoPanel.add(txtSDT);

        // Email
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(20, 170, 100, 25);
        infoPanel.add(lblEmail);

        JTextField txtEmail = new JTextField(nd.getEmail());
        txtEmail.setBounds(130, 170, 300, 30);
        infoPanel.add(txtEmail);
        //CCCD  
        JLabel lblCCCD = new JLabel("CCCD :");
        lblCCCD.setBounds(20, 220, 100, 25);
        infoPanel.add(lblCCCD);

        JTextField txtCCCD = new JTextField(nd.getCCCD());
        txtCCCD.setBounds(130, 220, 400, 30);
        infoPanel.add(txtCCCD);
        // NgaySinh
        JLabel lblNgaySinh = new JLabel("Ngày sinh:");
        lblNgaySinh.setBounds(20, 270, 100, 25);
        infoPanel.add(lblNgaySinh);


        SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");
        JTextField txtNgaySinh = new JTextField(String.valueOf(sdf2.format(nd.getNgaySinh())));


        txtNgaySinh.setBounds(130, 270, 400, 30);
        infoPanel.add(txtNgaySinh);
        //Gioitinh
        JLabel lblGioiTinh = new JLabel("Giới tính:");
        lblGioiTinh.setBounds(20, 320, 100, 25);
        infoPanel.add(lblGioiTinh);

        JTextField txtGioiTinh = new JTextField(nd.getGioiTinh());
        txtGioiTinh.setBounds(130, 320, 400, 30);
        infoPanel.add(txtGioiTinh);
        // Nút cập nhật
        JButton btnCapNhat = new JButton("Cập nhật");
        btnCapNhat.setBounds(130, 370, 120, 35);
        btnCapNhat.setBackground(new Color(0, 123, 255));
        btnCapNhat.setForeground(Color.WHITE);
        infoPanel.add(btnCapNhat);
        btnCapNhat.addActionListener(e -> {
            nd.setHoTen(txtHoTen.getText());
            nd.setSDT(txtSDT.getText());
            nd.setEmail(txtEmail.getText());
            nd.setCCCD(txtCCCD.getText());
            String ngaySinhStr = txtNgaySinh.getText(); // ví dụ chuỗi ngày sinh
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date ngaySinhDate = sdf.parse(ngaySinhStr);
                nd.setNgaySinh(ngaySinhDate);
            } catch (ParseException ex) {
                Logger.getLogger(ThongTinCaNhanPanel.class.getName()).log(Level.SEVERE, null, ex);
            }

            nd.setGioiTinh(txtGioiTinh.getText());
            if (!controller.KiemTraDinhDangCapNhat(nd)) {
                JOptionPane.showMessageDialog(this,
                        "Định dạng đơn hàng không hợp lệ. Vui lòng kiểm tra lại.",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return; 
            }
            try {
                if (TaiKhoanDAO.suaNguoiDung(nd, taiKhoan)) {
                    JOptionPane.showMessageDialog(this, "Cập nhật thông tin thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            } catch (SQLException ex) {
                Logger.getLogger(ThongTinCaNhanPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ThongTinCaNhanPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            JOptionPane.showMessageDialog(this,
                    "Lỗi hệ thống",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        });
        // Thêm infoPanel vào khu vực CENTER của mainPanel
        mainPanel.add(infoPanel, BorderLayout.CENTER);
        //Thanh Weather

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
                TaiKhoan taiKhoan = new TaiKhoan();
                taiKhoan.setIdTaiKhoan(1);
                taiKhoan.setVaiTro("KH");
                frame.add(new ThongTinCaNhanPanel(taiKhoan), BorderLayout.CENTER);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ThongTinCaNhanPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            frame.setVisible(true);
        });
    }
}
