package io.prismo.service

import io.prismo.domain.Accounts
import io.prismo.exception.AccountsWithoutDocumentNumberException
import io.prismo.test.GeneralTest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

import static org.junit.jupiter.api.Assertions.*

class AccountsServiceTest extends GeneralTest{

    @Autowired
    AccountsService accountsService

    @Test
    void accounts_TestValidCreate() {
        Accounts accounts = new Accounts(documentNumber: "12345678900")
        Accounts accountsCreated = accountsService.create(accounts)
        assertEquals(accountsCreated.documentNumber, accounts.documentNumber)
        assertTrue(accountsCreated.id > 0)
    }

    @Test
    void accounts_TestInvalidCreateWithoutDocument() {
        Accounts accounts = new Accounts()
        assertThrows(AccountsWithoutDocumentNumberException.class, {
            accountsService.create(accounts)
        })
    }

    @Test
    void accounts_CreateAndRecoveryById() {
        Accounts accountsCreated = accountsService.create(new Accounts(documentNumber: "12345678900"))
        Optional<Accounts> accountsRecovery = accountsService.get(accountsCreated.id)
        assertEquals(accountsCreated.id, accountsRecovery.get().id)
        assertEquals(accountsCreated.documentNumber, accountsRecovery.get().documentNumber)
    }

    @Test
    void accounts_RecoveryByIdInvalid() {
        assertThrows(NoSuchElementException, { accountsService.get(Integer.MAX_VALUE).get() })

    }
}
