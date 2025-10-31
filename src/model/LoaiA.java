package model;

public class LoaiA extends TheThuVien {
    public LoaiA(String maThe) {
        super(maThe, "A");
    }

    @Override
    public int getSoNgayMuon() { return 30; }

    @Override
    public int getSoSachMuon() { return 7; }
}