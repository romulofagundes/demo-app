package io.pismo.exception

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class AccountsNotFoundException extends ResponseStatusException  {
    AccountsNotFoundException(){
        super(HttpStatus.NOT_FOUND, "Account Not Found")
    }
}
