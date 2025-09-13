/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package appgiaovan.CustomerGUI;

import appgiaovan.Controller.QLDonHangController;
import appgiaovan.GUI.Components.RoundedButton;
import appgiaovan.GUI.Components.RoundedTextField;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import appgiaovan.Entity.DonHang;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ThongTinDonHang extends JFrame {
    
        private JTextField txtHoTen, txtSDT, txtEmail, txtCCCD, txtNgaySinh, txtGioiTinh;
        private JButton btnCapNhat;
        private DonHang donHang;
        private QLDonHangController qLDonHangController=new QLDonHangController();
    public ThongTinDonHang(int ID_DonHang) throws SQLException, ClassNotFoundException {
        donHang=hienThiDonHang(ID_DonHang);
        setTitle("Chi Tiết Đơn Hàng");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());


        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.WHITE);

        JLabel lblTitle = new JLabel("Chi Tiết Đơn Hàng");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setBounds(30, 20, 300, 30);
        mainPanel.add(lblTitle);

        // Mã đơn
        RoundedTextField txtMaDon = new RoundedTextField(String.valueOf(ID_DonHang));
        txtMaDon.setBorder(BorderFactory.createTitledBorder("Mã đơn hàng"));
        txtMaDon.setFocusable(false);
        txtMaDon.setBounds(30, 70, 200, 50);
        mainPanel.add(txtMaDon);

        // Người gửi
        RoundedTextField txtNguoiGui = new RoundedTextField(donHang.getTenNguoiGui());
        txtNguoiGui.setBorder(BorderFactory.createTitledBorder("Người gửi"));
        txtNguoiGui.setFocusable(false);
        txtNguoiGui.setBounds(250, 70, 200, 50);
        mainPanel.add(txtNguoiGui);

        // Người nhận
        RoundedTextField txtNguoiNhan = new RoundedTextField(donHang.getSdtNguoiNhan());
        txtNguoiNhan.setBorder(BorderFactory.createTitledBorder("Người nhận"));
        txtNguoiNhan.setFocusable(false);
        txtNguoiNhan.setBounds(470, 70, 200, 50);
        mainPanel.add(txtNguoiNhan);

        // Địa chỉ nhận
        RoundedTextField txtDiaChiNhan = new RoundedTextField(donHang.getDiaChiNhan());
        txtDiaChiNhan.setBorder(BorderFactory.createTitledBorder("Địa chỉ nhận"));
        txtDiaChiNhan.setFocusable(false);
        txtDiaChiNhan.setBounds(30, 140, 640, 50);
        mainPanel.add(txtDiaChiNhan);

        // Trạng thái
        RoundedTextField txtTrangThai = new RoundedTextField(donHang.getTrangThai());
        txtTrangThai.setBorder(BorderFactory.createTitledBorder("Trạng thái"));
        txtTrangThai.setFocusable(false);
        txtTrangThai.setBounds(30, 210, 200, 50);
        mainPanel.add(txtTrangThai);

        // Loại dịch vụ
        RoundedTextField txtLoaiDV = new RoundedTextField(donHang.getDichVu());
        txtLoaiDV.setBorder(BorderFactory.createTitledBorder("Loại dịch vụ"));
        txtLoaiDV.setFocusable(false);
        txtLoaiDV.setBounds(250, 210, 200, 50);
        mainPanel.add(txtLoaiDV);

        // Phí vận chuyển
        RoundedTextField txtPhi = new RoundedTextField(String.valueOf(donHang.getPhi()));
        txtPhi.setBorder(BorderFactory.createTitledBorder("Phí vận chuyển"));
        txtPhi.setFocusable(false);
        txtPhi.setBounds(470, 210, 200, 50);
        mainPanel.add(txtPhi);

        

        add(mainPanel, BorderLayout.CENTER);
        setVisible(true);
        RoundedButton btnDanhGia = new RoundedButton("Đánh giá đơn hàng");
        btnDanhGia.setBounds(100, 360, 200, 45);
        btnDanhGia.setBackground(new Color(0x28a745)); // Xanh lá
        btnDanhGia.setForeground(Color.WHITE);
        RoundedButton btnHuy = new RoundedButton("Hủy");
        btnHuy.setBounds(420, 360, 200, 45);
        btnHuy.setBackground(Color.RED); // Đỏ
        btnHuy.setForeground(Color.WHITE);
        //Xử lý sự kiện ấn nút đánh giá
        btnDanhGia.addActionListener(e -> {
            if(!donHang.getTrangThai().equals("Đã giao")){
                JOptionPane.showMessageDialog(null, "Đơn hàng chưa giao, không được đánh giá!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            }
            else{
                DanhGiaForm form = new DanhGiaForm(ID_DonHang);
                form.setVisible(true);
            }
        });
        //Xử lý sự kiện ấn nút hủy
        btnHuy.addActionListener(e -> {
            System.out.println(donHang.getTrangThai());
            if(!donHang.getTrangThai().equals("Đang xử lý")){
                JOptionPane.showMessageDialog(null, "Đơn hàng đã rời kho, không được hủy!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            }
            else{
                try {
                    HuyDonHang(ID_DonHang);
                    donHang = hienThiDonHang(ID_DonHang);
                    txtTrangThai.setText(donHang.getTrangThai());
                    JOptionPane.showMessageDialog(null, "Đã hủy đơn hàng thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } catch (SQLException ex) {
                    Logger.getLogger(ThongTinDonHang.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(this,
                            "Lỗi hệ thống",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ThongTinDonHang.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(this,
                            "Lỗi hệ thống",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        mainPanel.add(btnDanhGia);
        mainPanel.add(btnHuy);
    }
    void HuyDonHang(int ID_DonHang) throws SQLException, ClassNotFoundException{
        qLDonHangController.HuyDonHang(ID_DonHang);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            System.err.println("Không thể cài đặt FlatLaf");
        }

        SwingUtilities.invokeLater(() -> {
            try {
                new ThongTinDonHang(6).setVisible(true);
            } catch (SQLException ex) {
                Logger.getLogger(ThongTinDonHang.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ThongTinDonHang.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    private DonHang hienThiDonHang(int ID_DonHang) throws SQLException, ClassNotFoundException {
        DonHang donHang=new DonHang();
        donHang=qLDonHangController.layThongTinDH(ID_DonHang);
        return donHang;
    }
}

