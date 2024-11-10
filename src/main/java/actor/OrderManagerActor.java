package actor;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.*;
import message.Message;
import message.impl.CreateOrderMessage;
import model.Order;
import model.OrderStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.OrderRepositoryStub;

public class OrderManagerActor extends AbstractBehavior<Message> {
    private final Logger loggerFactory = LoggerFactory.getLogger(OrderManagerActor.class);
    private final OrderRepositoryStub orderRepositoryStub;

    public OrderManagerActor(ActorContext<Message> context, OrderRepositoryStub orderRepositoryStub) {
        super(context);
        this.orderRepositoryStub = orderRepositoryStub;
    }

    @Override
    public Receive<Message> createReceive() {
        return newReceiveBuilder()
                .onMessage(CreateOrderMessage.class, this::createOrder)
                .build();
    }

    private Behavior<Message> createOrder(CreateOrderMessage createOrderMessage) {
        Order maybeOrder  = orderRepositoryStub.getOrder(createOrderMessage.getId());

        if (maybeOrder != null) {
            loggerFactory.error("Order already exists with ID {}", maybeOrder.getId());
        }
        orderRepositoryStub.createOrder(createOrderMessage.getId(), new OrderStatus(enums.OrderStatus.CREATED.getValue()));
        loggerFactory.info("Created a new order with ID {}", createOrderMessage.getId());
        return this;
    }

    public static Behavior<Message> createBehavior() {
        return Behaviors.setup(context -> new OrderManagerActor(context, new OrderRepositoryStub() ));
    }
}
