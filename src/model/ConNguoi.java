package model;

public class ConNguoi {
    protected String ten;
    protected int tuoi;
    protected String diaChi;

    public ConNguoi() {}

    public ConNguoi(String ten, int tuoi, String diaChi) {
        this.ten = ten;
        this.tuoi = tuoi;
        this.diaChi = diaChi;
    }

    // Getters & Setters
    public String getTen() { return ten; }
    public void setTen(String ten) { this.ten = ten; }

    public int getTuoi() { return tuoi; }
    public void setTuoi(int tuoi) {
        if (tuoi > 18) this.tuoi = tuoi;
        else throw new IllegalArgumentException("Tuổi phải > 18");
    }

    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }

    @Override
    public String toString() {
        return String.format("%-20s %-5d %-20s", ten, tuoi, diaChi);
    }
}