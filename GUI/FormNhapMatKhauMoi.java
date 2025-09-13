/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package appgiaovan.GUI;

import appgiaovan.Controller.DangKyController;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.SwingUtilities;
import static appgiaovan.PasswordHashing.hashPassword;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
/**
 *
 * @author nhant
 */
public class FormNhapMatKhauMoi extends JFrame {
    private JPasswordField txtMatKhauMoi;
    private JPasswordField txtXacNhanMatKhau;
    private JButton btnXacNhan;
    private DangKyController dangKyController=new DangKyController();
    public FormNhapMatKhauMoi(String email) {
        setTitle("Đặt lại mật khẩu");
        setSize(400, 220);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lblMatKhauMoi = new JLabel("Mật khẩu mới:");
        JLabel lblXacNhanMatKhau = new JLabel("Xác nhận mật khẩu:");

        txtMatKhauMoi = new JPasswordField();
        txtXacNhanMatKhau = new JPasswordField();
        btnXacNhan = new JButton("Xác nhận");

        lblMatKhauMoi.setBounds(50, 30, 120, 25);
        txtMatKhauMoi.setBounds(180, 30, 150, 25);

        lblXacNhanMatKhau.setBounds(50, 70, 120, 25);
        txtXacNhanMatKhau.setBounds(180, 70, 150, 25);

        btnXacNhan.setBounds(140, 120, 100, 30);

        add(lblMatKhauMoi);
        add(txtMatKhauMoi);
        add(lblXacNhanMatKhau);
        add(txtXacNhanMatKhau);
        add(btnXacNhan);
        
        btnXacNhan.addActionListener(e->{
        char[] matKhauKiHieu = txtMatKhauMoi.getPassword();
        String matKhau = new String(matKhauKiHieu);
        char[] matKhauKiHieuNL = txtXacNhanMatKhau.getPassword();
        String matKhauNL = new String(matKhauKiHieuNL);
           
        if(matKhau.equals(matKhauNL)){
            try {
                dangKyController.CapNhatMK(hashPassword(matKhau),email);
                JOptionPane.showMessageDialog(null, "Cập nhật mật khẩu thành công!", 
                        "Thành công", JOptionPane.INFORMATION_MESSAGE);
                dispose(); 
            } catch (SQLException ex) {
                Logger.getLogger(FormNhapMatKhauMoi.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(FormNhapMatKhauMoi.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            JOptionPane.showMessageDialog(null, "Không trùng khớp", 
                        "THẤT BẠI", JOptionPane.WARNING_MESSAGE);
        }
        });
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new FormNhapMatKhauMoi("").setVisible(true);
        });
    }
    
}
