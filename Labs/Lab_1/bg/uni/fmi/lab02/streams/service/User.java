package bg.uni.fmi.lab02.streams.service;

import java.util.List;

public class User {
    private List<Order> orders;

    public void addOrder(Order order) {
        orders.add(order);
    }

    public List<Order> getOrders() {
        return orders;
    }
}
