// THAY THẾ TOÀN BỘ file MenuThuThu.java BẰNG CODE NÀY

package ui;
import java.util.Scanner;
import service.QuanLyThuVien;

public class MenuThuThu {
    private QuanLyThuVien ql;
    private Scanner sc;
    //biến nhớ mã thủ thư đăng nhập
    private String maThuThuDangNhap;

    public MenuThuThu(QuanLyThuVien ql, Scanner sc, String maThuThuDaXacThuc) {
        this.ql = ql;
        this.sc = sc;
        this.maThuThuDangNhap = maThuThuDaXacThuc;
    }

    public void hienThi() {
        while (true) {
            System.out.println("\n=== MENU THU THU ===");
            System.out.println("--- Quan Ly Sach ---");
            System.out.println("1. Xem tat ca sach");
            System.out.println("2. Xem chi tiet vi tri sach");
            System.out.println("3. Them sach moi");
            
            System.out.println("\n--- Quan Ly Muon Tra ---");
            System.out.println("4. Xem tat ca sach dang duoc muon"); // (Yêu cầu 3b)
            System.out.println("5. Xem chi tiet muon cua mot nguoi"); // (Yêu cầu 3c)
            System.out.println("6. Xem tat ca phieu tra");
            
            System.out.println("\n--- Quan Ly Nhân Sự ---");
            System.out.println("7. Them nguoi muon moi"); // (Yêu cầu 1)
            System.out.println("8. Xem danh sach nguoi muon"); // (Yêu cầu 3a)
            System.out.println("9. Xem danh sach thu thu");
            System.out.println("10. Them thu thu moi (Chi TT01)");

            System.out.println("\n--- Chuc nang khac ---");
            System.out.println("11. Chinh sua thong tin nguoi muon");
            System.out.println("12. Chinh sua thong tin sach");
            System.out.println("13. Chinh sua thong tin thu thu");
            System.out.println("14. Tim kiem sach, nguoi muon, thu thu");
            System.out.println("15. Chinh sua the nguoi muon");

            System.out.println("\n--- Thong Ke & Bao Cao ---");
            System.out.println("16. Thong ke tong quan");
            System.out.println("17. Thong ke sach qua han");
            System.out.println("18. Thong ke tien phat");

            System.out.println("\n0. Quay lai menu chinh");
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
                // QL Sach
                case 1: ql.hienThiSach(); break;
                case 2: ql.hienThiChiTietSach(); break;
                case 3: ql.themSach(); break;
                
                // QL Muon Tra
                case 4: ql.hienThiSachDangMuon(); break; // (Yêu cầu 3b)
                case 5: ql.xemChiTietMuonCuaNguoiMuon(); break; // (Yêu cầu 3c)
                case 6: ql.hienThiPhieuTra(); break;
                
                // QL Nguoi Muon
                case 7: ql.themNguoiMuon(); break; // (Yêu cầu 1)
                case 8: ql.hienThiNguoiMuon(); break; // (Yêu cầu 3a)
                case 9: ql.hienThiThuThu(); break;
                case 10: ql.themThuThuMoi(maThuThuDangNhap); break;
                // Chuc nang khac
                case 11: ql.chinhSuaThongTinNguoiMuon(); break;
                case 12: ql.chinhSuaThongTinSach(); break;
                case 13: ql.chinhSuaThongTinThuThu(maThuThuDangNhap); break;
                case 14: ql.timKiem(true); break;
                case 15: ql.chinhSuaTheNguoiMuon(); break;
                // Thong Ke & Bao Cao
                case 16: ql.thongKeTongQuan(); break;
                case 17: ql.thongKeSachQuaHan(); break;
                case 18: ql.thongKeTienPhat(); break;
                
                default: System.out.println("Lua chon khong hop le!");
            }
        }
    }
}