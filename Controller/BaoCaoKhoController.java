/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appgiaovan.Controller;

import appgiaovan.DAO.BaoCaoDAO;
import appgiaovan.Entity.BaoCaoKho;
import java.sql.SQLException;

/**
 *
 * @author HP
 */
public class BaoCaoKhoController {
    
    
    public void GuiBaoCao(BaoCaoKho baoCao, int idnvk) throws SQLException, ClassNotFoundException{
        BaoCaoDAO.GuiBaoCaoKho(baoCao, idnvk);
    }
}
