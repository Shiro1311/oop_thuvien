package model;

// CHANGED: Thêm implements IFileString
public class TacGia implements IFileString {
    private String id;
    private String ten;

    public TacGia() {}
    public TacGia(String id, String ten) {
        this.id = id;
        this.ten = ten;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTen() { return ten; }
    public void setTen(String ten) { this.ten = ten; }

    @Override
    public String toString() {
        return String.format("%-10s %-20s", id, ten);
    }

    public static String header() {
        return String.format("%-10s %-20s", "Mã TG", "Tên Tác Giả");
    }

    // NEW: Thêm phương thức toFileString
    @Override
    public String toFileString() {
        // Định dạng phải khớp với logic đọc của FileService: id;ten
        return id + ";" + ten;
    }
}