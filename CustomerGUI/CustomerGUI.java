/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package appgiaovan.CustomerGUI;
import appgiaovan.Controller.DangKyController;
import appgiaovan.Controller.TokenController;
import appgiaovan.Entity.TaiKhoan;
import appgiaovan.GUI.LOGIN;
import javax.swing.*;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import static javax.swing.JOptionPane.WARNING_MESSAGE;
import javax.swing.JPanel;

/**
 *
 * @author nhant
 */
public class CustomerGUI extends JFrame {
    private DangKyController dangKyController=new DangKyController();
    private CardLayout cardLayout;
    private JPanel contentPanel;
    public CustomerGUI(TaiKhoan taikhoan, int idToken) throws SQLException, ClassNotFoundException, Exception{
        int ID_KhachHang=dangKyController.layID_KhachHang(taikhoan.getIdTaiKhoan());
        setTitle("Giao diện chính");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        //Thêm sidebar
        CustomerSidebar sidebar = new CustomerSidebar(taikhoan.getIdTaiKhoan());
        add(sidebar, BorderLayout.WEST);
        // Thêm panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);
        // Panel trung tâm hiển thị nội dung
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        // Thêm các trang panel
        contentPanel.add(new KhachHangMainPanel(ID_KhachHang),"Trang chủ");
        contentPanel.add(new KHTaoDonHangPanel(ID_KhachHang),"Tạo đơn hàng");
        contentPanel.add(new TraCuuDonHangPanel(ID_KhachHang),"Tra cứu đơn hàng");
        contentPanel.add(new ThongTinCaNhanPanel(taikhoan),"Thông tin cá nhân");
        add(contentPanel,BorderLayout.CENTER);
        sidebar.addMenuClickListener((selectedName) -> {
            if (selectedName.equals("Đăng xuất")) {
                int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Bạn có chắc chắn muốn đăng xuất không?",
                    "Xác nhận đăng xuất",
                    JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    dispose();
                    try {
                        try {
                            new TokenController().capNhatToken(idToken);
                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(CustomerGUI.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(CustomerGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    SwingUtilities.invokeLater(() -> new LOGIN().setVisible(true));
                }
            } else {
                cardLayout.show(contentPanel, selectedName);
            }
        });
        
    }
    public int layID_KhachHang(int ID_TaiKhoan) throws SQLException{
        try {
            return dangKyController.layID_KhachHang(ID_TaiKhoan);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CustomerGUI.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex, "Lỗi hệ thống", WARNING_MESSAGE);
        }   
        return -1;
    }
    
}
