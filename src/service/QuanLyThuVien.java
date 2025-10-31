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
        
        // CHANGED: Dùng vòng lặp FOR
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

        // CHANGED: Dùng slMuon và gán mảng
        String idMuon = "MUON" + String.format("%03d", slMuon + 1);
        TTMuon tt = new TTMuon(idMuon, cts, the, Calendar.getInstance(), null);
        dsMuon[slMuon++] = tt; // Thêm vào mảng
        
        cts.setTheMuon(the);
        System.out.println("Muon sach thanh cong! Ma muon: " + idMuon);
    }

    @Override
    public void traSach() {
        System.out.print("Nhap ma muon: ");
        String maMuon = sc.nextLine().toUpperCase();
        
        // CHANGED: Dùng vòng lặp FOR
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
        double phat = songay > 7 ? (songay - 7) * 5000 : 0;

        // CHANGED: Dùng slPhieuTra và gán mảng
        String maPhieu = "PT" + String.format("%03d", slPhieuTra + 1);
        dsPhieuTra[slPhieuTra++] = new PhieuTraSach(maPhieu, tt, ngayTra, phat);
        
        System.out.println("Tra sach thanh cong! Phat: " + phat + "d");
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
        System.out.println("\n--- THEM NGUOI MUON MOI ---");

        // CHANGED: Dùng slNguoiMuon
        int soThuTu = slNguoiMuon + 1;
        String maNM = "NM" + String.format("%02d", soThuTu);
        System.out.println("Ma nguoi muon moi : " + maNM);

        System.out.print("Nhap ho ten: ");
        String ten = sc.nextLine();
        System.out.print("Nhap tuoi: ");
        int tuoi = Integer.parseInt(sc.nextLine());
        System.out.print("Nhap dia chi: ");
        String diaChi = sc.nextLine();

        // CHANGED: Dùng slThe
        TheThuVien theMoi;
        if (slThe == 0) {
            theMoi = new LoaiA("0001");
        } else {
            TheThuVien theCu = dsThe[slThe - 1]; // Lấy phần tử cuối
            if (theCu instanceof LoaiA) {
                theMoi = new LoaiB("000" + (slThe + 1));
            } else {
                theMoi = new LoaiA("000" + (slThe + 1));
            }
        }
        dsThe[slThe++] = theMoi; // Thêm vào mảng
        
        NguoiMuon nmMoi = new NguoiMuon(maNM, ten, tuoi, diaChi, theMoi);
        dsNguoiMuon[slNguoiMuon++] = nmMoi; // Thêm vào mảng

        System.out.println("=== THEM NGUOI MUON THANH CONG! ===");
        System.out.println("Ma: " + maNM);
        System.out.println("The thu vien: " + theMoi.getMaThe() + 
                         " (" + (theMoi instanceof LoaiA ? "Loai A" : "Loai B") + ")");
        System.out.println("Ban co the dung ma nay de muon sach ngay!");
    }
}