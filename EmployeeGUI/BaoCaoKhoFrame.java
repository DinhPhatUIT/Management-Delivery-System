package appgiaovan.EmployeeGUI;

import appgiaovan.Controller.BaoCaoKhoController;
import appgiaovan.Entity.BaoCaoKho;
import appgiaovan.Entity.NhanVienKho;
import appgiaovan.GUI.Components.RoundedButton;
import com.formdev.flatlaf.FlatLightLaf;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BaoCaoKhoFrame extends JPanel {

    private NhanVienKho nhanVienKho;

    private JTextField txtIdNhanVien;
    private JTextField txtTenNhanVien;
    private JTextField txtNgayBaoCao;
    private JDateChooser kyBaoCaoChooser;
    private Date selectedKyBaoCao;

    private JTextField txtSoGoiHangXuat;
    private JTextField txtSoGoiHangNhap;
    private JButton submitButton;

    private BaoCaoKhoController controller;

    public BaoCaoKhoFrame(NhanVienKho nhanVienKho) {
        this.nhanVienKho = nhanVienKho;
        controller = new BaoCaoKhoController();
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240)); // nền ngoài

        // Panel chứa nội dung chính
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30)); // padding trong

        // Tiêu đề
        JLabel titleLabel = new JLabel("Báo cáo công việc");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(titleLabel);

        JLabel descLabel = new JLabel("Nhập thông tin báo cáo.");
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        descLabel.setForeground(Color.GRAY);
        descLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        formPanel.add(descLabel);

        formPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        txtIdNhanVien = new JTextField();
        formPanel.add(createField("ID Nhân viên", txtIdNhanVien));

        txtTenNhanVien = new JTextField();
        formPanel.add(createField("Tên nhân viên", txtTenNhanVien));

        txtNgayBaoCao = new JTextField();
        formPanel.add(createField("Ngày báo cáo", txtNgayBaoCao));

        // JDateChooser cho kỳ báo cáo
        kyBaoCaoChooser = new JDateChooser();
        kyBaoCaoChooser.setDateFormatString("dd/MM/yyyy");
        kyBaoCaoChooser.setPreferredSize(new Dimension(300, 35));
        kyBaoCaoChooser.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(createField("Kỳ báo cáo", kyBaoCaoChooser));

        txtSoGoiHangNhap = new JTextField();
        formPanel.add(createField("Số gói hàng nhập", txtSoGoiHangNhap));

        txtSoGoiHangXuat = new JTextField();
        formPanel.add(createField("Số gói hàng xuất", txtSoGoiHangXuat));

        formPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Nút gửi
        submitButton = new JButton("Gửi");
        submitButton.setBackground(new Color(0, 102, 204));
        submitButton.setForeground(Color.WHITE);
        submitButton.setPreferredSize(new Dimension(150, 40));
        submitButton = new RoundedButton(submitButton, 20);
        submitButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(submitButton);

        // Bọc form trong panel để dễ chèn sidebar sau này
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(new Color(245, 245, 245));
        wrapper.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100)); // padding ngoài
        wrapper.add(formPanel, BorderLayout.CENTER);

        add(wrapper, BorderLayout.CENTER);
        HienThiThongTin();
        submitButton.addActionListener(e -> XuLyGuiBaoCao());
    }

    public void HienThiThongTin() {
        txtIdNhanVien.setText(String.valueOf(nhanVienKho.getID_NguoiDung()));
        txtIdNhanVien.setFocusable(false);
        txtIdNhanVien.setBackground(Color.LIGHT_GRAY);

        txtTenNhanVien.setText(nhanVienKho.getHoTen());
        txtTenNhanVien.setFocusable(false);
        txtTenNhanVien.setBackground(Color.LIGHT_GRAY);

        // Set ngày hôm nay cho ngày báo cáo
        Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        txtNgayBaoCao.setText(sdf.format(currentDate));
        txtNgayBaoCao.setFocusable(false);
        txtNgayBaoCao.setBackground(Color.LIGHT_GRAY);

        kyBaoCaoChooser.addPropertyChangeListener("date", evt -> {
            selectedKyBaoCao = kyBaoCaoChooser.getDate();
            System.out.println("Kỳ báo cáo được chọn: " + selectedKyBaoCao);
        });
    }

    public void XuLyGuiBaoCao() {
        try {
            String soNhapStr = txtSoGoiHangNhap.getText().trim();
            String soXuatStr = txtSoGoiHangXuat.getText().trim();

            if (soNhapStr.isEmpty() || soXuatStr.isEmpty() || kyBaoCaoChooser.getDate() == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int soNhap = Integer.parseInt(soNhapStr);
            int soXuat = Integer.parseInt(soXuatStr);

            // Tạo đối tượng báo cáo
            BaoCaoKho baoCaoKho = new BaoCaoKho();
            baoCaoKho.setIdNhanVien(nhanVienKho.getID_NguoiDung());
            baoCaoKho.setSoGoiHangNhap(soNhap);
            baoCaoKho.setSoGoiHangXuat(soXuat);
            baoCaoKho.setNgayKhoiTao(new Date()); 
            baoCaoKho.setKyBaoCao(kyBaoCaoChooser.getDate());

            controller.GuiBaoCao(baoCaoKho, nhanVienKho.getID_NguoiDung());

            JOptionPane.showMessageDialog(this, "Gửi báo cáo thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);

            txtSoGoiHangNhap.setText("");
            txtSoGoiHangXuat.setText("");
            kyBaoCaoChooser.setDate(null);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Số gói hàng phải là số nguyên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(BaoCaoKhoFrame.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Lỗi khi gửi báo cáo!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JPanel createField(String labelText, JComponent component) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JLabel label = new JLabel(labelText + " *");
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setForeground(Color.DARK_GRAY);

        component.setPreferredSize(new Dimension(300, 35));
        if (component instanceof JTextField) {
            component.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            component.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        }

        panel.add(label, BorderLayout.NORTH);
        panel.add(component, BorderLayout.CENTER);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        return panel;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Báo cáo nhân viên");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 700);
            frame.setLocationRelativeTo(null);
            NhanVienKho nvkho = new NhanVienKho();
            nvkho.setID_NguoiDung(1);
            frame.setContentPane(new BaoCaoKhoFrame(nvkho));
            frame.setVisible(true);
        });
    }
}
