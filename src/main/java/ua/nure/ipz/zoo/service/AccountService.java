package ua.nure.ipz.zoo.service;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import ua.nure.ipz.zoo.dto.AccountDto;
import ua.nure.ipz.zoo.exception.DuplicateNamedEntityException;
import ua.nure.ipz.zoo.exception.DuplicationEntityException;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public interface AccountService extends Service<AccountDto> {
    AccountDto identify(@NotBlank @Email String email, @NotBlank String password);

    UUID createOperator(@NotBlank String name, @NotBlank @Email String email, @NotBlank String password) throws DuplicateNamedEntityException;

    UUID createManager(@NotBlank String name, @NotBlank @Email String email, @NotBlank String password) throws DuplicateNamedEntityException;

    void changeName(@NotNull UUID accountId, @NotBlank String newName);

    void changeEmail(@NotNull UUID accountId, @NotBlank @Email String newEmail) throws DuplicateNamedEntityException;

    void changePassword(@NotNull UUID accountId, @NotBlank String newPassword);

    void assignProvision(@NotNull UUID managerId, @NotNull UUID provisionId) throws DuplicationEntityException;
}
