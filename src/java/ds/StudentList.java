/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ds;

import dto.Student;
import java.util.ArrayList;
import java.util.List;

public class StudentList {
    private NodeList head;
    private NodeList tail;
    private int size;

    public StudentList() {
        head = null;
        tail = null;
        size = 0;
    }

    public void addLast(Student s) {
        NodeList node = new NodeList(s);
        if (head == null) {
            head = tail = node;
        } else {
            tail.setNext(node);
            tail = node;
        }
        size++;
    }

    public Student search(String id) {
        NodeList p = head;
        while (p != null) {
            if (p.getStudent().getId().equalsIgnoreCase(id)) {
                return p.getStudent();
            }
            p = p.getNext();
        }
        return null;
    }

    public boolean delete(String id) {
        if (head == null) return false;
        if (head.getStudent().getId().equalsIgnoreCase(id)) {
            head = head.getNext();
            if (head == null) tail = null;
            size--;
            return true;
        }
        NodeList p = head;
        while (p.getNext() != null && !p.getNext().getStudent().getId().equalsIgnoreCase(id)) {
            p = p.getNext();
        }
        if (p.getNext() != null) {
            if (p.getNext() == tail) {
                tail = p;
            }
            p.setNext(p.getNext().getNext());
            size--;
            return true;
        }
        return false;
    }

    public void sortByGpaDescending() {
        if (head == null || head.getNext() == null) return;
        for (NodeList i = head; i != null; i = i.getNext()) {
            NodeList maxNode = i;
            for (NodeList j = i.getNext(); j != null; j = j.getNext()) {
                if (j.getStudent().getGpa() > maxNode.getStudent().getGpa()) {
                    maxNode = j;
                }
            }
            if (maxNode != i) {
                Student tmp = i.getStudent();
                i.setStudent(maxNode.getStudent());
                maxNode.setStudent(tmp);
            }
        }
    }

    public List<Student> toList() {
        List<Student> list = new ArrayList<>();
        NodeList p = head;
        while (p != null) {
            list.add(p.getStudent());
            p = p.getNext();
        }
        return list;
    }

    public int getSize() {
        return size;
    }

    public NodeList getHead() {
        return head;
    }
}
