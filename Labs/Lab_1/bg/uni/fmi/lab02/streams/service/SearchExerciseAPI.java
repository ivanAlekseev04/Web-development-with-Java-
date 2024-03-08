package bg.uni.fmi.lab02.streams.service;

import java.util.List;
import java.util.Optional;

public interface SearchExerciseAPI {

    /**
     * extract all active orders
     * @param user
     * @return List<Order>
     */
    List<Order> getActiveOrders(User user);

    List<Order> getActiveOrdersByIteration(User user);

    /**
     * Return order by a specific id
     * @param orders
     * @param orderId
     * @return Order
     */
    Order getOrderById(List<Order> orders, long orderId);

    Order getOrderByIdIteration(List<Order> orders, long orderId);

    /**
     * Return orders that have specific description for item
     * @param user
     * @param description
     * @return List<Order>
     */
    List<Order> getOrdersThatHaveItemDescription(User user, String description);

    /**
     * @return true if customer has at least one order with status ACTIVE
     */
    boolean hasActiveOrders(User user);

    /**
     * Return true if inside the Order we don't have OrderLine with special offer
     */
    boolean canBeReturned(Order order);

    /**
     * Return the order with maximum total price
     * @param user
     * @return
     */
    Optional<Order> getMaxPriceOrder(User user);
}
