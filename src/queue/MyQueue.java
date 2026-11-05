package queue;

import model.RegistrationRequest;

public class MyQueue {
    protected QueueNode head;
    protected QueueNode tail;
    protected int count;

    public MyQueue() {
        this.head = null;
        this.tail = null;
        this.count = 0;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public void clear() {
        head = tail = null;
        count = 0;
    }

    public int size() {
        return count;
    }

    public void enqueue(RegistrationRequest req) {
        if (req == null) return;
        QueueNode newNode = new QueueNode(req);
        if (isEmpty()) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        count++;
    }

    public RegistrationRequest dequeue() {
        if (isEmpty()) {
            return null;
        }
        RegistrationRequest req = head.info;
        head = head.next;
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
        return head.info;
    }

    public void display() {
        if (isEmpty()) {
            System.out.println("Hang doi dang ky hien dang trong.");
            return;
        }
        System.out.println("===== HANG DOI DANG KY MON HOC =====");
        QueueNode current = head;
        int index = 1;
        while (current != null) {
            System.out.println(" [" + index + "] " + current.info);
            current = current.next;
            index++;
        }
        System.out.println("Tong so yeu cau dang cho: " + count);
        System.out.println("====================================");
    }
}

