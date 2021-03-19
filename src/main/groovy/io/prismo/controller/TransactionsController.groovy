package io.prismo.controller

import io.prismo.dto.TransactionsDTO
import io.prismo.service.TransactionsServices
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/transactions")
class TransactionsController {

    @Autowired
    TransactionsServices transactionsServices

    @PostMapping
    ResponseEntity<TransactionsDTO> create(@RequestBody TransactionsDTO transactionsDTO){
        return ResponseEntity.ok(transactionsServices.create(transactionsDTO))
    }

}
