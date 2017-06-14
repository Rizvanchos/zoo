package ua.nure.ipz.zoo.repository.jpa;

import org.springframework.stereotype.Repository;
import ua.nure.ipz.zoo.entity.Account;
import ua.nure.ipz.zoo.entity.OperatorAccount;
import ua.nure.ipz.zoo.entity.Order;
import ua.nure.ipz.zoo.repository.AccountRepository;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.UUID;

@Repository
public class DefaultAccountRepository extends BasicRepository<Account> implements AccountRepository {
    public DefaultAccountRepository() {
        super(Account.class);
    }

    @Override
    public Account findByEmail(String email) {
        TypedQuery<Account> queryByEmail = getEntityManager().createQuery(
                "SELECT a " +
                        "FROM Account a " +
                        "WHERE a.email = :email", Account.class);

        queryByEmail.setParameter("email", email);
        List<Account> results = queryByEmail.getResultList();
        return getSingleResult(results);
    }

    @Override
    public Account findByOrder(Order o) {
        TypedQuery<Account> queryByOrder = getEntityManager().createQuery(
                "SELECT a FROM OperatorAccount a WHERE EXISTS ( " +
                        "SELECT 1 FROM a.orders o WHERE o.id = :orderId )", Account.class);
        queryByOrder.setParameter("orderId", o.getId());
        List<Account> results = queryByOrder.getResultList();
        return getSingleResult(results);
    }

    @Override
    public OperatorAccount findOperatorByDomainId(UUID operatorId) {
        Account account = findByDomainId(operatorId);
        if (account == null || !(account instanceof OperatorAccount))
            return null;

        return (OperatorAccount) account;
    }
}
