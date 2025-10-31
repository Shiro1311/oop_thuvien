// Đặt file này trong thư mục: src/MainProgram.java
import service.QuanLyThuVien;
import ui.MenuThuThu;
import ui.MenuNguoiMuon;
import java.util.Scanner;

public class MainProgram {
    public static void main(String[] args) {
        // CHANGED: Khởi tạo Scanner ở main
        Scanner sc = new Scanner(System.in);
        
        // CHANGED: Truyền Scanner vào constructor
        QuanLyThuVien ql = new QuanLyThuVien(sc); 
        
        ql.khoiTaoDuLieu(); // Sẽ gọi docDuLieu()
        
        // NEW: Gọi phương thức static
        QuanLyThuVien.inTenThuVien(); 

        while (true) {
            System.out.println("\n=== CHUONG TRINH QUAN LY THU VIEN ===");
            System.out.println("1. Thu thu");
            System.out.println("2. Nguoi muon");
            System.out.println("0. Thoat");
            System.out.print("Chon: ");
            int choice = Integer.parseInt(sc.nextLine());

            // CHANGED: Gọi ghiDuLieu() khi thoát
            if (choice == 0) {
                ql.ghiDuLieu(); // <-- NEW: Ghi file trước khi thoát
                break;
            }
            
            // CHANGED: Truyền 'sc' vào các Menu
            if (choice == 1) new MenuThuThu(ql, sc).hienThi(); 
            if (choice == 2) new MenuNguoiMuon(ql, sc).hienThi();
        }
        System.out.println("Tam biet!");
        sc.close(); // CHANGED: Đóng Scanner ở đây
    }
}