package repository;

import model.Order;
import model.OrderStatus;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class OrderRepositoryStub {
    private final Map<Integer, Order> orders = new ConcurrentHashMap<>();

    public OrderRepositoryStub() {
    }

    public int createOrder(Integer currentId, OrderStatus status) {
        orders.put(currentId, new Order(currentId, status));
        return getOrder(currentId).getId();
    }

    public Order getOrder(int id) {
        return orders.get(id);
    }

    public Boolean deleteOrder(int id) {
        return orders.remove(id) != null;
    }
}
