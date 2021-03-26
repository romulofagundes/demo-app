package io.pismo.service

import io.pismo.domain.Accounts
import io.pismo.exception.AccountsNotFoundException
import io.pismo.exception.AccountsWithoutCreditLimitException
import io.pismo.exception.AccountsWithoutDocumentNumberException
import io.pismo.repos.AccountsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AccountsService {

    @Autowired
    AccountsRepository accountsRepository

    Accounts create(Accounts accounts){
        if(!accounts.documentNumber){
            throw new AccountsWithoutDocumentNumberException()
        }
        return accountsRepository.save(accounts)
    }

    Accounts get(Long id){
        return accountsRepository.findById(id).orElseThrow{new AccountsNotFoundException()}
    }

    void debitValue(Accounts accounts, BigDecimal amount){
        accounts.availableLimitCredit += amount
        if(accounts.availableLimitCredit < 0){
            throw new AccountsWithoutCreditLimitException()
        }
        accountsRepository.save(accounts)
    }
}
