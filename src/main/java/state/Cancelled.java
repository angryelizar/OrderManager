package state;

import actor.OrderActor;

public class Cancelled implements OrderStatus {
    @Override
    public void onCreated(OrderActor order) {
        order.getContext().getLog().error("You cant make cancelled order to be created again!");
    }

    @Override
    public void onInProgress(OrderActor order) {
        order.getContext().getLog().error("You cant make cancelled order to be in progress!");
    }

    @Override
    public void onCanceled(OrderActor order) {
        order.getContext().getLog().error("It is already canceled!");
    }

    @Override
    public void onCompleted(OrderActor order) {
        order.getContext().getLog().error("You can make cancelled order to be completed!");
    }
}
