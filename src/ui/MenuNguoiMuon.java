// THAY THẾ TOÀN BỘ file MenuNguoiMuon.java BẰNG CODE NÀY

package ui;
import java.util.Scanner;
import service.QuanLyThuVien;

public class MenuNguoiMuon {
    private QuanLyThuVien ql;
    private Scanner sc; 

    public MenuNguoiMuon(QuanLyThuVien ql, Scanner sc) {
        this.ql = ql;
        this.sc = sc;
    }

    public void hienThi() {
        while (true) {
            System.out.println("\n=== MENU NGUOI MUON ===");
            System.out.println("1. Xem danh sach sach");
            System.out.println("2. Muon sach");
            System.out.println("3. Xem sach minh dang muon"); // Đổi tên cho rõ nghĩa
            System.out.println("4. Tra sach");
            // chức năng thêm
            System.out.println("5. Chinh sua thong tin ca nhan");
            System.out.println("6. Tim kiem sach");
            System.out.println("0. Quay lai menu chinh");
            System.out.print("Chon: ");
            
            int c = -1; // Gán giá trị không hợp lệ
            try {
                String input = sc.nextLine();
                if (input.isEmpty()) {
                    System.out.println("Vui long nhap mot so.");
                    continue;
                }
                c = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Lua chon khong hop le, vui long nhap so!");
                continue;
            }

            if (c == 0) break;
            
            switch(c) {
                case 1: ql.hienThiSach(); break;
                case 2: ql.muonSach(); break;
                // Hàm này vẫn hiển thị của tất cả mọi người,
                // nhưng người dùng có thể tự xem của mình
                case 3: ql.hienThiSachCuaMotNguoiMuon(); break; 
                case 4: ql.traSach(); break;
                //chức năng thêm
                case 5: ql.chinhSuaThongTinNguoiMuon(); break;
                case 6: ql.timKiem(false); break; // false vì đây là Nguoi Muon
                default: System.out.println("Lua chon khong hop le!");
            }
        }
    }
}