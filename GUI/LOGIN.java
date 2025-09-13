/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appgiaovan.GUI;

import appgiaovan.Controller.LoginController;
import appgiaovan.Controller.TokenController;
import appgiaovan.CustomerGUI.CustomerGUI;
import appgiaovan.EmployeeGUI.EmployeeGUI;
import appgiaovan.Entity.TaiKhoan;
import appgiaovan.ManagerGUI.ManagerGUI;
import appgiaovan.ShipperGUI.NVGHMainGUI;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 *
 * @author ASUS
 */
public class LOGIN extends JFrame {

    private LoginController log = new LoginController();
    private JTextField userField = new JTextField();
    private JPasswordField passField = new JPasswordField();
    private TaiKhoan tk = new TaiKhoan();
    private int idToken;
    private TokenController controller = new TokenController();

    public LOGIN() {
        setTitle("Đăng nhập - Đơn vị giao vận 3P1N");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JPanel mainPanel = new JPanel(null);
        mainPanel.setLayout(null);
        mainPanel.setBounds(0, 0, 900, 600);
        add(mainPanel);

        JLabel background = new JLabel(new javax.swing.ImageIcon(getClass().getResource("/images/warehouse_11zon.jpg")));// Thay bằng hình bạn muốn
        URL imageUrl = getClass().getResource("/images/warehouse_11zon.jpg");
        System.out.println("Image URL: " + imageUrl);

        background.setBounds(0, 0, 900, 600);
        
        JPanel loginPanel = new JPanel();
        loginPanel.setBounds(275, 100, 350, 350);
        loginPanel.setBackground(Color.WHITE);
        loginPanel.setLayout(null);
        loginPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        JLabel logo = new JLabel("3P1N - Đăng nhập");
        logo.setFont(new Font("Arial", Font.BOLD, 20));
        logo.setForeground(new Color(0, 102, 204));
        logo.setBounds(90, 45, 200, 40);
        loginPanel.add(logo);

        userField.setBounds(30, 115, 290, 45);
        userField.setBorder(BorderFactory.createTitledBorder("Tên đăng nhập"));
        loginPanel.add(userField);

        passField.setBounds(30, 165, 290, 40);
        passField.setBorder(BorderFactory.createTitledBorder("Mật khẩu"));
        loginPanel.add(passField);

        JLabel forgot = new JLabel("Quên mật khẩu?");
        forgot.setBounds(200, 205, 120, 20);
        forgot.setForeground(Color.BLUE);
        forgot.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginPanel.add(forgot);
        forgot.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SwingUtilities.invokeLater(() -> {
                    ForgotPass fp = new ForgotPass();
                    fp.setVisible(true);
                });
            }
        });

        JButton loginButton = new JButton("Đăng nhập");
        loginButton.setBounds(30, 240, 290, 40);
        loginButton.setBackground(new Color(0, 123, 255));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginPanel.add(loginButton);

        getRootPane().setDefaultButton(loginButton);

        loginButton.addActionListener(e -> {
            try {
                yeuCauXacThuc();
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(LOGIN.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(LOGIN.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        JLabel infoLabel = new JLabel("Bạn chưa có tài khoản?");
        infoLabel.setBounds(70, 290, 150, 20);
        loginPanel.add(infoLabel);

        JLabel registerLabel = new JLabel("Đăng ký ngay");
        registerLabel.setBounds(210, 290, 100, 20);
        registerLabel.setForeground(Color.BLUE);
        registerLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginPanel.add(registerLabel);

        registerLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SwingUtilities.invokeLater(() -> {
                    RegisterGUI fp = new RegisterGUI();
                    fp.setVisible(true);
                    dispose();
                });
            }
        });

        mainPanel.add(loginPanel);
        mainPanel.add(background);

    }

    public void yeuCauXacThuc() throws SQLException, ClassNotFoundException, Exception {
        String username = userField.getText().trim();
        String pass = new String(passField.getPassword());

        tk = log.yeuCauXacThuc(username, pass);

        if (tk == null) {

            JOptionPane.showMessageDialog(this, "Sai tên đăng nhập hoặc mật khẩu!", "Lỗi", JOptionPane.ERROR_MESSAGE);

        } else {
            if ("KH".equals(tk.getVaiTro())) {
                idToken = controller.TaoToken(username);
                new CustomerGUI(tk,idToken).setVisible(true);
                setVisible(false);
            } else if ("QL".equals(tk.getVaiTro())) {
                idToken = controller.TaoToken(username);
                new ManagerGUI(tk,idToken).setVisible(true);
                setVisible(false);
            } else if ("NVK".equals(tk.getVaiTro())) {
                idToken = controller.TaoToken(username);
                
                new EmployeeGUI(tk,idToken).setVisible(true);
                setVisible(false);
            } else if ("NVGH".equals(tk.getVaiTro())) {
                idToken = controller.TaoToken(username);
                new NVGHMainGUI(tk.getIdTaiKhoan(), idToken).setVisible(true);
                setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this, "Sai tên đăng nhập hoặc mật khẩu!", "Lỗi", JOptionPane.ERROR_MESSAGE);

            }
        }

    }

    public String getuser() {
        return userField.getText().trim();
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ex) {
            System.err.println("Không thể cài đặt FlatLaf");
        }
        SwingUtilities.invokeLater(() -> {
            LOGIN frame = new LOGIN();
            frame.setVisible(true);
        });
    }
}
