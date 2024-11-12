package state;


import actor.OrderActor;
import exception.OrderActorStateException;

public interface OrderStatus {
    void onCreated(OrderActor order) throws OrderActorStateException;

    void onInProgress(OrderActor order) throws OrderActorStateException;

    void onCanceled(OrderActor order) throws OrderActorStateException;

    void onCompleted(OrderActor order) throws OrderActorStateException;
}
