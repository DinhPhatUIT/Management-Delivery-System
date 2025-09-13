/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appgiaovan.EmployeeGUI;

import appgiaovan.DAO.DonHangDAO;
import appgiaovan.Entity.DonHang;
import appgiaovan.GUI.Components.RoundedButton;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TopPanelQLDH extends JPanel {

    private JButton addButton = new JButton("Thêm mới");
    private JButton filterButton = new JButton("Lọc");
    private JButton updateButton = new JButton("Sửa");
    private JButton deleteButton = new JButton("Hủy đơn hàng");
    private JButton refreshButton = new JButton("Refresh");

    private JButton phanCongButton = new JButton("Phân công giao hàng");
    private final JTextField idField = new JTextField("");
    private final JComboBox<String> statusComboBox;
    private final JTextField customerField = new JTextField("");
    private final DonHangDAO donHangDAO = new DonHangDAO();

    public TopPanelQLDH() throws SQLException, ClassNotFoundException {

        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        setBackground(Color.WHITE);

        // TextField - ID
        idField.setPreferredSize(new Dimension(80, 40));
        idField.setBorder(BorderFactory.createTitledBorder("ID"));
        add(idField);

        // ComboBox - Trạng thái
        String[] dsTrangThaiGoc = donHangDAO.DSTrangThai();
        String[] dsTrangThai = new String[dsTrangThaiGoc.length + 1];
        dsTrangThai[0] = "Tất cả"; // Thêm "Tất cả" vào đầu
        System.arraycopy(dsTrangThaiGoc, 0, dsTrangThai, 1, dsTrangThaiGoc.length);

        statusComboBox = new JComboBox<>(dsTrangThai);
        statusComboBox.setPreferredSize(new Dimension(130, 40));
        statusComboBox.setBorder(BorderFactory.createTitledBorder("Trạng thái"));
        add(statusComboBox);

        // TextField - Khách hàng
        customerField.setPreferredSize(new Dimension(120, 40));
        customerField.setBorder(BorderFactory.createTitledBorder("Tên khách hàng"));
        add(customerField);

        // Button - Lọc (màu xanh đậm)
        filterButton.setPreferredSize(new Dimension(60, 30));
        filterButton.setBackground(new Color(0, 136, 153));
        filterButton.setForeground(Color.WHITE);
        filterButton = new RoundedButton(filterButton, 20);
        add(filterButton);

        // Button - Thêm mới (màu xanh lá)
        addButton.setPreferredSize(new Dimension(100, 30));
        addButton.setBackground(new Color(0, 153, 76));
        addButton.setForeground(Color.WHITE);
        addButton = new RoundedButton(addButton, 20);
        add(addButton);

        deleteButton.setPreferredSize(new Dimension(120, 30));
        deleteButton.setBackground(new Color(200, 0, 0));
        deleteButton.setForeground(Color.white);
        deleteButton = new RoundedButton(deleteButton, 20);
        add(deleteButton);

        updateButton.setPreferredSize(new Dimension(100, 30));
        updateButton.setBackground(new Color(0, 153, 76));
        updateButton.setForeground(Color.WHITE);
        updateButton = new RoundedButton(updateButton, 20);
        add(updateButton);

        phanCongButton.setPreferredSize(new Dimension(150, 30));
        phanCongButton.setBackground(new Color(0, 153, 76));
        phanCongButton.setForeground(Color.WHITE);
        phanCongButton = new RoundedButton(phanCongButton, 20);
        add(phanCongButton);

        refreshButton.setPreferredSize(new Dimension(150, 30));
        refreshButton.setBackground(new Color(0, 136, 153));
        refreshButton.setForeground(Color.WHITE);
        refreshButton = new RoundedButton(refreshButton, 20);
        add(refreshButton);

    }

    public JButton getRefreshButton() {
        return this.refreshButton;
    }

    public JButton getaddButton() {
        return this.addButton;
    }

    public JButton getupdateButton() {
        return this.updateButton;
    }

    public JButton getfilterButton() {
        return this.filterButton;
    }

    public JButton getPhanCongButton() {
        return this.phanCongButton;
    }

    public JButton getDeleteButton() {
        return this.deleteButton;
    }

    public DonHang getDonHang() {
        DonHang dh = new DonHang();

        String idText = idField.getText().trim();
        if (!idText.isEmpty()) {
            dh.setIdDonHang(Integer.parseInt(idText));
        } else {
            dh.setIdDonHang(null);
        }

        Object selected = statusComboBox.getSelectedItem();
        dh.setTrangThai(selected != null ? selected.toString() : null);

        String name = customerField.getText().trim();
        dh.setTenNguoiGui(name.isEmpty() ? null : name);
        System.out.println(dh.getTrangThai());
        return dh;
    }

    static public void main(String[] args) {

        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ex) {
            System.err.println("Không thể cài đặt FlatLaf");
        }
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Lọc đơn hàng");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(900, 120);
            frame.setLocationRelativeTo(null);

            try {
                frame.add(new TopPanelQLDH());
            } catch (SQLException ex) {
                Logger.getLogger(TopPanelQLDH.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(TopPanelQLDH.class.getName()).log(Level.SEVERE, null, ex);
            }

            frame.setVisible(true);
        });
    }
}
