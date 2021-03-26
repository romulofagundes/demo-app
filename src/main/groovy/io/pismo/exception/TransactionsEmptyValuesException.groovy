package io.pismo.exception

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class TransactionsEmptyValuesException extends ResponseStatusException{

    TransactionsEmptyValuesException(){
        super(HttpStatus.BAD_REQUEST,"Transactions with Required Field is Empty")
    }
}
