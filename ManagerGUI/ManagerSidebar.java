/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appgiaovan.ManagerGUI;
import appgiaovan.GUI.Components.MenuBar;
import java.util.Arrays;
import java.util.List;
import java.awt.BorderLayout;
import java.sql.SQLException;
import javax.swing.JFrame;

/**
 *
 * @author pc
 */
public class ManagerSidebar extends MenuBar {
    private static final List<String>  items = Arrays.asList("Trang chủ", "Quản lý nhân viên kho","Quản lý shipper", "Quản lý khách hàng","Xem báo cáo", "Báo cáo thống kê","Thông tin cá nhân",  "Đăng xuất");
      
    private static final List<String>  icons = Arrays.asList("home.png", "employee.png", "employee.png", "customer.png","report.png", "statistic.png", "customer.png", "logout.png");

    public ManagerSidebar(int idtk) throws SQLException, ClassNotFoundException {
        super(ManagerSidebar.items, ManagerSidebar.icons, idtk);
    }



    public static void main(String[] args) {
        try {
            JFrame frame = new JFrame("Giao diện Quản Lý");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1300, 600);
            frame.setLocationRelativeTo(null);

            ManagerSidebar sidebar = new ManagerSidebar(6);
            frame.add(sidebar, BorderLayout.WEST); 
            frame.setVisible(true);
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }


    
}



