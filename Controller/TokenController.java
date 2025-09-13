/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appgiaovan.Controller;
import appgiaovan.DAO.TokenDAO;
import java.sql.SQLException;

/**
 *
 * @author ASUS
 */
public class TokenController {
    private TokenDAO token =  new TokenDAO();
    public int TaoToken(String username) throws SQLException, ClassNotFoundException{
        return token.taoToken(username);
    }
    public void capNhatToken(int idToken) throws SQLException, ClassNotFoundException{
        token.capNhatToken(idToken);
    }
}
