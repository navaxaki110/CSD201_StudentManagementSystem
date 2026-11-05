/**
 * Node dành cho cấu trúc hàng đợi MyQueue (Linked List-based Queue).
 * Chuẩn theo Slide 2_Stack_Queue.pptx môn CSD201.
 */
public class QueueNode {
    public RegistrationRequest info; // Yêu cầu đăng ký môn học
    public QueueNode next;

    public QueueNode(RegistrationRequest info) {
        this(info, null);
    }

    public QueueNode(RegistrationRequest info, QueueNode next) {
        this.info = info;
        this.next = next;
    }
}

