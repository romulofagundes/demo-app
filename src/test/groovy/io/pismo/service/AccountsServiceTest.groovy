package io.pismo.service

import io.pismo.domain.Accounts
import io.pismo.exception.AccountsNotFoundException
import io.pismo.exception.AccountsWithoutDocumentNumberException
import io.pismo.test.GeneralTest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

import static org.junit.jupiter.api.Assertions.*

class AccountsServiceTest extends GeneralTest{

    @Autowired
    AccountsService accountsService

    @Test
    void accounts_TestValidCreate() {
        Accounts accounts = new Accounts(documentNumber: UUID.randomUUID().toString())
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
        Accounts accountsCreated = accountsService.create(new Accounts(documentNumber: UUID.randomUUID().toString()))
        Accounts accountsRecovery = accountsService.get(accountsCreated.id)
        assertEquals(accountsCreated.id, accountsRecovery.id)
        assertEquals(accountsCreated.documentNumber, accountsRecovery.documentNumber)
    }

    @Test
    void accounts_RecoveryByIdInvalid() {
        assertThrows(AccountsNotFoundException, { accountsService.get(Integer.MAX_VALUE) })

    }
}
