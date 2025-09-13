/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package appgiaovan.CustomerGUI;

import appgiaovan.Controller.QLDonHangController;
import appgiaovan.GUI.Components.RoundedPanel;
import javax.swing.*;
import java.awt.*;
import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.data.category.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
 public class KhachHangMainPanel extends JPanel {
     QLDonHangController qlDonHangController=new QLDonHangController();
    public KhachHangMainPanel(int ID_KhachHang) throws SQLException, ClassNotFoundException{
        setLayout(new BorderLayout());
        JPanel mainPanel = new JPanel(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);
        
        JPanel statPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        statPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        int TongSoDon=qlDonHangController.LayTongSoDon(ID_KhachHang);
        int SoDonDaGiao=qlDonHangController.LayTongSoDonDaGiao(ID_KhachHang);
        int SoDonDangVanChuyen=(TongSoDon-SoDonDaGiao);
        statPanel.add(RoundedPanel.createStatBox("Tổng số đơn", Integer.toString(TongSoDon), "", new Color(76, 175, 80)));
        statPanel.add(RoundedPanel.createStatBox("Đã giao", Integer.toString(SoDonDaGiao), "", new Color(33, 150, 243)));
        statPanel.add(RoundedPanel.createStatBox("Đang vận chuyển", Integer.toString(SoDonDangVanChuyen), "", new Color(255, 152, 0)));

        mainPanel.add(statPanel, BorderLayout.NORTH);

        // Biểu đồ (sử dụng JFreeChart)
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(TongSoDon, "Số lượng", "Tổng đơn");
        dataset.addValue(SoDonDaGiao, "Số lượng", "Đã giao");
        dataset.addValue(SoDonDangVanChuyen, "Số lượng", "Đang giao");

        JFreeChart barChart = ChartFactory.createBarChart(
        "Thống kê đơn hàng",
        "Trạng thái",
        "Số đơn",
        dataset,
        PlotOrientation.VERTICAL,
        false, true, false
    );

        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new Dimension(800, 300));
        mainPanel.add(chartPanel, BorderLayout.CENTER);
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Test Employee Main Panel");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1000, 600);
            frame.setLocationRelativeTo(null);
            frame.setLayout(new BorderLayout());

            try {
                frame.add(new KhachHangMainPanel(8), BorderLayout.CENTER);
            } catch (SQLException ex) {
                Logger.getLogger(KhachHangMainPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(KhachHangMainPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            frame.setVisible(true);
        });
    }
    
}
