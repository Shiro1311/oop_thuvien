import java.util.Scanner;
import service.QuanLyThuVien;
import ui.MenuNguoiMuon;
import ui.MenuThuThu;

public class MainProgram {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        QuanLyThuVien ql = new QuanLyThuVien(sc); 
        
        ql.khoiTaoDuLieu(); 
        QuanLyThuVien.inTenThuVien(); 

        while (true) {
            System.out.println("\n=== CHUONG TRINH QUAN LY THU VIEN ===");
            System.out.println("1. Thu thu");
            System.out.println("2. Nguoi muon");
            System.out.println("0. Thoat");
            System.out.print("Chon: ");
            
            int choice = -1;
            try {
                String input = sc.nextLine();
                if (input.isEmpty()) continue;
                choice = Integer.parseInt(input);
            } catch (Exception e) {
                System.out.println("Vui long nhap so!");
                continue;
            }

            if (choice == 0) {
                ql.ghiDuLieu();
                break;
            }
            
            // =============================================================
            // === NEW:1
            //BƯỚC XÁC THỰC Ở ĐÂY  ===
            // =============================================================
            if (choice == 1) {
                System.out.print("\n--- XAC THUC THU THU ---");
                System.out.print("\nVui long nhap Ma Thu Thu cua ban (VD: TT01): ");
                String maNhap = sc.nextLine().toUpperCase();
                
                // Gọi hàm "gác cổng"
                if (ql.xacThucThuThu(maNhap)) {
                    // Nếu đúng, mới cho vào Menu
                    new MenuThuThu(ql, sc, maNhap).hienThi(); 
                }
                // Nếu sai, tự động quay lại menu chính
            }
            // =============================================================
            
            if (choice == 2) {
                new MenuNguoiMuon(ql, sc).hienThi();
            }
        }
        System.out.println("Tam biet!");
        sc.close();
    }
}