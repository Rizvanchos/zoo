package ua.nure.ipz.zoo.repository;

import ua.nure.ipz.zoo.entity.Order;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends Repository<Order> {
    List<UUID> selectAccepted();

    List<UUID> selectProcessing();

    List<UUID> selectFinished();
}
