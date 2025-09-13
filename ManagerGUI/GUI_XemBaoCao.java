

package appgiaovan.ManagerGUI;

import appgiaovan.Controller.XemBaoCaoController;
import appgiaovan.Entity.BaoCaoGiaoHang;
import appgiaovan.Entity.BaoCaoKho;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class GUI_XemBaoCao extends JPanel {
    private JTabbedPane tabbedPane;
    private JTextField txtSearchKho, txtSearchGiao;
    private JButton btnSearchKho, btnSearchGiao;
    private JTable tableKho, tableGiao;
    private DefaultTableModel modelKho, modelGiao;
    private XemBaoCaoController controller = new XemBaoCaoController();

    public GUI_XemBaoCao() {
        setLayout(new BorderLayout());
        tabbedPane = new JTabbedPane();

        JPanel pnlKho = new JPanel(new BorderLayout());
        JPanel topKho = new JPanel();
        txtSearchKho = new JTextField(20);
        btnSearchKho = new JButton("Tìm kiếm");
        topKho.add(new JLabel("Keyword (ngày):"));
        topKho.add(txtSearchKho);
        topKho.add(btnSearchKho);
        pnlKho.add(topKho, BorderLayout.NORTH);

        modelKho = new DefaultTableModel(new String[]{
            "ID BC", "ID NV", "Ngày tạo", "Kỳ báo cáo", "Gói nhập", "Gói xuất"
        }, 0);
        tableKho = new JTable(modelKho);
        pnlKho.add(new JScrollPane(tableKho), BorderLayout.CENTER);

        JPanel pnlGiao = new JPanel(new BorderLayout());
        JPanel topGiao = new JPanel();
        txtSearchGiao = new JTextField(20);
        btnSearchGiao = new JButton("Tìm kiếm");
        topGiao.add(new JLabel("Keyword (ngày):"));
        topGiao.add(txtSearchGiao);
        topGiao.add(btnSearchGiao);
        pnlGiao.add(topGiao, BorderLayout.NORTH);

        modelGiao = new DefaultTableModel(new String[]{
            "ID BC", "ID NV", "Ngày tạo", "Đã giao", "Thất bại", "Tiền COD"
        }, 0);
        tableGiao = new JTable(modelGiao);
        pnlGiao.add(new JScrollPane(tableGiao), BorderLayout.CENTER);

        tabbedPane.addTab("Báo Cáo Kho", pnlKho);
        tabbedPane.addTab("Báo Cáo Giao Hàng", pnlGiao);
        add(tabbedPane, BorderLayout.CENTER);

        btnSearchKho.addActionListener(e -> loadBaoCaoKho());
        btnSearchGiao.addActionListener(e -> loadBaoCaoGiaoHang());

        loadBaoCaoKho();
        loadBaoCaoGiaoHang();
    }

    private void loadBaoCaoKho() {
        String kw = txtSearchKho.getText().trim();
        try {
            List<BaoCaoKho> list = controller.fetchBaoCaoKho(kw);
            modelKho.setRowCount(0);
            for (BaoCaoKho bc : list) {
                modelKho.addRow(new Object[]{
                    bc.getIdBaoCao(),
                    bc.getIdNhanVien(),
                    bc.getNgayKhoiTao(),
                    bc.getKyBaoCao(),
                    bc.getSoGoiHangNhap(),
                    bc.getSoGoiHangXuat()
                });
            }
        } catch (SQLException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi tải báo cáo kho:\n" + ex.getMessage(),
                                          "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadBaoCaoGiaoHang() {
        String kw = txtSearchGiao.getText().trim();
        try {
            List<BaoCaoGiaoHang> list = controller.fetchBaoCaoGiaoHang(kw);
            modelGiao.setRowCount(0);
            for (BaoCaoGiaoHang bc : list) {
                modelGiao.addRow(new Object[]{
                    bc.getIdBaoCao(),
                    bc.getIdNhanVien(),
                    bc.getNgayKhoiTao(),
                    bc.getTongDonHangDaGiao(),
                    bc.getTongDHGiaoThatBai(),
                    bc.getTongTienCOD()
                });
            }
        } catch (SQLException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi tải báo cáo giao hàng:\n" + ex.getMessage(),
                                          "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public static void main(String[] args) {

    SwingUtilities.invokeLater(() -> {
        JFrame frame = new JFrame("Xem Báo Cáo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new GUI_XemBaoCao());
        frame.pack();             
        frame.setLocationRelativeTo(null); 
        frame.setVisible(true);
    });
}

}
