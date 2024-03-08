package bg.uni.fmi.lab02.streams.service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Order {
    private long id;
    private List<OrderLine> orderLines;
    private LocalDate orderDate;
    private OrderStatus status;
    private PaymentMethod paymentMethod;

    public Order(long id, List<OrderLine> orderLines, LocalDate orderDate, OrderStatus status, PaymentMethod paymentMethod) {
        this.id = id;
        this.orderLines = orderLines;
        this.orderDate = orderDate;
        this.status = status;
        this.paymentMethod = paymentMethod;
    }

    public Order() {
        this.id = 0;
        this.orderLines = Collections.emptyList();
        this.orderDate = LocalDate.MIN;
        this.status = OrderStatus.INACTIVE;
        this.paymentMethod = PaymentMethod.CASH_ON_DELIVERY;
    }

    public long getId() {
        return id;
    }

    public List<OrderLine> getOrderLines() {
        return orderLines;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public boolean isActive() {
        return status == OrderStatus.ACTIVE;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setOrderLines(List<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
