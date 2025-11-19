// Đặt file này trong thư mục: src/service/QuanLyThuVien.java
package service;
import java.text.SimpleDateFormat;
import java.util.Calendar; // Vẫn cần dùng cho logic ngày tháng
import java.util.Scanner;
import model.*;
import service.FileService.DuLieuDoc;

/**
 * CHANGED:
 * 1. Implement IQuanLy (Interface)
 * 2. Thay thế toàn bộ 'List<...>' bằng mảng '[]'
 * 3. Thêm các biến đếm 'sl...' (số lượng) để quản lý mảng
 * 4. Thay thế '.add()' bằng 'ds[sl++] = ...'
 * 5. Thay thế '.stream()', '.forEach()' bằng vòng lặp 'for'
 * 6. Thêm thuộc tính/phương thức 'static'
 * 7. Thêm sườn (stub) cho logic doc/ghi file
 */
public class QuanLyThuVien implements IQuanLy { // CHANGED: Thêm interface

    // --- STATIC (Đáp ứng yêu cầu) ---
    private static final String TEN_THU_VIEN = "Thu Vien Tuong Lai"; // NEW: Thuộc tính static
    
    public static void inTenThuVien() { // NEW: Phương thức static
        System.out.println("Chao mung den voi: " + TEN_THU_VIEN);
    }
    // ---------------------------------

    // --- ARRAY (Thay cho ArrayList) ---
    private static final int MAX_SIZE = 1000; // NEW: Kích thước tối đa cho các mảng
    
    private Sach[] dsSach;
    private ChiTietSach[] dsCTS;
    private TacGia[] dsTacGia;
    private NhaXuatBan[] dsNXB;
    private ViTri[] dsViTri;
    private TheThuVien[] dsThe;
    private NguoiMuon[] dsNguoiMuon;
    private TTMuon[] dsMuon;
    private PhieuTraSach[] dsPhieuTra;
    private ThuThu[] dsThuThu; // <--- THÊM DÒNG NÀY

    // NEW: Biến đếm số lượng phần tử thực tế trong mảng
    private int slSach, slCTS, slTacGia, slNXB, slViTri, slThe, slNguoiMuon, slMuon, slPhieuTra, slThuThu;
    // ---------------------------------
    
    private Scanner sc; // CHANGED: Sẽ được truyền từ MainProgram

    // CHANGED: Constructor để khởi tạo mảng và nhận Scanner
    public QuanLyThuVien(Scanner sc) {
        this.sc = sc;
        
        // Khởi tạo các mảng
        dsSach = new Sach[MAX_SIZE];
        dsCTS = new ChiTietSach[MAX_SIZE];
        dsTacGia = new TacGia[MAX_SIZE];
        dsNXB = new NhaXuatBan[MAX_SIZE];
        dsViTri = new ViTri[MAX_SIZE];
        dsThe = new TheThuVien[MAX_SIZE];
        dsNguoiMuon = new NguoiMuon[MAX_SIZE];
        dsMuon = new TTMuon[MAX_SIZE];
        dsPhieuTra = new PhieuTraSach[MAX_SIZE];
        dsThuThu = new ThuThu[MAX_SIZE]; // <--- THÊM DÒNG NÀY
        
        // Khởi tạo số lượng
        slSach = 0; slCTS = 0; slTacGia = 0; slNXB = 0; slViTri = 0; 
        slThe = 0; slNguoiMuon = 0; slMuon = 0; slPhieuTra = 0; slThuThu = 0;
    }

    // --- FILE I/O (Đáp ứng yêu cầu) ---
    public void khoiTaoDuLieu() {
        // CHANGED: Logic khởi tạo sẽ gọi hàm đọc file
        docDuLieu();
        // System.out.println("Da tai du lieu tu file!");
    }
    
    /**
     * NEW: Đây là nơi logic ĐỌC DỮ LIỆU TỪ FILE của bạn
     * Bạn cần đọc các file .txt (VD: TacGia.txt, NXB.txt, Sach.txt...)
     * và nạp dữ liệu vào các mảng (dsTacGia, dsNXB, dsSach...)
     */
    // Thay thế hàm docDuLieu() cũ của bạn bằng hàm này
    public void docDuLieu() {
        System.out.println("Dang doc du lieu tu cac file .txt...");
        
        // Bước 1: Đọc các đối tượng độc lập
        DuLieuDoc<TacGia> dataTG = FileService.docTacGia();
        dsTacGia = dataTG.data; slTacGia = dataTG.count;

        DuLieuDoc<NhaXuatBan> dataNXB = FileService.docNXB();
        dsNXB = dataNXB.data; slNXB = dataNXB.count;
        
        DuLieuDoc<ViTri> dataVT = FileService.docViTri();
        dsViTri = dataVT.data; slViTri = dataVT.count;
        
        DuLieuDoc<TheThuVien> dataThe = FileService.docTheThuVien();
        dsThe = dataThe.data; slThe = dataThe.count;
        
        DuLieuDoc<ThuThu> dataTT = FileService.docThuThu(); // <--- THÊM MỚI
        dsThuThu = dataTT.data; slThuThu = dataTT.count; // <--- THÊM MỚI

        // Bước 2: Đọc các đối tượng phụ thuộc cấp 1
        DuLieuDoc<Sach> dataSach = FileService.docSach(dsTacGia, slTacGia, dsNXB, slNXB);
        dsSach = dataSach.data; slSach = dataSach.count;

        DuLieuDoc<NguoiMuon> dataNM = FileService.docNguoiMuon(dsThe, slThe);
        dsNguoiMuon = dataNM.data; slNguoiMuon = dataNM.count;

        // Bước 3: Đọc các đối tượng phụ thuộc cấp 2
        DuLieuDoc<ChiTietSach> dataCTS = FileService.docChiTietSach(dsSach, slSach, dsViTri, slViTri, dsThe, slThe);
        dsCTS = dataCTS.data; slCTS = dataCTS.count;

        // Bước 4: Đọc các đối tượng phụ thuộc cấp 3
        DuLieuDoc<TTMuon> dataMuon = FileService.docTTMuon(dsCTS, slCTS, dsThe, slThe);
        dsMuon = dataMuon.data; slMuon = dataMuon.count;

        // Bước 5: Đọc các đối tượng phụ thuộc cấp 4
        DuLieuDoc<PhieuTraSach> dataTra = FileService.docPhieuTra(dsMuon, slMuon);
        dsPhieuTra = dataTra.data; slPhieuTra = dataTra.count;

        // === XÓA ĐOẠN IF TẠO DỮ LIỆU MẪU, CHỈ GIỮ LẠI DÒNG NÀY ===
        System.out.println("Doc du lieu hoan tat! He thong da san sang.");
        System.out.println("- Sach: " + slSach + ", Ban sao: " + slCTS);
        System.out.println("- Nguoi muon: " + slNguoiMuon + ", Thu thu: " + slThuThu);
    }
    
    /**
     * NEW: Đây là nơi logic GHI DỮ LIỆU RA FILE của bạn
     * Hàm này sẽ được gọi ở MainProgram khi thoát
     */
    public void ghiDuLieu() {
        System.out.println("Dang ghi du lieu ra file...");
        // TODO: Viết logic ghi file ở đây
        // VD: FileService.ghiSach("data/Sach.txt", dsSach, slSach);
        //     FileService.ghiTacGia("data/TacGia.txt", dsTacGia, slTacGia);
        FileService.ghiTacGia(dsTacGia, slTacGia);
        FileService.ghiNXB(dsNXB, slNXB);
        FileService.ghiViTri(dsViTri, slViTri);
        FileService.ghiTheThuVien(dsThe, slThe);
        FileService.ghiThuThu(dsThuThu, slThuThu); // NEW
        FileService.ghiSach(dsSach, slSach);
        FileService.ghiNguoiMuon(dsNguoiMuon, slNguoiMuon);
        FileService.ghiChiTietSach(dsCTS, slCTS);
        FileService.ghiTTMuon(dsMuon, slMuon);
        FileService.ghiPhieuTra(dsPhieuTra, slPhieuTra); 
    }
    // ---------------------------------

