package model;

// CHANGED: Thêm implements IFileString
public class NhaXuatBan implements IFileString {
    private String ma;
    private String ten;

    public NhaXuatBan() {}
    public NhaXuatBan(String ma, String ten) {
        this.ma = ma;
        this.ten = ten;
    }

    public String getMa() { return ma; }
    public void setMa(String ma) { this.ma = ma; }

    public String getTen() { return ten; }
    public void setTen(String ten) { this.ten = ten; }

    @Override
    public String toString() {
        return String.format("%-10s %-20s", ma, ten);
    }

    public static String header() {
        return String.format("%-10s %-20s", "Mã NXB", "Tên NXB");
    }

    // NEW: Thêm phương thức toFileString
    @Override
    public String toFileString() {
        // Định dạng phải khớp với logic đọc của FileService: ma;ten
        return ma + ";" + ten;
    }
}