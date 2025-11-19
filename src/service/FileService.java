// Đặt file này trong thư mục: src/service/FileService.java
package service;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import model.*;

/**
 * NEW:
 * Lớp này chứa các phương thức static để đọc và ghi dữ liệu
 * từ các file .txt trong thư mục 'data/'.
 * Sử dụng dấu chấm phẩy (;) làm ký tự phân tách.
 */
public class FileService {

    private static final String DATA_DIR = "data"; // Tên thư mục chứa data
    private static final String DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";
    private static final int MAX_SIZE = 1000; // Phải đồng bộ với QuanLyThuVien

    // Hàm trợ giúp để đảm bảo thư mục "data" tồn tại
    private static void ensureDataDirectoryExists() {
        File dir = new File(DATA_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }
    
    // Hàm trợ giúp phân tích ngày tháng
    private static Calendar parseCalendar(String str) throws ParseException {
        if (str == null || str.equals("null") || str.isEmpty()) return null;
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(str));
        return cal;
    }

    // === GHI FILE (Chung) ===
    
    private static <T extends IFileString> void ghiFile(String filename, T[] arr, int count) {
        ensureDataDirectoryExists();
        String filePath = DATA_DIR + File.separator + filename;
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (int i = 0; i < count; i++) {
                writer.write(arr[i].toFileString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Loi khi ghi file " + filename + ": " + e.getMessage());
        }
    }

    // === GỌI CÁC HÀM GHI CỤ THỂ ===
    public static void ghiTacGia(TacGia[] arr, int count) { ghiFile("TacGia.txt", arr, count); }
    public static void ghiNXB(NhaXuatBan[] arr, int count) { ghiFile("NXB.txt", arr, count); }
    public static void ghiViTri(ViTri[] arr, int count) { ghiFile("ViTri.txt", arr, count); }
    public static void ghiTheThuVien(TheThuVien[] arr, int count) { ghiFile("TheThuVien.txt", arr, count); }
    public static void ghiThuThu(ThuThu[] arr, int count) { ghiFile("ThuThu.txt", arr, count); } // NEW
    public static void ghiSach(Sach[] arr, int count) { ghiFile("Sach.txt", arr, count); }
    public static void ghiNguoiMuon(NguoiMuon[] arr, int count) { ghiFile("NguoiMuon.txt", arr, count); }
    public static void ghiChiTietSach(ChiTietSach[] arr, int count) { ghiFile("ChiTietSach.txt", arr, count); }
    public static void ghiTTMuon(TTMuon[] arr, int count) { ghiFile("TTMuon.txt", arr, count); }
    public static void ghiPhieuTra(PhieuTraSach[] arr, int count) { ghiFile("PhieuTra.txt", arr, count); }


    // === ĐỌC FILE (Cụ thể) ===

    // 1. Đọc Tác Giả
    public static DuLieuDoc<TacGia> docTacGia() {
        TacGia[] ds = new TacGia[MAX_SIZE];
        int count = 0;
        File file = new File(DATA_DIR + File.separator + "TacGia.txt");
        if (!file.exists()) return new DuLieuDoc<>(ds, 0); // File không tồn tại

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null && count < MAX_SIZE) {
                String[] parts = line.split(";");
                if (parts.length == 2) {
                    ds[count++] = new TacGia(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            System.err.println("Loi doc file TacGia.txt: " + e.getMessage());
        }
        return new DuLieuDoc<>(ds, count);
    }

    // 2. Đọc NXB
    public static DuLieuDoc<NhaXuatBan> docNXB() {
        NhaXuatBan[] ds = new NhaXuatBan[MAX_SIZE];
        int count = 0;
        File file = new File(DATA_DIR + File.separator + "NXB.txt");
        if (!file.exists()) return new DuLieuDoc<>(ds, 0);

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null && count < MAX_SIZE) {
                String[] parts = line.split(";");
                if (parts.length == 2) {
                    ds[count++] = new NhaXuatBan(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            System.err.println("Loi doc file NXB.txt: " + e.getMessage());
        }
        return new DuLieuDoc<>(ds, count);
    }
    
    // 3. Đọc Vị Trí
    public static DuLieuDoc<ViTri> docViTri() {
        ViTri[] ds = new ViTri[MAX_SIZE];
        int count = 0;
        File file = new File(DATA_DIR + File.separator + "ViTri.txt");
        if (!file.exists()) return new DuLieuDoc<>(ds, 0);

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null && count < MAX_SIZE) {
                String[] parts = line.split(";");
                if (parts.length == 4) {
                    ds[count++] = new ViTri(parts[0], parts[1], parts[2], parts[3]);
                }
            }
        } catch (IOException e) {
            System.err.println("Loi doc file ViTri.txt: " + e.getMessage());
        }
        return new DuLieuDoc<>(ds, count);
    }

    // 4. Đọc Thẻ Thư Viện
    public static DuLieuDoc<TheThuVien> docTheThuVien() {
        TheThuVien[] ds = new TheThuVien[MAX_SIZE];
        int count = 0;
        File file = new File(DATA_DIR + File.separator + "TheThuVien.txt");
        if (!file.exists()) return new DuLieuDoc<>(ds, 0);

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null && count < MAX_SIZE) {
                String[] parts = line.split(";");
                if (parts.length == 2) { // maThe;loai (A/B)
                    if (parts[1].equals("A")) {
                        ds[count++] = new LoaiA(parts[0]);
                    } else if (parts[1].equals("B")) {
                        ds[count++] = new LoaiB(parts[0]);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Loi doc file TheThuVien.txt: " + e.getMessage());
        }
        return new DuLieuDoc<>(ds, count);
    }
    
    // 5. Đọc Thủ Thư (NEW)
    public static DuLieuDoc<ThuThu> docThuThu() {
        ThuThu[] ds = new ThuThu[MAX_SIZE];
        int count = 0;
        File file = new File(DATA_DIR + File.separator + "ThuThu.txt");
        if (!file.exists()) return new DuLieuDoc<>(ds, 0);

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null && count < MAX_SIZE) {
                String[] parts = line.split(";");
                if (parts.length == 6) {
                    // ma;ten;tuoi;diaChi;gioLam;luongGio
                    ds[count++] = new ThuThu(parts[0], parts[1], Integer.parseInt(parts[2]),
                                   parts[3], Integer.parseInt(parts[4]), Integer.parseInt(parts[5]));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Loi doc file ThuThu.txt: " + e.getMessage());
        }
        return new DuLieuDoc<>(ds, count);
    }

    // 6. Đọc Sách (Phụ thuộc TacGia, NXB)
    public static DuLieuDoc<Sach> docSach(TacGia[] dsTG, int slTG, NhaXuatBan[] dsNXB, int slNXB) {
        Sach[] ds = new Sach[MAX_SIZE];
        int count = 0;
        File file = new File(DATA_DIR + File.separator + "Sach.txt");
        if (!file.exists()) return new DuLieuDoc<>(ds, 0);

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null && count < MAX_SIZE) {
                String[] parts = line.split(";");
                if (parts.length == 6) {
                    TacGia tg = QuanLyThuVien.timTacGiaById(parts[2], dsTG, slTG);
                    NhaXuatBan nxb = QuanLyThuVien.timNXBById(parts[3], dsNXB, slNXB);
                    
                    ds[count++] = new Sach(parts[0], parts[1], tg, nxb, 
                                        Integer.parseInt(parts[4]), Integer.parseInt(parts[5]));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Loi doc file Sach.txt: " + e.getMessage());
        }
        return new DuLieuDoc<>(ds, count);
    }

    // 7. Đọc Người Mượn (Phụ thuộc TheThuVien)
    public static DuLieuDoc<NguoiMuon> docNguoiMuon(TheThuVien[] dsThe, int slThe) {
        NguoiMuon[] ds = new NguoiMuon[MAX_SIZE];
        int count = 0;
        File file = new File(DATA_DIR + File.separator + "NguoiMuon.txt");
        if (!file.exists()) return new DuLieuDoc<>(ds, 0);

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null && count < MAX_SIZE) {
                String[] parts = line.split(";");
                if (parts.length == 5) {
                    // ma;ten;tuoi;diaChi;maThe
                    TheThuVien the = QuanLyThuVien.timTheById(parts[4], dsThe, slThe);
                    ds[count++] = new NguoiMuon(parts[0], parts[1], 
                                Integer.parseInt(parts[2]), parts[3], the);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Loi doc file NguoiMuon.txt: " + e.getMessage());
        }
        return new DuLieuDoc<>(ds, count);
    }

    // 8. Đọc Chi Tiết Sách (Phụ thuộc Sach, ViTri, TheThuVien)
    public static DuLieuDoc<ChiTietSach> docChiTietSach(Sach[] dsSach, int slSach, ViTri[] dsVT, int slVT, TheThuVien[] dsThe, int slThe) {
        ChiTietSach[] ds = new ChiTietSach[MAX_SIZE];
        int count = 0;
        File file = new File(DATA_DIR + File.separator + "ChiTietSach.txt");
        if (!file.exists()) return new DuLieuDoc<>(ds, 0);

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null && count < MAX_SIZE) {
                String[] parts = line.split(";");
                if (parts.length == 4) {
                    // id;maSach;idViTri;maTheMuon (có thể 'null')
                    Sach sach = QuanLyThuVien.timSachById(parts[1], dsSach, slSach);
                    ViTri vt = QuanLyThuVien.timViTriById(parts[2], dsVT, slVT);
                    TheThuVien the = parts[3].equals("null") ? null : QuanLyThuVien.timTheById(parts[3], dsThe, slThe);
                    
                    ChiTietSach cts = new ChiTietSach(parts[0], sach, vt);
                    cts.setTheMuon(the); // Gán thẻ mượn (nếu có)
                    ds[count++] = cts;
                }
            }
        } catch (IOException e) {
            System.err.println("Loi doc file ChiTietSach.txt: " + e.getMessage());
        }
        return new DuLieuDoc<>(ds, count);
    }

