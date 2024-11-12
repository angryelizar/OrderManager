package state;

import actor.OrderActor;
import exception.OrderActorStateException;

public class Created implements OrderStatus {
    @Override
    public void onCreated(OrderActor order) {
        throw new OrderActorStateException("Order has been created");
    }

    @Override
    public void onInProgress(OrderActor order) {
        order.setStatus(new InProgress());
    }

    @Override
    public void onCanceled(OrderActor order) {
        order.setStatus(new Cancelled());
    }

    @Override
    public void onCompleted(OrderActor order) throws OrderActorStateException {
        throw new OrderActorStateException("Order has need to be in progress first!");
    }
}
