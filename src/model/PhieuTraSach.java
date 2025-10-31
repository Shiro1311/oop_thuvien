package model;
import java.text.SimpleDateFormat;
import java.util.Calendar;

// CHANGED: Thêm implements IFileString
public class PhieuTraSach implements IFileString {
    private String maPhieu;
    private TTMuon ttMuon;
    private Calendar ngayTra;
    private double tienPhat;

    public PhieuTraSach() {}

    public PhieuTraSach(String maPhieu, TTMuon ttMuon, Calendar ngayTra, double tienPhat) {
        this.maPhieu = maPhieu;
        this.ttMuon = ttMuon;
        this.ngayTra = ngayTra;
        this.tienPhat = tienPhat;
    }

    public String getMaPhieu() { return maPhieu; }
    public TTMuon getTTMuon() { return ttMuon; }
    public Calendar getNgayTra() { return ngayTra; }
    public double getTienPhat() { return tienPhat; }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String ngayTraStr = ngayTra != null ? sdf.format(ngayTra.getTime()) : "Chua tra";
        String idMuon = ttMuon != null ? ttMuon.getId() : "null"; // Sửa lỗi nếu ttMuon là null
        return String.format("%-10s %-15s %-12s %.0fd", maPhieu, idMuon, ngayTraStr, tienPhat);
    }

    public static String header() {
        return String.format("%-10s %-15s %-12s %s", "MA PHIEU", "MA MUON", "NGAY TRA", "PHAT");
    }

    // NEW: Hàm trợ giúp để format Calendar (phải giống hệt FileService)
    private String formatCalendar(Calendar cal) {
        if (cal == null) return "null";
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return sdf.format(cal.getTime());
    }

    // NEW: Thêm phương thức toFileString
    @Override
    public String toFileString() {
        // Định dạng phải khớp với logic đọc của FileService:
        // maPhieu;idTTMuon;ngayTra;tienPhat
        
        String idTTMuon = (ttMuon == null) ? "null" : ttMuon.getId();
        
        return maPhieu + ";" + idTTMuon + ";" + formatCalendar(ngayTra) + ";" + tienPhat;
    }
}