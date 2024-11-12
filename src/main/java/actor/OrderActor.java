package actor;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import message.OrderMessage;
import message.impl.UpdateOrderStatusMessage;
import state.Created;
import state.OrderStatus;

public class OrderActor extends AbstractBehavior<OrderMessage> {
    private final String id;
    private OrderStatus status;
    private String currentState;

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    public OrderActor(ActorContext<OrderMessage> context, String id, OrderStatus status) {
        super(context);
        this.id = id;
        this.status = new Created();
        this.currentState = enums.OrderStatus.CREATED.getValue();
    }

    public static Behavior<OrderMessage> create(String id, OrderStatus status) {
        return Behaviors.setup(context -> new OrderActor(context, id, status));
    }

    @Override
    public Receive<OrderMessage> createReceive() {
        return newReceiveBuilder()
                .onMessage(UpdateOrderStatusMessage.class, this::updateStatus)
                .build();
    }

    private Behavior<OrderMessage> updateStatus(UpdateOrderStatusMessage updateOrderStatusMessage) {
        return this;
    }
}
