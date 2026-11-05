/**
 * Hàng đợi MyQueue quản lý danh sách sinh viên đăng ký môn học (FIFO - First In, First Out).
 * Cài đặt bằng danh sách liên kết theo chuẩn Slide 2_Stack_Queue.pptx môn CSD201.
 */
public class MyQueue {
    protected QueueNode head;
    protected QueueNode tail;
    protected int count;

    public MyQueue() {
        this.head = null;
        this.tail = null;
        this.count = 0;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public void clear() {
        head = tail = null;
        count = 0;
    }

    public int size() {
        return count;
    }

    /**
     * Thêm yêu cầu đăng ký vào cuối hàng đợi (Enqueue - O(1))
     */
    public void enqueue(RegistrationRequest req) {
        if (req == null) return;
        QueueNode newNode = new QueueNode(req);
        if (isEmpty()) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        count++;
    }

    /**
     * Lấy và xóa yêu cầu ở đầu hàng đợi ra xử lý (Dequeue - O(1))
     * Tuân thủ nghiêm ngặt nguyên lý FIFO (Đến trước được xử lý trước)
     */
    public RegistrationRequest dequeue() {
        if (isEmpty()) {
            return null;
        }
        RegistrationRequest req = head.info;
        head = head.next;
        if (head == null) {
            tail = null;
        }
        count--;
        return req;
    }

    /**
     * Xem yêu cầu ở đầu hàng đợi nhưng không xóa (Front / Peek - O(1))
     */
    public RegistrationRequest front() {
        if (isEmpty()) {
            return null;
        }
        return head.info;
    }

    /**
     * Hiển thị toàn bộ hàng đợi đăng ký môn học
     */
    public void display() {
        if (isEmpty()) {
            System.out.println("(!) Hang doi dang ky hien dang trong. Khong co sinh vien nao dang cho.");
            return;
        }
        System.out.println("===== HANG DOI DANG KY MON HOC (FIFO - First In First Out) =====");
        QueueNode current = head;
        int index = 1;
        while (current != null) {
            System.out.println(" [" + index + "] " + current.info);
            current = current.next;
            index++;
        }
        System.out.println("Tong so yeu cau dang cho: " + count);
        System.out.println("===============================================================");
    }
}

