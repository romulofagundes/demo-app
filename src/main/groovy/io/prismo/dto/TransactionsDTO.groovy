package io.prismo.dto

import com.fasterxml.jackson.annotation.JsonProperty

class TransactionsDTO {

    @JsonProperty("transaction_id")
    Long id

    @JsonProperty(value ="account_id", required = true)
    Long accountId

    @JsonProperty(value = "operation_type_id", required = true)
    Long operationTypeId

    @JsonProperty(value = "amount", required = true)
    BigDecimal amount



    @JsonProperty("eventdate")
    Date eventDate
}
