/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appgiaovan.ManagerGUI;

import appgiaovan.DAO.DoanhThuLoiNhuanDAO;
import appgiaovan.EmailSender;
import appgiaovan.Entity.DoanhThuLoiNhuan;
import appgiaovan.report.ExportPDF;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author HP
 */
public class ThongKeDoanhThuPanel extends JPanel {

    private String namBaoCao = "";
    private double tongDoanhThu = 0;

    public ThongKeDoanhThuPanel() {
        setLayout(new BorderLayout());
        List<DoanhThuLoiNhuan> list = null;
        try {
            list = new DoanhThuLoiNhuanDAO().getListDoanhThuLoiNhuan();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ThongKePanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        JPanel infoPanel = new JPanel(new GridLayout(2, 3, 10, 5));
        infoPanel.setBorder(BorderFactory.createTitledBorder("Thông tin báo cáo"));

        JLabel lblNam = new JLabel("Năm báo cáo:");
        JTextField txtNam = new JTextField();
        txtNam.setEditable(false);

        JLabel lblTongDoanhThu = new JLabel("Tổng doanh thu:");
        JTextField txtTongDoanhThu = new JTextField();
        txtTongDoanhThu.setEditable(false);


        infoPanel.add(lblNam);
        infoPanel.add(lblTongDoanhThu);
        infoPanel.add(txtNam);
        infoPanel.add(txtTongDoanhThu);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM");
        SimpleDateFormat thangFormat = new SimpleDateFormat("MM/yyyy");

        if (list != null && !list.isEmpty()) {
            tongDoanhThu = list.stream().mapToDouble(DoanhThuLoiNhuan::getDoanhThu).sum();
            int currentYear = LocalDate.now().getYear();
            namBaoCao = String.valueOf(currentYear);
        }

        txtNam.setText(namBaoCao);
        txtTongDoanhThu.setText(String.format("%.2f triệu", tongDoanhThu));

        add(infoPanel, BorderLayout.NORTH);

        // ======= BIỂU ĐỒ =========
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (DoanhThuLoiNhuan dtln : list) {
            int thang = dtln.getThang();
            dataset.addValue(dtln.getDoanhThu(), "Doanh thu", String.valueOf(thang));
        }

        JFreeChart lineChart = ChartFactory.createLineChart(
                "Biểu đồ Doanh thu (triệu VND)",
                "Tháng", "Giá trị", dataset,
                PlotOrientation.VERTICAL, true, true, false
        );

        CategoryPlot plot = lineChart.getCategoryPlot();
        plot.setBackgroundPaint(new GradientPaint(0, 0, new Color(245, 245, 245), 0, 600, Color.WHITE));
        plot.setOutlineVisible(false);
        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(new Color(200, 200, 200));
        plot.setDomainGridlinesVisible(false);

        LineAndShapeRenderer renderer = new LineAndShapeRenderer();
        renderer.setSeriesPaint(0, new Color(33, 150, 243));
        renderer.setSeriesStroke(0, new BasicStroke(3f));
        renderer.setSeriesShapesVisible(0, true);
        renderer.setSeriesShape(0, new java.awt.geom.Ellipse2D.Double(-4, -4, 8, 8));

        renderer.setSeriesPaint(1, new Color(76, 175, 80));
        renderer.setSeriesStroke(1, new BasicStroke(3f));
        renderer.setSeriesShapesVisible(1, true);
        renderer.setSeriesShape(1, new java.awt.geom.Rectangle2D.Double(-4, -4, 8, 8));

        plot.setRenderer(renderer);

        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
        domainAxis.setTickLabelFont(new Font("Segoe UI", Font.PLAIN, 12));
        domainAxis.setAxisLineVisible(false);

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setTickLabelFont(new Font("Segoe UI", Font.PLAIN, 12));
        rangeAxis.setAxisLineVisible(false);

        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new Dimension(900, 350));
        chartPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(chartPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton extractPDF = new JButton("Xuất PDF");
        extractPDF.setFont(new Font("Segoe UI", Font.BOLD, 13));
        extractPDF.setBackground(new Color(33, 150, 243));
        extractPDF.addActionListener(e -> {
            ExportPDF.exportDoanhThu(lineChart, namBaoCao, tongDoanhThu);
        });

        buttonPanel.add(extractPDF);
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
            ThongKeDoanhThuPanel panel = new ThongKeDoanhThuPanel();
            frame.setContentPane(panel);
            frame.setVisible(true);
        });
    }

}
