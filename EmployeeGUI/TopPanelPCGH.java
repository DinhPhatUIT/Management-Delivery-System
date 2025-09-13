/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appgiaovan.EmployeeGUI;

import appgiaovan.DAO.NhanVienGiaoHangDAO;
import appgiaovan.Entity.NhanVienGiaoHang;
import appgiaovan.GUI.Components.RoundedButton;
import appgiaovan.GUI.Components.RoundedComboBox;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TopPanelPCGH extends JPanel {

    private JButton btnXacNhan = new JButton("Xác nhận");
    private JComboBox cbSelectShipper;
    private NhanVienGiaoHangDAO nhanVienGiaoHangDAO = new NhanVienGiaoHangDAO();
    public TopPanelPCGH(int idKho) throws SQLException, ClassNotFoundException {
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        setBackground(Color.WHITE);

        List<NhanVienGiaoHang> dsShipper = nhanVienGiaoHangDAO.LayDSNhanVienGiaoHangTheoKho(idKho);
        cbSelectShipper = new RoundedComboBox();
        for(NhanVienGiaoHang nv : dsShipper){
            cbSelectShipper.addItem(nv);
        }
        cbSelectShipper.setPreferredSize(new Dimension(160, 40));
        cbSelectShipper.setBorder(BorderFactory.createTitledBorder("Chọn nhân viên giao hàng"));
        add(cbSelectShipper);
        btnXacNhan.setPreferredSize(new Dimension(80, 30));
        btnXacNhan.setBackground(new Color(0, 153, 76));
        btnXacNhan.setForeground(Color.WHITE);
        btnXacNhan = new RoundedButton(btnXacNhan, 20);
        add(btnXacNhan);

    }

 

    public JButton getBtnXacNhan() {
        return this.btnXacNhan;
    }
    public NhanVienGiaoHang getNVGiaoHang(){
        return (NhanVienGiaoHang) cbSelectShipper.getSelectedItem();
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
                frame.add(new TopPanelPCGH(1));
            } catch (SQLException ex) {
                Logger.getLogger(TopPanelPCGH.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(TopPanelPCGH.class.getName()).log(Level.SEVERE, null, ex);
            }
            frame.setVisible(true);
        });
    }
}
