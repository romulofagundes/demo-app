package io.pismo.service

import io.pismo.domain.Accounts
import io.pismo.dto.TransactionsDTO
import io.pismo.exception.AccountsNotFoundException
import io.pismo.exception.AccountsWithoutCreditLimitException

import io.pismo.exception.InvalidOperationalTypesException
import io.pismo.exception.TransactionsAmountWithNoFunds
import io.pismo.exception.TransactionsEmptyValuesException
import io.pismo.test.GeneralTest
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
        accounts = accountsService.create(new Accounts(documentNumber: UUID.randomUUID().toString()))
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
        def accountNew = accountsService.create(new Accounts(documentNumber: UUID.randomUUID().toString()))
        def transactionsDTO = new TransactionsDTO(
                accountId: accountNew.id,
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
        assertThrows(AccountsNotFoundException.class, { transactionsServices.create(transactionsDTO) })
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
        def accountNew = accountsService.create(new Accounts(documentNumber: UUID.randomUUID().toString()))
        def amountTest = new BigDecimal("10.00")
        def transactionsDTO = new TransactionsDTO(
                accountId: accountNew.id,
                operationTypeId: 4,
                amount: amountTest
        )
        transactionsServices.create(transactionsDTO)
        def accountRecovery = accountsService.get(accountNew.id)
        assertEquals(accountRecovery.availableLimitCredit, (Accounts.INITIAL_CREDIT_LIMIT+amountTest))
    }

    @Test
    void transactions_TestAccountWithMinusCreditLimit() {
        def accountNew = accountsService.create(new Accounts(documentNumber: UUID.randomUUID().toString()))
        def amountTest = new BigDecimal("10.00")
        def transactionsDTO = new TransactionsDTO(
                accountId: accountNew.id,
                operationTypeId: 2,
                amount: amountTest
        )
        transactionsServices.create(transactionsDTO)
        def accountRecovery = accountsService.get(accountNew.id)
        assertEquals(accountRecovery.availableLimitCredit, (Accounts.INITIAL_CREDIT_LIMIT-amountTest))
    }

    @Test
    void transactions_TestAccountWithMinusWithouLimitCredit() {
        def accountNew = accountsService.create(new Accounts(documentNumber: UUID.randomUUID().toString()))
        def amountTest = Accounts.INITIAL_CREDIT_LIMIT + 1
        def transactionsDTO = new TransactionsDTO(
                accountId: accountNew.id,
                operationTypeId: 2,
                amount: amountTest
        )
        assertThrows(AccountsWithoutCreditLimitException.class, { transactionsServices.create(transactionsDTO) })

    }

}
