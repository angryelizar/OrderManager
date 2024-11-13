package state;

import actor.OrderActor;

public class Completed implements OrderStatus {
    @Override
    public void onCreated(OrderActor order) {
        order.getContext().getLog().error("Order has been created already!");
    }

    @Override
    public void onInProgress(OrderActor order) {
        order.getContext().getLog().error("Completed order can't be in progress!");
    }

    @Override
    public void onCanceled(OrderActor order) {
        order.getContext().getLog().error("Completed order can't be canceled!");
    }

    @Override
    public void onCompleted(OrderActor order) {
        order.getContext().getLog().error("Order has been completed already!");
    }
}
