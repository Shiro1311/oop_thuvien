package model;
import java.text.SimpleDateFormat;
import java.util.Calendar; // NEW: Import

// CHANGED: Thêm implements IFileString
public class TTMuon implements IFileString {
    private String id;
    private ChiTietSach chiTietSach;
    private TheThuVien theMuon;
    private Calendar ngayMuon;
    private Calendar ngayTra;
    private String maThuThu;

    public TTMuon() {}

    public TTMuon(String id, ChiTietSach chiTietSach, TheThuVien theMuon, Calendar ngayMuon, Calendar ngayTra, String maThuThu) {
        this.id = id;
        this.chiTietSach = chiTietSach;
        this.theMuon = theMuon;
        this.ngayMuon = ngayMuon;
        this.ngayTra = ngayTra;
        this.maThuThu = maThuThu;
    }

    public String getId() { return id; }
    public ChiTietSach getChiTietSach() { return chiTietSach; }
    public TheThuVien getTheMuon() { return theMuon; }
    public Calendar getNgayMuon() { return ngayMuon; }
    public Calendar getNgayTra() { return ngayTra; }
    public String getMaThuThu() { return maThuThu; }
    
    public void setNgayTra(Calendar ngayTra) { this.ngayTra = ngayTra; }
    
    // NEW: Hàm trợ giúp để format Calendar (phải giống hệt FileService)
    private String formatCalendar(Calendar cal) {
        if (cal == null) return "null";
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return sdf.format(cal.getTime());
    }

    // NEW: Thêm hàm này để cập nhật phiếu mượn
    public void setTheMuon(TheThuVien the) {
        this.theMuon = the;
    }

    // NEW: Thêm phương thức toFileString
    @Override
    public String toFileString() {
        // Định dạng phải khớp với logic đọc của FileService:
        // id;idCTS;maTheMuon;ngayMuon;ngayTra
        
        String idCTS = (chiTietSach != null) ? chiTietSach.getId() : "null";
        String maThe = (theMuon != null) ? theMuon.getMaThe() : "null";
        
        return id + ";" + idCTS + ";" + maThe + ";" +
               formatCalendar(ngayMuon) + ";" + formatCalendar(ngayTra) + ";" + maThuThu;
    }
}