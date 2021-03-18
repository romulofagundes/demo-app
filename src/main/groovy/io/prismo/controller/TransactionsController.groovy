package io.prismo.controller

import io.prismo.domain.Transactions
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/transactions")
class TransactionsController {

    @PostMapping
    ResponseEntity<Transactions> create(@RequestBody Transactions transactions){

    }

}
