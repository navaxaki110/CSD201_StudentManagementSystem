/**
 * Node dành cho cây nhị phân tìm kiếm BSTree (Binary Search Tree).
 * Đúng chuẩn theo Slide 4A-Trees1.ppt và 4B-Trees2.ppt môn CSD201.
 */
public class TreeNode {
    public Student info;    // Dữ liệu sinh viên
    public TreeNode left;   // Nhánh con bên trái (chứa ID nhỏ hơn)
    public TreeNode right;  // Nhánh con bên phải (chứa ID lớn hơn)

    public TreeNode(Student info) {
        this.info = info;
        this.left = null;
        this.right = null;
    }
}

