package bg.uni.fmi.lab02.streams.service;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class SearchExercise implements SearchExerciseAPI {
    @Override
    public List<Order> getActiveOrders(User user) {
        return user.getOrders()
                .stream()
                .filter(order -> order.getStatus() == OrderStatus.ACTIVE)
                .toList();
    }

    @Override
    public List<Order> getActiveOrdersByIteration(User user) {
        List<Order> res = new LinkedList<>();

        for(var o : user.getOrders()) {
            if(o.getStatus().ordinal() == OrderStatus.ACTIVE.ordinal()) {
                res.add(o);
            }
        }

        return res;
    }

    @Override
    public Order getOrderById(List<Order> orders, long orderId) {
        return orders
                .stream()
                .filter(order -> order.getId() == orderId)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(String.format("There is no such order with id %o_id", orderId)));
    }

    @Override
    public Order getOrderByIdIteration(List<Order> orders, long orderId) {
        for(var o : orders) {
            if(o.getId() == orderId) {
                return o;
            }
        }

        throw new NoSuchElementException(String.format("There is no such order with id %o_id", orderId));
    }

    @Override
    public List<Order> getOrdersThatHaveItemDescription(User user, String description) {
        // Let's assume that if at least one item from the Orders' order line
        // has stated in parameters description, then this Order can be added
        // to result list

        return user.getOrders()
                .stream()
                .filter(o -> o.getOrderLines()
                        .stream()
                        .anyMatch(orl -> orl.getItem().getDescription().equals(description))).toList();
    }

    @Override
    public boolean hasActiveOrders(User user) {
        return user.getOrders().stream().anyMatch(or -> or.isActive());
    }

    @Override
    public boolean canBeReturned(Order order) {
        return order.getOrderLines().stream().noneMatch(OrderLine::isSpecialOffer);
    }

    @Override
    public Optional<Order> getMaxPriceOrder(User user) {
        return user.getOrders()
                .stream()
                .max(Comparator.comparing(en -> en.getOrderLines()
                        .stream()
                        .mapToInt(ol -> ol.getItem().getPrice().intValue()).sum()));
    }
}
