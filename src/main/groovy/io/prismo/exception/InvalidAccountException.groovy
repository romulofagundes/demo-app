package io.prismo.exception

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class InvalidAccountException extends ResponseStatusException{
    InvalidAccountException(){
        super(HttpStatus.NOT_FOUND,"Invalid Account Exception")
    }
}