    // CHANGED: Toàn bộ hàm này dùng array 'ds[sl++] = ...' thay vì '.add()'
    /*private void taoDuLieuMau() {
        // TAC GIA
        dsTacGia[slTacGia++] = new TacGia("TG01", "Nguyen Nhat Anh");
        dsTacGia[slTacGia++] = new TacGia("TG02", "To Hoai");
        dsTacGia[slTacGia++] = new TacGia("TG03", "Nam Cao");
        //... (thêm các tác giả còn lại)
        dsTacGia[slTacGia++] = new TacGia("TG15", "Yuval Noah Harari");

        //  NHA XUAT BAN 
        dsNXB[slNXB++] = new NhaXuatBan("NXB01", "Kim Dong");
        dsNXB[slNXB++] = new NhaXuatBan("NXB02", "Giao Duc");
        dsNXB[slNXB++] = new NhaXuatBan("NXB03", "Van Hoc");

        //  VI TRI
        for (int i = 1; i <= 16; i++) {
            dsViTri[slViTri++] = new ViTri("VT" + String.format("%02d", i),
                    "Khu " + ((i-1)/4 + 1), "Ke " + ((i-1)%4 + 1), "Hang " + (i%2 == 1 ? "1" : "2"));
        }

        // THE THU VIEN
        dsThe[slThe++] = new LoaiA("0001");
        dsThe[slThe++] = new LoaiB("0002");

        // NGUOI MUON (dùng dsThe[0] thay vì dsThe.get(0))
        dsNguoiMuon[slNguoiMuon++] = new NguoiMuon("NM01", "Tran Van A", 20, "Ha Noi", dsThe[0]);
        dsNguoiMuon[slNguoiMuon++] = new NguoiMuon("NM02", "Le Thi B", 22, "Sai Gon", dsThe[1]);

        // THU THU (NEW)
        dsThuThu[slThuThu++] = new ThuThu("TT01", "Nguyen Van Thu", 30, "Ha Noi", 160, 50000); // <--- THÊM MỚI

        // SACH MAU 
        String[][] sachMau = {
            {"TL0001", "Cho toi xin mot ve di tuoi tho", "TG01", "NXB01", "2010", "200"},
            {"TL0002", "De Men Phieu Luu Ky", "TG02", "NXB01", "1941", "150"},
            // ... (thêm các sách còn lại)
            {"TL0016", "Sapiens", "TG15", "NXB03", "2014", "500"}
        };

        for (int i = 0; i < sachMau.length; i++) {
            String[] s = sachMau[i];
            // SỬA 2 DÒNG NÀY:
            TacGia tg = timTacGiaById(s[2], dsTacGia, slTacGia); 
            NhaXuatBan nxb = timNXBById(s[3], dsNXB, slNXB); 

            // Giữ nguyên logic cũ của bạn (nếu không tìm thấy thì dùng [0])
            if (tg == null) tg = dsTacGia[0];
            if (nxb == null) nxb = dsNXB[0];

            Sach sach = new Sach(s[0], s[1], tg, nxb, Integer.parseInt(s[4]), Integer.parseInt(s[5]));
            dsSach[slSach++] = sach;

            String ctsId = "CTS" + String.format("%02d", i + 1);
            dsCTS[slCTS++] = new ChiTietSach(ctsId, sach, dsViTri[i]); // Dùng dsViTri[i]
        }
        
        dsPhieuTra[slPhieuTra++] = new PhieuTraSach("PT001", null, Calendar.getInstance(), 0, "N/A");
    }*/

    // CHANGED: Viết lại hàm tìm kiếm dùng vòng lặp FOR
    /*private TacGia timTacGia(String id) {
        for (int i = 0; i < slTacGia; i++) {
            if (dsTacGia[i].getId().equals(id)) {
                return dsTacGia[i];
            }
        }
        return dsTacGia[0]; // Trả về mặc định nếu không tìm thấy
    }

    // CHANGED: Viết lại hàm tìm kiếm dùng vòng lặp FOR
    private NhaXuatBan timNXB(String ma) {
        for (int i = 0; i < slNXB; i++) {
            if (dsNXB[i].getMa().equals(ma)) {
                return dsNXB[i];
            }
        }
        return dsNXB[0]; // Trả về mặc định
    }*/

    // ĐẶT CÁC HÀM NÀY VÀO TRONG file QuanLyThuVien.java

    // === CÁC HÀM TÌM KIẾM STATIC (PUBLIC) ===
    // Đây là "Quầy lễ tân" để FileService có thể "hỏi"
    
    public static TacGia timTacGiaById(String id, TacGia[] ds, int count) {
        for (int i = 0; i < count; i++) {
            if (ds[i].getId().equals(id)) {
                return ds[i];
            }
        }
        return null; // Trả về null nếu không tìm thấy
    }

    public static NhaXuatBan timNXBById(String ma, NhaXuatBan[] ds, int count) {
        for (int i = 0; i < count; i++) {
            if (ds[i].getMa().equals(ma)) {
                return ds[i];
            }
        }
        return null; // Trả về null
    }
    
    public static ViTri timViTriById(String id, ViTri[] ds, int count) {
        for (int i = 0; i < count; i++) {
            if (ds[i].getId().equals(id)) {
                return ds[i];
            }
        }
        return null;
    }
    
    public static TheThuVien timTheById(String ma, TheThuVien[] ds, int count) {
        for (int i = 0; i < count; i++) {
            if (ds[i].getMaThe().equals(ma)) {
                return ds[i];
            }
        }
        return null;
    }

    public static Sach timSachById(String ma, Sach[] ds, int count) {
        for (int i = 0; i < count; i++) {
            if (ds[i].getMa().equals(ma)) {
                return ds[i];
            }
        }
        return null;
    }
    
    public static ChiTietSach timCTSById(String id, ChiTietSach[] ds, int count) {
        for (int i = 0; i < count; i++) {
            if (ds[i].getId().equals(id)) {
                return ds[i];
            }
        }
        return null;
    }
    
    public static TTMuon timTTMuonById(String id, TTMuon[] ds, int count) {
        for (int i = 0; i < count; i++) {
            if (ds[i].getId().equals(id)) {
                return ds[i];
            }
        }
        return null;
    }

    // CHANGED: Viết lại hàm tìm kiếm dùng vòng lặp FOR
    private ChiTietSach timCTSChuaMuon(String maSach) {
        for (int i = 0; i < slCTS; i++) {
            if (dsCTS[i].getSach().getMa().equals(maSach) && dsCTS[i].getTheMuon() == null) {
                return dsCTS[i];
            }
        }
        return null; // Trả về null nếu không tìm thấy
    }

    // === HIEN THI (THU THU) ===
    
    // CHANGED: Dùng vòng lặp FOR thay cho forEach
    // HÃY DÙNG HÀM NÀY ĐỂ THAY THẾ HÀM 

    @Override
    public void hienThiSach() {
        System.out.println("\n--- DANH SACH DAU SACH (BAO GOM THONG KE BAN SAO) ---");
        
        // Header (Giữ nguyên)
        System.out.println(String.format("%-8s %-30s %-20s %-10s %-8s %-8s %-10s", 
                            "MA SACH", "TEN SACH", "TAC GIA", "NAM XB", "TONG SL", "DANG MUON", "CON LAI"));
        System.out.println("-".repeat(100));

        if (slSach == 0) {
            System.out.println("Chua co dau sach nao trong he thong.");
            return;
        }

        // Loop 1: Lặp qua từng TỰA SÁCH
        for(int i = 0; i < slSach; i++) {
            Sach sachHienTai = dsSach[i];
            String maSachHienTai = sachHienTai.getMa();

            // ... (Code đếm tongBanSao và dangMuon giữ nguyên) ...
            int tongBanSao = 0;
            int dangMuon = 0;
            for (int j = 0; j < slCTS; j++) {
                if (dsCTS[j].getSach() != null && dsCTS[j].getSach().getMa().equals(maSachHienTai)) {
                    tongBanSao++; 
                    if (dsCTS[j].getTheMuon() != null) {
                        dangMuon++;
                    }
                }
            }
            
            // Lấy thông tin Tác giả
            String tenTacGia = "N/A";
            if (sachHienTai.getTacGia() != null) {
                tenTacGia = sachHienTai.getTacGia().getTen();
            }

            // =============================================================
            // === NEW: FIX LỖI XẤU (CĂN LỀ) MÀY VỪA CHỈ RA ===
            // =============================================================
            
            // Lấy tên sách gốc
            String tenSach = sachHienTai.getTen();
            
            // Nếu tên sách dài hơn 30 (độ rộng cột), cắt bớt còn 27 + "..."
            if (tenSach.length() > 30) {
                tenSach = tenSach.substring(0, 27) + "..."; 
            }
            
            // Tương tự, nếu tên tác giả dài hơn 20 (độ rộng cột), cắt bớt
            if (tenTacGia.length() > 20) {
                tenTacGia = tenTacGia.substring(0, 17) + "...";
            }
            // =============================================================
            // === KẾT THÚC FIX ===
            // =============================================================

            // In ra dòng thống kê (giờ đã dùng biến tenSach và tenTacGia đã cắt bớt)
            System.out.printf("%-8s %-30s %-20s %-10d %-8d %-8d %-10d%n",
                sachHienTai.getMa(),
                tenSach, // <-- Dùng biến đã fix
                tenTacGia, // <-- Dùng biến đã fix
                sachHienTai.getNamXB(),
                tongBanSao, 
                dangMuon,   
                (tongBanSao - dangMuon)
            );
        }
    }

    // CHANGED: Dùng vòng lặp FOR
    public void hienThiChiTietSach() {
        System.out.println("\n" + ChiTietSach.header());
        // === SỬA DÒNG NÀY ===
        System.out.println("-".repeat(95)); // Kéo dài ra cho khớp header mới
        // ======================
        
        for(int i = 0; i < slCTS; i++) {
            System.out.println(dsCTS[i]);
        }
    }

