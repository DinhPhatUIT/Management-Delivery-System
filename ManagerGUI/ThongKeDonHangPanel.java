/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appgiaovan.ManagerGUI;

import appgiaovan.Controller.ThongKeController;
import appgiaovan.EmailSender;
import appgiaovan.Entity.TK_DonHang;
import appgiaovan.report.ExportPDF;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author HP
 */
public class ThongKeDonHangPanel extends JPanel {

    private ThongKeController controller;
   private List<TK_DonHang> list = null;
    public ThongKeDonHangPanel() {
        setLayout(new BorderLayout());
        try {
            controller = new ThongKeController();
        } catch (SQLException ex) {
            Logger.getLogger(ThongKeDonHangPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ThongKeDonHangPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            list = controller.getListTKDonHang(); 
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ThongKePanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (list == null || list.isEmpty()) {
            add(new JLabel("Không có dữ liệu đơn hàng", JLabel.CENTER), BorderLayout.CENTER);
            return;
        }

        String[] columns = {"Ngày", "Tổng đơn hàng", "Đã giao", "Thất bại", "Đã huỷ"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        for (TK_DonHang tk : list) {
            Object[] row = {
                sdf.format(tk.getNgay()),
                tk.getTongSoDonHang(),
                tk.getSoLuongDaGiao(),
                tk.getSoLuongThatBai(),
                tk.getSoLuongDaHuy()
            };
            tableModel.addRow(row);
        }
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(600, 150)); // chiều cao cho bảng

        add(scrollPane, BorderLayout.NORTH);

        // --- Tạo dataset cho biểu đồ cột ---
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        SimpleDateFormat sdfChart = new SimpleDateFormat("dd/MM");
        for (TK_DonHang tk : list) {
            String dateStr = sdfChart.format(tk.getNgay());
            dataset.addValue(tk.getSoLuongDaGiao(), "Đã giao", dateStr);
            dataset.addValue(tk.getSoLuongThatBai(), "Thất bại", dateStr);
            dataset.addValue(tk.getSoLuongDaHuy(), "Đã huỷ", dateStr);
        }

        // Tạo biểu đồ cột
        JFreeChart barChart = ChartFactory.createBarChart(
                "Thống kê đơn hàng theo ngày",
                "Ngày",
                "Số lượng",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);

        // Tuỳ chỉnh biểu đồ
        CategoryPlot plot = (CategoryPlot) barChart.getPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();

        renderer.setSeriesPaint(0, new Color(76, 175, 80));  
        renderer.setSeriesPaint(1, new Color(244, 67, 54));  
        renderer.setSeriesPaint(2, new Color(255, 152, 0));  

        plot.setBackgroundPaint(Color.white);
        plot.setDomainGridlinePaint(Color.LIGHT_GRAY);
        plot.setRangeGridlinePaint(Color.LIGHT_GRAY);

        // Panel chứa biểu đồ
        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new Dimension(600, 400));
        chartPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(chartPanel, BorderLayout.CENTER);

        // --- Button Export PDF ---
        JPanel bottomPanel = new JPanel();
        javax.swing.JButton btnExport = new javax.swing.JButton("Xuất PDF");
        btnExport.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnExport.setBackground(new Color(33, 150, 243));
        btnExport.addActionListener(e -> {
            try {
                ExportPDF.exportDonHang(list, barChart);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        bottomPanel.add(btnExport);
        JButton btnSendMail = new JButton("Gửi Email");
        btnSendMail.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnSendMail.setBackground(new Color(33, 150, 243));
        btnSendMail.setFocusPainted(false);
        btnSendMail.addActionListener(e -> {
            EmailSender.sendFileByEmail(); 
        });
        bottomPanel.add(btnSendMail);
        add(bottomPanel, BorderLayout.SOUTH);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
            }

            JFrame frame = new JFrame("Quản Lý Khách Hàng");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1300, 600);
            frame.setLocationRelativeTo(null); 
            ThongKeDonHangPanel panel = new ThongKeDonHangPanel();
            frame.setContentPane(panel);
            frame.setVisible(true);
        });
    }
}
