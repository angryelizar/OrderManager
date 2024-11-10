package message.impl;

import message.OrderManagerMessage;

public class DeleteOrderMessage implements OrderManagerMessage {
    private String id;

    public DeleteOrderMessage(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
