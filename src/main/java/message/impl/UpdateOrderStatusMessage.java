package message.impl;

import message.Message;
import state.OrderStatus;

public class UpdateOrderStatusMessage implements Message {
    private String id;
    private OrderStatus status;

    public UpdateOrderStatusMessage(String id, OrderStatus status) {
        this.id = id;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
