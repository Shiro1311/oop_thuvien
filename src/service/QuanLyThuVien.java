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

        // Kiểm tra nếu không có dữ liệu (lần chạy đầu tiên) thì tạo dữ liệu mẫu
        if (slTacGia == 0 && slNXB == 0 && slSach == 0) {
            System.out.println("Khong tim thay file du lieu hoac file rong. Dang tao du lieu mau...");
            taoDuLieuMau();
            System.out.println("Da tao " + slSach + " sach mau. Du lieu se duoc ghi khi thoat chuong trinh.");
        } else {
            System.out.println("Doc du lieu hoan tat! Tai " + slSach + " sach, " 
                            + slNguoiMuon + " nguoi muon, " + slThuThu + " thu thu."); // <--- CẬP NHẬT
        }
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
    private void taoDuLieuMau() {
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
        
        dsPhieuTra[slPhieuTra++] = new PhieuTraSach("PT001", null, null, 0);
    }

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
    @Override
    public void hienThiSach() {
        System.out.println("\n" + Sach.header());
        System.out.println("-".repeat(90));
        for(int i = 0; i < slSach; i++) {
            dsSach[i].hienThi();
        }
    }

    // CHANGED: Dùng vòng lặp FOR
    public void hienThiChiTietSach() {
        System.out.println("\n" + ChiTietSach.header());
        System.out.println("-".repeat(50));
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
    public void muonSach() {
        System.out.print("Nhap ma sach muon: ");
        String maSach = sc.nextLine().toUpperCase();
        ChiTietSach cts = timCTSChuaMuon(maSach);
        if (cts == null) {
            System.out.println("Khong tim thay sach hoac da duoc muon!");
            return;
        }

        System.out.print("Nhap ma nguoi muon (NM01/NM02): ");
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
        if (the == null) {
            System.out.println("Nguoi muon chua co the!");
            return;
        }

        // =============================================================
        // === NEW: (YÊU CẦU 1) KIỂM TRA GIỚI HẠN SỐ LƯỢNG MƯỢN ===
        // =============================================================
        int soSachToiDa = the.getSoSachMuon(); // Lấy từ thẻ (VD: 4 hoặc 7)
        int soSachDangMuon = 0;
        
        // Đếm số sách người này đang mượn (chưa trả)
        for (int i = 0; i < slMuon; i++) {
            TTMuon tt_dangkiemtra = dsMuon[i];
            // Phải đúng thẻ VÀ sách đó chưa trả
            if (tt_dangkiemtra.getTheMuon() == the && tt_dangkiemtra.getNgayTra() == null) {
                soSachDangMuon++;
            }
        }
        
        System.out.println("Thong tin muon: Da muon " + soSachDangMuon + " / " + soSachToiDa + " cuon toi da.");

        // Kiểm tra giới hạn
        if (soSachDangMuon >= soSachToiDa) {
            System.out.println("LOI: Ban da muon dat so luong sach toi da (" + soSachToiDa + " cuon).");
            System.out.println("Vui long tra bot sach truoc khi muon them.");
            return; // Dừng lại, không cho mượn
        }
        // =============================================================
        // === KẾT THÚC CODE MỚI ===
        // =============================================================

        String idMuon = "MUON" + String.format("%03d", slMuon + 1);
        TTMuon tt = new TTMuon(idMuon, cts, the, Calendar.getInstance(), null);
        dsMuon[slMuon++] = tt; // Thêm vào mảng
        
        cts.setTheMuon(the);
        System.out.println("Muon sach thanh cong! Ma muon: " + idMuon);
        System.out.println("So luot muon con lai: " + (soSachToiDa - soSachDangMuon - 1));
    }

    @Override
    public void traSach() {
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
        tt.getChiTietSach().setTheMuon(null); // Trả sách về kho

        // =============================================================
        // === SỬA LỖI LOGIC TÍNH PHẠT (YÊU CẦU 2) ===
        // =============================================================
        
        // Tính tổng số ngày đã mượn
        long songay = (ngayTra.getTimeInMillis() - tt.getNgayMuon().getTimeInMillis()) / (1000 * 60 * 60 * 24);
        
        // Lấy số ngày mượn tối đa TỪ THẺ (LoaiA: 30, LoaiB: 15)
        int soNgayMuonToiDa = tt.getTheMuon().getSoNgayMuon(); 
        
        System.out.println("So ngay da muon: " + songay + " / " + soNgayMuonToiDa + " (toi da)");
        
        double phat = 0;
        // So sánh với số ngày tối đa của thẻ, KHÔNG DÙNG SỐ 7 NỮA
        if (songay > soNgayMuonToiDa) {
            long ngayQuaHan = songay - soNgayMuonToiDa;
            phat = ngayQuaHan * 5000; // 5000d cho mỗi ngày quá hạn
            System.out.println("CANH BAO: Ban da tra sach qua han " + ngayQuaHan + " ngay.");
        }
        // =============================================================
        // === KẾT THÚC SỬA LỖI ===
        // =============================================================

        String maPhieu = "PT" + String.format("%03d", slPhieuTra + 1);
        dsPhieuTra[slPhieuTra++] = new PhieuTraSach(maPhieu, tt, ngayTra, phat);
        
        System.out.println("Tra sach thanh cong! Tong tien phat: " + phat + "d");
    }

    // CHANGED: Dùng vòng lặp FOR
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

        // CHANGED: Dùng slSach
        int soThuTu = slSach + 1;
        String maSach = "TL" + String.format("%04d", soThuTu);
        System.out.println("Ma sach : " + maSach);

        System.out.print("Nhap ten sach: ");
        String ten = sc.nextLine();

        System.out.print("Nhap ma tac gia ( de trong de them moi): ");
        String maTGInput = sc.nextLine().trim().toUpperCase();
        TacGia tg;

        if (maTGInput.isEmpty()) {
            // CHANGED: Dùng slTacGia
            int soTGMoi = slTacGia + 1;
            String maTGMoi = "TG" + String.format("%02d", soTGMoi);
            System.out.print("Nhap ten tac gia: ");
            String tenTGMoi = sc.nextLine();
            tg = new TacGia(maTGMoi, tenTGMoi);
            dsTacGia[slTacGia++] = tg; // Thêm vào mảng
            System.out.println("Da them tac gia moi: " + maTGMoi + " - " + tenTGMoi);
        } else {
            // SỬA DÒNG NÀY:
            tg = timTacGiaById(maTGInput, dsTacGia, slTacGia);
            if (tg == null) tg = dsTacGia[0]; // Giữ logic dự phòng
        }

        // Tương tự cho NXB
        System.out.print("Nhap ma NXB (de trong de them moi): ");
        String maNXBInput = sc.nextLine().trim().toUpperCase();
        NhaXuatBan nxb;
        if (maNXBInput.isEmpty()) {
            int soNXBMoi = slNXB + 1;
            String maNXBMoi = "NXB" + String.format("%02d", soNXBMoi);
            System.out.print("Nhap ten NXB: ");
            String tenNXBMoi = sc.nextLine();
            nxb = new NhaXuatBan(maNXBMoi, tenNXBMoi);
            dsNXB[slNXB++] = nxb; // Thêm vào mảng
            System.out.println("Da them NXB moi: " + maNXBMoi + " - " + tenNXBMoi);
        } else {
            // SỬA DÒNG NÀY:
            nxb = timNXBById(maNXBInput, dsNXB, slNXB);
            if (nxb == null) nxb = dsNXB[0]; // Giữ logic dự phòng
        }
        
        System.out.print("Nhap nam xuat ban: ");
        int nam = Integer.parseInt(sc.nextLine());
        System.out.print("Nhap so trang: ");
        int trang = Integer.parseInt(sc.nextLine());

        Sach sachMoi = new Sach(maSach, ten, tg, nxb, nam, trang);
        dsSach[slSach++] = sachMoi; // Thêm vào mảng

        // Tao vi tri moi
        int vtIndex = slViTri; // Dùng slViTri
        String idVT = "VT" + String.format("%02d", vtIndex + 1);
        String khu = "Khu " + ((vtIndex / 4) + 1);
        String ke = "Ke " + ((vtIndex % 4) + 1);
        String hang = "Hang " + ((vtIndex % 2) + 1);
        ViTri vtMoi = new ViTri(idVT, khu, ke, hang);
        dsViTri[slViTri++] = vtMoi; // Thêm vào mảng

        // Tao chi tiet sach
        String ctsId = "CTS" + String.format("%02d", slCTS + 1);
        dsCTS[slCTS++] = new ChiTietSach(ctsId, sachMoi, vtMoi); // Thêm vào mảng

        System.out.println("=== THEM SACH THANH CONG! ===");
        System.out.println("Ma sach: " + maSach);
        System.out.println("Vi tri: " + vtMoi.getViTri());
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
        
        // Bước 1: Xác thực quyền (Logic MỚI của mày)
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

    

    
    
}