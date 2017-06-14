package ua.nure.ipz.zoo.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class ContactTest {

    private static final String NAME = "Name";
    private static final String EMAIL = "Email";
    private static final String CONTACT_PHONE = "1234-5678-90";

    @Test
    public void shouldCreateContactWithDefaultValues_whenCallDefaultConstructor() {
        Contact contact = new Contact();
        assertNull(contact.getName());
        assertNull(contact.getEmail());
        assertNull(contact.getContactPhone());
    }

    @Test
    public void shouldCreateContactWithParameters_whenConstructorWithParameters() {
        Contact contact = new Contact(NAME, EMAIL, CONTACT_PHONE);

        assertEquals(NAME, contact.getName());
        assertEquals(EMAIL, contact.getEmail());
        assertEquals(CONTACT_PHONE, contact.getContactPhone());
    }

    @Test
    public void shouldSetNewContactName_whenSetNewName() {
        Contact contact = new Contact();
        contact.setName(NAME);

        assertEquals(NAME, contact.getName());
    }

    @Test
    public void shouldSetNewContactEmail_whenSetNewEmail(){
        Contact contact = new Contact();
        contact.setEmail(EMAIL);

        assertEquals(EMAIL, contact.getEmail());
    }

    @Test
    public void shouldSetNewContactPhoneToContact_whenSetNewContactPhone() {
        Contact contact = new Contact();
        contact.setContactPhone(CONTACT_PHONE);

        assertEquals(CONTACT_PHONE, contact.getContactPhone());
    }
}
