/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appgiaovan.ManagerGUI;

import appgiaovan.Controller.QLKHController;
import appgiaovan.Entity.KhachHang;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class FormSuaKH extends JDialog {
    private QLKHController controller;
    private KhachHang kh;
    private JTextField txtID, txtHoTen, txtSDT, txtEmail, txtCCCD, txtNgaySinh;
    private JComboBox<String> cboGioiTinh;

    public FormSuaKH( KhachHang kh) throws ClassNotFoundException {
        JFrame frame = new JFrame("Quản Lý Khách Hàng");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1300, 600);
        frame.setLocationRelativeTo(null); // Center the frame
        super(frame, "Sửa Khách Hàng", true);
        this.controller = new QLKHController();
        this.kh = kh;
        initUI();
        hienThiThongTinKhachHang();
    }

    private void initUI() {
        JPanel pnl = new JPanel(new GridBagLayout());
        pnl.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        pnl.setPreferredSize(new Dimension(450, 350));  // cùng kích thước với form Thêm
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.HORIZONTAL;

        String[] labels = {
            "Mã KH:", "Họ Tên:", "SĐT:", "Email:",
            "CCCD:", "Ngày sinh:", "Giới tính:"
        };
        Component[] fields = {
            txtID = new JTextField(), txtHoTen = new JTextField(),
            txtSDT = new JTextField(), txtEmail = new JTextField(),
            txtCCCD = new JTextField(), txtNgaySinh = new JTextField(),
            cboGioiTinh = new JComboBox<>(new String[]{"Nam", "Nữ"})
        };
        txtID.setEnabled(false);

        for (int i = 0; i < labels.length; i++) {
            c.gridx = 0; c.gridy = i; c.weightx = 0.2;
            pnl.add(new JLabel(labels[i]), c);
            c.gridx = 1; c.weightx = 0.8;
            pnl.add(fields[i], c);
        }

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        JButton btnSave = new JButton("Lưu");    btnSave.addActionListener(e->onSave());
        JButton btnCancel = new JButton("Hủy");  btnCancel.addActionListener(e->dispose());
        btnPanel.add(btnSave); btnPanel.add(btnCancel);

        c.gridx = 0; c.gridy = labels.length;
        c.gridwidth = 2; c.anchor = GridBagConstraints.EAST;
        pnl.add(btnPanel, c);

        setContentPane(pnl);
        pack();
        setLocationRelativeTo(getOwner());
        setResizable(false);
    }

    public void hienThiThongTinKhachHang() {
        txtID.setText(String.valueOf(kh.getID_NguoiDung()));
        txtHoTen.setText(kh.getHoTen());
        txtSDT.setText(kh.getSDT());
        txtEmail.setText(kh.getEmail());
        txtCCCD.setText(kh.getCCCD());
        txtNgaySinh.setText(kh.getNgaySinh().toString());
        cboGioiTinh.setSelectedItem(kh.getGioiTinh());
    }

   public boolean kiemTraDinhDangThongTin() {

        if (txtID.getText().trim().isEmpty() ||
            txtHoTen.getText().trim().isEmpty() ||
            txtSDT.getText().trim().isEmpty() ||
            txtEmail.getText().trim().isEmpty() ||
            txtCCCD.getText().trim().isEmpty() ||
            txtNgaySinh.getText().trim().isEmpty() ||
            cboGioiTinh.getSelectedItem() == null) {
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
            hienThiThongBao("Thông tin không hợp lệ"); return;
        }
        kh.setHoTen(txtHoTen.getText());
        kh.setSDT(txtSDT.getText());
        kh.setEmail(txtEmail.getText());
        kh.setCCCD(txtCCCD.getText());
        kh.setNgaySinh(java.sql.Date.valueOf(txtNgaySinh.getText()));
        kh.setGioiTinh(cboGioiTinh.getSelectedItem().toString());
        boolean ok = controller.suaKhachHang(kh);
        hienThiThongBao(ok?"Sửa thành công":"Sửa thất bại");
        if (ok) dispose();
    }
    
}
