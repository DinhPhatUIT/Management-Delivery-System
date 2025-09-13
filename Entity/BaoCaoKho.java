/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appgiaovan.Entity;

import java.util.Date;

/**
 *
 * @author HP
 */
public class BaoCaoKho extends BaoCao {

    private Date kyBaoCao;
    private Integer soGoiHangNhap;
    private Integer soGoiHangXuat;

    public void setKyBaoCao(Date kyBaoCao) {
        this.kyBaoCao = kyBaoCao;
    }

    public Date getKyBaoCao() {
        return this.kyBaoCao;
    }

    public void setSoGoiHangNhap(Integer soGoiHangNhap) {
        this.soGoiHangNhap = soGoiHangNhap;
    }

    public Integer getSoGoiHangNhap() {
        return this.soGoiHangNhap;
    }
    public void setSoGoiHangXuat(Integer soGoiHangXuat) {
        this.soGoiHangXuat = soGoiHangXuat;
    }

    public Integer getSoGoiHangXuat() {
        return this.soGoiHangXuat;
    }
    
}
