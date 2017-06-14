package ua.nure.ipz.zoo.entity;

import javax.persistence.Embeddable;

@Embeddable
public class Contact {
    private String name;
    private String email;
    private String contactPhone;

    public Contact() {
    }

    public Contact(String name, String email, String contactPhone) {
        this.name = name;
        this.email = email;
        this.contactPhone = contactPhone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }
}
