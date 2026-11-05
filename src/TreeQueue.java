public class TreeQueue {
    private static class Node {
        TreeNode treeNode;
        Node next;
        Node(TreeNode treeNode) {
            this.treeNode = treeNode;
            this.next = null;
        }
    }

    private Node head;
    private Node tail;

    public TreeQueue() {
        head = tail = null;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public void enqueue(TreeNode treeNode) {
        if (treeNode == null) return;
        Node newNode = new Node(treeNode);
        if (isEmpty()) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
    }

    public TreeNode dequeue() {
        if (isEmpty()) return null;
        TreeNode node = head.treeNode;
        head = head.next;
        if (head == null) tail = null;
        return node;
    }
}
