package appgiaovan.EmployeeGUI;

import appgiaovan.DAO.DoanhThuLoiNhuanDAO;
import appgiaovan.DAO.DonHangDAO;
import appgiaovan.DAO.KhoHangDAO;
import appgiaovan.Entity.DoanhThuLoiNhuan;
import appgiaovan.Entity.KhoHang;
import appgiaovan.GUI.Components.RoundedPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;

public class EmployeeMainPanel extends JPanel {

    private DefaultCategoryDataset dataset = new DefaultCategoryDataset();

    public EmployeeMainPanel() throws SQLException, ClassNotFoundException {
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel statPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        statPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        java.util.List<DoanhThuLoiNhuan> list = new DoanhThuLoiNhuanDAO().getListDoanhThuLoiNhuan();

        double TongDoanhThu = 0;
        for (DoanhThuLoiNhuan dtln : list) {
            TongDoanhThu += dtln.getDoanhThu();
        }

        int TongDonHang = DonHangDAO.TongDonHang();
        KhoHang khoHang = KhoHangDAO.LayThongTinKho(1);
        int tonKho = khoHang.getSlHangTon();

        DecimalFormat df = new DecimalFormat("#,###");

        String doanhThuStr = df.format(TongDoanhThu) + " VND";
        String donHangStr = df.format(TongDonHang);
        String tonKhoStr = df.format(tonKho) + " sản phẩm";

        statPanel.add(RoundedPanel.createStatBox("DOANH THU THÁNG", doanhThuStr, "", new Color(76, 175, 80)));
        statPanel.add(RoundedPanel.createStatBox("TỔNG ĐƠN HÀNG THÁNG", donHangStr, "", new Color(255, 152, 0)));
        statPanel.add(RoundedPanel.createStatBox("TỒN KHO", tonKhoStr, "", new Color(121, 85, 72)));

        mainPanel.add(statPanel, BorderLayout.NORTH);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM");

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
        mainPanel.add(chartPanel, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);

        int delay = 600;
        new javax.swing.Timer(delay, e -> loadChartData()).start();

    }

    public void loadChartData() {
        try {
            java.util.List<DoanhThuLoiNhuan> list = new DoanhThuLoiNhuanDAO().getListDoanhThuLoiNhuan();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM");
            dataset.clear();

            for (DoanhThuLoiNhuan dtln : list) {
                String ngay = sdf.format(dtln.getThang());
                dataset.addValue(dtln.getDoanhThu(), "Doanh thu", ngay);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi tải dữ liệu: " + ex.getMessage());
        }
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
                frame.add(new EmployeeMainPanel(), BorderLayout.CENTER);
            } catch (SQLException ex) {
                Logger.getLogger(EmployeeMainPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(EmployeeMainPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            frame.setVisible(true);
        });
    }

}
