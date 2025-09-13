package appgiaovan.ManagerGUI;

import appgiaovan.Controller.TokenController;
import appgiaovan.CustomerGUI.ThongTinCaNhanPanel;
import appgiaovan.DAO.TokenDAO;
import appgiaovan.Entity.TaiKhoan;
import appgiaovan.GUI.LOGIN;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class ManagerGUI extends JFrame {

    private CardLayout cardLayout;
    private JPanel contentPanel;
    private TaiKhoan taiKhoan;
    
    public ManagerGUI(TaiKhoan tk, int idToken) throws SQLException, ClassNotFoundException {
        this.taiKhoan = tk;
        setTitle("3P1N - Quản lý");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        ManagerSidebar sidebar = new ManagerSidebar(taiKhoan.getIdTaiKhoan());
        add(sidebar, BorderLayout.WEST);

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);


        

        contentPanel.add(new ManagerMainScreen(), "Trang chủ");
        contentPanel.add(new GUI_QLNVKho(), "Quản lý nhân viên kho");
        contentPanel.add(new GUI_QLShipper(), "Quản lý shipper");
        contentPanel.add(new GUI_QLKH(), "Quản lý khách hàng");

        contentPanel.add(new ThongTinCaNhanPanel(tk), "Thông tin cá nhân");

        contentPanel.add(new GUI_XemBaoCao(), "Xem báo cáo");
        contentPanel.add(new ThongKePanel(), "Báo cáo thống kê");

        add(contentPanel, BorderLayout.CENTER);

        sidebar.addMenuClickListener((selectedName) -> {
            if (selectedName.equals("Đăng xuất")) {
                int confirm = JOptionPane.showConfirmDialog(
                        this,
                        "Bạn có chắc chắn muốn đăng xuất không?",
                        "Xác nhận đăng xuất",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    dispose();
                    try {
                        try {
                            new TokenController().capNhatToken(idToken);
                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(ManagerGUI.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(ManagerGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    SwingUtilities.invokeLater(() -> new LOGIN().setVisible(true));
                }
            } else {
                cardLayout.show(contentPanel, selectedName);
            }
        });
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ex) {
            System.err.println("Không thể cài đặt FlatLaf");
        }
        SwingUtilities.invokeLater(() -> {
            try {
                TaiKhoan tk = new TaiKhoan();

                tk.setIdTaiKhoan(101);
                String username="admin1";
                TokenDAO tokenDAO =new TokenDAO();
                int id=0;
                id=tokenDAO.taoToken(username);
                new ManagerGUI(tk,1).setVisible(true);

            } catch (SQLException ex) {
                Logger.getLogger(ManagerGUI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ManagerGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
}
