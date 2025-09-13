/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appgiaovan.Entity;

/**
 *
 * @author ASUS
 */
import java.util.*;

public class BaoCao {
    protected Integer idBaoCao;
    protected Integer idNhanVien;
    protected Date ngayKhoiTao;

    public Integer getIdBaoCao() {
        return idBaoCao;
    }

    public void setIdBaoCao(Integer idBaoCao) {
        this.idBaoCao = idBaoCao;
    }

    public Integer getIdNhanVien() {
        return idNhanVien;
    }

    public void setIdNhanVien(Integer idNhanVien) {
        this.idNhanVien = idNhanVien;
    }

    public Date getNgayKhoiTao() {
        return ngayKhoiTao;
    }

    public void setNgayKhoiTao(Date ngayKhoiTao) {
        this.ngayKhoiTao = ngayKhoiTao;
    }
    
}
