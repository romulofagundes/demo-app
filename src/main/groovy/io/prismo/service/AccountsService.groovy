package io.prismo.service

import io.prismo.domain.Accounts
import io.prismo.exception.AccountsWithoutDocumentNumberException
import io.prismo.repos.AccountsRepository
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

    Optional<Accounts> get(Long id){
        return accountsRepository.findById(id)
    }
}
