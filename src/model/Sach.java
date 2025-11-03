package model;

// CHANGED: Thêm implements IFileString
public class Sach implements IFileString {
    private String ma;
    private String ten;
    private TacGia tacGia;
    private NhaXuatBan nxb;
    private int namXB;
    private int soTrang;

    public Sach() {}

    public Sach(String ma, String ten, TacGia tacGia, NhaXuatBan nxb, int namXB, int soTrang) {
        this.ma = ma;
        this.ten = ten;
        this.tacGia = tacGia;
        this.nxb = nxb;
        this.namXB = namXB;
        this.soTrang = soTrang;
    }

    public String getMa() { return ma; }
    public String getTen() { return ten; }
    public TacGia getTacGia() { return tacGia; }
    public NhaXuatBan getNxb() { return nxb; }
    public int getNamXB() { return namXB; }
    public int getSoTrang() { return soTrang; }

    public void hienThi() {
        String tg = (tacGia != null) ? tacGia.getTen() : "Khong xac dinh";
        String nxbTen = (nxb != null) ? nxb.getTen() : "Khong xac dinh";
        System.out.printf("Ma: %s | Ten: %s | TG: %s | NXB: %s | Nam: %d | Trang: %d%n",
                ma, ten, tg, nxbTen, namXB, soTrang);
    }

    @Override
    public String toString() {
        String tgId = (tacGia != null) ? tacGia.getId() : "TG00";
        String nxbMa = (nxb != null) ? nxb.getMa() : "NXB00";
        return ma + ";" + ten + ";" + tgId + ";" + nxbMa + ";" + namXB + ";" + soTrang;
    }

    public static String header() {
        return "MA SACH     TEN SACH               TAC GIA         NXB       NAM  TRANG";
    }

    // NEW: Thêm 3 hàm set này vào
    public void setTen(String ten) { this.ten = ten; }
    public void setNamXB(int namXB) { this.namXB = namXB; }
    public void setSoTrang(int soTrang) { this.soTrang = soTrang; }

    // NEW: Thêm phương thức toFileString
    @Override
    public String toFileString() {
        // Định dạng phải khớp với logic đọc của FileService:
        // ma;ten;idTacGia;maNXB;namXB;soTrang
        
        // Xử lý an toàn nếu tg hoặc nxb là null (khi đọc file)
        String tgId = (tacGia != null) ? tacGia.getId() : "null";
        String nxbMa = (nxb != null) ? nxb.getMa() : "null";
        
        return ma + ";" + ten + ";" + tgId + ";" + nxbMa + ";" + namXB + ";" + soTrang;
    }
}