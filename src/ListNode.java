/**
 * Node dành cho cấu trúc Singly Linked List (Danh sách liên kết đơn).
 * Đúng chuẩn theo Slide 1_List.pptx môn CSD201.
 */
public class ListNode {
    public Student info;   // Dữ liệu chứa thông tin sinh viên
    public ListNode next;  // Con trỏ liên kết đến Node kế tiếp

    public ListNode() {
        this(null, null);
    }

    public ListNode(Student info) {
        this(info, null);
    }

    public ListNode(Student info, ListNode next) {
        this.info = info;
        this.next = next;
    }
}

