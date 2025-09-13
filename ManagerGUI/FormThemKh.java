/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appgiaovan.ManagerGUI;

import appgiaovan.Controller.QLKHController;
import appgiaovan.Entity.KhachHang;
import appgiaovan.Entity.TaiKhoan;
import javax.swing.*;
import java.awt.*;
import static appgiaovan.PasswordHashing.hashPassword;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class FormThemKh extends JDialog {

    private QLKHController controller;
    private JTextField txtID, txtHoTen, txtSDT, txtEmail, txtCCCD, txtNgaySinh, txtTenDangNhap, txtMatKhau;
    private JComboBox<String> cboGioiTinh;

    public FormThemKh() throws ClassNotFoundException {
        JFrame frame = new JFrame("Quản Lý Khách Hàng");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1300, 600);
        frame.setLocationRelativeTo(null); // Center the frame
        super(frame, "Thêm Khách Hàng", true);
        controller = new QLKHController();
        initUI();
        hienThiMaKhachHangMoi();
    }

    private void initUI() {
        JPanel pnl = new JPanel(new GridBagLayout());
        pnl.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        pnl.setPreferredSize(new Dimension(450, 350));  // <--- tăng chiều rộng
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.HORIZONTAL;

        String[] labels = {
            "Mã KH:", "Họ Tên:", "SĐT:", "Email:",
            "CCCD:", "Ngày sinh:", "Giới tính:",
            "Tên đăng nhập:", "Mật khẩu:"
        };
        Component[] fields = {
            txtID = new JTextField(), txtHoTen = new JTextField(),
            txtSDT = new JTextField(), txtEmail = new JTextField(),
            txtCCCD = new JTextField(),
            txtNgaySinh = new JTextField(),
            cboGioiTinh = new JComboBox<>(new String[]{"Nam", "Nữ"}),
            txtTenDangNhap = new JTextField(),
            txtMatKhau = new JPasswordField()
        };
        txtID.setEnabled(false);

        for (int i = 0; i < labels.length; i++) {
            c.gridx = 0;
            c.gridy = i;
            c.weightx = 0.2;
            pnl.add(new JLabel(labels[i]), c);

            c.gridx = 1;
            c.weightx = 0.8;
            pnl.add(fields[i], c);
        }

        // nút lưu/hủy
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        JButton btnSave = new JButton("Lưu");
        btnSave.addActionListener(e -> onSave());
        JButton btnCancel = new JButton("Hủy");
        btnCancel.addActionListener(e -> dispose());
        btnPanel.add(btnSave);
        btnPanel.add(btnCancel);

        c.gridx = 0;
        c.gridy = labels.length;
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.EAST;
        pnl.add(btnPanel, c);

        setContentPane(pnl);
        pack();
        setLocationRelativeTo(getOwner());
        setResizable(false);
    }

    public void hienThiMaKhachHangMoi() throws ClassNotFoundException {
        txtID.setText(String.valueOf(controller.layMaKhachHangMoi()));
    }

    public boolean kiemTraDinhDangThongTin() {

        if (txtID.getText().trim().isEmpty() ||
            txtHoTen.getText().trim().isEmpty() ||
            txtSDT.getText().trim().isEmpty() ||
            txtEmail.getText().trim().isEmpty() ||
            txtCCCD.getText().trim().isEmpty() ||
            txtNgaySinh.getText().trim().isEmpty() ||
            cboGioiTinh.getSelectedItem() == null ||
            txtTenDangNhap.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin.");
            return false;
        }


        String sdt = txtSDT.getText().trim();
        if (!sdt.matches("^0\\d{9}$")) {
            JOptionPane.showMessageDialog(null, "Số điện thoại phải có 10 chữ số và bắt đầu bằng số 0.");
            return false;
        }

        String ngaySinh = txtNgaySinh.getText().trim();
        if (!ngaySinh.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
            JOptionPane.showMessageDialog(null, "Ngày sinh phải theo định dạng YYYY-MM-DD.");
            return false;
        }


        try {
            LocalDate.parse(ngaySinh); 
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(null, "Ngày sinh không hợp lệ.");
            return false;
        }

        return true; 
    }


    public void hienThiThongBao(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    private void onSave() {
        if (!kiemTraDinhDangThongTin()) {
            hienThiThongBao("Thông tin không hợp lệ");
            return;
        }
        KhachHang kh = new KhachHang();
        TaiKhoan tk = new TaiKhoan();
        kh.setID_NguoiDung(Integer.parseInt(txtID.getText()));
        kh.setHoTen(txtHoTen.getText());
        kh.setSDT(txtSDT.getText());
        kh.setEmail(txtEmail.getText());
        kh.setCCCD(txtCCCD.getText());
        kh.setNgaySinh(java.sql.Date.valueOf(txtNgaySinh.getText()));
        kh.setGioiTinh(cboGioiTinh.getSelectedItem().toString());
        tk.setTenTaiKhoan(txtTenDangNhap.getText());
        tk.setMatKhauMaHoa(hashPassword(txtMatKhau.getText()));
        boolean ok = controller.taoKhachHang(kh, tk);
        hienThiThongBao(ok ? "Thêm thành công" : "Thêm thất bại");
        if (ok) {
            dispose();
        }
    }
    
    

    public static void main(String[] args) {
        try {
            javax.swing.SwingUtilities.invokeLater(() -> {
                try {
                    FormThemKh dialog = new FormThemKh();
                    dialog.setVisible(true);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    
}