    // 9. Đọc TTMuon (Phụ thuộc ChiTietSach, TheThuVien)
    // 9. Đọc TTMuon (CẬP NHẬT)
    public static DuLieuDoc<TTMuon> docTTMuon(ChiTietSach[] dsCTS, int slCTS, TheThuVien[] dsThe, int slThe) {
        TTMuon[] ds = new TTMuon[MAX_SIZE];
        int count = 0;
        File file = new File(DATA_DIR + File.separator + "TTMuon.txt");
        if (!file.exists()) return new DuLieuDoc<>(ds, 0);

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null && count < MAX_SIZE) {
                String[] parts = line.split(";");
                // Chấp nhận cả file cũ (5 phần) và file mới (6 phần)
                if (parts.length >= 5) {
                    ChiTietSach cts = QuanLyThuVien.timCTSById(parts[1], dsCTS, slCTS);
                    TheThuVien the = QuanLyThuVien.timTheById(parts[2], dsThe, slThe);
                    Calendar ngayMuon = parseCalendar(parts[3]);
                    Calendar ngayTra = parseCalendar(parts[4]);
                    
                    // Xử lý tương thích ngược: Nếu là file cũ chưa có mã TT thì gán "N/A"
                    String maThuThu = "N/A"; 
                    if (parts.length >= 6) {
                        maThuThu = parts[5];
                    }
                    
                    if (cts != null && the != null && ngayMuon != null) {
                        // Gọi Constructor mới
                        TTMuon tt = new TTMuon(parts[0], cts, the, ngayMuon, ngayTra, maThuThu);
                        ds[count++] = tt;
                    }
                }
            }
        } catch (IOException | ParseException e) {
            System.err.println("Loi doc file TTMuon.txt: " + e.getMessage());
        }
        return new DuLieuDoc<>(ds, count);
    }

    // 10. Đọc Phiếu Trả (Phụ thuộc TTMuon)
    // 10. Đọc Phiếu Trả (CẬP NHẬT ĐỂ AN TOÀN HƠN)
    public static DuLieuDoc<PhieuTraSach> docPhieuTra(TTMuon[] dsMuon, int slMuon) {
        PhieuTraSach[] ds = new PhieuTraSach[MAX_SIZE];
        int count = 0;
        File file = new File(DATA_DIR + File.separator + "PhieuTra.txt");
        if (!file.exists()) return new DuLieuDoc<>(ds, 0);

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null && count < MAX_SIZE) {
                String[] parts = line.split(";");
                // Chấp nhận đọc nếu có ít nhất 4 phần (format cũ)
                if (parts.length >= 4) {
                    TTMuon tt = QuanLyThuVien.timTTMuonById(parts[1], dsMuon, slMuon);
                    Calendar ngayTra = parseCalendar(parts[2]);
                    double phat = Double.parseDouble(parts[3]);
                    
                    // Xử lý cột thứ 5 (MaThuThu) nếu có
                    String maThuThu = "N/A";
                    if (parts.length >= 5) {
                        maThuThu = parts[4];
                    }

                    // Nếu tt không tìm thấy (do dsMuon chưa load đủ), ta vẫn có thể load phiếu trả với tt=null để hiển thị log
                    // Tuy nhiên logic chuẩn là phải có tt. Ở đây ta tạm chấp nhận load vào.
                    ds[count++] = new PhieuTraSach(parts[0], tt, ngayTra, phat, maThuThu);
                }
            }
        } catch (IOException | ParseException | NumberFormatException e) {
            System.err.println("Loi doc file PhieuTra.txt: " + e.getMessage());
        }
        return new DuLieuDoc<>(ds, count);
    }
    
    
    /**
     * Lớp nội bộ (helper class) để chứa kết quả đọc file
     */
    public static class DuLieuDoc<T> {
        public T[] data;
        public int count;
        
        public DuLieuDoc(T[] data, int count) {
            this.data = data;
            this.count = count;
        }
    }
}