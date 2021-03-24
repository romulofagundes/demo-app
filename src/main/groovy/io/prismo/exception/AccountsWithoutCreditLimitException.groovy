package io.prismo.exception

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class AccountsWithoutCreditLimitException extends ResponseStatusException {
    AccountsWithoutCreditLimitException() {
        super(HttpStatus.BAD_REQUEST,"Account Without Credit Limit")
    }
}
