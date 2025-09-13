package appgiaovan.GUI.Components;

import javax.swing.*;
import java.awt.*;

public class ThongTinCaNhan extends JPanel {

    public ThongTinCaNhan() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(1000, 600));

       

        // Khu vực trung tâm
        JPanel mainPanel = new JPanel(new BorderLayout());

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

        JTextField txtHoTen = new JTextField("Nguyễn Văn A");
        txtHoTen.setBounds(130, 70, 200, 30);
        infoPanel.add(txtHoTen);

        // Số điện thoại
        JLabel lblSDT = new JLabel("Số điện thoại:");
        lblSDT.setBounds(20, 120, 100, 25);
        infoPanel.add(lblSDT);

        JTextField txtSDT = new JTextField("0123456789");
        txtSDT.setBounds(130, 120, 200, 30);
        infoPanel.add(txtSDT);

        // Email
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(20, 170, 100, 25);
        infoPanel.add(lblEmail);

        JTextField txtEmail = new JTextField("email@example.com");
        txtEmail.setBounds(130, 170, 300, 30);
        infoPanel.add(txtEmail);

        // Địa chỉ
        JLabel lblDiaChi = new JLabel("Địa chỉ:");
        lblDiaChi.setBounds(20, 220, 100, 25);
        infoPanel.add(lblDiaChi);

        JTextField txtDiaChi = new JTextField("123 Đường ABC, Quận XYZ");
        txtDiaChi.setBounds(130, 220, 400, 30);
        infoPanel.add(txtDiaChi);

        // Nút cập nhật
        JButton btnCapNhat = new JButton("Cập nhật");
        btnCapNhat.setBounds(130, 280, 120, 35);
        btnCapNhat.setBackground(new Color(0, 123, 255));
        btnCapNhat.setForeground(Color.WHITE);
        infoPanel.add(btnCapNhat);

        // Thêm các panel
        mainPanel.add(infoPanel, BorderLayout.CENTER);

        // Thanh thời tiết
        TimeWeather customerTimeWeather = new TimeWeather("Ho Chi Minh 30 độ");
        mainPanel.add(customerTimeWeather, BorderLayout.NORTH);

        // Thêm sidebar và mainPanel vào chính JPanel này
        
        add(mainPanel, BorderLayout.CENTER);
    }

    // Nếu bạn vẫn muốn test riêng JPanel này thì có thể dùng đoạn này
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf());
        } catch (Exception ex) {
            System.err.println("Không thể cài đặt FlatLaf");
        }

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Test ThongTinCaNhan JPanel");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1000, 600);
            frame.setLocationRelativeTo(null);
            frame.setContentPane(new ThongTinCaNhan()); // sử dụng JPanel
            frame.setVisible(true);
        });
    }
}
