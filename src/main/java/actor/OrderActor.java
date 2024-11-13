package actor;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import message.OrderMessage;
import message.impl.GetListOfOrdersMessage;
import message.impl.UpdateOrderStatusMessage;
import state.Created;
import state.OrderStatus;
import state.StateContext;

public class OrderActor extends AbstractBehavior<OrderMessage> {
    private final String id;
    private OrderStatus status;


    public OrderActor(ActorContext<OrderMessage> context, String id, OrderStatus status) {
        super(context);
        this.id = id;
        this.status = new Created();
    }

    public static Behavior<OrderMessage> create(String id, OrderStatus status) {
        return Behaviors.setup(context -> new OrderActor(context, id, status));
    }

    @Override
    public Receive<OrderMessage> createReceive() {
        return newReceiveBuilder()
                .onMessage(UpdateOrderStatusMessage.class, this::updateStatus)
                .onMessage(GetListOfOrdersMessage.class, this::getOrderInfo)
                .build();
    }

    private Behavior<OrderMessage> getOrderInfo(GetListOfOrdersMessage getListOfOrdersMessage) {
        String log = String.format("|  ID %-5s | %-20s |", getId(), getStatus().getClass().getSimpleName());
        getContext().getLog().info(log);
        return this;
    }

    private Behavior<OrderMessage> updateStatus(UpdateOrderStatusMessage updateOrderStatusMessage) {
        StateContext stateContext = new StateContext(status);
        String currentState = updateOrderStatusMessage.getStatus().getClass().getSimpleName();
        switch (currentState) {
            case "Created":
                stateContext.onCreated(this);
                break;
            case "InProgress":
                stateContext.onInProgress(this);
                break;
            case "Completed":
                stateContext.onCompleted(this);
                break;
            case "Cancelled":
                stateContext.onCanceled(this);
                break;
            default:
                break;
        }
        return this;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public OrderStatus getStatus() {
        return status;
    }
}
