/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ds;

import dto.RegistrationRequest;
import java.util.ArrayList;
import java.util.List;

public class Queue {
    private NodeQueue head;
    private NodeQueue tail;
    private int count;

    public Queue() {
        head = null;
        tail = null;
        count = 0;
    }

    public void enqueue(RegistrationRequest req) {
        NodeQueue node = new NodeQueue(req);
        if (isEmpty()) {
            head = tail = node;
        } else {
            tail.setNext(node);
            tail = node;
        }
        count++;
    }

    public RegistrationRequest dequeue() {
        if (isEmpty()) {
            return null;
        }
        RegistrationRequest req = head.getRequest();
        head = head.getNext();
        if (head == null) {
            tail = null;
        }
        count--;
        return req;
    }

    public RegistrationRequest front() {
        if (isEmpty()) {
            return null;
        }
        return head.getRequest();
    }

    public boolean isEmpty() {
        return head == null;
    }

    public int getCount() {
        return count;
    }

    public NodeQueue getHead() {
        return head;
    }

    public List<RegistrationRequest> getList() {
        List<RegistrationRequest> list = new ArrayList<>();
        NodeQueue p = head;
        while (p != null) {
            list.add(p.getRequest());
            p = p.getNext();
        }
        return list;
    }
}
