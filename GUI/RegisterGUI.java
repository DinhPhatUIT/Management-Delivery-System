/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appgiaovan.GUI;
import appgiaovan.Controller.DangKyController;
import appgiaovan.EmailSender;
import appgiaovan.Entity.KhachHang;
import appgiaovan.Entity.TaiKhoan;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import com.toedter.calendar.JDateChooser;
import java.util.Date;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import static appgiaovan.PasswordHashing.hashPassword;
import appgiaovan.VerificationForm;
import java.util.Random;
/**
 *
 * @author ASUS
 */
public class RegisterGUI extends JFrame{
    
    private DangKyController controller = new DangKyController();
    public RegisterGUI() {
        setTitle("Đăng ký - Đơn vị giao vận 3P1N");
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
        JPanel registerPanel = new JPanel();
        registerPanel.setBounds(275, 10, 350, 540);
        registerPanel.setBackground(Color.WHITE);
        registerPanel.setLayout(null);
        registerPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        JLabel logo = new JLabel("Đăng ký tài khoản mới");
        logo.setFont(new Font("Arial", Font.BOLD, 20));
        logo.setForeground(new Color(0, 90, 204));
        logo.setBounds(70, 10, 250, 25);
        registerPanel.add(logo);
        
        //nhập họ tên
        JTextField txthoTen = new JTextField();
        txthoTen.setBounds(30, 50, 290, 40);
        txthoTen.setBorder(BorderFactory.createTitledBorder("Họ Tên"));
        registerPanel.add(txthoTen);

        // nhập tên đăng nhập
        JTextField txttenDangNhap = new JTextField();
        txttenDangNhap.setBounds(30, 95, 290, 40);
        txttenDangNhap.setBorder(BorderFactory.createTitledBorder("Tên đăng nhập"));
        registerPanel.add(txttenDangNhap);
        
        //nhập pass
        JPasswordField txtmatKhau = new JPasswordField();
        txtmatKhau.setBounds(30, 145, 290, 40);
        txtmatKhau.setBorder(BorderFactory.createTitledBorder("Mật khẩu"));
        registerPanel.add(txtmatKhau);

        // nhập lại pass
        JPasswordField txtmatKhauNL = new JPasswordField();
        txtmatKhauNL.setBounds(30, 195, 290, 40);
        txtmatKhauNL.setBorder(BorderFactory.createTitledBorder("Nhập lại mật khẩu"));
        registerPanel.add(txtmatKhauNL);
        
        //nhập CCCD
        JTextField txtCCCD = new JTextField();
        txtCCCD.setBounds(30, 245, 290, 40);
        txtCCCD.setBorder(BorderFactory.createTitledBorder("CCCD"));
        registerPanel.add(txtCCCD);
        
        //nhập Email
        JTextField txtemail = new JTextField();
        txtemail.setBounds(30, 295, 290, 40);
        txtemail.setBorder(BorderFactory.createTitledBorder("Email"));
        registerPanel.add(txtemail);
        
        //nhập SĐT
        JTextField txtSDT= new JTextField();
        txtSDT.setBounds(30, 345, 290, 35);
        txtSDT.setBorder(BorderFactory.createTitledBorder("Số điện thoại"));
        registerPanel.add(txtSDT);
        
        JDateChooser csdate = new JDateChooser();
        csdate.setDateFormatString("dd/MM/yyyy");
        csdate.setBounds(30, 390, 290, 40); // vị trí và kích thước
        csdate.setBorder(BorderFactory.createTitledBorder("Ngày sinh"));
        registerPanel.add(csdate);
        
        //nhập GT
        String[] genders = {"Nam", "Nữ"};
        JComboBox<String> cbgioiTinh = new JComboBox<>(genders);
        cbgioiTinh.setBounds(30, 435, 290, 40);
        cbgioiTinh.setBorder(BorderFactory.createTitledBorder("Giới tính"));
        registerPanel.add(cbgioiTinh);

        
        JButton dkyButton = new JButton("Đăng ký");
        dkyButton.setBounds(30, 485, 290, 25);
        dkyButton.setBackground(new Color(0, 123, 255));
        dkyButton.setForeground(Color.WHITE);
        dkyButton.setFocusPainted(false);
        registerPanel.add(dkyButton);
        
        
        JLabel infoLabel = new JLabel("Bạn đã có tài khoản?");
        infoLabel.setBounds(70, 515, 150, 20);
        registerPanel.add(infoLabel);

        JLabel loginLabel = new JLabel("Đăng nhập ngay");
        loginLabel.setBounds(190, 515, 100, 20);
        loginLabel.setForeground(Color.BLUE);
        loginLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        registerPanel.add(loginLabel);

        loginLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Hiển thị form Đăng NHẬP
                SwingUtilities.invokeLater(() -> {
                    LOGIN fp = new LOGIN();
                    fp.setVisible(true);
                    dispose();
                });
            }
        });
        dkyButton.addActionListener(e -> {

            try {

                String hoTen = txthoTen.getText().trim();
                String tenDangNhap = txttenDangNhap.getText().trim();
                char[] matKhauKiHieu = txtmatKhau.getPassword();
                String matKhau = new String(matKhauKiHieu);
                char[] matKhauKiHieuNL = txtmatKhauNL.getPassword();
                String matKhauNL = new String(matKhauKiHieuNL);
                String CCCD= txtCCCD.getText().trim();
                String email=txtemail.getText().trim();
                String SDT=txtSDT.getText().trim();
                Date ngaySinh = csdate.getDate();
                String gioiTinh = (String) cbgioiTinh.getSelectedItem();
    
                // Tạo đối tượng KHACHHANG
                KhachHang kh = new KhachHang();
                kh.setHoTen(hoTen);
                kh.setCCCD(CCCD);
                kh.setEmail(email);
                kh.setSDT(SDT);
                kh.setNgaySinh(ngaySinh);
                kh.setGioiTinh(gioiTinh);
                //Tạo đối tượng TAIKHOAN
                TaiKhoan tk=new TaiKhoan();
                tk.setTenTaiKhoan(tenDangNhap);
                tk.setMatKhauMaHoa(hashPassword(matKhau));
                

                if (!controller.KiemTraDinhDang(kh,matKhau,matKhauNL,tenDangNhap)) {
                    JOptionPane.showMessageDialog(this,
                            "Định dạng đơn hàng không hợp lệ. Vui lòng kiểm tra lại.",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String generatedCode = String.valueOf(new Random().nextInt(900000) + 100000);
                EmailSender.sendEmail(email, generatedCode);
                new VerificationForm(generatedCode,email,kh,tk).setVisible(true);
                
               

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi: " + 
                        ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
               
            }

        });
        mainPanel.add(registerPanel);        
        mainPanel.add(background);

}
    
    
   
    public static void main(String[] args) {
        try {
        UIManager.setLookAndFeel(new FlatLightLaf());
    } catch (Exception ex) {
        System.err.println("Không thể cài đặt FlatLaf");
    }

    SwingUtilities.invokeLater(() -> {
        RegisterGUI frame = new RegisterGUI();
            frame.setVisible(true);
        
    });
    }
}
