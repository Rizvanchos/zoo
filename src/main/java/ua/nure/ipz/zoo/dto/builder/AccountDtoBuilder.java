package ua.nure.ipz.zoo.dto.builder;

import org.springframework.stereotype.Component;
import ua.nure.ipz.zoo.dto.AccountDto;
import ua.nure.ipz.zoo.entity.Account;

@Component
public class AccountDtoBuilder implements DtoBuilder<Account, AccountDto> {
    @Override
    public AccountDto toDto(Account source) {
        return new AccountDto(source.getDomainId(), source.getName(), source.getEmail(), source.getPassword());
    }
}
