package model;

// CHANGED: Phải extends ConNguoi (giống ThuThu) và implements IFileString
public class NguoiMuon extends ConNguoi implements IFileString {
    private String ma;
    private TheThuVien the;

    public NguoiMuon() {
        super(); // Gọi constructor của ConNguoi
    }

    // CHANGED: Dùng super() để gán thông tin cho ConNguoi
    public NguoiMuon(String ma, String ten, int tuoi, String diaChi, TheThuVien the) {
        super(ten, tuoi, diaChi); // Gửi thông tin lên lớp cha (ConNguoi)
        this.ma = ma;
        this.the = the;
    }

    public String getMa() { return ma; }
    public TheThuVien getThe() { return the; }
    // getTen(), getTuoi(), getDiaChi() đã được kế thừa từ ConNguoi

    // NEW: Thêm hàm này để có thể "đổi thẻ"
    public void setThe(TheThuVien the) {
        this.the = the;
    }

    // NEW: Thêm phương thức toFileString
    @Override
    public String toFileString() {
        // Định dạng phải khớp với logic đọc của FileService:
        // ma;ten;tuoi;diaChi;maThe
        
        // Lấy ten, tuoi, diaChi từ lớp cha (ConNguoi)
        String maThe = (the != null) ? the.getMaThe() : "null";
        
        return ma + ";" + ten + ";" + tuoi + ";" + diaChi + ";" + maThe;
    }
}