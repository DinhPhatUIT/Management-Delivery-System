/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package appgiaovan.CustomerGUI;

import appgiaovan.GUI.Components.MenuBar;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class CustomerSidebar extends MenuBar {

    private static final List<String> items = Arrays.asList("Trang chủ", "Tạo đơn hàng", "Tra cứu đơn hàng","Thông tin cá nhân", "Đăng xuất");
    private static final List<String> icons = Arrays.asList("home.jpg", "order.png", "package.png","customer.png", "logout.png");
    public CustomerSidebar(int ID_TaiKhoan) throws SQLException, ClassNotFoundException {
        super(CustomerSidebar.items, CustomerSidebar.icons, ID_TaiKhoan);
    }
}

