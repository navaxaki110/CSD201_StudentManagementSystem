package tree;

import model.Student;

public class TreeNode {
    public Student info;
    public TreeNode left;
    public TreeNode right;

    public TreeNode(Student info) {
        this.info = info;
        this.left = null;
        this.right = null;
    }
}

