package io.pismo.exception

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class AccountsWithoutDocumentNumberException extends ResponseStatusException{
    AccountsWithoutDocumentNumberException(){
        super(HttpStatus.BAD_REQUEST,"Document Number is Required")
    }
}
