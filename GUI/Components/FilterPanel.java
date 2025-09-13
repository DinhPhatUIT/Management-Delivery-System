/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appgiaovan.GUI.Components;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import java.awt.*;

public class FilterPanel extends JPanel {

    public FilterPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        setBackground(Color.WHITE);

        // TextField - ID
        JTextField idField = new JTextField("ID");
        idField.setPreferredSize(new Dimension(80, 30));
        add(idField);

        // ComboBox - Trạng thái
        JComboBox<String> statusComboBox = new JComboBox<>(new String[]{"Trạng thái"});
        statusComboBox.setPreferredSize(new Dimension(120, 30));
        add(statusComboBox);

        // TextField - Khách hàng
        JTextField customerField = new JTextField("Text");
        customerField.setPreferredSize(new Dimension(100, 30));
        add(customerField);

        // Button - Lọc (màu xanh đậm)
        JButton filterButton = new JButton("Lọc");
        filterButton.setPreferredSize(new Dimension(60, 30));
        filterButton.setBackground(new Color(0, 136, 153));
        filterButton.setForeground(Color.WHITE);
        RoundedButton roundedfilterBtn = new RoundedButton(filterButton, 20);
        add(roundedfilterBtn);

        // Button - Thêm mới (màu xanh lá)
        JButton addButton = new JButton("Thêm mới");
        addButton.setPreferredSize(new Dimension(100, 30));
        addButton.setBackground(new Color(0, 153, 76));
        addButton.setForeground(Color.WHITE);
        RoundedButton roundedaddBtn = new RoundedButton(addButton, 20);
        add(roundedaddBtn);

        // JButton - Thao tác (màu xanh dương)
        JButton actionButton = new JButton("Phân công");
        actionButton.setPreferredSize(new Dimension(100, 30));
        actionButton.setBackground(new Color(0, 123, 255));
        actionButton.setForeground(Color.WHITE);
        RoundedButton roundedactionBtn = new RoundedButton(actionButton, 20);
        add(roundedactionBtn);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(new FlatLightLaf());
            } catch (Exception ex) {
                System.err.println("Không thể cài đặt FlatLaf");
            }
            JFrame frame = new JFrame("Lọc đơn hàng");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(900, 120);
            frame.setLocationRelativeTo(null);
            frame.add(new FilterPanel());
            frame.setVisible(true);
        });
    }
}
