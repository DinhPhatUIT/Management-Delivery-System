/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appgiaovan.Controller;

import appgiaovan.DAO.BaoCaoDAO;

/**
 *
 * @author ASUS
 */
public class LapBaoCaoController {

    public LapBaoCaoController() {
    }
    
    public void ThemBaoCao(int idtk,int dagiao,int thatbai,int cod) {
        new BaoCaoDAO().BaoCaoGiaoHang(idtk, dagiao, thatbai, cod);
    }
    
}
