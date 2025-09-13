/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appgiaovan.CustomerGUI;

/**
 *
 * @author nhant
 */
import appgiaovan.Controller.QLDonHangController;
import appgiaovan.DAO.DanhGiaDAO;
import appgiaovan.Entity.DanhGia;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DanhGiaForm extends JFrame {
    private JTextArea txtNoiDung;
    private JRadioButton[] stars;
    private ButtonGroup starGroup;
    private JButton btnGuiDanhGia;
    private QLDonHangController qLDonHangController=new QLDonHangController();;
    public DanhGiaForm(int ID_DonHang) {
        setTitle("Đánh Giá Đơn Hàng");
        setSize(400, 350);
        setLocationRelativeTo(null); // Hiển thị ở giữa màn hình
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JLabel lblTitle = new JLabel("Đánh Giá Đơn Hàng", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        add(lblTitle, BorderLayout.NORTH);

        // Vùng nhập nội dung đánh giá
        JPanel noiDungPanel = new JPanel(new BorderLayout());
        JLabel lblNoiDung = new JLabel("Nội dung đánh giá:");
        txtNoiDung = new JTextArea(5, 30);
        txtNoiDung.setLineWrap(true);
        txtNoiDung.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(txtNoiDung);

        noiDungPanel.add(lblNoiDung, BorderLayout.NORTH);
        noiDungPanel.add(scrollPane, BorderLayout.CENTER);
        add(noiDungPanel, BorderLayout.CENTER);

        // Khung chọn số sao
        JPanel starPanel = new JPanel();
        starPanel.setBorder(BorderFactory.createTitledBorder("Chọn số sao:"));
        stars = new JRadioButton[5];
        starGroup = new ButtonGroup();
        for (int i = 0; i < 5; i++) {
            stars[i] = new JRadioButton((i + 1) + " ★");
            starGroup.add(stars[i]);
            starPanel.add(stars[i]);
        }

        // Nút gửi
        btnGuiDanhGia = new JButton("Gửi Đánh Giá");
        btnGuiDanhGia.addActionListener(e -> {
            String noiDung = txtNoiDung.getText();
            int soSao = getSelectedStar();

            if (noiDung.isEmpty() || soSao == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập nội dung và chọn số sao!");
                return;
            } 
            
            //Tao doi tuong DanhGia
            DanhGia danhGia=new DanhGia();
            danhGia.setNoiDungDanhGia(noiDung);
            danhGia.setDiemDanhGia(soSao);
            danhGia.setIdDonHang(ID_DonHang);
            try { 
                qLDonHangController.ThemDanhGia(danhGia);
                JOptionPane.showMessageDialog(this, "Đánh giá đã được gửi:\nSao: " + soSao + "\nNội dung: " + noiDung);
                dispose(); 
            } catch (SQLException ex) {
                Logger.getLogger(DanhGiaForm.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Đơn hàng đã được đánh giá", "Thông báo", JOptionPane.WARNING_MESSAGE);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(DanhGiaForm.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Đơn hàng đã được đánh giá", "Thông báo", JOptionPane.WARNING_MESSAGE);
            }
            
        });

        // Panel dưới cùng chứa sao và nút
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(starPanel, BorderLayout.CENTER);
        bottomPanel.add(btnGuiDanhGia, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    public int getSelectedStar() {
        for (int i = 0; i < stars.length; i++) {
            if (stars[i].isSelected()) {
                return i + 1;
            }
        }
        return -1;
    }
}