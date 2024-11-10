package message.impl;

import message.Message;

public class CreateOrderMessage implements Message {
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
