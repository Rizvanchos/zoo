package ua.nure.ipz.zoo.entity;

import ua.nure.ipz.zoo.exception.OrderLifecycleException;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class OperatorAccount extends Account {
    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Order> orders = new ArrayList<>();

    public OperatorAccount() {
    }

    public OperatorAccount(String name, String email, String password) {
        super(name, email, password);
    }

    public void trackOrder(Order order) {
        orders.add(order);
    }

    public void processOrder(Order order) throws OrderLifecycleException {
        order.process();
        order.finish();
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
