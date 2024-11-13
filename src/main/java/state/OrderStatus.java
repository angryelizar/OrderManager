package state;


import actor.OrderActor;

public interface OrderStatus {
    void onCreated(OrderActor order);

    void onInProgress(OrderActor order);

    void onCanceled(OrderActor order);

    void onCompleted(OrderActor order);
}
