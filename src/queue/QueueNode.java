package queue;

import model.RegistrationRequest;

public class QueueNode {
    public RegistrationRequest info;
    public QueueNode next;

    public QueueNode(RegistrationRequest info) {
        this(info, null);
    }

    public QueueNode(RegistrationRequest info, QueueNode next) {
        this.info = info;
        this.next = next;
    }
}
