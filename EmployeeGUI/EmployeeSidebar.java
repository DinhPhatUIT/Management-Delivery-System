package appgiaovan.EmployeeGUI;

import appgiaovan.GUI.Components.MenuBar;
import java.util.Arrays;
import java.util.List;
import java.sql.SQLException;


public class EmployeeSidebar extends MenuBar {

    private static final List<String> items = Arrays.asList("Trang chủ", "Báo cáo", "Quản lý đơn hàng", "Quản lý gói hàng","Thông tin cá nhân", "Đăng xuất");
    private static final List<String> icons = Arrays.asList("home.png", "report.png", "order.png", "package.png","customer.png", "logout.png");

    public EmployeeSidebar(int idTaiKhoan) throws SQLException, ClassNotFoundException {
            super(EmployeeSidebar.items, EmployeeSidebar.icons,idTaiKhoan);
    }
    
}
