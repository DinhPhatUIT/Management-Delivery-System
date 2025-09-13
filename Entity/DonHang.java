
package appgiaovan.Entity;

import appgiaovan.DAO.KhoHangDAO;
import java.sql.SQLException;
import java.util.Date;

public class DonHang {

    private Integer idDonHang;
    private Integer idKhachHang;        
    private Integer idNVGiaoHang;
    private String sdtNguoiGui;
    private String sdtNguoiNhan;
    private Integer idKhoTiepNhan;
    private String tenNguoiGui;
    private String tenNguoiNhan;
    private String diaChiNhan;
    private Double tienCOD;
    private Double phi;
    private Date thoiGianNhan;
    private Date thoiGianTao;
    private Date thoiGianDuKien;
    private String trangThai;
    private String dichVu;
    private String loaiHangHoa;

    public DonHang(int idDonHang, Integer idKhachHang, Integer idNVGiaoHang, String sdtNguoiGui,
            String sdtNguoiNhan, Integer idKhoTiepNhan, String tenNguoiGui, String tenNguoiNhan,
            String diaChiNhan, Double tienCOD, Double phi, Date thoiGianNhan, Date thoiGianTao,
            Date thoiGianDuKien, String trangThai, String dichVu, String loaiHangHoa) {
        this.idDonHang = idDonHang;
        this.idKhachHang = idKhachHang;
        this.idNVGiaoHang = idNVGiaoHang;
        this.sdtNguoiGui = sdtNguoiGui;
        this.sdtNguoiNhan = sdtNguoiNhan;
        this.idKhoTiepNhan = idKhoTiepNhan;
        this.tenNguoiGui = tenNguoiGui;
        this.tenNguoiNhan = tenNguoiNhan;
        this.diaChiNhan = diaChiNhan;
        this.tienCOD = tienCOD;
        this.phi = phi;
        this.thoiGianNhan = thoiGianNhan;
        this.thoiGianTao = thoiGianTao;
        this.thoiGianDuKien = thoiGianDuKien;
        this.trangThai = trangThai;
        this.dichVu = dichVu;
        this.loaiHangHoa = loaiHangHoa;
    }

    public DonHang(Integer idNVGiaoHang, String sdtNguoiGui, String tenNguoiGui,
            String sdtNguoiNhan, String tenNguoiNhan,
            String diaChiNhan, String dichVu, String loaiHangHoa, Integer idKhoTiepNhan) {
        this.idDonHang = idDonHang;
        this.idKhachHang = idKhachHang;
        this.idNVGiaoHang = idNVGiaoHang;
        this.sdtNguoiGui = sdtNguoiGui;
        this.sdtNguoiNhan = sdtNguoiNhan;
        this.idKhoTiepNhan = idKhoTiepNhan;
        this.tenNguoiGui = tenNguoiGui;
        this.tenNguoiNhan = tenNguoiNhan;
        this.diaChiNhan = diaChiNhan;
        this.tienCOD = tienCOD;
        this.phi = phi;
        this.thoiGianNhan = thoiGianNhan;
        this.thoiGianTao = thoiGianTao;
        this.thoiGianDuKien = thoiGianDuKien;
        this.trangThai = trangThai;
        this.dichVu = dichVu;
        this.loaiHangHoa = loaiHangHoa;
    }
    public static String[] getTableHeaders() {
        return new String[]{" ", "ID", "Người gửi",  "Người nhận", "Trạng thái", "Loại dịch vụ", "Kho tiếp nhận", "Địa chỉ nhận"};
    }

    public Object[] toTableRow() throws SQLException, ClassNotFoundException {
        KhoHangDAO khoHangDAO = new KhoHangDAO();
        String TenKho = khoHangDAO.LayTenKho(idKhoTiepNhan);
        return new Object[]{"",idDonHang,  "<html>" + tenNguoiGui + "<br/>" + sdtNguoiGui + "</html>"
                ,  "<html>" + tenNguoiNhan + "<br/>" + sdtNguoiNhan + "</html>", trangThai,dichVu, TenKho, diaChiNhan};
    }
    
   
    public static String[] getTableHeaders1() {
        return new String[] {
            "", "Mã đơn hàng", "Tên người nhận", "Địa chỉ", "SĐT nhận",
            "Trạng thái", "Tiền COD", "Thời gian tạo", "SĐT gửi", "Tên người gửi"
        };
    }

