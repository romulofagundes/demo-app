package io.prismo.controller

import io.prismo.domain.Accounts
import io.prismo.exception.AccountsNotFoundException
import io.prismo.service.AccountsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/accounts")
class AccountsController {

    @Autowired
    AccountsService accountsService

    @PostMapping
    ResponseEntity<Accounts> create(@RequestBody Accounts accounts){
        return ResponseEntity.status(HttpStatus.CREATED).body(accountsService.create(accounts))
    }

    @GetMapping("/{id}")
    ResponseEntity<Accounts> get(@PathVariable("id") Long id){
        Accounts returned = accountsService.get(id).orElseThrow{new AccountsNotFoundException()}
        return ResponseEntity.ok(returned)
    }
}
