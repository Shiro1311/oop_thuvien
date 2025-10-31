package ui;
import java.util.Scanner;
import service.QuanLyThuVien;

public class MenuThuThu {
    private QuanLyThuVien ql;
    private Scanner sc; // CHANGED: Bỏ khởi tạo

    // CHANGED: Nhận Scanner từ constructor
    public MenuThuThu(QuanLyThuVien ql, Scanner sc) {
        this.ql = ql;
        this.sc = sc;
    }

    public void hienThi() {
        while (true) {
            System.out.println("\n=== MENU THU THU ===");
            System.out.println("1. Xem sach");
            System.out.println("2. Xem chi tiet sach");
            System.out.println("3. Them sach");
            System.out.println("0. Thoat");
            System.out.print("Chon: ");
            int c = Integer.parseInt(sc.nextLine());

            if (c == 0) break;
            if (c == 1) ql.hienThiSach();
            if (c == 2) ql.hienThiChiTietSach();
            if (c == 3) ql.themSach();
        }
    }
}