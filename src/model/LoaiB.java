package model;

public class LoaiB extends TheThuVien {
    public LoaiB(String maThe) {
        super(maThe, "B");
    }

    @Override
    public int getSoNgayMuon() { return 15; }

    @Override
    public int getSoSachMuon() { return 4; }
}