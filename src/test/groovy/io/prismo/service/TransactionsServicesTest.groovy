package io.prismo.service

import io.prismo.domain.Accounts
import io.prismo.dto.TransactionsDTO
import io.prismo.exception.AccountsWithoutCreditLimitException
import io.prismo.exception.InvalidAccountException
import io.prismo.exception.InvalidOperationalTypesException
import io.prismo.exception.TransactionsAmountWithNoFunds
import io.prismo.exception.TransactionsEmptyValuesException
import io.prismo.test.GeneralTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

import static org.junit.jupiter.api.Assertions.*

class TransactionsServicesTest extends GeneralTest {

    @Autowired
    TransactionsServices transactionsServices

    @Autowired
    AccountsService accountsService

    Accounts accounts

    @BeforeEach
    void setup() {
        accounts = accountsService.create(new Accounts(documentNumber: "12345678900"))
    }

    @Test
    void transactions_TestValidCreate() {
        def transactionsDTO = new TransactionsDTO(
                accountId: accounts.id,
                operationTypeId: 4,
                amount: new BigDecimal(10)
        )
        TransactionsDTO transactionsDTOCreated = transactionsServices.create(transactionsDTO)
        assertEquals(transactionsDTOCreated.accountId, transactionsDTO.accountId)
        assertEquals(transactionsDTOCreated.operationTypeId, transactionsDTO.operationTypeId)
        assertTrue(transactionsDTOCreated.amount > 0)
        assertNotNull(transactionsDTOCreated.eventDate)
        assertNotNull(transactionsDTOCreated.id)
    }

    @Test
    void transactions_TestValidCreateWithNegativeOperation() {
        def transactionsDTO = new TransactionsDTO(
                accountId: accounts.id,
                operationTypeId: 1,
                amount: new BigDecimal(10)
        )
        TransactionsDTO transactionsDTOCreated = transactionsServices.create(transactionsDTO)
        assertEquals(transactionsDTOCreated.accountId, transactionsDTO.accountId)
        assertEquals(transactionsDTOCreated.operationTypeId, transactionsDTO.operationTypeId)
        assertTrue(transactionsDTOCreated.amount < 0)
        assertNotNull(transactionsDTOCreated.eventDate)
        assertNotNull(transactionsDTOCreated.id)
    }

    @Test
    void transactions_TestInvalidAccountIDCreate() {
        def transactionsDTO = new TransactionsDTO(
                accountId: Integer.MAX_VALUE,
                operationTypeId: 4,
                amount: new BigDecimal(10)
        )
        assertThrows(InvalidAccountException.class, { transactionsServices.create(transactionsDTO) })
    }

    @Test
    void transactions_TestInvalidOperationalTypeIdCreate() {
        def transactionsDTO = new TransactionsDTO(
                accountId: accounts.id,
                operationTypeId: Integer.MAX_VALUE,
                amount: new BigDecimal(10)
        )
        assertThrows(InvalidOperationalTypesException.class, { transactionsServices.create(transactionsDTO) })
    }


    @Test
    void transactions_TestValidCreateWithZeroAmount() {
        def transactionsDTO = new TransactionsDTO(
                accountId: accounts.id,
                operationTypeId: 4,
                amount: new BigDecimal(0)
        )
        assertThrows(TransactionsAmountWithNoFunds.class, { transactionsServices.create(transactionsDTO) })
    }

    @Test
    void transactions_TestInvalidCreateWithNegativeAmount() {
        def transactionsDTO = new TransactionsDTO(
                accountId: accounts.id,
                operationTypeId: 4,
                amount: new BigDecimal(Integer.MIN_VALUE)
        )
        assertThrows(TransactionsAmountWithNoFunds.class, { transactionsServices.create(transactionsDTO) })
    }

    @Test
    void transactions_TestInvalidCreateWithoutValues() {
        def transactionsDTO = new TransactionsDTO()
        assertThrows(TransactionsEmptyValuesException.class, { transactionsServices.create(transactionsDTO) })
    }

    @Test
    void transactions_TestAccountWithPlusCreditLimit() {
        def accountNew = accountsService.create(new Accounts(documentNumber: "12345678999"))

        def transactionsDTO = new TransactionsDTO(
                accountId: accountNew.id,
                operationTypeId: 4,
                amount: new BigDecimal(10)
        )
        transactionsServices.create(transactionsDTO)
        def accountRecovery = accountsService.get(accountNew.id).get()
        assertEquals(accountRecovery.availableLimitCredit, new BigDecimal("510.00"))
    }

    @Test
    void transactions_TestAccountWithMinusCreditLimit() {
        def accountNew = accountsService.create(new Accounts(documentNumber: "12345678998"))

        def transactionsDTO = new TransactionsDTO(
                accountId: accountNew.id,
                operationTypeId: 2,
                amount: new BigDecimal(10)
        )
        transactionsServices.create(transactionsDTO)
        def accountRecovery = accountsService.get(accountNew.id).get()
        assertEquals(accountRecovery.availableLimitCredit, new BigDecimal("490.00"))
    }

    @Test
    void transactions_TestAccountWithMinusWithouLimitCredit() {
        def accountNew = accountsService.create(new Accounts(documentNumber: "12345678799"))

        def transactionsDTO = new TransactionsDTO(
                accountId: accountNew.id,
                operationTypeId: 2,
                amount: new BigDecimal(510)
        )
        assertThrows(AccountsWithoutCreditLimitException.class, { transactionsServices.create(transactionsDTO) })

    }

}
