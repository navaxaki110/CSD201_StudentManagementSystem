/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import ds.NodeTree;
import ds.StudentBST;
import dto.Student;
import java.util.ArrayList;
import java.util.List;

public class StudentTreeService {
    private StudentBST tree;

    public StudentTreeService() {
    }

    public StudentTreeService(StudentBST tree) {
        this.tree = tree;
    }

    public List<Student> searchByKeyword(String keyword) {
        List<Student> results = new ArrayList<>();
        if (keyword != null && !keyword.trim().isEmpty() && tree != null) {
            searchKeywordRec(tree.getRoot(), keyword.toLowerCase().trim(), results);
        }
        return results;
    }

    private void searchKeywordRec(NodeTree node, String keyword, List<Student> results) {
        if (node == null) return;
        searchKeywordRec(node.getLeft(), keyword, results);
        Student s = node.getStudent();
        if (s.getId().toLowerCase().contains(keyword) || 
            s.getName().toLowerCase().contains(keyword) ||
            s.getMajor().toLowerCase().contains(keyword)) {
            results.add(s);
        }
        searchKeywordRec(node.getRight(), keyword, results);
    }
}
