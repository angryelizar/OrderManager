package state;

import actor.OrderActor;
import exception.OrderActorStateException;

public class Cancelled implements OrderStatus {
    @Override
    public void onCreated(OrderActor order) {
        throw new OrderActorStateException("You can make cancelled order to be created again!");
    }

    @Override
    public void onInProgress(OrderActor order) {
        throw new OrderActorStateException("You can make cancelled order to be in progress!");
    }

    @Override
    public void onCanceled(OrderActor order) {
        throw new OrderActorStateException("It is already canceled!");
    }

    @Override
    public void onCompleted(OrderActor order) throws OrderActorStateException {
        throw new OrderActorStateException("You can make cancelled order to be completed!");
    }
}
