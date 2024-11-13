package message.impl;

import message.OrderManagerMessage;
import message.OrderMessage;
import state.OrderStatus;

public class UpdateOrderStatusMessage implements OrderManagerMessage, OrderMessage {
    private String id;
    private OrderStatus status;

    public UpdateOrderStatusMessage(String id, OrderStatus status) {
        this.id = id;
        this.status = status;
    }

    public Integer getId() {
        return Integer.parseInt(id);
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
