package model;

// CHANGED: Thêm implements IFileString
public abstract class TheThuVien implements IFileString {
    protected String maThe;
    protected String loai;

    public TheThuVien() {}
    public TheThuVien(String maThe, String loai) {
        this.maThe = maThe;
        this.loai = loai;
    }

    public String getMaThe() { return maThe; }
    public void setMaThe(String maThe) { this.maThe = maThe; }

    public String getLoai() { return loai; }
    public void setLoai(String loai) { this.loai = loai; }

    public abstract int getSoNgayMuon();
    public abstract int getSoSachMuon();

    @Override
    public String toString() {
        return String.format("%-8s %-5s", maThe, loai);
    }

    public static String header() {
        return String.format("%-8s %-5s", "Mã Thẻ", "Loại");
    }
    
    // NEW: Thêm phương thức toFileString
    @Override
    public String toFileString() {
        // Định dạng phải khớp với logic đọc của FileService: maThe;loai
        // Các lớp con (LoaiA, LoaiB) sẽ kế thừa hàm này
        return maThe + ";" + loai;
    }
}