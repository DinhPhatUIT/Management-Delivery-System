// File: XemBaoCaoController.java
// Package: appgiaovan.Controller

package appgiaovan.Controller;
import appgiaovan.DAO.XemBaoCaoDAO;
import appgiaovan.Entity.BaoCaoGiaoHang;
import appgiaovan.Entity.BaoCaoKho;
import java.sql.SQLException;
import java.util.List;

public class XemBaoCaoController {
    private XemBaoCaoDAO dao = new XemBaoCaoDAO();

    public List<BaoCaoKho> fetchBaoCaoKho(String keyword) throws SQLException, ClassNotFoundException {
        return dao.getBaoCaoKho(keyword);
    }

    public List<BaoCaoGiaoHang> fetchBaoCaoGiaoHang(String keyword) throws SQLException, ClassNotFoundException {
        return dao.getBaoCaoGiaoHang(keyword);
    }
}
