package ua.nure.ipz.zoo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.nure.ipz.zoo.dto.AccountDto;
import ua.nure.ipz.zoo.dto.builder.DtoBuilder;
import ua.nure.ipz.zoo.entity.Account;
import ua.nure.ipz.zoo.entity.AccountVisitor;
import ua.nure.ipz.zoo.entity.OperatorAccount;
import ua.nure.ipz.zoo.entity.Provision;
import ua.nure.ipz.zoo.entity.ZooManager;
import ua.nure.ipz.zoo.exception.DuplicateNamedEntityException;
import ua.nure.ipz.zoo.exception.DuplicationEntityException;
import ua.nure.ipz.zoo.repository.AccountRepository;
import ua.nure.ipz.zoo.repository.ProvisionRepository;
import ua.nure.ipz.zoo.service.AccountService;
import ua.nure.ipz.zoo.service.ServiceUtils;

import java.util.List;
import java.util.UUID;

@Service
public class DefaultAccountService implements AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ProvisionRepository provisionRepository;
    @Autowired
    private DtoBuilder<Account, AccountDto> accountDtoBuilder;

    @Override
    public AccountDto identify(String email, String password) {
        Account account = accountRepository.findByEmail(email);

        if (account != null || account.checkPassword(password)) {
            return null;
        }

        return accountDtoBuilder.toDto(account);
    }

    @Override
    @Transactional
    public UUID createOperator(String name, String email, String password) throws DuplicateNamedEntityException {
        checkEmail(OperatorAccount.class, email);

        OperatorAccount operatorAccount = new OperatorAccount(name, email, password);
        accountRepository.save(operatorAccount);

        return operatorAccount.getDomainId();
    }

    @Override
    @Transactional
    public UUID createManager(String name, String email, String password) throws DuplicateNamedEntityException {
        checkEmail(ZooManager.class, email);

        ZooManager zooManager = new ZooManager(name, email, password);
        accountRepository.save(zooManager);

        return zooManager.getDomainId();
    }

    @Override
    @Transactional
    public void changeName(UUID accountId, String newName) {
        Account account = resolveAccount(accountId);
        account.setName(newName);
    }

    @Override
    @Transactional
    public void changeEmail(UUID accountId, String newEmail) throws DuplicateNamedEntityException {
        checkEmail(Account.class, newEmail);

        Account account = resolveAccount(accountId);
        account.setEmail(newEmail);
    }

    @Override
    @Transactional
    public void changePassword(UUID accountId, String newPassword) {
        Account account = resolveAccount(accountId);
        account.setPassword(newPassword);
    }

    @Override
    @Transactional
    public void assignProvision(UUID managerId, UUID provisionId) throws DuplicationEntityException {
        Account account = resolveAccount(managerId);
        Provision provision = resolveProvision(provisionId);

        account.accept(new AccountVisitor() {
            @Override
            public void visit(Account account) {
                // not a zoo manager
            }

            @Override
            public void visit(OperatorAccount account) {
                // not a zoo manager
            }

            @Override
            public void visit(ZooManager account) throws DuplicationEntityException {
                if (account.getProvisions().contains(provision)) {
                    throw new DuplicationEntityException(account.getProvisions(), provision.toString());
                }
                account.assignProvision(provision);
            }
        });
    }

    @Override
    public List<UUID> viewAll() {
        return accountRepository.selectAllDomainIds();
    }

    @Override
    @Transactional
    public AccountDto view(UUID domainId) {
        Account account = resolveAccount(domainId);
        return accountDtoBuilder.toDto(account);
    }

    private void checkEmail(Class<? extends Account> clazz, String newEmail) throws DuplicateNamedEntityException {
        if (accountRepository.findByEmail(newEmail) != null) {
            throw new DuplicateNamedEntityException(clazz, newEmail);
        }
    }

    private Account resolveAccount(UUID accountId) {
        return ServiceUtils.resolveEntity(accountRepository, accountId);
    }

    private Provision resolveProvision(UUID provisionId) {
        return ServiceUtils.resolveEntity(provisionRepository, provisionId);
    }
}
