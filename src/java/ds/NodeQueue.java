/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ds;

import dto.RegistrationRequest;

public class NodeQueue {
    RegistrationRequest request;
    NodeQueue next;

    public NodeQueue() {
    }

    public NodeQueue(RegistrationRequest request) {
        this.request = request;
        this.next = null;
    }

    public RegistrationRequest getRequest() {
        return request;
    }

    public void setRequest(RegistrationRequest request) {
        this.request = request;
    }

    public NodeQueue getNext() {
        return next;
    }

    public void setNext(NodeQueue next) {
        this.next = next;
    }
}
