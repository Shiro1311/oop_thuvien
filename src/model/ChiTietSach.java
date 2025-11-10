package model;

// CHANGED: Thêm implements IFileString
public class ChiTietSach implements IFileString {
    private String id;
    private Sach sach;
    private ViTri viTri;
    private TheThuVien theMuon; // null = chua muon

    public ChiTietSach() {}

    public ChiTietSach(String id, Sach sach, ViTri viTri) {
        this.id = id;
        this.sach = sach;
        this.viTri = viTri;
    }

    public ChiTietSach(String id, Sach sach, ViTri viTri, TheThuVien theMuon) {
        this.id = id;
        this.sach = sach;
        this.viTri = viTri;
        this.theMuon = theMuon;
    }

    public String getId() { return id; }
    public Sach getSach() { return sach; }
    public ViTri getViTri() { return viTri; }
    public TheThuVien getTheMuon() { return theMuon; }

    public void setTheMuon(TheThuVien theMuon) {
        this.theMuon = theMuon;
    }

    @Override
    public String toString() {
        String maSach = "N/A";
        String tenSach = "N/A";
        
        if (sach != null) {
            maSach = sach.getMa();
            tenSach = sach.getTen(); // <-- Lấy tên đầy đủ
        }

        String viTriStr = (viTri != null) ? viTri.getViTri() : "N/A";
        String trangThai = (theMuon == null) ? "Trong kho" : "Dang muon";
        
        // In ra với cột tên sách mới
        return String.format("%-8s %-10s %-40s %-20s %-10s", 
                            id, maSach, tenSach, viTriStr, trangThai);
    }

    public static String header() {
        // Thêm cột TÊN SÁCH
        return String.format("%-8s %-10s %-40s %-20s %-10s", 
                            "ID CTS", "MA SACH", "TEN SACH (Day du)", "VI TRI", "TRANG THAI");
    }

    // NEW: Thêm phương thức toFileString
    @Override
    public String toFileString() {
        // Định dạng phải khớp với logic đọc của FileService:
        // id;maSach;idViTri;maTheMuon (có thể 'null')
        
        String maSach = (sach != null) ? sach.getMa() : "null";
        String viTriId = (viTri != null) ? viTri.getId() : "null";
        String maThe = (theMuon == null) ? "null" : theMuon.getMaThe();
        
        return id + ";" + maSach + ";" + viTriId + ";" + maThe;
    }
}