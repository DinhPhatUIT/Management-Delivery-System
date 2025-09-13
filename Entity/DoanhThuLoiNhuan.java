/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appgiaovan.Entity;




public class DoanhThuLoiNhuan {
    private int thang;
    private Double doanhThu;

    // Constructor mặc định
    public DoanhThuLoiNhuan() {}

    // Constructor có tham số
    public DoanhThuLoiNhuan(int thang, Double doanhThu) {
        this.thang = thang;
        this.doanhThu = doanhThu;
    }

    // Getters and Setters
    public int getThang() {
        return thang;
    }

    public void setThang(int thang) {
        this.thang = thang;
    }

    public Double getDoanhThu() {
        return doanhThu;
    }

    public void setDoanhThu(Double doanhThu) {
        this.doanhThu = doanhThu;
    }

    
}

