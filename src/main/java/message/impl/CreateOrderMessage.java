package message.impl;

import message.OrderManagerMessage;

public class CreateOrderMessage implements OrderManagerMessage {
    private String id;

    public CreateOrderMessage(String id) {
        this.id = id;
    }

    public int getId() {
        return Integer.parseInt(id);
    }

    public void setId(String id) {
        this.id = id;
    }
}
