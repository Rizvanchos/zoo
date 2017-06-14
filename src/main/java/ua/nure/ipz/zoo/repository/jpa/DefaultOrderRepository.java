package ua.nure.ipz.zoo.repository.jpa;

import org.springframework.stereotype.Repository;
import ua.nure.ipz.zoo.entity.Order;
import ua.nure.ipz.zoo.entity.OrderStatus;
import ua.nure.ipz.zoo.repository.OrderRepository;

import javax.annotation.PostConstruct;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.UUID;

@Repository
public class DefaultOrderRepository extends BasicRepository<Order> implements OrderRepository {
    private TypedQuery<UUID> queryByOrderStatus;

    public DefaultOrderRepository() {
        super(Order.class);
    }

    @PostConstruct
    private void init() {
        queryByOrderStatus = getEntityManager().createQuery("SELECT o.domainId FROM Order o WHERE o.status = :orderStatus", UUID.class);
    }

    @Override
    public List<UUID> selectAccepted() {
        return selectByOrderStatus(OrderStatus.ACCEPTED);
    }

    @Override
    public List<UUID> selectProcessing() {
        return selectByOrderStatus(OrderStatus.PROCESSING);
    }

    @Override
    public List<UUID> selectFinished() {
        return selectByOrderStatus(OrderStatus.FINISHED);
    }

    private List<UUID> selectByOrderStatus(OrderStatus orderStatus) {
        queryByOrderStatus.setParameter("orderStatus", orderStatus);
        return queryByOrderStatus.getResultList();
    }
}
