/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appgiaovan.EmployeeGUI;

import appgiaovan.DAO.KhoHangDAO;
import appgiaovan.Entity.KhoHang;
import appgiaovan.GUI.Components.RoundedButton;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TopPanelTGH extends JPanel {

    private final KhoHangDAO khoHangDAO = new KhoHangDAO();
    private final JComboBox khoDenComboBox;
    private JButton addButton = new JButton("Đóng gói");

    public TopPanelTGH() throws SQLException, ClassNotFoundException {
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        setBackground(Color.WHITE);

        // TextField - ID
        List<KhoHang> listKho = khoHangDAO.LayThongTinKho();
        khoDenComboBox = new JComboBox();

        for (KhoHang kho : listKho) {
            khoDenComboBox.addItem(kho);
        }
        khoDenComboBox.setPreferredSize(new Dimension(170, 50));

        khoDenComboBox.setBorder(BorderFactory.createTitledBorder("Chọn kho đến"));
        add(khoDenComboBox);

        addButton.setPreferredSize(new Dimension(100, 30));
        addButton.setBackground(new Color(0, 153, 76));
        addButton.setForeground(Color.WHITE);
        addButton = new RoundedButton(addButton, 20);
        add(addButton);

    }

    public KhoHang getKhoHangDen() {

        KhoHang selectedKho = (KhoHang) khoDenComboBox.getSelectedItem();
        return selectedKho;
    }

    public JButton getAddButton() {
        System.out.println("hello world");
        return this.addButton;
    }

    public static void main(String[] args) {
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
                frame.add(new TopPanelTGH());
            } catch (SQLException ex) {
                Logger.getLogger(TopPanelTGH.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(TopPanelTGH.class.getName()).log(Level.SEVERE, null, ex);
            }
            frame.setVisible(true);
        });
    }
}
