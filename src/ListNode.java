public class ListNode {
    public Student info;
    public ListNode next;

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
