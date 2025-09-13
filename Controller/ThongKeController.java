package appgiaovan.Controller;
import appgiaovan.DAO.ThongKeDAO;
import appgiaovan.Entity.TK_DanhGia;
import appgiaovan.Entity.TK_DonHang;
import java.sql.SQLException;
import java.util.List;


public class ThongKeController {

    private ThongKeDAO dao;

    public ThongKeController() throws SQLException, ClassNotFoundException {
        dao = new ThongKeDAO();
    }
        
    public List<TK_DanhGia> getListTKDanhGia() throws SQLException, ClassNotFoundException {
        
        return ThongKeDAO.getListTKDanhGia();
    }

    public List<TK_DonHang> getListTKDonHang() throws SQLException, ClassNotFoundException {
        return ThongKeDAO.getListTKDonHang();

    }
    

}
