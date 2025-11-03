package model;

// CHANGED: Thêm implements IFileString
public class ThuThu extends ConNguoi implements IFileString {
    private String ma;
    private int gioLam;
    private int luongGio;

    public ThuThu() {}
    public ThuThu(String ma, String ten, int tuoi, String diaChi, int gioLam, int luongGio) {
        super(ten, tuoi, diaChi);
        this.ma = ma;
        this.gioLam = gioLam;
        this.luongGio = luongGio;
    }

    public String getMa() { return ma; }
    public int tinhLuong() { return gioLam * luongGio; }

    @Override
    public String toString() {
        return String.format("%-6s %-20s %-5d %-20s %-8d %-8d", ma, ten, tuoi, diaChi, gioLam, luongGio);
    }

    public static String header() {
        return String.format("%-6s %-20s %-5s %-20s %-8s %-8s", "MÃ TT", "TÊN", "TUỔI", "ĐỊA CHỈ", "GIỜ", "LƯƠNG");
    }

    // NEW: Thêm 2 hàm set này vào
    public void setGioLam(int gioLam) { this.gioLam = gioLam; }
    public void setLuongGio(int luongGio) { this.luongGio = luongGio; }
    // (setTen, setTuoi, setDiaChi đã được kế thừa từ ConNguoi)
    
    // NEW: Thêm phương thức toFileString
    @Override
    public String toFileString() {
        // Định dạng phải khớp với logic đọc của FileService:
        // ma;ten;tuoi;diaChi;gioLam;luongGio
        
        // ten, tuoi, diaChi lấy từ lớp cha ConNguoi
        return ma + ";" + ten + ";" + tuoi + ";" + diaChi + ";" + gioLam + ";" + luongGio;
    }
}