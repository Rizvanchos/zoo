package ua.nure.ipz.zoo.repository;

import ua.nure.ipz.zoo.entity.Account;
import ua.nure.ipz.zoo.entity.OperatorAccount;
import ua.nure.ipz.zoo.entity.Order;

import java.util.UUID;

public interface AccountRepository extends Repository<Account> {
    Account findByEmail(String email);

    Account findByOrder(Order o);

    OperatorAccount findOperatorByDomainId(UUID operatorId);
}
