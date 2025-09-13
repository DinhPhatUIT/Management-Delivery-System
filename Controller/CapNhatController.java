/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appgiaovan.Controller;

import appgiaovan.DAO.DonHangDAO;
import java.sql.SQLException;

/**
 *
 * @author ASUS
 */
public class CapNhatController {
    private DonHangDAO dh = new DonHangDAO();
    public void CapNhatDHDaGiao(int iddh, String trangthai) throws SQLException, ClassNotFoundException{
        dh.CapNhatDH(iddh, trangthai);
    }
}
