package state;

import actor.OrderActor;

public class Created implements OrderStatus {
    @Override
    public void onCreated(OrderActor order) {
        order.getContext().getLog().error("Order has been created already!");
    }

    @Override
    public void onInProgress(OrderActor order) {
        order.setStatus(new InProgress());
        order.getContext().getLog().info("Order has changed state to in-progress");
    }

    @Override
    public void onCanceled(OrderActor order) {
        order.setStatus(new Cancelled());
        order.getContext().getLog().info("Order has been cancelled");
    }

    @Override
    public void onCompleted(OrderActor order) {
        order.getContext().getLog().error("Order has need to be in progress first!");
    }
}
