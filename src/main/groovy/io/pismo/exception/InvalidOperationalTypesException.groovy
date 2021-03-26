package io.pismo.exception

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class InvalidOperationalTypesException extends ResponseStatusException{
    InvalidOperationalTypesException(){
        super(HttpStatus.NOT_FOUND,"Invalid Operational Types Exception")
    }
}
