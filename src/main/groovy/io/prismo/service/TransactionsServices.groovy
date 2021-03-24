package io.prismo.service

import io.prismo.domain.Transactions
import io.prismo.dto.TransactionsDTO
import io.prismo.exception.AccountsWithoutCreditLimitException
import io.prismo.exception.InvalidAccountException
import io.prismo.exception.InvalidOperationalTypesException
import io.prismo.exception.TransactionsAmountWithNoFunds
import io.prismo.exception.TransactionsEmptyValuesException
import io.prismo.repos.AccountsRepository
import io.prismo.repos.OperationalTypesRepository
import io.prismo.repos.TransactionsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import javax.transaction.Transactional

@Service
class TransactionsServices {

    @Autowired
    TransactionsRepository transactionsRepository

    @Autowired
    AccountsRepository accountsRepository

    @Autowired
    OperationalTypesRepository operationalTypesRepository

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

        def accounts = accountsRepository
                .findById(transactionsDTO.accountId)
                .orElseThrow { new InvalidAccountException() }

        def operationalTypes = operationalTypesRepository
                .findById(transactionsDTO.operationTypeId)
                .orElseThrow { new InvalidOperationalTypesException() }


        def transactions = new Transactions(accounts: accounts, operationalTypes: operationalTypes)

        if ([1l, 2l, 3l].contains(transactionsDTO.operationTypeId)) {
            transactionsDTO.amount *= -1
        }
        transactions.accounts.availableLimitCredit += transactionsDTO.amount
        if (transactions.accounts.availableLimitCredit < 0) {
            throw new AccountsWithoutCreditLimitException()
        }
        transactions.amount = transactionsDTO.amount
        transactionsRepository.save(transactions)

        transactionsDTO.id = transactions.id
        transactionsDTO.eventDate = transactions.eventDate

        return transactionsDTO
    }

}
