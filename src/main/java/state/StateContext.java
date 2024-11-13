package state;

import actor.OrderActor;

public class StateContext {
    private OrderStatus orderStatus;

    public StateContext(OrderStatus initOrderStatus) {
        this.orderStatus = initOrderStatus;
    }

    public void onCreated(OrderActor order) {
        orderStatus.onCreated(order);
    }

    public void onInProgress(OrderActor order) {
        orderStatus.onInProgress(order);
    }

    public void onCanceled(OrderActor order) {
        orderStatus.onCanceled(order);
    }

    public void onCompleted(OrderActor order) {
       orderStatus.onCompleted(order);
    }
}
