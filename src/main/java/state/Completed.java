package state;

import actor.OrderActor;
import exception.OrderActorStateException;

public class Completed implements OrderStatus {
    @Override
    public void onCreated(OrderActor order) {
        throw new OrderActorStateException("Order has been created already!");
    }

    @Override
    public void onInProgress(OrderActor order) {
        throw new OrderActorStateException("Order has been in progress already!");
    }

    @Override
    public void onCanceled(OrderActor order) {
        throw new OrderActorStateException("Completed order can't be canceled!");
    }

    @Override
    public void onCompleted(OrderActor order) throws OrderActorStateException {
        throw new OrderActorStateException("Order has been completed already!");
    }
}
