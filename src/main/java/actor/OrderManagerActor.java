package actor;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.*;
import message.OrderManagerMessage;
import message.OrderMessage;
import message.impl.CreateOrderMessage;
import message.impl.UpdateOrderStatusMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.OrderRepositoryStub;
import state.Created;
import state.OrderStatus;

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
                .build();
    }

    private Behavior<OrderManagerMessage> updateStatus(UpdateOrderStatusMessage updateOrderStatusMessage) {
        Optional<ActorRef<OrderMessage>> maybeOrder  = Optional.ofNullable(orderRepositoryStub.getOrder(updateOrderStatusMessage.getId()));

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
        ActorRef<OrderMessage> maybeOrder  = orderRepositoryStub.getOrder(createOrderMessage.getId());

        if (maybeOrder != null) {
            log.error("Order already exists with ID {}", createOrderMessage.getId());
            return this;
        }

        int orderId = createOrderMessage.getId();
        ActorRef<OrderMessage> orderActor  = getContext().spawn(OrderActor.create(Integer.toString(orderId), new Created()), "OrderActor-" + orderId);

        orderRepositoryStub.createOrder(createOrderMessage.getId(), orderActor);
        log.info("Created a new order with ID {}", createOrderMessage.getId());

        return this;
    }

    public static Behavior<OrderManagerMessage> createBehavior() {
        return Behaviors.setup(context -> new OrderManagerActor(context, new OrderRepositoryStub() ));
    }
}
