/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appgiaovan.EmployeeGUI;

import appgiaovan.DAO.DonHangDAO;
import appgiaovan.DAO.GoiHangDAO;
import appgiaovan.Entity.GoiHang;
import appgiaovan.GUI.Components.RoundedButton;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TopPanelQLGH extends JPanel {

    private JButton addButton = new JButton("Thêm mới");
    private JButton filterButton = new JButton("Lọc");
    private JButton updateButton = new JButton("Hoàn thành");
    private final JTextField idField = new JTextField("");
    private final JComboBox<String> statusComboBox ;
    private final JTextField customerField = new JTextField("");
    private final DonHangDAO donHangDAO  = new DonHangDAO();
    private GoiHangDAO goiHangDAO = new GoiHangDAO();
    
    public TopPanelQLGH() throws SQLException, ClassNotFoundException, Exception  {
        
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        setBackground(Color.WHITE);

        // TextField - ID
        idField.setPreferredSize(new Dimension(80, 40));
        idField.setBorder(BorderFactory.createTitledBorder("ID"));
        add(idField);

        // ComboBox - Trạng thái
        String[] dsTrangThai = goiHangDAO.DSTrangThai();
        statusComboBox =  new JComboBox<>(dsTrangThai);
        statusComboBox.setPreferredSize(new Dimension(130, 40));
        statusComboBox.setBorder(BorderFactory.createTitledBorder("Trạng thái"));
        add(statusComboBox);


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

        updateButton.setPreferredSize(new Dimension(100, 30));
        updateButton.setBackground(new Color(0, 153, 76));
        updateButton.setForeground(Color.WHITE);
        updateButton = new RoundedButton(updateButton, 20);
        add(updateButton);

    }

    public JButton getaddButton() {
        return this.addButton;
    }
    
    public JButton getUpdateButton(){
        return this.updateButton;
    }

    public JButton getfilterButton() {
        return this.filterButton;
    }
    
    
    
    public GoiHang getGoiHang() {
        GoiHang goiHang = new GoiHang();

        String idText = idField.getText().trim();
        if (!idText.isEmpty()) {
            goiHang.setIdGoiHang(Integer.valueOf(idText));
        } else {
            goiHang.setIdGoiHang(null);
        }

        Object selected = statusComboBox.getSelectedItem();
        goiHang.setTrangThai(selected != null ? selected.toString() : null);

        
        return goiHang;
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
                frame.add(new TopPanelQLGH());
            } catch (SQLException ex) {
                Logger.getLogger(TopPanelQLGH.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(TopPanelQLGH.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(TopPanelQLGH.class.getName()).log(Level.SEVERE, null, ex);
            }
           
            
            frame.setVisible(true);
        });
    }
}
