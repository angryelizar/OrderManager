package state;

import actor.OrderActor;

public class InProgress implements OrderStatus {
    @Override
    public void onCreated(OrderActor order) {
        order.getContext().getLog().error("Order in progress!");
    }

    @Override
    public void onInProgress(OrderActor order) {
        order.getContext().getLog().error("Order already in progress!");
    }

    @Override
    public void onCanceled(OrderActor order) {
        order.getContext().getLog().info("Order canceled!");
        order.setStatus(new Cancelled());
    }

    @Override
    public void onCompleted(OrderActor order) {
        order.getContext().getLog().info("Order completed!");
        order.setStatus(new Completed());
    }
}
