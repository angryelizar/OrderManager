package state;

import actor.OrderActor;
import exception.OrderActorStateException;

public class InProgress implements OrderStatus {
    @Override
    public void onCreated(OrderActor order) {
        throw new OrderActorStateException("Order in progress!");
    }

    @Override
    public void onInProgress(OrderActor order) {
        throw new OrderActorStateException("Order already in progress!");
    }

    @Override
    public void onCanceled(OrderActor order) {
        order.setStatus(new Cancelled());
    }

    @Override
    public void onCompleted(OrderActor order) throws OrderActorStateException {
        order.setStatus(new Completed());
    }
}
