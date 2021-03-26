package io.pismo.service

import io.pismo.domain.Transactions
import io.pismo.dto.TransactionsDTO
import io.pismo.exception.TransactionsAmountWithNoFunds
import io.pismo.exception.TransactionsEmptyValuesException
import io.pismo.repos.TransactionsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import javax.transaction.Transactional

@Service
class TransactionsServices {

    @Autowired
    TransactionsRepository transactionsRepository

    @Autowired
    AccountsService accountsService

    @Autowired
    OperationalTypesService operationalTypesService

    @Transactional
    TransactionsDTO create(TransactionsDTO transactionsDTO) {
        if (transactionsDTO.amount == null
                && transactionsDTO.accountId == null
                && transactionsDTO.operationTypeId == null
        ) {
            throw new TransactionsEmptyValuesException()
        }
        if (transactionsDTO.amount <= 0) {
            throw new TransactionsAmountWithNoFunds()
        }

        def accounts = accountsService.get(transactionsDTO.accountId)
        def operationalTypes = operationalTypesService.get(transactionsDTO.operationTypeId)

        def transactions = new Transactions(accounts: accounts, operationalTypes: operationalTypes)

        def calculatedValue = calcValue(transactionsDTO.operationTypeId, transactionsDTO.amount)
        accountsService.debitValue(accounts, calculatedValue)
        transactions.amount = calculatedValue
        transactionsDTO.amount = calculatedValue
        transactionsRepository.save(transactions)
        transactionsDTO.id = transactions.id
        transactionsDTO.eventDate = transactions.eventDate

        return transactionsDTO
    }

    private BigDecimal calcValue(Long operationTypeId, BigDecimal amount) {
        def returnedAmount = amount
        if ([1l, 2l, 3l].contains(operationTypeId)) {
            returnedAmount *= -1
        }
        return returnedAmount
    }

}
