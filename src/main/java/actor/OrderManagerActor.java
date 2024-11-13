package actor;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.*;
import message.OrderManagerMessage;
import message.OrderMessage;
import message.impl.CreateOrderMessage;
import message.impl.DeleteOrderMessage;
import message.impl.GetListOfOrdersMessage;
import message.impl.UpdateOrderStatusMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.OrderRepositoryStub;
import state.Created;
import state.OrderStatus;

import java.util.List;
import java.util.Optional;

public class OrderManagerActor extends AbstractBehavior<OrderManagerMessage> {
    private final Logger log = LoggerFactory.getLogger(OrderManagerActor.class);
    private final OrderRepositoryStub orderRepositoryStub;

    public OrderManagerActor(ActorContext<OrderManagerMessage> context, OrderRepositoryStub orderRepositoryStub) {
        super(context);
        this.orderRepositoryStub = orderRepositoryStub;
    }

    @Override
    public Receive<OrderManagerMessage> createReceive() {
        return newReceiveBuilder()
                .onMessage(CreateOrderMessage.class, this::createOrder)
                .onMessage(UpdateOrderStatusMessage.class, this::updateStatus)
                .onMessage(DeleteOrderMessage.class, this::deleteOrder)
                .onMessage(GetListOfOrdersMessage.class, this::getList)
                .build();
    }

    private Behavior<OrderManagerMessage> getList(GetListOfOrdersMessage getListOfOrdersMessage) {
        List<ActorRef<OrderMessage>> orderActors = orderRepositoryStub.getAllOrders();
        orderActors.forEach(orderActor -> orderActor.tell(getListOfOrdersMessage));
        return this;
    }

    private Behavior<OrderManagerMessage> deleteOrder(DeleteOrderMessage deleteOrderMessage) {
        Optional<ActorRef<OrderMessage>> maybeOrder = Optional.ofNullable(orderRepositoryStub.getOrder(deleteOrderMessage.getId()));

        if (maybeOrder.isEmpty()) {
            log.error("Order {} does not exist", deleteOrderMessage.getId());
            return this;
        }

        log.info("Deleting order {}", deleteOrderMessage.getId());
        getContext().stop(maybeOrder.get());
        orderRepositoryStub.deleteOrder(deleteOrderMessage.getId());
        return this;
    }

    private Behavior<OrderManagerMessage> updateStatus(UpdateOrderStatusMessage updateOrderStatusMessage) {
        Optional<ActorRef<OrderMessage>> maybeOrder = Optional.ofNullable(orderRepositoryStub.getOrder(updateOrderStatusMessage.getId()));

        if (maybeOrder.isEmpty()) {
            log.error("Order {} does not exist", updateOrderStatusMessage.getId());
            return this;
        }

        Optional<OrderStatus> maybeStatus = Optional.ofNullable(updateOrderStatusMessage.getStatus());
        if (maybeStatus.isEmpty()) {
            log.error("Status not found!");
        }
        maybeOrder.get().tell(updateOrderStatusMessage);
        return this;
    }


    private Behavior<OrderManagerMessage> createOrder(CreateOrderMessage createOrderMessage) {
        ActorRef<OrderMessage> maybeOrder = orderRepositoryStub.getOrder(createOrderMessage.getId());

        if (maybeOrder != null) {
            log.error("Order already exists with ID {}", createOrderMessage.getId());
            return this;
        }

        int orderId = createOrderMessage.getId();
        ActorRef<OrderMessage> orderActor = getContext().spawn(OrderActor.create(Integer.toString(orderId), new Created()), "OrderActor-" + orderId);

        orderRepositoryStub.createOrder(createOrderMessage.getId(), orderActor);
        log.info("Created a new order with ID {}", createOrderMessage.getId());

        return this;
    }

    public static Behavior<OrderManagerMessage> createBehavior() {
        return Behaviors.setup(context -> new OrderManagerActor(context, new OrderRepositoryStub()));
    }
}
