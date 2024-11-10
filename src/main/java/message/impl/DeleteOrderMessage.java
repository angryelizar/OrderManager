package message.impl;

import message.Message;

public class DeleteOrderMessage implements Message {
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
