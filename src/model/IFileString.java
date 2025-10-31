package model;
// Đặt file này trong thư mục: src/model/IFileString.java
/**
 * NEW:
 * Interface này buộc các lớp model phải có phương thức toFileString()
 * để phục vụ cho việc ghi file.
 */
public interface IFileString {
    /**
     * Trả về một chuỗi đại diện cho đối tượng,
     * dùng để ghi ra file.
     * Các trường phân tách bằng dấu chấm phẩy (;)
     */
    String toFileString();
}