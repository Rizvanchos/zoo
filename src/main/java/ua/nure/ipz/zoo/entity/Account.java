package ua.nure.ipz.zoo.entity;

import ua.nure.ipz.zoo.util.DomainEntity;

import javax.persistence.Entity;
import java.util.Objects;

@Entity
public class Account extends DomainEntity {
    private String name;
    private String email;
    private String password;

    public Account() {
    }

    public Account(String name, String email, String password) {
        setName(name);
        setPassword(password);
        setEmail(email);
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

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean checkPassword(String password) {
        return Objects.equals(this.getPassword(), password);
    }

    public void accept(AccountVisitor visitor) {
        visitor.visit(this);
    }
}
