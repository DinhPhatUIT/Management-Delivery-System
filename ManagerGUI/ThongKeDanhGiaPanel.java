/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appgiaovan.ManagerGUI;

import appgiaovan.Controller.ThongKeController;
import appgiaovan.EmailSender;
import appgiaovan.Entity.TK_DanhGia;
import appgiaovan.report.ExportPDF;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author HP
 */
public class ThongKeDanhGiaPanel extends JPanel {

    private ThongKeController controller;

    public ThongKeDanhGiaPanel() {
        List<TK_DanhGia> list = null;
        setLayout(new BorderLayout());
        try {
            controller = new ThongKeController();
        } catch (SQLException ex) {
            Logger.getLogger(ThongKeDanhGiaPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ThongKeDanhGiaPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            list = controller.getListTKDanhGia(); // Giả sử DAO tồn tại
            System.out.println(list.size());
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ThongKePanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (list == null || list.isEmpty()) {
            add(new JLabel("Không có dữ liệu đánh giá", JLabel.CENTER), BorderLayout.CENTER);
            return;
        }

        TK_DanhGia latest = list.get(list.size() - 1);

        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("1 sao", latest.getSoLuong1Sao());
        dataset.setValue("2 sao", latest.getSoLuong2Sao());
        dataset.setValue("3 sao", latest.getSoLuong3Sao());
        dataset.setValue("4 sao", latest.getSoLuong4Sao());
        dataset.setValue("5 sao", latest.getSoLuong5Sao());

        JFreeChart pieChart = ChartFactory.createPieChart(
                "Tỷ lệ đánh giá sao", dataset, true, true, false);

        PiePlot plot = (PiePlot) pieChart.getPlot();
        plot.setSectionPaint("1 sao", new Color(244, 67, 54));
        plot.setSectionPaint("2 sao", new Color(255, 152, 0));
        plot.setSectionPaint("3 sao", new Color(255, 235, 59));
        plot.setSectionPaint("4 sao", new Color(76, 175, 80));
        plot.setSectionPaint("5 sao", new Color(33, 150, 243));
        plot.setLabelFont(new Font("Segoe UI", Font.PLAIN, 12));
        plot.setSimpleLabels(true);
        plot.setCircular(true);

        ChartPanel chartPanel = new ChartPanel(pieChart);
        chartPanel.setPreferredSize(new Dimension(500, 300));
        chartPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        
        JPanel infoPanel = new JPanel(new GridLayout(2, 2, 10, 5));
        infoPanel.setBorder(BorderFactory.createTitledBorder("Thông tin tổng quan"));

        JLabel lblTong = new JLabel("Tổng lượt đánh giá:");
        JTextField txtTong = new JTextField(String.valueOf(latest.getTongLuotDanhGia()));
        txtTong.setEditable(false);

        JLabel lblNgay = new JLabel("Ngày báo cáo:");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        JTextField txtNgay = new JTextField(sdf.format(latest.getNgay()));
        txtNgay.setEditable(false);

        infoPanel.add(lblTong);
        infoPanel.add(txtTong);
        infoPanel.add(lblNgay);
        infoPanel.add(txtNgay);

        
        add(infoPanel, BorderLayout.NORTH);
        add(chartPanel, BorderLayout.CENTER);

        
        JButton btnExportPDF = new JButton("Xuất PDF");
        btnExportPDF.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnExportPDF.setBackground(new Color(0, 123, 255));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnExportPDF);

        btnExportPDF.addActionListener(e -> {
            try {

                ExportPDF.exportDanhGia(latest, pieChart);
            } catch (Exception ex) {
                Logger.getLogger(ThongKeDanhGiaPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        JButton btnSendMail = new JButton("Gửi Email");
        btnSendMail.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnSendMail.setBackground(new Color(33, 150, 243));
        btnSendMail.setFocusPainted(false);
        btnSendMail.addActionListener(e -> {
            EmailSender.sendFileByEmail(); 
        });

        buttonPanel.add(btnSendMail);
        add(buttonPanel, BorderLayout.SOUTH);

    }

    static public void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
            }

            JFrame frame = new JFrame("Quản Lý Khách Hàng");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1300, 600);
            frame.setLocationRelativeTo(null); 
            ThongKeDanhGiaPanel panel = new ThongKeDanhGiaPanel();
            frame.setContentPane(panel);
            frame.setVisible(true);
        });
    }
}
