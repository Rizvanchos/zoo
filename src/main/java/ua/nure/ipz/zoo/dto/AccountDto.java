package ua.nure.ipz.zoo.dto;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class AccountDto extends DomainEntityDto<AccountDto> {
    private String name;
    private String email;
    private String password;

    public AccountDto(UUID domainId, String name, String email, String password) {
        super(domainId);
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    protected List<Object> getAttributesToIncludeInEqualityCheck() {
        return Arrays.asList(getDomainId(), getName(), getEmail(), getPassword());
    }

    @Override
    public String toString() {
        return String.format("ID = %s\nName = %s\nEmail = %s", getDomainId(), name, email);
    }
}
