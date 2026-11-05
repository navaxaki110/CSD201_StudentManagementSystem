/**
 * Cấu trúc Singly Linked List (Danh sách liên kết đơn) để lưu trữ danh sách sinh viên.
 * Cài đặt chuẩn theo giáo trình và Slide 1_List.pptx môn CSD201.
 */
public class MyList {
    private ListNode head;
    private ListNode tail;
    private int size;

    public MyList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public void clear() {
        head = tail = null;
        size = 0;
    }

    public int size() {
        return size;
    }

    public ListNode getHead() {
        return head;
    }

    /**
     * Thêm sinh viên vào cuối danh sách (O(1))
     */
    public void addLast(Student student) {
        if (student == null) return;
        ListNode newNode = new ListNode(student);
        if (isEmpty()) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    /**
     * Thêm sinh viên vào đầu danh sách (O(1))
     */
    public void addFirst(Student student) {
        if (student == null) return;
        ListNode newNode = new ListNode(student);
        if (isEmpty()) {
            head = tail = newNode;
        } else {
            newNode.next = head;
            head = newNode;
        }
        size++;
    }

    /**
     * Tìm kiếm sinh viên theo ID trên Linked List (Độ phức tạp O(n))
     */
    public ListNode search(String id) {
        ListNode current = head;
        while (current != null) {
            if (current.info.getId().equalsIgnoreCase(id)) {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    /**
     * Xóa sinh viên theo ID khỏi Linked List (Độ phức tạp O(n))
     * Trả về true nếu xóa thành công, false nếu không tìm thấy.
     */
    public boolean delete(String id) {
        if (isEmpty()) return false;

        // Nếu phần tử cần xóa nằm ở đầu (head)
        if (head.info.getId().equalsIgnoreCase(id)) {
            head = head.next;
            if (head == null) {
                tail = null;
            }
            size--;
            return true;
        }

        // Tìm vị trí node đứng trước node cần xóa
        ListNode current = head;
        while (current.next != null && !current.next.info.getId().equalsIgnoreCase(id)) {
            current = current.next;
        }

        // Nếu tìm thấy
        if (current.next != null) {
            if (current.next == tail) {
                tail = current;
            }
            current.next = current.next.next;
            size--;
            return true;
        }

        return false;
    }

    /**
     * Sắp xếp danh sách sinh viên theo GPA giảm dần bằng thuật toán Selection Sort trên Linked List
     */
    public void sortByGpaDescending() {
        if (head == null || head.next == null) return;

        for (ListNode p = head; p.next != null; p = p.next) {
            ListNode maxNode = p;
            for (ListNode q = p.next; q != null; q = q.next) {
                if (q.info.getGpa() > maxNode.info.getGpa()) {
                    maxNode = q;
                }
            }
            if (maxNode != p) {
                // Hoán đổi thông tin giữa 2 Node
                Student temp = p.info;
                p.info = maxNode.info;
                maxNode.info = temp;
            }
        }
    }

    /**
     * Duyệt và in toàn bộ danh sách sinh viên dưới dạng bảng
     */
    public void traverse() {
        if (isEmpty()) {
            System.out.println("(!) Danh sach sinh vien trong Linked List hien dang trong.");
            return;
        }
        printTableHeader();
        ListNode current = head;
        while (current != null) {
            System.out.println(current.info);
            current = current.next;
        }
        printTableFooter();
        System.out.println("Tong so sinh vien: " + size);
    }

    public static void printTableHeader() {
        System.out.println("+------------+----------------------+--------------+-------+------------------------+");
        System.out.println("| MSSV       | Ho va Ten            | Chuyen Nganh | GPA   | Mon da dang ky         |");
        System.out.println("+------------+----------------------+--------------+-------+------------------------+");
    }

    public static void printTableFooter() {
        System.out.println("+------------+----------------------+--------------+-------+------------------------+");
    }
}

