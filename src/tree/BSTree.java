package tree;

import list.MyList;
import model.Student;

public class BSTree {
    private TreeNode root;

    public BSTree() {
        this.root = null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public void clear() {
        root = null;
    }

    public TreeNode getRoot() {
        return root;
    }

    public boolean insert(Student student) {
        if (student == null) return false;
        TreeNode newNode = new TreeNode(student);

        if (isEmpty()) {
            root = newNode;
            return true;
        }

        TreeNode parent = null;
        TreeNode current = root;

        while (current != null) {
            int cmp = student.getId().compareToIgnoreCase(current.info.getId());
            if (cmp == 0) {
                return false;
            }
            parent = current;
            if (cmp < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }

        if (student.getId().compareToIgnoreCase(parent.info.getId()) < 0) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }
        return true;
    }

    public Student search(String id) {
        TreeNode current = root;
        while (current != null) {
            int cmp = id.compareToIgnoreCase(current.info.getId());
            if (cmp == 0) {
                return current.info;
            } else if (cmp < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return null;
    }

    public void inOrder() {
        if (isEmpty()) {
            System.out.println("Cay BST hien dang trong.");
            return;
        }
        MyList.printTableHeader();
        inOrderRec(root);
        MyList.printTableFooter();
    }

    private void inOrderRec(TreeNode p) {
        if (p == null) return;
        inOrderRec(p.left);
        System.out.println(p.info);
        inOrderRec(p.right);
    }

    public void preOrder() {
        if (isEmpty()) {
            System.out.println("Cay BST hien dang trong.");
            return;
        }
        MyList.printTableHeader();
        preOrderRec(root);
        MyList.printTableFooter();
    }

    private void preOrderRec(TreeNode p) {
        if (p == null) return;
        System.out.println(p.info);
        preOrderRec(p.left);
        preOrderRec(p.right);
    }

    public void postOrder() {
        if (isEmpty()) {
            System.out.println("Cay BST hien dang trong.");
            return;
        }
        MyList.printTableHeader();
        postOrderRec(root);
        MyList.printTableFooter();
    }

    private void postOrderRec(TreeNode p) {
        if (p == null) return;
        postOrderRec(p.left);
        postOrderRec(p.right);
        System.out.println(p.info);
    }

    public void breadthFirst() {
        if (isEmpty()) {
            System.out.println("Cay BST hien dang trong.");
            return;
        }
        MyList.printTableHeader();
        TreeQueue queue = new TreeQueue();
        queue.enqueue(root);

        while (!queue.isEmpty()) {
            TreeNode p = queue.dequeue();
            System.out.println(p.info);
            if (p.left != null) queue.enqueue(p.left);
            if (p.right != null) queue.enqueue(p.right);
        }
        MyList.printTableFooter();
    }

    public boolean deleteByCopying(String id) {
        TreeNode parent = null;
        TreeNode current = root;

        while (current != null) {
            int cmp = id.compareToIgnoreCase(current.info.getId());
            if (cmp == 0) break;
            parent = current;
            if (cmp < 0) current = current.left;
            else current = current.right;
        }

        if (current == null) return false;

        if (current.left == null || current.right == null) {
            TreeNode child = (current.left != null) ? current.left : current.right;

            if (parent == null) {
                root = child;
            } else if (parent.left == current) {
                parent.left = child;
            } else {
                parent.right = child;
            }
            return true;
        }

        TreeNode pR = current.left;
        TreeNode parentOfPR = current;

        while (pR.right != null) {
            parentOfPR = pR;
            pR = pR.right;
        }

        current.info = pR.info;

        if (parentOfPR == current) {
            parentOfPR.left = pR.left;
        } else {
            parentOfPR.right = pR.left;
        }

        return true;
    }

    public int count() {
        return countRec(root);
    }

    private int countRec(TreeNode p) {
        if (p == null) return 0;
        return 1 + countRec(p.left) + countRec(p.right);
    }

    public int height() {
        return heightRec(root);
    }

    private int heightRec(TreeNode p) {
        if (p == null) return 0;
        int leftH = heightRec(p.left);
        int rightH = heightRec(p.right);
        return 1 + Math.max(leftH, rightH);
    }
}
