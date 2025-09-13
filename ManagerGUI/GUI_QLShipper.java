/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appgiaovan.ManagerGUI;

import appgiaovan.Controller.QLShipperController;
import appgiaovan.Entity.NhanVienGiaoHang;
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
public class GUI_QLShipper extends JPanel {
    private QLShipperController controller;
    private JTable tblNhanVienGiaoHang;
    private JTextField txtSearch;
    private TaiKhoan tk;
    public GUI_QLShipper() throws ClassNotFoundException {
        controller = new QLShipperController();
        setLayout(new BorderLayout());
        initUI();
    }

    private void initUI() throws ClassNotFoundException {
        JPanel pnlTop = new JPanel();
        txtSearch = new JTextField(20);
        JButton btnSearch = new JButton("Tìm kiếm");
        btnSearch.addActionListener(e -> {
            try {
                xuLiYeuCauTimKiemNhanVienGiaoHang();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(GUI_QLShipper.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        pnlTop.add(txtSearch);
        pnlTop.add(btnSearch);
        add(pnlTop, BorderLayout.NORTH);

        tblNhanVienGiaoHang = new JTable();
        add(new JScrollPane(tblNhanVienGiaoHang), BorderLayout.CENTER);

        JButton btnAdd = new JButton("Thêm");
        btnAdd.addActionListener(e -> {
            FormThemShipper form = null;
            try {
                form = new FormThemShipper();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(GUI_QLShipper.class.getName()).log(Level.SEVERE, null, ex);
            }
            form.setVisible(true);
            try {
                hienThiDanhSachNhanVienGiaoHang();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(GUI_QLShipper.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        JButton btnEdit = new JButton("Sửa");
        btnEdit.addActionListener(e -> {
            try {
                xuLiLayThongTinNhanVienGiaoHang();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(GUI_QLShipper.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        JButton btnDelete = new JButton("Xóa");
        btnDelete.addActionListener(e -> {
            try {
                xuLiXoaNhanVienGiaoHang();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(GUI_QLShipper.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(GUI_QLShipper.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        JPanel pnlButtons = new JPanel();
        pnlButtons.add(btnAdd);
        pnlButtons.add(btnEdit);
        pnlButtons.add(btnDelete);
        add(pnlButtons, BorderLayout.SOUTH);

        hienThiDanhSachNhanVienGiaoHang();
    }

    public void hienThiDanhSachNhanVienGiaoHang() throws ClassNotFoundException {
        List<NhanVienGiaoHang> list = controller.layTatCaNhanVienGiaoHang();
        tblNhanVienGiaoHang.setModel(new NhanVienGiaoHangTableModel(list));
    }

    public void xuLiYeuCauTimKiemNhanVienGiaoHang() throws ClassNotFoundException {
        String kw = txtSearch.getText();
        List<NhanVienGiaoHang> list = controller.timKiemNhanVienGiaoHang(kw);
        tblNhanVienGiaoHang.setModel(new NhanVienGiaoHangTableModel(list));
    }

    public void xuLiLayThongTinNhanVienGiaoHang() throws ClassNotFoundException {
        int row = tblNhanVienGiaoHang.getSelectedRow();
        if (row < 0) {
            return;
        }
        int id = (int) tblNhanVienGiaoHang.getValueAt(row, 0); // column Mã KH
        FormSuaShipper form = new FormSuaShipper(controller.layThongTinNhanVienGiaoHang(id));
        form.setVisible(true);
        hienThiDanhSachNhanVienGiaoHang();
    }

    public void xuLiXoaNhanVienGiaoHang() throws ClassNotFoundException, Exception {
        int row = tblNhanVienGiaoHang.getSelectedRow();
        if (row < 0) {
            return;
        }
        int idNhanVien = (int) tblNhanVienGiaoHang.getValueAt(row, 0);

        int idTaiKhoan = controller.getIdTaiKhoanByNhanVienGiaoHang(idNhanVien);

        int choice = JOptionPane.showConfirmDialog(this, "Xóa nhân viên giao hàng?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            controller.xoaNhanVienGiaoHang(idTaiKhoan);
            hienThiDanhSachNhanVienGiaoHang();
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
                JFrame frame = new JFrame("Quản Lý Nhân Viên Giao Hang");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(1300, 600);
                frame.setLocationRelativeTo(null); 

                GUI_QLShipper panel = new GUI_QLShipper();
                frame.setContentPane(panel);
                
                frame.setVisible(true);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(GUI_QLKH.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

}
