package io.prismo.exception

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class TransactionsAmountWithNoFunds extends ResponseStatusException{

    TransactionsAmountWithNoFunds(){
        super(HttpStatus.NOT_ACCEPTABLE,"Invalid Transactional: Amount with no funds")
    }
}
