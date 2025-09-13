/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package appgiaovan.CustomerGUI;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class TableListDonHang extends JPanel {

    public TableListDonHang(String[] columnNames, Object[][] data) {
        setLayout(new BorderLayout());

        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            public boolean isCellEditable(int row, int column) {
                return column == 0; // Chỉ checkbox có thể chỉnh sửa
            }

            public Class<?> getColumnClass(int column) {
                return column == 0 ? Boolean.class : String.class;
            }
        };

        JTable table = new JTable(model) {
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(242, 247, 255));
                } else {
                    c.setBackground(new Color(200, 230, 255));
                }

                if (c instanceof JComponent) {
                    ((JComponent) c).setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                }

                return c;
            }
        };

        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(70);
        table.setShowGrid(true);
        table.setGridColor(new Color(230, 230, 230));
        table.setIntercellSpacing(new Dimension(1, 1));

        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setVerticalAlignment(SwingConstants.TOP);

        for (int i = 1; i < columnNames.length; i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }

        if (columnNames.length > 0) {
            table.getColumnModel().getColumn(0).setMaxWidth(30); // Checkbox
        }

        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            String[] columns = {"", "ID", "Khách hàng", "Sản phẩm", "ĐVT", "Giá", "SL"};
            Object[][] data = {
                {false, "<html><b style='color:#007bff;'>93900415</b><br><span style='color:gray;font-size:10px;'>10:48 15/04</span><br><span style='color:#007bff;'>Kho 2 - Tháo</span></html>",
                        "<html><span style='color:#007bff;'>0254654141 ▼</span><br><b>Triệu Mạnh Tùng</b><br><span style='color:gray;'>Số 15, ngõ 207</span><br><span style='color:gray;'>Quận Hoàng Mai, Hà Nội</span></html>",
                        "Váy hoa big size - XL - Đỏ thọ đen", "", "<html><b>400.000</b><br><span style='color:red;'>- 80.000</span></html>", 1},
                {false, "<html><b style='color:#007bff;'>93200103</b><br><span style='color:gray;font-size:10px;'>18:48 10/04</span><br><span style='color:#007bff;'>Kho 2 - Tháo</span></html>",
                        "<html><span style='color:#007bff;'>0238024020 ▼</span><br><b>Vũ Tiến Tài</b><br><span style='color:gray;'>Số 34 Ngách 173/68 Hoàng Hoa Thám, Hà Nội</span><br><i style='color:gray;'>(Cập đồng)</i><br><span style='color:gray;'>Quận Hai Bà Trưng, Hà Nội</span></html>",
                        "Váy hoa big size - Xanh Neon - Đỏ thọ đen", "", "<html><b>400.000</b><br><span style='color:red;'>- 40.000</span></html>", 2}
            };

            JFrame frame = new JFrame("Danh sách đơn hàng");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1000, 350);
            frame.setLocationRelativeTo(null);
            frame.add(new TableListDonHang(columns, data));
            frame.setVisible(true);
        });
    }
}