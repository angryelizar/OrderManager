package repository;

import akka.actor.typed.ActorRef;
import message.OrderMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class OrderRepositoryStub {
    private final Map<Integer, ActorRef<OrderMessage>> orders = new ConcurrentHashMap<>();

    public OrderRepositoryStub() {
    }

    public int createOrder(Integer currentId, ActorRef<OrderMessage> order) {
        orders.put(currentId, order);
        return currentId;
    }

    public ActorRef<OrderMessage> getOrder(int id) {
        return orders.get(id);
    }

    public Boolean deleteOrder(int id) {
        return orders.remove(id) != null;
    }

    public List<ActorRef<OrderMessage>> getAllOrders() {
        return new ArrayList<>(orders.values());
    }
}