    // CHANGED: Dùng vòng lặp FOR
    public void hienThiPhieuTra() {
        System.out.println("\n" + PhieuTraSach.header());
        System.out.println("-".repeat(50));
        for(int i = 0; i < slPhieuTra; i++) {
            System.out.println(dsPhieuTra[i]);
        }
    }

    // === CHUC NANG NGUOI MUON ===
    
    @Override
    public void muonSach(String maThuThu) {
        System.out.println("--- THU THU: CHO MUON SACH (Nhan vien: " + maThuThu + ") ---");
        System.out.print("Nhap ma sach muon (VD: TL0001): ");
        String maSach = sc.nextLine().toUpperCase();
        ChiTietSach cts = timCTSChuaMuon(maSach);
        if (cts == null) {
            System.out.println("Khong tim thay sach hoac sach da duoc muon het!");
            return;
        }

        System.out.print("Nhap ma nguoi muon (VD: NM01): ");
        String maNM = sc.nextLine().toUpperCase();
        
        NguoiMuon nm = null;
        for(int i = 0; i < slNguoiMuon; i++) {
            if(dsNguoiMuon[i].getMa().equals(maNM)) {
                nm = dsNguoiMuon[i];
                break;
            }
        }
        
        if (nm == null) {
            System.out.println("Khong tim thay nguoi muon!");
            return;
        }

        TheThuVien the = nm.getThe();
        
        // --- KIỂM TRA GIỚI HẠN SỐ SÁCH ---
        int soSachToiDa = the.getSoSachMuon();
        int soSachDangMuon = 0;
        for (int i = 0; i < slMuon; i++) {
            TTMuon tt_check = dsMuon[i];
            if (tt_check.getTheMuon() == the && tt_check.getNgayTra() == null) {
                soSachDangMuon++;
            }
        }
        
        if (soSachDangMuon >= soSachToiDa) {
            System.out.println("LOI: Nguoi nay da muon dat gioi han (" + soSachToiDa + " cuon).");
            return;
        }
        // ----------------------------------

        String idMuon = "MUON" + String.format("%03d", slMuon + 1);
        
        // CHANGED: Tạo TTMuon với mã thủ thư
        TTMuon tt = new TTMuon(idMuon, cts, the, Calendar.getInstance(), null, maThuThu);
        dsMuon[slMuon++] = tt; 
        
        cts.setTheMuon(the);
        System.out.println("Muon sach thanh cong! Ma muon: " + idMuon);
        System.out.println("Nhan vien thuc hien: " + maThuThu);
        System.out.println("So luot muon con lai cua khach: " + (soSachToiDa - soSachDangMuon - 1));
        
        // BẮT BUỘC: Ghi file ngay lập tức
        ghiDuLieu();
    }

    @Override
    public void traSach(String maThuThu) {
        System.out.println("--- THU THU: NHAN SACH TRA (Nhan vien: " + maThuThu + ") ---");
        System.out.print("Nhap ma muon (VD: MUON001): ");
        String maMuon = sc.nextLine().toUpperCase();
        
        TTMuon tt = null;
        for(int i = 0; i < slMuon; i++) {
            if(dsMuon[i].getId().equals(maMuon)) {
                tt = dsMuon[i];
                break;
            }
        }

        if (tt == null || tt.getNgayTra() != null) {
            System.out.println("Khong tim thay phieu muon hoac da tra!");
            return;
        }

        Calendar ngayTra = Calendar.getInstance();
        tt.setNgayTra(ngayTra);
        tt.getChiTietSach().setTheMuon(null); 

        long songay = (ngayTra.getTimeInMillis() - tt.getNgayMuon().getTimeInMillis()) / (1000 * 60 * 60 * 24);
        int soNgayMuonToiDa = tt.getTheMuon().getSoNgayMuon(); 
        
        System.out.println("So ngay da muon: " + songay + " / " + soNgayMuonToiDa + " (toi da)");
        
        double phat = 0;
        if (songay > soNgayMuonToiDa) {
            long ngayQuaHan = songay - soNgayMuonToiDa;
            phat = ngayQuaHan * 5000; 
            System.out.println("CANH BAO: Ban da tra sach qua han " + ngayQuaHan + " ngay.");
        }
        
        // GHI PHIẾU TRẢ CÓ ID THỦ THƯ
        String maPhieu = "PT" + String.format("%03d", slPhieuTra + 1);
        // CHANGED: Truyền thêm maThuThu vào constructor mới
        dsPhieuTra[slPhieuTra++] = new PhieuTraSach(maPhieu, tt, ngayTra, phat, maThuThu); 
        
        System.out.println("Tra sach thanh cong! Tong tien phat: " + phat + "d");
        System.out.println("ID Thu thu nhan: " + maThuThu);
    }
   
    public void hienThiSachDangMuon() {
        System.out.println("\nID MUON   MA SACH   NGUOI MUON     NGAY MUON");
        System.out.println("-".repeat(50));
        
        for (int i = 0; i < slMuon; i++) {
            TTMuon t = dsMuon[i];
            if (t.getNgayTra() == null) {
                // Tìm tên người mượn
                String tenNM = "Khong xac dinh";
                for (int j = 0; j < slNguoiMuon; j++) {
                    if (dsNguoiMuon[j].getThe() == t.getTheMuon()) {
                        tenNM = dsNguoiMuon[j].getTen();
                        break;
                    }
                }
                
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                System.out.printf("%-10s %-10s %-15s %s%n",
                    t.getId(), t.getChiTietSach().getSach().getMa(),
                    tenNM, sdf.format(t.getNgayMuon().getTime()));
            }
        }
    }