    public Object[] toTableRow1() {
        return new Object[] {
            false,              
            idDonHang,
            tenNguoiNhan,
            diaChiNhan,
            sdtNguoiNhan,
            trangThai,
            tienCOD,
            thoiGianTao,
            sdtNguoiGui,
            tenNguoiGui,
            idKhoTiepNhan
        };
    }
    

    public DonHang() {
    }


    public Integer getIdDonHang() {
        return idDonHang;
    }

    public void setIdDonHang(Integer idDonHang) {
        this.idDonHang = idDonHang;
    }

    public void setIdDonHang(int idDonHang) {
        this.idDonHang = idDonHang;
    }

    public Integer getIdKhachHang() {
        return idKhachHang;
    }

    public void setIdKhachHang(Integer idKhachHang) {
        this.idKhachHang = idKhachHang;
    }

    public Integer getIdNVGiaoHang() {
        return idNVGiaoHang;
    }

    public void setIdNVGiaoHang(Integer idNVGiaoHang) {
        this.idNVGiaoHang = idNVGiaoHang;
    }

    public String getSdtNguoiGui() {
        return sdtNguoiGui;
    }

    public void setSdtNguoiGui(String sdtNguoiGui) {
        this.sdtNguoiGui = sdtNguoiGui;
    }

    public String getSdtNguoiNhan() {
        return sdtNguoiNhan;
    }

    public void setSdtNguoiNhan(String sdtNguoiNhan) {
        this.sdtNguoiNhan = sdtNguoiNhan;
    }

    public Integer getIdKhoTiepNhan() {
        return idKhoTiepNhan;
    }

    public void setIdKhoTiepNhan(Integer idKhoTiepNhan) {
        this.idKhoTiepNhan = idKhoTiepNhan;
    }

    public String getTenNguoiGui() {
        return tenNguoiGui;
    }

    public void setTenNguoiGui(String tenNguoiGui) {
        this.tenNguoiGui = tenNguoiGui;
    }

    public String getTenNguoiNhan() {
        return tenNguoiNhan;
    }

    public void setTenNguoiNhan(String tenNguoiNhan) {
        this.tenNguoiNhan = tenNguoiNhan;
    }

    public String getDiaChiNhan() {
        return diaChiNhan;
    }

    public void setDiaChiNhan(String diaChiNhan) {
        this.diaChiNhan = diaChiNhan;
    }

    public Double getTienCOD() {
        return tienCOD;
    }

    public void setTienCOD(Double tienCOD) {
        this.tienCOD = tienCOD;
    }

    public Double getPhi() {
        return phi;
    }

    public void setPhi(Double phi) {
        this.phi = phi;
    }

    public Date getThoiGianNhan() {
        return thoiGianNhan;
    }

    public void setThoiGianNhan(Date thoiGianNhan) {
        this.thoiGianNhan = thoiGianNhan;
    }

    public Date getThoiGianTao() {
        return thoiGianTao;
    }

    public void setThoiGianTao(Date thoiGianTao) {
        this.thoiGianTao = thoiGianTao;
    }

    public Date getThoiGianDuKien() {
        return thoiGianDuKien;
    }

    public void setThoiGianDuKien(Date thoiGianDuKien) {
        this.thoiGianDuKien = thoiGianDuKien;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getDichVu() {
        return dichVu;
    }

    public void setDichVu(String dichVu) {
        this.dichVu = dichVu;
    }

    public String getLoaiHangHoa() {
        return loaiHangHoa;
    }

    public void setLoaiHangHoa(String loaiHangHoa) {
        this.loaiHangHoa = loaiHangHoa;
    }
    static public void main(String[] args){
        
    }
    
}
