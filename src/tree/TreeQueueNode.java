package tree;

public class TreeQueueNode {
    public TreeNode treeNode;
    public TreeQueueNode next;

    public TreeQueueNode(TreeNode treeNode) {
        this.treeNode = treeNode;
        this.next = null;
    }
}
