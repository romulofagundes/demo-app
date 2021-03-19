package io.prismo.service

import io.prismo.domain.Transactions
import io.prismo.dto.TransactionsDTO
import io.prismo.exception.InvalidAccountException
import io.prismo.exception.InvalidOperationalTypesException
import io.prismo.exception.TransactionsAmountWithNoFunds
import io.prismo.exception.TransactionsEmptyValuesException
import io.prismo.repos.AccountsRepository
import io.prismo.repos.OperationalTypesRepository
import io.prismo.repos.TransactionsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TransactionsServices {

    @Autowired
    TransactionsRepository transactionsRepository

    @Autowired
    AccountsRepository accountsRepository

    @Autowired
    OperationalTypesRepository operationalTypesRepository

    TransactionsDTO create(TransactionsDTO transactionsDTO) {
        if (transactionsDTO.amount == null
                && transactionsDTO.accountId == null
                && transactionsDTO.operationTypeId == null
        ) {
            throw new TransactionsEmptyValuesException()
        }
        def transactions = new Transactions()
        transactions.accounts = accountsRepository
                .findById(transactionsDTO.accountId)
                .orElseThrow { new InvalidAccountException() }
        transactions.operationalTypes = operationalTypesRepository
                .findById(transactionsDTO.operationTypeId)
                .orElseThrow { new InvalidOperationalTypesException() }

        if (transactionsDTO.amount <= 0) {
            throw new TransactionsAmountWithNoFunds()
        }
        if ([1l, 2l, 3l].contains(transactionsDTO.operationTypeId)) {
            transactionsDTO.amount *= -1
        }
        transactions.amount = transactionsDTO.amount
        transactionsRepository.save(transactions)

        transactionsDTO.id = transactions.id
        transactionsDTO.eventDate = transactions.eventDate

        return transactionsDTO
    }
}
