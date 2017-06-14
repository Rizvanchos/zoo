package ua.nure.ipz.zoo.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AccountTest {

    private static final String NAME = "Name";
    private static final String EMAIL = "Email";
    private static final String PASSWORD = "Password";

    @Mock
    private AccountVisitor accountVisitor;

    @Test
    public void shouldCreateAccountWithParameters_whenCallConstructorWithParameters() {
        Account account = getAccount();

        assertEquals(NAME, account.getName());
        assertEquals(EMAIL, account.getEmail());
        assertEquals(PASSWORD, account.getPassword());
    }

    private Account getAccount() {
        return new Account(NAME, EMAIL, PASSWORD);
    }

    @Test
    public void shouldReturnTrue_whenPasswordEquals() {
        Account account = getAccount();
        assertTrue(PASSWORD, account.checkPassword(PASSWORD));
    }

    @Test
    public void shouldCallVisit_whenPerformAccept() {
        Account account = getAccount();
        account.accept(accountVisitor);
        verify(accountVisitor).visit(account);
    }
}
