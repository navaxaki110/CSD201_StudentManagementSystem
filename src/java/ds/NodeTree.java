/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ds;

import dto.Student;

public class NodeTree {
    private Student student;
    private NodeTree left;
    private NodeTree right;

    public NodeTree() {
    }

    public NodeTree(Student student, NodeTree left, NodeTree right) {
        this.student = student;
        this.left = left;
        this.right = right;
    }

    public NodeTree(Student student) {
        this(student, null, null);
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public NodeTree getLeft() {
        return left;
    }

    public void setLeft(NodeTree left) {
        this.left = left;
    }

    public NodeTree getRight() {
        return right;
    }

    public void setRight(NodeTree right) {
        this.right = right;
    }
}
