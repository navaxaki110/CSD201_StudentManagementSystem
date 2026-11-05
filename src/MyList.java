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

    public boolean delete(String id) {
        if (isEmpty()) return false;

        if (head.info.getId().equalsIgnoreCase(id)) {
            head = head.next;
            if (head == null) {
                tail = null;
            }
            size--;
            return true;
        }

        ListNode current = head;
        while (current.next != null && !current.next.info.getId().equalsIgnoreCase(id)) {
            current = current.next;
        }

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
                Student temp = p.info;
                p.info = maxNode.info;
                maxNode.info = temp;
            }
        }
    }

    public void traverse() {
        if (isEmpty()) {
            System.out.println("Danh sach sinh vien hien dang trong.");
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
