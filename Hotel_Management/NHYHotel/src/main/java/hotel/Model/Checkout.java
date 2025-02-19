package hotel.Model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "checkout")
public class Checkout {
    @Id
    private int maTraPhong;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maDatPhong")
    private Checkin datPhong;
    private Date ngayTra;
    @DateTimeFormat(pattern = "HH:mm")
    private java.util.Date gioTra;
    private Double tongTien;
    private String nguoiThuTien;

    public Checkout(int maTraPhong, Checkin datPhong, Date ngayTra, Double tongTien) {
        this.maTraPhong = maTraPhong;
        this.datPhong = datPhong;
        this.ngayTra = ngayTra;
        this.tongTien = tongTien;
    }


    public Checkout() {
    }


    public String getNguoiThuTien() {
        return nguoiThuTien;
    }

    public void setNguoiThuTien(String nguoiThuTien) {
        this.nguoiThuTien = nguoiThuTien;
    }

    public int getMaTraPhong() {
        return maTraPhong;
    }

    public void setMaTraPhong(int maTraPhong) {
        this.maTraPhong = maTraPhong;
    }

    public Checkin getDatPhong() {
        return datPhong;
    }

    public void setDatPhong(Checkin datPhong) {
        this.datPhong = datPhong;
    }



    public Date getNgayTra() {
        return ngayTra;
    }

    public void setNgayTra(Date ngayTra) {
        this.ngayTra = ngayTra;
    }

    public java.util.Date getGioTra() {
        return gioTra;
    }

    public void setGioTra(java.util.Date gioTra) {
        this.gioTra = gioTra;
    }

    public Double getTongTien() {
        return tongTien;
    }

    public void setTongTien(Double tongTien) {
        this.tongTien = tongTien;
    }


    @Override
    public String toString() {
        return "TraPhong{" +
                "maTraPhong=" + maTraPhong +
                ", datPhong=" + datPhong +
                ", ngayTra=" + ngayTra +
                ", gioTra=" + gioTra +
                ", tongTien=" + tongTien +
                ", nguoiThuTien='" + nguoiThuTien + '\'' +
                '}';
    }
}
