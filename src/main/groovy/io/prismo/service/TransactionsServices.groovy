package io.prismo.service

import io.prismo.domain.Transactions
import io.prismo.repos.TransactionsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TransactionsServices {

    @Autowired
    TransactionsRepository transactionsRepository

    Transactions create(Transactions transactions){
        return transactionsRepository.save(transactions)
    }
}
