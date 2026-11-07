/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ds;

import dto.Student;
import java.util.ArrayList;
import java.util.List;

public class StudentBST {
    private NodeTree root;
    private int size;

    public StudentBST() {
        this.root = null;
        this.size = 0;
    }

    // ========== BUILD BALANCED ==========
    public void buildBalanced(List<Student> sortedList) {
        this.size = sortedList.size();
        this.root = buildBalancedRec(sortedList, 0, sortedList.size() - 1);
    }

    private NodeTree buildBalancedRec(List<Student> list, int start, int end) {
        if (start > end) return null;
        int mid = (start + end) / 2;
        NodeTree node = new NodeTree(list.get(mid), null, null);
        node.setLeft(buildBalancedRec(list, start, mid - 1));
        node.setRight(buildBalancedRec(list, mid + 1, end));
        return node;
    }

    // ========== INSERT ==========
    public boolean insert(Student student) {
        if (student == null || student.getId() == null) return false;
        if (root == null) {
            root = new NodeTree(student, null, null);
            size++;
            return true;
        }
        NodeTree p = root;
        NodeTree parent = null;
        while (p != null) {
            int cmp = student.getId().compareToIgnoreCase(p.getStudent().getId());
            if (cmp == 0) {
                return false; // Duplicate ID
            }
            parent = p;
            if (cmp < 0) {
                p = p.getLeft();
            } else {
                p = p.getRight();
            }
        }
        int cmp = student.getId().compareToIgnoreCase(parent.getStudent().getId());
        if (cmp < 0) {
            parent.setLeft(new NodeTree(student, null, null));
        } else {
            parent.setRight(new NodeTree(student, null, null));
        }
        size++;
        return true;
    }

    // ========== SEARCH BY ID ==========
    public NodeTree search(NodeTree p, String id) {
        if (p == null || id == null) return null;
        int cmp = id.compareToIgnoreCase(p.getStudent().getId());
        if (cmp == 0) return p;
        if (cmp < 0) return search(p.getLeft(), id);
        return search(p.getRight(), id);
    }

    public Student search(String id) {
        NodeTree node = search(root, id);
        return (node != null) ? node.getStudent() : null;
    }

    // ========== INORDER LIST ==========
    public List<Student> inorderList() {
        List<Student> results = new ArrayList<>();
        inOrder(root, results);
        return results;
    }

    private void inOrder(NodeTree p, List<Student> results) {
        if (p == null) return;
        inOrder(p.getLeft(), results);
        results.add(p.getStudent());
        inOrder(p.getRight(), results);
    }

    // ========== DELETE BY COPYING ==========
    public boolean deleteByCopying(String id) {
        NodeTree p = root;
        NodeTree parent = null;
        while (p != null && !p.getStudent().getId().equalsIgnoreCase(id)) {
            parent = p;
            if (id.compareToIgnoreCase(p.getStudent().getId()) < 0) {
                p = p.getLeft();
            } else {
                p = p.getRight();
            }
        }
        if (p == null) return false; // Not found

        // Case 1: Node has no left child
        if (p.getLeft() == null) {
            if (parent == null) {
                root = p.getRight();
            } else if (parent.getLeft() == p) {
                parent.setLeft(p.getRight());
            } else {
                parent.setRight(p.getRight());
            }
        }
        // Case 2: Node has no right child
        else if (p.getRight() == null) {
            if (parent == null) {
                root = p.getLeft();
            } else if (parent.getLeft() == p) {
                parent.setLeft(p.getLeft());
            } else {
                parent.setRight(p.getLeft());
            }
        }
        // Case 3: Node has two children (find rightmost in left subtree)
        else {
            NodeTree rightMost = p.getLeft();
            NodeTree parentRM = p;
            while (rightMost.getRight() != null) {
                parentRM = rightMost;
                rightMost = rightMost.getRight();
            }
            p.setStudent(rightMost.getStudent());
            if (parentRM == p) {
                parentRM.setLeft(rightMost.getLeft());
            } else {
                parentRM.setRight(rightMost.getLeft());
            }
        }
        size--;
        return true;
    }

    public NodeTree getRoot() {
        return root;
    }

    public int getSize() {
        return size;
    }
}
