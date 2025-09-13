/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appgiaovan.ManagerGUI;

import appgiaovan.Controller.QLNVKhoController;
import appgiaovan.Entity.NhanVienKho;
import appgiaovan.Entity.TaiKhoan;

import java.awt.BorderLayout;
import javax.swing.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


import javax.swing.JPanel;

/**
 *
 * @author pc
 */
public class GUI_QLNVKho extends JPanel {
    private QLNVKhoController controller;
    private JTable tblNhanVienKho;
    private JTextField txtSearch;
    private TaiKhoan tk;
    public GUI_QLNVKho() throws ClassNotFoundException {
        controller = new QLNVKhoController();
        setLayout(new BorderLayout());
        initUI();
    }

    private void initUI() throws ClassNotFoundException {
        JPanel pnlTop = new JPanel();
        txtSearch = new JTextField(20);
        JButton btnSearch = new JButton("Tìm kiếm");
        btnSearch.addActionListener(e -> {
            try {
                xuLiYeuCauTimKiemNhanVienKho();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(GUI_QLNVKho.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        pnlTop.add(txtSearch);
        pnlTop.add(btnSearch);
        add(pnlTop, BorderLayout.NORTH);

        tblNhanVienKho = new JTable();
        add(new JScrollPane(tblNhanVienKho), BorderLayout.CENTER);

        JButton btnAdd = new JButton("Thêm");
        btnAdd.addActionListener(e -> {
            FormThemNVKho form = null;
            try {
                form = new FormThemNVKho();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(GUI_QLNVKho.class.getName()).log(Level.SEVERE, null, ex);
            }
            form.setVisible(true);
            try {
                hienThiDanhSachNhanVienKho();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(GUI_QLNVKho.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        JButton btnEdit = new JButton("Sửa");
        btnEdit.addActionListener(e -> {
            try {
                xuLiLayThongTinNhanVienKho();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(GUI_QLNVKho.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        JButton btnDelete = new JButton("Xóa");
        btnDelete.addActionListener(e -> {
            try {
                xuLiXoaNhanVienKho();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(GUI_QLNVKho.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(GUI_QLNVKho.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        JPanel pnlButtons = new JPanel();
        pnlButtons.add(btnAdd);
        pnlButtons.add(btnEdit);
        pnlButtons.add(btnDelete);
        add(pnlButtons, BorderLayout.SOUTH);

        hienThiDanhSachNhanVienKho();
    }

    public void hienThiDanhSachNhanVienKho() throws ClassNotFoundException {
        List<NhanVienKho> list = controller.layTatCaNhanVienKho();
        tblNhanVienKho.setModel(new NhanVienKhoTableModel(list));
    }

    public void xuLiYeuCauTimKiemNhanVienKho() throws ClassNotFoundException {
        String kw = txtSearch.getText();
        List<NhanVienKho> list = controller.timKiemNhanVienKho(kw);
        tblNhanVienKho.setModel(new NhanVienKhoTableModel(list));
    }

    public void xuLiLayThongTinNhanVienKho() throws ClassNotFoundException {
        int row = tblNhanVienKho.getSelectedRow();
        if (row < 0) {
            return;
        }
        int id = (int) tblNhanVienKho.getValueAt(row, 0); // column Mã KH
        FormSuaNVKho form = new FormSuaNVKho(controller.layThongTinNhanVienKho(id));
        form.setVisible(true);
        hienThiDanhSachNhanVienKho();
    }

    public void xuLiXoaNhanVienKho() throws ClassNotFoundException, Exception {
        int row = tblNhanVienKho.getSelectedRow();
        if (row < 0) {
            return;
        }
        int idNhanVien = (int) tblNhanVienKho.getValueAt(row, 0);

        int idTaiKhoan = controller.getIdTaiKhoanByNhanVienKho(idNhanVien);

        int choice = JOptionPane.showConfirmDialog(this, "Xóa nhân viên kho?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            controller.xoaNhanVienKho(idTaiKhoan);
            hienThiDanhSachNhanVienKho();
            JOptionPane.showMessageDialog(this, "Xóa thành công");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
            }

            try {
                JFrame frame = new JFrame("Quản Lý Nhân Viên Kho");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(1300, 600);
                frame.setLocationRelativeTo(null); 

                GUI_QLNVKho panel = new GUI_QLNVKho();
                frame.setContentPane(panel);
                
                frame.setVisible(true);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(GUI_QLKH.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

}
