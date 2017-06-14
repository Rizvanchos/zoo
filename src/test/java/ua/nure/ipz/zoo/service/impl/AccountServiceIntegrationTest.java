package ua.nure.ipz.zoo.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ua.nure.ipz.zoo.dto.AccountDto;
import ua.nure.ipz.zoo.exception.DuplicateNamedEntityException;
import ua.nure.ipz.zoo.exception.ServiceValidationException;
import ua.nure.ipz.zoo.service.AccountService;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ua.nure.ipz.zoo.config.ZooJUnitConfiguration.class)
@Transactional
public class AccountServiceIntegrationTest {

    private static final String WASYA = "Wasya";
    private static final String WASYA_EMAIL = "wasya@zoo.com";
    private static final String WASYA_2_EMAIL = "wasya2@zoo.com";
    private static final String WASYA_PASSWORD = "12345";
    private static final String PETYA = "Petya";
    private static final String PETYA_EMAIL = "petya@zoo.com";
    private static final String PETYA_PASSWORD = "54321";
    private static final String EMPTY_STRING = "";
    private static final String BAD_EMAIL = "BAD EMAIL";
    private static final String NOT_A_GOOD_EMAIL = "NOT A GOOD EMAIL";
    @Autowired
    private AccountService accountService;

    @Test
    public void shouldBeNoAccounts_whenViewAllWithNoAccounts() {
        assertThat(accountService.viewAll(), empty());
    }

    @Test
    public void shouldCreateNewOperator_whenCallCreateAccount() throws DuplicateNamedEntityException {
        UUID operatorId = accountService.createOperator(WASYA, WASYA_EMAIL, WASYA_PASSWORD);

        AccountDto accountDto = accountService.view(operatorId);
        assertEquals(accountDto.getDomainId(), operatorId);
        assertEquals(accountDto.getName(), WASYA);
        assertEquals(accountDto.getEmail(), WASYA_EMAIL);
        assertEquals(accountDto.getPassword(), WASYA_PASSWORD);
    }

    @Test
    public void shouldCreate2Operators_whenCreate2Operators() throws DuplicateNamedEntityException {
        UUID operator1Id = accountService.createOperator(WASYA, WASYA_EMAIL, WASYA_PASSWORD);
        UUID operator2Id = accountService.createOperator(PETYA, PETYA_EMAIL, PETYA_PASSWORD);

        AccountDto account1Dto = accountService.view(operator1Id);
        assertEquals(account1Dto.getDomainId(), operator1Id);
        assertEquals(account1Dto.getName(), WASYA);
        assertEquals(account1Dto.getEmail(), WASYA_EMAIL);
        assertEquals(account1Dto.getPassword(), WASYA_PASSWORD);

        AccountDto account2Dto = accountService.view(operator2Id);
        assertEquals(account2Dto.getDomainId(), operator2Id);
        assertEquals(account2Dto.getName(), PETYA);
        assertEquals(account2Dto.getEmail(), PETYA_EMAIL);
        assertEquals(account2Dto.getPassword(), PETYA_PASSWORD);

        List<UUID> accountIds = accountService.viewAll();
        assertThat(accountIds, hasSize(2));
        assertThat(accountIds, containsInAnyOrder(operator1Id, operator2Id));
    }

    @Test(expected = DuplicateNamedEntityException.class)
    public void shouldThrow_whenCreateDuplicateEmailsOperators() throws DuplicateNamedEntityException {
        accountService.createOperator(WASYA, WASYA_EMAIL, WASYA_PASSWORD);
        accountService.createOperator(PETYA, WASYA_EMAIL, WASYA_PASSWORD);
    }

    @Test(expected = ServiceValidationException.class)
    public void shouldThrow_whenPassEmptyName() throws DuplicateNamedEntityException {
        accountService.createOperator(EMPTY_STRING, WASYA_EMAIL, WASYA_PASSWORD);
    }

    @Test(expected = ServiceValidationException.class)
    public void shouldThrow_whenPassEmptyEmail() throws DuplicateNamedEntityException {
        accountService.createOperator(WASYA, EMPTY_STRING, WASYA_PASSWORD);
    }

    @Test(expected = ServiceValidationException.class)
    public void shouldThrow_whenPassInvalidEmail() throws DuplicateNamedEntityException {
        accountService.createOperator(WASYA, NOT_A_GOOD_EMAIL, WASYA_PASSWORD);
    }

    @Test(expected = ServiceValidationException.class)
    public void shouldThrow_whenPassEmptyPassword() throws DuplicateNamedEntityException {
        accountService.createOperator(WASYA, WASYA_EMAIL, EMPTY_STRING);
    }

    @Test
    public void shouldRenameOperator_whenCallChangeName() throws DuplicateNamedEntityException {
        UUID operatorId = accountService.createOperator(WASYA, WASYA_EMAIL, WASYA_PASSWORD);
        accountService.changeName(operatorId, PETYA);

        assertEquals(accountService.view(operatorId).getName(), PETYA);
    }

    @Test(expected = ServiceValidationException.class)
    public void shouldThrowException_whenChangeNameToEmpty() throws DuplicateNamedEntityException {
        UUID operatorId = accountService.createOperator(WASYA, WASYA_EMAIL, WASYA_PASSWORD);
        accountService.changeName(operatorId, EMPTY_STRING);
    }

    @Test
    public void shouldChangeEmail_whenCallChangeEmail() throws DuplicateNamedEntityException {
        UUID operatorId = accountService.createOperator(WASYA, WASYA_EMAIL, WASYA_PASSWORD);
        accountService.changeEmail(operatorId, WASYA_2_EMAIL);

        assertEquals(accountService.view(operatorId).getEmail(), WASYA_2_EMAIL);
    }

    @Test(expected = ServiceValidationException.class)
    public void shouldThrowException_whenChangeEmailToEmpty() throws DuplicateNamedEntityException {
        UUID operatorId = accountService.createOperator(WASYA, WASYA_EMAIL, WASYA_PASSWORD);
        accountService.changeEmail(operatorId, EMPTY_STRING);
    }

    @Test(expected = ServiceValidationException.class)
    public void shouldThrowException_whenChangeEmailToInvalid() throws DuplicateNamedEntityException {
        UUID operatorId = accountService.createOperator(WASYA, WASYA_EMAIL, WASYA_PASSWORD);
        accountService.changeEmail(operatorId, BAD_EMAIL);
    }

    @Test(expected = DuplicateNamedEntityException.class)
    public void shouldThrowException_whenChangeEmailToExisting() throws DuplicateNamedEntityException {
        accountService.createOperator(PETYA, PETYA_EMAIL, WASYA_PASSWORD);
        UUID operatorId = accountService.createOperator(WASYA, WASYA_EMAIL, WASYA_PASSWORD);

        accountService.changeEmail(operatorId, PETYA_EMAIL);
    }

    @Test
    public void shouldChangePassword_whenCallChangePassword() throws DuplicateNamedEntityException {
        UUID operatorId = accountService.createOperator(WASYA, WASYA_EMAIL, WASYA_PASSWORD);
        accountService.changePassword(operatorId, PETYA_PASSWORD);

        assertEquals(accountService.view(operatorId).getPassword(), PETYA_PASSWORD);
    }

    @Test(expected = ServiceValidationException.class)
    public void shouldThrowException_whenChangePasswordToEmpty() throws DuplicateNamedEntityException {
        UUID operatorId = accountService.createOperator(WASYA, WASYA_EMAIL, WASYA_PASSWORD);
        accountService.changePassword(operatorId, EMPTY_STRING);
    }
}
