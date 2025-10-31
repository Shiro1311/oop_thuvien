package model;

// CHANGED: Thêm implements IFileString
public class ViTri implements IFileString {
    private String id;
    private String khuVuc;
    private String ke;
    private String hang;

    public ViTri() {}

    public ViTri(String id, String khuVuc, String ke, String hang) {
        this.id = id;
        this.khuVuc = khuVuc;
        this.ke = ke;
        this.hang = hang;
    }

    public String getId() { return id; }
    public String getKhuVuc() { return khuVuc; }
    public String getKe() { return ke; }
    public String getHang() { return hang; }

    public String getViTri() {
        return khuVuc + " - " + ke + " - " + hang;
    }

    @Override
    public String toString() {
        return String.format("%-5s %-12s %-8s %-8s", id, khuVuc, ke, hang);
    }

    public static String header() {
        return String.format("%-5s %-12s %-8s %-8s", "ID", "KHU VUC", "KE", "HANG");
    }
    
    // NEW: Thêm phương thức toFileString
    @Override
    public String toFileString() {
        // Định dạng phải khớp với logic đọc của FileService:
        // id;khuVuc;ke;hang
        return id + ";" + khuVuc + ";" + ke + ";" + hang;
    }
}