package tree;

public class TreeQueue {
    private TreeQueueNode head;
    private TreeQueueNode tail;

    public TreeQueue() {
        head = tail = null;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public void enqueue(TreeNode treeNode) {
        if (treeNode == null) return;
        TreeQueueNode newNode = new TreeQueueNode(treeNode);
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
