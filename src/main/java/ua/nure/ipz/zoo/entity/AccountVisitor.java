package ua.nure.ipz.zoo.entity;

import ua.nure.ipz.zoo.exception.DuplicateNamedEntityException;
import ua.nure.ipz.zoo.exception.DuplicationEntityException;

public interface AccountVisitor {
    void visit(Account account);

    void visit(OperatorAccount account);

    void visit(ZooManager account) throws DuplicateNamedEntityException, DuplicationEntityException;
}
