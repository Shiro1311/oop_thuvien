// THAY THẾ TOÀN BỘ file PhieuTraSach.java BẰNG CODE NÀY

package model;
import java.text.SimpleDateFormat;
import java.util.Calendar;

// CHANGED: Thêm implements IFileString (nếu chưa có)
public class PhieuTraSach implements IFileString {
    private String maPhieu;
    private TTMuon ttMuon;
    private Calendar ngayTra;
    private double tienPhat;
    private String maThuThu; 

    public PhieuTraSach() {}

    // CHANGED: Constructor mới
    public PhieuTraSach(String maPhieu, TTMuon ttMuon, Calendar ngayTra, double tienPhat, String maThuThu) {
        this.maPhieu = maPhieu;
        this.ttMuon = ttMuon;
        this.ngayTra = ngayTra;
        this.tienPhat = tienPhat;
        this.maThuThu = maThuThu; 
    }

    public String getMaPhieu() { return maPhieu; }
    public TTMuon getTTMuon() { return ttMuon; }
    public Calendar getNgayTra() { return ngayTra; }
    public double getTienPhat() { return tienPhat; }
    public String getMaThuThu() { return maThuThu; } 

    // CHANGED: Cập nhật hàm hiển thị để show Thủ thư
    @Override
    public String toString() {
        if (ttMuon == null) {
            return String.format("%-10s %-15s %-12s %.0fd %-10s", maPhieu, "N/A", "N/A", tienPhat, maThuThu);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return String.format("%-10s %-15s %-12s %.0fd %-10s",
                maPhieu, ttMuon.getId(), sdf.format(ngayTra.getTime()), tienPhat, maThuThu);
    }

    // CHANGED: Cập nhật header
    public static String header() {
        return String.format("%-10s %-15s %-12s %-8s %s", "MA PHIEU", "MA MUON", "NGAY TRA", "PHAT", "TT NHAN");
    }
    
    // Cập nhật toFileString()
    @Override
    public String toFileString() {
        String idTTMuon = (ttMuon == null) ? "null" : ttMuon.getId();
        
        return maPhieu + ";" + idTTMuon + ";" + formatCalendar(ngayTra) + ";" + tienPhat + ";" + maThuThu;
    }
    
    // (Dán lại hàm formatCalendar() nếu bị mất)
    private String formatCalendar(Calendar cal) {
        if (cal == null) return "null";
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return sdf.format(cal.getTime());
    }
}