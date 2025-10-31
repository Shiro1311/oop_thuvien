package ui;
import java.util.Scanner;
import service.QuanLyThuVien;

public class MenuNguoiMuon {
    private QuanLyThuVien ql;
    private Scanner sc; // CHANGED: Bỏ khởi tạo

    // CHANGED: Nhận Scanner từ constructor
    public MenuNguoiMuon(QuanLyThuVien ql, Scanner sc) {
        this.ql = ql;
        this.sc = sc;
    }

    public void hienThi() {
        while (true) {
            System.out.println("\n=== MENU NGUOI MUON ===");
            System.out.println("1. Xem sach");
            System.out.println("2. Muon sach");
            System.out.println("3. Xem sach dang muon");
            System.out.println("4. Tra sach");
            System.out.println("5. Them nguoi muon moi");  
            System.out.println("0. Thoat");
            System.out.print("Chon: ");
            int c = Integer.parseInt(sc.nextLine());

            if (c == 0) break;
            if (c == 1) ql.hienThiSach();
            if (c == 2) ql.muonSach();
            if (c == 3) ql.hienThiSachDangMuon();
            if (c == 4) ql.traSach();
            if (c == 5) ql.themNguoiMuon();  
        }
    }
}