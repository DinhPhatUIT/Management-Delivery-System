package appgiaovan.EmployeeGUI;

import appgiaovan.Entity.DonHang;
import appgiaovan.Entity.GoiHang;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class TableDonHang extends JPanel {

    private final JTable table;
    private DefaultTableModel model;

    public TableDonHang(String[] columnNames, Object[][] data) {
        setLayout(new BorderLayout());

        model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0;
            }

            @Override
            public Class<?> getColumnClass(int column) {
                return column == 0 ? Boolean.class : String.class;
            }
        };

        table = new JTable(model) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(242, 247, 255));
                } else {
                    c.setBackground(new Color(200, 230, 255));
                }

                if (c instanceof JComponent jComponent) {
                    jComponent.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
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
            table.getColumnModel().getColumn(0).setMaxWidth(30); 
        }

        add(new JScrollPane(table), BorderLayout.CENTER);
    }

   
    public void setTableDataDonHang(List<DonHang> dsDonHang) throws SQLException, ClassNotFoundException {
        model.setRowCount(0); 
        
        String[] columns = DonHang.getTableHeaders();
        Object[][] data = new Object[dsDonHang.size()][columns.length];

        for (int i = 0; i < dsDonHang.size(); i++) {
            data[i] = dsDonHang.get(i).toTableRow();
        }
        setTableData(data);
    }
    
    public void setTableDataGoiHang(List<GoiHang> dsGoiHang) throws SQLException, ClassNotFoundException{
        model.setRowCount(0);
        String[] columns = GoiHang.getTableHeaders();
        Object[][] data = new Object[dsGoiHang.size()][columns.length];

        for (int i = 0; i < dsGoiHang.size(); i++) {
            data[i] = dsGoiHang.get(i).toTableRow();
        }
        setTableData(data);
        
    }
    
    public void setTableData(Object[][] newData) {
        model.setRowCount(0); 

        for (Object[] row : newData) {
            if (row.length > 0) {
                Object val = row[0];
                if (!(val instanceof Boolean)) {
                    if (val instanceof Integer integer) {
                        row[0] = integer != 0;
                    } else if (val instanceof String strVal) {
                        row[0] = strVal.equalsIgnoreCase("true") || strVal.equals("1");
                    } else {
                        row[0] = false; 
                    }
                }
            }
            model.addRow(row);
        }
    }

    public JTable getTable() {
        return table;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Test TableList");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 400);
            frame.setLocationRelativeTo(null);

            String[] columns = {"Chọn", "ID", "Tên", "Giá"};
            Object[][] data = {
                {false, "001", "Sản phẩm A", "100.000"},
                {true, "002", "Sản phẩm B", "200.000"},
                {false, "003", "Sản phẩm C", "150.000"}
            };

            TableDonHang tableList = new TableDonHang(columns, data);

            frame.add(tableList);
            frame.setVisible(true);

            new Timer(3000, e -> {
                Object[][] newData = {
                    {"true", "004", "Sản phẩm D", "300.000"},
                    {0, "005", "Sản phẩm E", "250.000"},
                    {1, "006", "Sản phẩm F", "280.000"},
                    {false, "007", "Sản phẩm G", "400.000"},
                };
                tableList.setTableData(newData);
            }).start();
        });
    }

    public int getRowCount() {
        return model.getRowCount();
    }
    
    public Object getValueAt(int row, int column){
        return model.getValueAt(row, column);
    }
}