    @Override
    public void themSach() {
        System.out.println("\n--- THEM SACH MOI ---");

        // Bước 1: Nhập thông tin TỰA SÁCH (Chỉ làm 1 lần)
        String maSach = "TL" + String.format("%04d", slSach + 1);
        System.out.println("Ma tua sach : " + maSach);

        System.out.print("Nhap ten sach: ");
        String ten = sc.nextLine();
        
        // ... (Code nhập Tác Giả (tg) và NXB (nxb) y như cũ) ...
        System.out.print("Nhap ma tac gia ( de trong de them moi): ");
        String maTGInput = sc.nextLine().trim().toUpperCase();
        TacGia tg;
        if (maTGInput.isEmpty()) {
            int soTGMoi = slTacGia + 1;
            String maTGMoi = "TG" + String.format("%02d", soTGMoi);
            System.out.print("Nhap ten tac gia: ");
            String tenTGMoi = sc.nextLine();
            tg = new TacGia(maTGMoi, tenTGMoi);
            dsTacGia[slTacGia++] = tg;
        } else {
            tg = timTacGiaById(maTGInput, dsTacGia, slTacGia);
            if (tg == null) tg = dsTacGia[0]; 
        }

        System.out.print("Nhap ma NXB (de trong de them moi): ");
        String maNXBInput = sc.nextLine().trim().toUpperCase();
        NhaXuatBan nxb;
        if (maNXBInput.isEmpty()) {
            int soNXBMoi = slNXB + 1;
            String maNXBMoi = "NXB" + String.format("%02d", soNXBMoi);
            System.out.print("Nhap ten NXB: ");
            String tenNXBMoi = sc.nextLine();
            nxb = new NhaXuatBan(maNXBMoi, tenNXBMoi);
            dsNXB[slNXB++] = nxb; 
        } else {
            nxb = timNXBById(maNXBInput, dsNXB, slNXB);
            if (nxb == null) nxb = dsNXB[0];
        }
        
        System.out.print("Nhap nam xuat ban: ");
        int nam = Integer.parseInt(sc.nextLine());
        System.out.print("Nhap so trang: ");
        int trang = Integer.parseInt(sc.nextLine());

        // Tạo Tựa Sách (chỉ 1 đối tượng Sach)
        Sach sachMoi = new Sach(maSach, ten, tg, nxb, nam, trang);
        dsSach[slSach++] = sachMoi; // Thêm vào mảng Tựa Sách

        // =============================================================
        // === NEW: HỎI SỐ LƯỢNG BẢN SAO ===
        // =============================================================
        System.out.println("\n--- NHAP SO LUONG BAN SAO ---");
        System.out.println("Tua sach \"" + ten + "\" da duoc them.");
        System.out.print("Ban muon them bao nhieu ban sao (copy) cho tua sach nay? ");
        
        int soBanSao = 0;
        try {
            soBanSao = Integer.parseInt(sc.nextLine());
            if (soBanSao <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            System.out.println("So luong khong hop le. Mac dinh them 1 ban sao.");
            soBanSao = 1;
        }

        System.out.println("Dang them " + soBanSao + " ban sao vao kho...");

        // Chạy vòng lặp để tạo ra từng Bản Sao (ChiTietSach)
        for (int i = 0; i < soBanSao; i++) {
            
            // 1. Tạo Vị Trí Mới
            int vtIndex = slViTri; // Lấy số lượng Vị Trí hiện tại
            String idVT = "VT" + String.format("%02d", vtIndex + 1);
            String khu = "Khu " + ((vtIndex / 4) + 1);
            String ke = "Ke " + ((vtIndex % 4) + 1);
            String hang = "Hang " + ((vtIndex % 2) + 1);
            ViTri vtMoi = new ViTri(idVT, khu, ke, hang);
            dsViTri[slViTri++] = vtMoi; // Thêm vào mảng Vị Trí

            // 2. Tạo Bản Sao Mới
            String ctsId = "CTS" + String.format("%02d", slCTS + 1);
            // Trỏ bản sao này vào Tựa Sách (sachMoi) và Vị Trí (vtMoi)
            dsCTS[slCTS++] = new ChiTietSach(ctsId, sachMoi, vtMoi); 
            
            System.out.println("  + Da them ban sao " + (i+1) + " (ID: " + ctsId + ") vao vi tri: " + vtMoi.getViTri());
        }
        // =============================================================
        // === KẾT THÚC CODE MỚI ===
        // =============================================================

        System.out.println("=== THEM SACH VA BAN SAO THANH CONG! ===");
        System.out.println("Ma tua sach: " + maSach);
        System.out.println("Tong so ban sao da them: " + soBanSao);
        //ghi dữ liệu ngay lập tức
        ghiDuLieu();
    }
    
    @Override
    public void themNguoiMuon() {
        System.out.println("\n--- THEM NGUOI MUON MOI (THU THU) ---");

        // 1. Nhập thông tin cá nhân
        int soThuTu = slNguoiMuon + 1;
        String maNM = "NM" + String.format("%02d", soThuTu);
        System.out.println("Ma nguoi muon moi : " + maNM);

        System.out.print("Nhap ho ten: ");
        String ten = sc.nextLine();
        System.out.print("Nhap tuoi: ");
        int tuoi = 0;
        try {
            tuoi = Integer.parseInt(sc.nextLine());
            if (tuoi <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            System.out.println("Tuoi khong hop le. Dat mac dinh la 18.");
            tuoi = 18;
        }
        
        System.out.print("Nhap dia chi: ");
        String diaChi = sc.nextLine();

        // 2. CHỌN LOẠI THẺ (SỬA ĐỔI LỚN THEO YÊU CẦU)
        TheThuVien theMoi;
        String maTheMoi = String.format("%04d", slThe + 1); // Mã thẻ mới
        int luaChonLoaiThe = 0;

        do {
            System.out.println("Chon loai the cap cho nguoi muon: ");
            System.out.println("1. Loai A (Muon 30 ngay, 7 sach)");
            System.out.println("2. Loai B (Muon 15 ngay, 4 sach)");
            System.out.print("Lua chon cua ban (1 hoac 2): ");
            try {
                luaChonLoaiThe = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                luaChonLoaiThe = 0; // Gán giá trị không hợp lệ để lặp lại
            }
            
            if (luaChonLoaiThe == 1) {
                theMoi = new LoaiA(maTheMoi);
            } else if (luaChonLoaiThe == 2) {
                theMoi = new LoaiB(maTheMoi);
            } else {
                System.out.println("Lua chon khong hop le. Vui long chon 1 hoac 2.");
                theMoi = null; // Gán null để vòng lặp tiếp tục
            }
            
        } while (theMoi == null); // Lặp lại nếu chưa chọn đúng
        
        // 3. Thêm vào mảng
        dsThe[slThe++] = theMoi; // Thêm thẻ mới vào danh sách thẻ
        
        NguoiMuon nmMoi = new NguoiMuon(maNM, ten, tuoi, diaChi, theMoi);
        dsNguoiMuon[slNguoiMuon++] = nmMoi; // Thêm người mượn mới vào danh sách

        System.out.println("=== THEM NGUOI MUON THANH CONG! ===");
        System.out.println("Ma: " + maNM);
        System.out.println("The thu vien: " + theMoi.getMaThe() + " (" + theMoi.getLoai() + ")");

        // Ghi dữ liệu ngay lập tức
        ghiDuLieu();
    }

    /**
     * NEW: Chức năng riêng cho MenuNguoiMuon (Yêu cầu của bạn)
     * Chỉ hiển thị sách của một người mượn cụ thể.
     * UPDATED: Thêm logic tính số ngày mượn và cảnh báo phạt
     */
    public void hienThiSachCuaMotNguoiMuon() {
            System.out.println("\n--- XEM SACH BAN DANG MUON ---");
            System.out.print("Vui long nhap Ma Nguoi Muon cua ban (VD: NM01): ");
            String maNM = sc.nextLine().toUpperCase();
            
            // Bước 1: Tìm người mượn
            NguoiMuon nm = null;
            for(int i = 0; i < slNguoiMuon; i++) {
                if(dsNguoiMuon[i].getMa().equals(maNM)) {
                    nm = dsNguoiMuon[i];
                    break;
                }
            }
            
            if (nm == null) {
                System.out.println("Khong tim thay Ma Nguoi Muon: " + maNM);
                return;
            }
            
            // Bước 2: Lấy thẻ của họ và chào mừng
            TheThuVien theCuaNM = nm.getThe();
            System.out.println("Chao mung, " + nm.getTen() + ". (The: " + theCuaNM.getMaThe() + ")");
            
            // =============================================================
            // === NEW: LẤY THÔNG TIN NGÀY VÀ LỊCH TRƯỚC ===
            // =============================================================
            int maxDays = theCuaNM.getSoNgayMuon(); // Lấy số ngày tối đa (15 hoặc 30)
            Calendar homNay = Calendar.getInstance(); // Lấy ngày hôm nay
            
            System.out.println("Quyen loi cua ban: Toi da " + maxDays + " ngay/lan muon.");
            // =============================================================

            
            // Bước 3: Lặp qua danh sách mượn
            System.out.println("\n--- CAC SACH BAN DANG MUON ---");
            
            boolean timThaySach = false;
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            for (int i = 0; i < slMuon; i++) {
                TTMuon tt = dsMuon[i];
                
                // Kiểm tra: Phải là thẻ của người này VÀ sách chưa trả
                if (tt.getTheMuon() == theCuaNM && tt.getNgayTra() == null) {
                    timThaySach = true;
                    
                    String tenSach = "Sach khong ro ten";
                    if (tt.getChiTietSach() != null && tt.getChiTietSach().getSach() != null) {
                        tenSach = tt.getChiTietSach().getSach().getTen();
                    }
                    String ngayMuonStr = sdf.format(tt.getNgayMuon().getTime());
                    
                    // =============================================================
                    // === NEW: TÍNH TOÁN SỐ NGÀY VÀ PHẠT (YÊU CẦU CỦA TÔI) ===
                    // =============================================================
                    
                    // Tính số ngày đã mượn
                    long millisDiff = homNay.getTimeInMillis() - tt.getNgayMuon().getTimeInMillis();
                    long soNgayDaMuon = (millisDiff / (1000 * 60 * 60 * 24)) + 1; // +1 vì tính cả ngày mượn
                    
                    double phatTiemNang = 0;
                    String trangThaiNgay = soNgayDaMuon + "/" + maxDays + " ngay";

                    // Nếu lố ngày
                    if (soNgayDaMuon > maxDays) {
                        long ngayQuaHan = soNgayDaMuon - maxDays;
                        phatTiemNang = ngayQuaHan * 5000; // 5000d 
                        trangThaiNgay += " (DA QUA HAN " + ngayQuaHan + " NGAY)";
                    }
                    // =============================================================
                    
                    // In ra thông tin chi tiết
                    System.out.printf("* %s%n", tenSach);
                    System.out.printf("  - Ma muon: %s%n", tt.getId());
                    System.out.printf("  - Ngay muon: %s%n", ngayMuonStr);
                    System.out.printf("  - Trang thai: %s%n", trangThaiNgay); // Mới
                    
                    if (phatTiemNang > 0) {
                        System.out.printf("  - PHAT TAM TINH: %.0fd%n", phatTiemNang); // Mới
                    }
                }
            }
            
            if (!timThaySach) {
                System.out.println("Ban hien khong muon cuon sach nao.");
            }
        }

    // =================================================================
    // === YÊU CẦU 3: THÊM CÁC CHỨC NĂNG MỚI CHO THỦ THƯ ===
    // =================================================================
    
    
    /**
     * NEW: (Yêu cầu 3a) Hiển thị tất cả người mượn
     */
    public void hienThiNguoiMuon() {
        System.out.println("\n--- DANH SACH NGUOI MUON ---");
        System.out.println(String.format("%-6s %-20s %-5s %-15s %-8s %-5s", 
                            "MA NM", "TEN", "TUOI", "DIA CHI", "MA THE", "LOAI"));
        System.out.println("-".repeat(70));
        
        if (slNguoiMuon == 0) {
            System.out.println("Chua co nguoi muon nao trong he thong.");
            return;
        }
        
        for (int i = 0; i < slNguoiMuon; i++) {
            NguoiMuon nm = dsNguoiMuon[i];
            TheThuVien the = nm.getThe();
            System.out.printf("%-6s %-20s %-5d %-15s %-8s %-5s%n",
                nm.getMa(), nm.getTen(), nm.getTuoi(), nm.getDiaChi(),
                the.getMaThe(), the.getLoai());
        }
    }

    /**
     * NEW: (Yêu cầu 3c) Xem chi tiết mượn của 1 người
     */
    public void xemChiTietMuonCuaNguoiMuon() {
        System.out.println("\n--- XEM CHI TIET NGUOI MUON ---");
        System.out.print("Nhap ma nguoi muon (VD: NM01): ");
        String maNM = sc.nextLine().toUpperCase();
        
        // Bước 1: Tìm người mượn
        NguoiMuon nm = null;
        for(int i = 0; i < slNguoiMuon; i++) {
            if(dsNguoiMuon[i].getMa().equals(maNM)) {
                nm = dsNguoiMuon[i];
                break;
            }
        }
        
        if (nm == null) {
            System.out.println("Khong tim thay nguoi muon voi ma: " + maNM);
            return;
        }
        
        TheThuVien theCuaNM = nm.getThe();
        int maxDays = theCuaNM.getSoNgayMuon();
        
        System.out.println("--- THONG TIN NGUOI MUON ---");
        System.out.println("Ten: " + nm.getTen());
        System.out.println("The: " + theCuaNM.getMaThe() + " (Loai " + theCuaNM.getLoai() + ")");
        System.out.println("Quyen loi: " + maxDays + " ngay toi da");
        
        // Bước 2 & 3: Tìm sách đang mượn và tính ngày
        System.out.println("\n--- CAC SACH DANG MUON ---");
        
        boolean timThaySach = false;
        Calendar homNay = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        for (int i = 0; i < slMuon; i++) {
            TTMuon tt = dsMuon[i];
            
            // Kiểm tra: đúng người mượn VÀ sách chưa trả
            if (tt.getTheMuon() == theCuaNM && tt.getNgayTra() == null) {
                timThaySach = true;
                
                // Tính số ngày đã mượn
                long millisDiff = homNay.getTimeInMillis() - tt.getNgayMuon().getTimeInMillis();
                // +1 vì ngày mượn được tính là ngày 1
                long soNgayDaMuon = (millisDiff / (1000 * 60 * 60 * 24)) + 1; 
                
                String tenSach = "Sach khong ro ten";
                if (tt.getChiTietSach() != null && tt.getChiTietSach().getSach() != null) {
                    tenSach = tt.getChiTietSach().getSach().getTen();
                }
                String ngayMuonStr = sdf.format(tt.getNgayMuon().getTime());
                
                // So sánh và in ra
                String trangThaiNgay = soNgayDaMuon + "/" + maxDays;
                if (soNgayDaMuon > maxDays) {
                    trangThaiNgay += " (QUA HAN " + (soNgayDaMuon - maxDays) + " NGAY)";
                }
                
                System.out.printf("* %s%n", tenSach);
                System.out.printf("  - Ma muon: %s%n", tt.getId());
                System.out.printf("  - Ngay muon: %s%n", ngayMuonStr);
                System.out.printf("  - So ngay: %s%n", trangThaiNgay);
            }
        }
        
        if (!timThaySach) {
            System.out.println("Nguoi muon nay khong dang muon cuon sach nao.");
        }
    }

    /**
     * NEW: (Yêu cầu 1a) Hiển thị tất cả thủ thư
     */
    public void hienThiThuThu() {
        System.out.println("\n--- DANH SACH THU THU ---");
        System.out.println(ThuThu.header()); // Gọi hàm header() static từ model
        System.out.println("-".repeat(80));
        
        if (slThuThu == 0) {
            System.out.println("Chua co thu thu nao trong he thong.");
            return;
        }
        
        for (int i = 0; i < slThuThu; i++) {
            System.out.println(dsThuThu[i]); // Gọi hàm toString()
        }
    }

    /**
     * NEW: Thêm thủ thư mới (có xác thực "TT01")
     * CHANGED: Nhận mã của người gọi lệnh (session) để kiểm tra quyền
     */
    public void themThuThuMoi(String maNguoiGoiLenh) {
        System.out.println("\n--- THEM THU THU MOI ---");
        
        // Bước 1: Xác thực quyền 
        // Nó kiểm tra cái mã được "nhớ" lúc đăng nhập
        if (!maNguoiGoiLenh.equals("TT01")) {
            System.out.println("LOI: Ban (Ma: " + maNguoiGoiLenh + ") khong co quyen them thu thu moi.");
            System.out.println("Chi co Thu Thu Tong (TT01) moi co quyen nay.");
            return;
        }
        
        System.out.println("Xac thuc quyen TT01 thanh cong.");

        // Bước 2: Nhập thông tin (Giữ nguyên)
        int soThuTu = slThuThu + 1;
        String maMoi = "TT" + String.format("%02d", soThuTu);
        System.out.println("Ma thu thu moi: " + maMoi);

        System.out.print("Nhap ho ten: ");
        String ten = sc.nextLine();
        System.out.print("Nhap tuoi: ");
        int tuoi = Integer.parseInt(sc.nextLine());
        System.out.print("Nhap dia chi: ");
        String diaChi = sc.nextLine();
        System.out.print("Nhap so gio lam (mac dinh 160): ");
        int gioLam = 160;
        try { gioLam = Integer.parseInt(sc.nextLine()); } catch (Exception e) {}
        System.out.print("Nhap luong gio (mac dinh 50000): ");
        int luongGio = 50000;
        try { luongGio = Integer.parseInt(sc.nextLine()); } catch (Exception e) {}

        // Bước 3: Thêm vào mảng
        ThuThu ttMoi = new ThuThu(maMoi, ten, tuoi, diaChi, gioLam, luongGio);
        dsThuThu[slThuThu++] = ttMoi;
        
        System.out.println("=== THEM THU THU MOI THANH CONG! ===");
        System.out.println(ttMoi);
    }
    /**
     * NEW:  Hàm "gác cổng"
     * Kiểm tra xem mã thủ thư có tồn tại trong danh sách không
     */
    public boolean xacThucThuThu(String maThuThu) {
        if (slThuThu == 0) {
            System.out.println("LOI: Chua co thu thu nao trong he thong.");
            return false;
        }
        
        for (int i = 0; i < slThuThu; i++) {
            if (dsThuThu[i].getMa().equals(maThuThu)) {
                System.out.println("Xac thuc thanh cong. Chao mung " + dsThuThu[i].getTen() + "!");
                return true; // Tìm thấy
            }
        }
        
        System.out.println("LOI: Ma thu thu \"" + maThuThu + "\" khong ton tai.");
        return false; // Không tìm thấy
    }

    

    // =================================================================
    // === BIG UPDATE: THÊM CHỨC NĂNG SỬA (EDIT) ===
    // =================================================================

    /**
     * NEW: (Yêu cầu 11) Chỉnh sửa thông tin người mượn
     * Dùng chung cho cả Thủ Thư và Người Mượn
     */
     

    public void chinhSuaThongTinNguoiMuon() {
        System.out.println("\n--- CHINH SUA THONG TIN NGUOI MUON ---");
        System.out.print("Nhap Ma Nguoi Muon (VD: NM01) ban muon sua: ");
        String maNM = sc.nextLine().toUpperCase();
        
        NguoiMuon nm = null;
        for(int i = 0; i < slNguoiMuon; i++) {
            if(dsNguoiMuon[i].getMa().equals(maNM)) {
                nm = dsNguoiMuon[i];
                break;
            }
        }
        
        if (nm == null) {
            System.out.println("Khong tim thay Nguoi Muon voi ma: " + maNM);
            return;
        }

        System.out.println("Tim thay: " + nm.getTen() + ". (Bo trong de giu nguyen thong tin cu)");

        // Sửa Tên (giữ nguyên)
        System.out.printf("Nhap ten moi (Cu: %s): ", nm.getTen());
        String tenMoi = sc.nextLine();
        if (!tenMoi.isEmpty()) {
            nm.setTen(tenMoi);
            System.out.println("-> Da cap nhat ten.");
        }

        // === RÀNG BUỘC (VALIDATION) CHO TUỔI ===
        System.out.printf("Nhap tuoi moi (Cu: %d): ", nm.getTuoi());
        // Cho phép bỏ trống để giữ nguyên
        String tuoiMoiStr = sc.nextLine();
        if (!tuoiMoiStr.isEmpty()) {
            try {
                int tuoiMoi = Integer.parseInt(tuoiMoiStr);
                // Đặt ràng buộc cứng
                if (tuoiMoi >= 6 && tuoiMoi <= 100) { 
                    nm.setTuoi(tuoiMoi);
                    System.out.println("-> Da cap nhat tuoi.");
                } else {
                    System.out.println("-> Tuoi phai tu 6 den 100. Khong cap nhat.");
                }
            } catch (Exception e) {
                System.out.println("-> Tuoi nhap vao khong hop le. Khong cap nhat.");
            }
        }
        // === KẾT THÚC RÀNG BUỘC ===

        // Sửa Địa chỉ (giữ nguyên)
        System.out.printf("Nhap dia chi moi (Cu: %s): ", nm.getDiaChi());
        String diaChiMoi = sc.nextLine();
        if (!diaChiMoi.isEmpty()) {
            nm.setDiaChi(diaChiMoi);
            System.out.println("-> Da cap nhat dia chi.");
        }
        
        System.out.println("=== CAP NHAT THONG TIN THANH CONG! ===");
    }

    /**
     * NEW: (Yêu cầu 12) Chỉnh sửa thông tin sách
     */
    public void chinhSuaThongTinSach() {
        System.out.println("\n--- CHINH SUA THONG TIN SACH ---");
        System.out.print("Nhap Ma Sach (VD: TL0001) ban muon sua: ");
        String maSach = sc.nextLine().toUpperCase();
        
        // Bước 1: Tìm sách
        Sach sach = null;
        for(int i = 0; i < slSach; i++) {
            if(dsSach[i].getMa().equals(maSach)) {
                sach = dsSach[i];
                break;
            }
        }
        
        if (sach == null) {
            System.out.println("Khong tim thay Sach voi ma: " + maSach);
            return;
        }

        System.out.println("Tim thay: " + sach.getTen() + ". (Bo trong de giu nguyen thong tin cu)");

        // Bước 2: Sửa Tên
        System.out.printf("Nhap ten moi (Cu: %s): ", sach.getTen());
        String tenMoi = sc.nextLine();
        if (!tenMoi.isEmpty()) {
            sach.setTen(tenMoi);
            System.out.println("-> Da cap nhat ten.");
        }

        // Bước 3: Sửa Năm XB
        System.out.printf("Nhap nam XB moi (Cu: %d): ", sach.getNamXB());
        String namMoiStr = sc.nextLine();
        if (!namMoiStr.isEmpty()) {
            try {
                int namMoi = Integer.parseInt(namMoiStr);
                sach.setNamXB(namMoi);
                System.out.println("-> Da cap nhat nam XB.");
            } catch (Exception e) {
                System.out.println("-> Nam nhap vao khong hop le. Khong cap nhat.");
            }
        }

        // Bước 4: Sửa Số trang
        System.out.printf("Nhap so trang moi (Cu: %d): ", sach.getSoTrang());
        String trangMoiStr = sc.nextLine();
        if (!trangMoiStr.isEmpty()) {
            try {
                int trangMoi = Integer.parseInt(trangMoiStr);
                sach.setSoTrang(trangMoi);
                System.out.println("-> Da cap nhat so trang.");
            } catch (Exception e) {
                System.out.println("-> So trang nhap vao khong hop le. Khong cap nhat.");
            }
        }
        
        // Ghi chú: Không cho sửa Tác Giả, NXB vì nghiệp vụ phức tạp.
        System.out.println("=== CAP NHAT THONG TIN SACH THANH CONG! ===");
    }

    /**
     * NEW: (Yêu cầu 13) Chỉnh sửa thông tin thủ thư
     * Yêu cầu mã thủ thư đang đăng nhập (session)
     */
    public void chinhSuaThongTinThuThu(String maThuThuDangNhap) {
        System.out.println("\n--- CHINH SUA THONG TIN THU THU ---");
        System.out.print("Nhap Ma Thu Thu (VD: TT01) ban muon sua: ");
        String maTT = sc.nextLine().toUpperCase();
        
        // Bước 1: Tìm Thủ Thư cần sửa
        ThuThu ttCanSua = null;
        for(int i = 0; i < slThuThu; i++) {
            if(dsThuThu[i].getMa().equals(maTT)) {
                ttCanSua = dsThuThu[i];
                break;
            }
        }
        
        if (ttCanSua == null) {
            System.out.println("Khong tim thay Thu Thu voi ma: " + maTT);
            return;
        }

        // Bước 2: Xác thực quyền
        // Quy tắc: 1. Bạn được sửa chính mình. 2. TT01 được sửa mọi người.
        boolean duocPhepSua = false;
        if (ttCanSua.getMa().equals(maThuThuDangNhap)) {
            duocPhepSua = true; // Được tự sửa mình
            System.out.println("Ban dang chinh sua thong tin cua chinh minh.");
        } else if (maThuThuDangNhap.equals("TT01")) {
            duocPhepSua = true; // TT01 được sửa người khác
            System.out.println("Thu Thu Tong (TT01) dang chinh sua thong tin cua " + ttCanSua.getTen());
        }

        if (!duocPhepSua) {
            System.out.println("LOI: Ban (Ma: " + maThuThuDangNhap + ") khong co quyen sua thong tin cua " + maTT);
            return;
        }

        // Bước 3: Sửa thông tin
        System.out.println("Tim thay: " + ttCanSua.getTen() + ". (Bo trong de giu nguyen thong tin cu)");
        
        System.out.printf("Nhap ten moi (Cu: %s): ", ttCanSua.getTen());
        String tenMoi = sc.nextLine();
        if (!tenMoi.isEmpty()) ttCanSua.setTen(tenMoi);

        System.out.printf("Nhap tuoi moi (Cu: %d): ", ttCanSua.getTuoi());
        String tuoiMoiStr = sc.nextLine();
        if (!tuoiMoiStr.isEmpty()) {
            try { ttCanSua.setTuoi(Integer.parseInt(tuoiMoiStr)); } catch (Exception e) {}
        }

        System.out.printf("Nhap dia chi moi (Cu: %s): ", ttCanSua.getDiaChi());
        String diaChiMoi = sc.nextLine();
        if (!diaChiMoi.isEmpty()) ttCanSua.setDiaChi(diaChiMoi);

        // Chỉ TT01 mới được sửa Lương (logic thêm)
        if (maThuThuDangNhap.equals("TT01")) {
             // Try to read current 'gioLam' reflectively (try common field names), fallback to 0
             int curGioVal = 0;
             try {
                 java.lang.reflect.Field f = ttCanSua.getClass().getDeclaredField("gioLam");
                 f.setAccessible(true);
                 curGioVal = f.getInt(ttCanSua);
             } catch (Exception e1) {
                 try {
                     java.lang.reflect.Field f2 = ttCanSua.getClass().getDeclaredField("soGioLam");
                     f2.setAccessible(true);
                     curGioVal = f2.getInt(ttCanSua);
                 } catch (Exception e2) {
                     // leave default 0
                 }
             }
             System.out.printf("Nhap gio lam moi (Cu: %d): ", curGioVal);
             String gioMoiStr = sc.nextLine();
             if (!gioMoiStr.isEmpty()) {
                 try { ttCanSua.setGioLam(Integer.parseInt(gioMoiStr)); } catch (Exception e) {}
             }
             
             // Try to read current 'luongGio' reflectively (try common field names), fallback to 0
             int curLuongVal = 0;
             try {
                 java.lang.reflect.Field f3 = ttCanSua.getClass().getDeclaredField("luongGio");
                 f3.setAccessible(true);
                 curLuongVal = f3.getInt(ttCanSua);
             } catch (Exception e1) {
                 try {
                     java.lang.reflect.Field f4 = ttCanSua.getClass().getDeclaredField("luong");
                     f4.setAccessible(true);
                     curLuongVal = f4.getInt(ttCanSua);
                 } catch (Exception e2) {
                     // leave default 0
                 }
             }
             System.out.printf("Nhap luong gio moi (Cu: %d): ", curLuongVal);
             String luongMoiStr = sc.nextLine();
             if (!luongMoiStr.isEmpty()) {
                 try { ttCanSua.setLuongGio(Integer.parseInt(luongMoiStr)); } catch (Exception e) {}
             }
        } else {
            System.out.println("(Ban khong co quyen thay doi Gio Lam va Luong Gio. Chi TT01 moi co the.)");
        }

        System.out.println("=== CAP NHAT THONG TIN THU THU THANH CONG! ===");
    }

    // =================================================================
    // === BIG UPDATE: THÊM CHỨC NĂNG TÌM KIẾM (SEARCH) ===
    // =================================================================

    /**
     * NEW: (Yêu cầu 14) Hàm tìm kiếm TỔNG QUÁT
     * Dùng boolean 'isThuThu' để phân quyền 
     */
    public void timKiem(boolean isThuThu) {
        // Nếu là Người Mượn, chỉ cho tìm sách
        if (!isThuThu) {
            System.out.println("\n--- TIM KIEM SACH ---");
            timKiemSachHelper(); // Gọi thẳng hàm tìm sách
            return;
        }
        
        // Nếu là Thủ Thư, cho thấy menu đầy đủ
        while (true) {
            System.out.println("\n--- MENU TIM KIEM (THU THU) ---");
            System.out.println("1. Tim kiem Sach");
            System.out.println("2. Tim kiem Nguoi Muon");
            System.out.println("3. Tim kiem Thu Thu");
            System.out.println("0. Quay lai Menu Thu Thu");
            System.out.print("Chon: ");
            
            int c = -1;
            try { c = Integer.parseInt(sc.nextLine()); } catch (Exception e) {}
            
            if (c == 0) break;
            
            switch(c) {
                case 1: timKiemSachHelper(); break;
                case 2: timKiemNguoiMuonHelper(); break;
                case 3: timKiemThuThuHelper(); break;
                default: System.out.println("Lua chon khong hop le.");
            }
        }
    }

    /**
     * NEW: (Yêu cầu 15) Chỉnh sửa (Thay đổi) Thẻ Thư Viện
     * UPDATED: Sửa lỗi trùng mã và lỗi ghi đè bằng cách "Sửa Tại Chỗ"
     */
    public void chinhSuaTheNguoiMuon() {
        System.out.println("\n--- THAY DOI LOAI THE THU VIEN ---");
        System.out.print("Nhap Ma Nguoi Muon (VD: NM01) ban muon doi the: ");
        String maNM = sc.nextLine().toUpperCase();
        
        // Bước 1: Tìm người mượn
        NguoiMuon nm = null;
        for(int i = 0; i < slNguoiMuon; i++) {
            if(dsNguoiMuon[i].getMa().equals(maNM)) {
                nm = dsNguoiMuon[i];
                break;
            }
        }
        
        if (nm == null) {
            System.out.println("Khong tim thay Nguoi Muon: " + maNM);
            return;
        }

        // Bước 2: Lấy thông tin thẻ cũ (gồm MÃ và VỊ TRÍ)
        TheThuVien theCu = nm.getThe();
        String maTheGiunguyen = theCu.getMaThe(); // <<< LẤY MÃ CŨ
        int indexTheCu = -1; // <<< LẤY VỊ TRÍ CŨ
        
        for (int i = 0; i < slThe; i++) {
            if (dsThe[i] == theCu) { // So sánh tham chiếu (địa chỉ ô nhớ)
                indexTheCu = i;
                break;
            }
        }

        if (indexTheCu == -1) {
            System.out.println("LOI: Khong tim thay the trong he thong. Du lieu bi hong!");
            return;
        }

        System.out.println("Nguoi muon: " + nm.getTen());
        System.out.println("The hien tai: " + maTheGiunguyen + " (Loai " + theCu.getLoai() + ")");

        // Bước 3: Xác định thẻ mới (NHƯNG DÙNG LẠI MÃ CŨ)
        TheThuVien theMoi;

        if (theCu instanceof LoaiA) {
            System.out.print("Ban co muon doi sang LOAI B khong? (y/n): ");
            theMoi = new LoaiB(maTheGiunguyen); // <<< DÙNG LẠI MÃ CŨ
        } else {
            System.out.print("Ban co muon doi sang LOAI A khong? (y/n): ");
            theMoi = new LoaiA(maTheGiunguyen); // <<< DÙNG LẠI MÃ CŨ
        }
        
        String confirm = sc.nextLine().toLowerCase();
        if (!confirm.equals("y")) {
            System.out.println("Huy bo thao tac.");
            return;
        }

        // Bước 4: Thay thế tại chỗ (An toàn)
        
        // 4a. Thay thế trong mảng dsThe (Đè lên thẻ cũ ở đúng vị trí)
        dsThe[indexTheCu] = theMoi; 
        
        // 4b. Cập nhật NguoiMuon (Đưa "chìa khóa" mới)
        nm.setThe(theMoi); 

        // 4c. Cập nhật TẤT CẢ phiếu mượn (để trỏ vào thẻ mới)
        int countMuon = 0;
        for (int i = 0; i < slMuon; i++) {
            if (dsMuon[i].getTheMuon() == theCu) { // Tìm tất cả phiếu trỏ vào thẻ CŨ
                dsMuon[i].setTheMuon(theMoi); // Bắt nó trỏ vào thẻ MỚI
                countMuon++;
            }
        }
        
        // KHÔNG CẦN DỒN MẢNG, KHÔNG CẦN slThe++ hay slThe--
        // MỌI THỨ AN TOÀN

        System.out.println("=== DOI THE THANH CONG! ===");
        System.out.println("Ma the " + maTheGiunguyen + " da duoc chuyen sang (Loai " + theMoi.getLoai() + ")");
        System.out.println("Da cap nhat " + countMuon + " phieu muon lien quan.");
        // ghi dữ liệu ngay sau khi thay đổi
        ghiDuLieu();
    }

    /**
     * NEW: (Yêu cầu 16a) Thống kê Tổng quan
     */
    public void thongKeTongQuan() {
        System.out.println("\n--- BAO CAO THONG KE TONG QUAN ---");
        
        // 1. Đếm sách đang mượn
        int sachDangMuon = 0;
        for(int i = 0; i < slMuon; i++) {
            if (dsMuon[i].getNgayTra() == null) {
                sachDangMuon++;
            }
        }
        
        // 2. Đếm loại thẻ
        int loaiA = 0;
        int loaiB = 0;
        for(int i = 0; i < slThe; i++) {
            if (dsThe[i] instanceof LoaiA) {
                loaiA++;
            } else {
                loaiB++;
            }
        }

        System.out.println("- Tong so dau sach: " + slSach);
        System.out.println("- Tong so luong sach (chi tiet): " + slCTS);
        System.out.println("- Sach dang duoc muon: " + sachDangMuon);
        System.out.println("- Sach con lai trong kho: " + (slCTS - sachDangMuon));
        System.out.println("- Tong so nguoi muon: " + slNguoiMuon);
        System.out.println("  + Loai A: " + loaiA);
        System.out.println("  + Loai B: " + loaiB);
        System.out.println("- Tong so thu thu: " + slThuThu);
    }

    /**
     * NEW: (Yêu cầu 16b) Thống kê Sách quá hạn
     */
    public void thongKeSachQuaHan() {
        System.out.println("\n--- BAO CAO SACH DANG MUON QUA HAN ---");
        
        Calendar homNay = Calendar.getInstance();
        boolean timThay = false;

        System.out.printf("%-10s %-25s %-10s %-5s %s%n", 
            "MA MUON", "TEN SACH", "NGUOI MUON", "LOAI", "TRANG THAI");
        System.out.println("-".repeat(80));

        for (int i = 0; i < slMuon; i++) {
            TTMuon tt = dsMuon[i];
            
            // Chỉ kiểm tra sách CHƯA TRẢ
            if (tt.getNgayTra() == null) {
                long millisDiff = homNay.getTimeInMillis() - tt.getNgayMuon().getTimeInMillis();
                long soNgayDaMuon = (millisDiff / (1000 * 60 * 60 * 24)) + 1;
                int maxDays = tt.getTheMuon().getSoNgayMuon();
                
                // Nếu lố ngày
                if (soNgayDaMuon > maxDays) {
                    timThay = true;
                    String tenSach = tt.getChiTietSach().getSach().getTen();
                    String tenNM = "N/A";
                    // Tìm tên người mượn
                    for (int j = 0; j < slNguoiMuon; j++) {
                        if (dsNguoiMuon[j].getThe() == tt.getTheMuon()) {
                            tenNM = dsNguoiMuon[j].getTen(); break;
                        }
                    }
                    String loaiThe = tt.getTheMuon().getLoai();
                    String trangThai = "Qua han " + (soNgayDaMuon - maxDays) + " ngay";
                    
                    System.out.printf("%-10s %-25s %-10s %-5s %s%n", 
                        tt.getId(), tenSach, tenNM, loaiThe, trangThai);
                }
            }
        }
        
        if (!timThay) {
            System.out.println("Chuc mung! Khong co sach nao bi qua han.");
        }
    }

    /**
     * NEW: (Yêu cầu 16c) Thống kê Tiền phạt
     */
    public void thongKeTienPhat() {
        System.out.println("\n--- BAO CAO DOANH THU TIEN PHAT ---");
        
        double tongTienPhat = 0;
        int soLuotPhat = 0;
        
        for (int i = 0; i < slPhieuTra; i++) {
            if (dsPhieuTra[i].getTienPhat() > 0) {
                tongTienPhat += dsPhieuTra[i].getTienPhat();
                soLuotPhat++;
            }
        }
        
        System.out.println("- Tong so phieu tra co phat: " + soLuotPhat);
        System.out.printf("- TONG DOANH THU tu tien phat: %.0fd%n", tongTienPhat);
    }

    // --- CÁC HÀM HELPER CHO VIỆC TÌM KIẾM ---

    private void timKiemSachHelper() {
        System.out.print("Nhap ten sach can tim (de trong de hien tat ca): ");
        String keyword = sc.nextLine().toLowerCase().trim();
        boolean timThay = false;
        
        System.out.println("\n--- KET QUA TIM KIEM SACH ---");
        
        // Header xịn (giống hienThiSach)
        System.out.println(String.format("%-8s %-30s %-20s %-10s %-8s %-8s %-10s", 
                            "MA SACH", "TEN SACH", "TAC GIA", "NAM XB", "TONG SL", "DANG MUON", "CON LAI"));
        System.out.println("-".repeat(100));

        // Loop 1: Lặp qua từng TỰA SÁCH
        for(int i = 0; i < slSach; i++) {
            Sach sachHienTai = dsSach[i];
            
            // Chỉ hiển thị nếu tên sách chứa từ khóa
            if(sachHienTai.getTen().toLowerCase().contains(keyword)) {
                timThay = true;
                String maSachHienTai = sachHienTai.getMa();

                // Biến đếm thống kê (y hệt hienThiSach)
                int tongBanSao = 0;
                int dangMuon = 0;

                // Loop 2 (Lồng): Lặp qua tất cả BẢN SAO để đếm
                for (int j = 0; j < slCTS; j++) {
                    if (dsCTS[j].getSach() != null && dsCTS[j].getSach().getMa().equals(maSachHienTai)) {
                        tongBanSao++; 
                        if (dsCTS[j].getTheMuon() != null) {
                            dangMuon++;
                        }
                    }
                }
                
                String tenTacGia = "N/A";
                if (sachHienTai.getTacGia() != null) {
                    tenTacGia = sachHienTai.getTacGia().getTen();
                }

                // In ra dòng thống kê của tựa sách này
                System.out.printf("%-8s %-30s %-20s %-10d %-8d %-8d %-10d%n",
                    sachHienTai.getMa(),
                    sachHienTai.getTen(),
                    tenTacGia,
                    sachHienTai.getNamXB(),
                    tongBanSao,
                    dangMuon,
                    (tongBanSao - dangMuon)
                );
            } // Hết khối if (tim kiem)
        } // Hết vòng lặp Tựa Sách

        if (!timThay) System.out.println("Khong tim thay sach nao co ten chua \"" + keyword + "\"");
    }

    private void timKiemNguoiMuonHelper() {
        System.out.print("Nhap ten nguoi muon can tim: ");
        String keyword = sc.nextLine().toLowerCase().trim();
        boolean timThay = false;

        System.out.println("\n--- KET QUA TIM KIEM NGUOI MUON ---");
        System.out.println(String.format("%-6s %-20s %-5s %-15s %-8s", 
                            "MA NM", "TEN", "TUOI", "DIA CHI", "MA THE"));
        System.out.println("-".repeat(65));

        for(int i = 0; i < slNguoiMuon; i++) {
            if(dsNguoiMuon[i].getTen().toLowerCase().contains(keyword)) {
                NguoiMuon nm = dsNguoiMuon[i];
                System.out.printf("%-6s %-20s %-5d %-15s %-8s%n",
                    nm.getMa(), nm.getTen(), nm.getTuoi(), nm.getDiaChi(),
                    nm.getThe().getMaThe());
                timThay = true;
            }
        }
        if (!timThay) System.out.println("Khong tim thay nguoi muon nao co ten chua \"" + keyword + "\"");
    }

    private void timKiemThuThuHelper() {
        System.out.print("Nhap ten thu thu can tim: ");
        String keyword = sc.nextLine().toLowerCase().trim();
        boolean timThay = false;

        System.out.println("\n--- KET QUA TIM KIEM THU THU ---");
        System.out.println(ThuThu.header());
        System.out.println("-".repeat(80));

        for(int i = 0; i < slThuThu; i++) {
            if(dsThuThu[i].getTen().toLowerCase().contains(keyword)) {
                System.out.println(dsThuThu[i]);
                timThay = true;
            }
        }
        if (!timThay) System.out.println("Khong tim thay thu thu nao co ten chua \"" + keyword + "\"");
    }

    

    /**
     * NEW: Thống kê chi tiết LOG mượn/trả kèm tên Thủ thư thực hiện
     */
    // HÃY DÙNG HÀM NÀY ĐỂ THAY THẾ HÀM thongKeLichSuPhucVu() CŨ

    /**
     * NEW: Thống kê chi tiết LOG mượn/trả
     * UPDATED: Hiển thị TÊN THỦ THƯ thay vì chỉ hiện mã cho chuyên nghiệp
     */
    public void thongKeLichSuPhucVu() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        
        System.out.println("\n====================================================================================================");
        System.out.println("                                  NHAT KY HOAT DONG THU THU                                         ");
        System.out.println("====================================================================================================\n");

        // --- PHẦN 1: LOG MƯỢN SÁCH ---
        System.out.println(">>> PHAN 1: LICH SU CHO MUON SACH");
        // Mở rộng cột TT THUC HIEN ra để chứa Tên
        System.out.println(String.format("%-10s %-25s %-15s %-18s %-25s", 
                            "MA MUON", "TEN SACH", "NGUOI MUON", "NGAY MUON", "TT THUC HIEN"));
        System.out.println("-".repeat(100));

        if (slMuon == 0) System.out.println("(Chua co du lieu muon)");

        for (int i = 0; i < slMuon; i++) {
            TTMuon tt = dsMuon[i];
            
            // 1. Xử lý tên sách
            String tenSach = "N/A";
            if (tt.getChiTietSach() != null && tt.getChiTietSach().getSach() != null) {
                tenSach = tt.getChiTietSach().getSach().getTen();
                if (tenSach.length() > 22) tenSach = tenSach.substring(0, 22) + "...";
            }

            // 2. Xử lý tên người mượn
            String tenNM = "N/A";
            for (int j = 0; j < slNguoiMuon; j++) {
                if (dsNguoiMuon[j].getThe() == tt.getTheMuon()) {
                    tenNM = dsNguoiMuon[j].getTen(); break;
                }
            }
            if (tenNM.length() > 12) tenNM = tenNM.substring(0, 12) + "...";

            // 3. XỬ LÝ TÊN THỦ THƯ (Lookup)
            String maTT = tt.getMaThuThu();
            String tenTT = "N/A"; // Mặc định nếu không tìm thấy hoặc null
            
            if (maTT != null && !maTT.equals("null")) {
                // Chạy vòng lặp tìm tên thủ thư trong danh sách
                for (int k = 0; k < slThuThu; k++) {
                    if (dsThuThu[k].getMa().equals(maTT)) {
                        tenTT = dsThuThu[k].getTen();
                        break;
                    }
                }
                // Format lại cho đẹp: "Ten (Ma)"
                if (tenTT.length() > 15) tenTT = tenTT.substring(0, 15) + "..";
                tenTT = tenTT + " (" + maTT + ")";
            } else {
                tenTT = "Tu dong (System)";
            }

            System.out.printf("%-10s %-25s %-15s %-18s %-25s%n",
                tt.getId(),
                tenSach,
                tenNM,
                sdf.format(tt.getNgayMuon().getTime()),
                tenTT // <--- Đã hiện tên thủ thư
            );
        }

        System.out.println("\n" + "=".repeat(100) + "\n");

        // --- PHẦN 2: LOG TRẢ SÁCH ---
        System.out.println(">>> PHAN 2: LICH SU NHAN TRA SACH");
        System.out.println(String.format("%-10s %-10s %-18s %-12s %-15s %-25s", 
                            "MA PHIEU", "MA MUON", "NGAY TRA", "TIEN PHAT", "TRANG THAI", "TT NHAN"));
        System.out.println("-".repeat(100));

        if (slPhieuTra == 0) System.out.println("(Chua co du lieu tra)");

        for (int i = 0; i < slPhieuTra; i++) {
            PhieuTraSach pt = dsPhieuTra[i];
            
            String idMuon = (pt.getTTMuon() != null) ? pt.getTTMuon().getId() : "N/A";
            String trangThai = (pt.getTienPhat() > 0) ? "TRE HAN" : "DUNG HAN";
            
            String ngayTraStr = "N/A";
            if (pt.getNgayTra() != null) {
                ngayTraStr = sdf.format(pt.getNgayTra().getTime());
            }

            // 3. XỬ LÝ TÊN THỦ THƯ NHẬN (Lookup)
            String maTT = pt.getMaThuThu();
            String tenTT = "N/A";
            
            if (maTT != null && !maTT.equals("null") && !maTT.equals("N/A")) {
                for (int k = 0; k < slThuThu; k++) {
                    if (dsThuThu[k].getMa().equals(maTT)) {
                        tenTT = dsThuThu[k].getTen();
                        break;
                    }
                }
                if (tenTT.length() > 15) tenTT = tenTT.substring(0, 15) + "..";
                tenTT = tenTT + " (" + maTT + ")";
            } else {
                tenTT = "N/A";
            }

            System.out.printf("%-10s %-10s %-18s %-12.0f %-15s %-25s%n",
                pt.getMaPhieu(),
                idMuon,
                ngayTraStr,
                pt.getTienPhat(),
                trangThai,
                tenTT // <--- Đã hiện tên thủ thư
            );
        }
        System.out.println();
    }

    /**
     * NEW: Hàm "helper" dùng để RÀNG BUỘC
     * Bắt người dùng nhập một số hợp lệ trong khoảng min/max
     */
    private int getIntInput(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = Integer.parseInt(sc.nextLine());
                if (value >= min && value <= max) {
                    return value; // Nhập đúng, trả về
                } else {
                    System.out.println("LOI: So phai nam trong khoang tu " + min + " den " + max + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("LOI: Vui long nhap mot con so hop le.");
            }
        }
    }
    
    
}