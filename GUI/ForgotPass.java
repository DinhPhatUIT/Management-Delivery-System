/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appgiaovan.GUI;

import appgiaovan.EmailSender;
import appgiaovan.VerificationForm;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import java.awt.*;
import java.util.Random;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class ForgotPass extends JFrame {
    public ForgotPass() {
        setTitle("Quên mật khẩu - Đơn vị giao vận 3P1N");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Nhập email hoặc username
        JTextField emailField = new JTextField();
        panel.add(new JLabel("Nhập email:"));
        panel.add(emailField);

        // Nút gửi
        JButton sendBtn = new JButton("Gửi yêu cầu");
        sendBtn.addActionListener(ev -> {
            String input = emailField.getText().trim();
            //  Gọi backend gửi mail/OTP
            String generatedCode = String.valueOf(new Random().nextInt(900000) + 100000);
            EmailSender.sendEmail(input, generatedCode);
            new VerificationForm(generatedCode,input).setVisible(true);
            JOptionPane.showMessageDialog(this,
                    "Kiểm tra mail để được hướng dẫn",
                "Đã gửi", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
        });
        panel.add(sendBtn);

        add(panel, BorderLayout.CENTER);
    }
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ex) {
            System.err.println("Không thể cài đặt FlatLaf");
        }
        SwingUtilities.invokeLater(() -> {
            ForgotPass frame = new ForgotPass();
            frame.setVisible(true);
        });
    }
}

