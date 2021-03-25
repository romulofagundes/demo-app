package io.prismo.controller

import com.fasterxml.jackson.databind.ObjectMapper
import io.prismo.domain.Accounts
import io.prismo.dto.TransactionsDTO
import io.prismo.service.AccountsService
import io.prismo.test.GeneralTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import static org.junit.jupiter.api.Assertions.*

class TransactionsControllerTest extends GeneralTest{

    @Autowired
    MockMvc mvc

    @Autowired
    ObjectMapper objectMapper

    @Autowired
    AccountsService accountsService

    Accounts accounts

    @BeforeEach
    void setup(){
        accounts = accountsService.create(new Accounts(documentNumber: UUID.randomUUID().toString()))
    }

    @Test
    void transactionals_CreateWithPositiveAmountAndTransactionalType4() {
        def transactionsDTO = new TransactionsDTO(
                accountId: accounts.id,
                operationTypeId: 4,
                amount: new BigDecimal(10)
        )

        def response = mvc.perform(
                post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionsDTO)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated())
                .andReturn().response
        TransactionsDTO trasactionasDTORetorno = objectMapper.readerFor(TransactionsDTO.class).readValue(response.contentAsString)
        assertNotNull(trasactionasDTORetorno.id)
        assertTrue(trasactionasDTORetorno.amount > 0)
        assertEquals(trasactionasDTORetorno.amount,transactionsDTO.amount)

    }

    @Test
    void transactionals_CreateWithPositiveAmountAndTransactionalType1() {
        def transactionsDTO = new TransactionsDTO(
                accountId: accounts.id,
                operationTypeId: 1,
                amount: new BigDecimal(10)
        )

        def response = mvc.perform(
                post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionsDTO)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated())
                .andReturn().response
        TransactionsDTO trasactionasDTORetorno = objectMapper.readerFor(TransactionsDTO.class).readValue(response.contentAsString)
        assertNotNull(trasactionasDTORetorno.id)
        assertTrue(trasactionasDTORetorno.amount < 0)
        assertEquals(trasactionasDTORetorno.amount,transactionsDTO.amount * -1)
    }


    @Test
    void transactionals_CreateWithoutAmount() {
        def transactionsDTO = new TransactionsDTO(
                accountId: accounts.id,
                operationTypeId: 1,
                amount: new BigDecimal(0)
        )
        def response = mvc.perform(
                post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionsDTO)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotAcceptable())
                .andReturn().response

        assertEquals(response.errorMessage,"Invalid Transactional: Amount with no funds")
    }

    @Test
    void transactionals_CreateWithoutValues() {
        def transactionsDTO = new TransactionsDTO()
        def response = mvc.perform(
                post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionsDTO)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest())
                .andReturn().response

        assertEquals(response.errorMessage,"Transactions with Required Field is Empty")
    }

    @Test
    void transactionals_CreateWithNegativeAmount(){
        def transactionsDTO = new TransactionsDTO(
                accountId: accounts.id,
                operationTypeId: 1,
                amount: new BigDecimal(-1)
        )
        def response = mvc.perform(
                post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionsDTO)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotAcceptable())
                .andReturn().response

        assertEquals(response.errorMessage,"Invalid Transactional: Amount with no funds")
    }

    @Test
    void transactionals_CreateWithoutValidTransactionalType() {
        def transactionsDTO = new TransactionsDTO(
                accountId: accounts.id,
                operationTypeId: -1,
                amount: new BigDecimal(10)
        )

        def response = mvc.perform(
                post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionsDTO)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound())
                .andReturn().response

        assertEquals(response.errorMessage,"Invalid Operational Types Exception")
    }

    @Test
    def transactionals_CreateWithoutValidAccount() {
        def transactionsDTO = new TransactionsDTO(
                accountId: -1,
                operationTypeId: 1,
                amount: new BigDecimal(10)
        )

        def response = mvc.perform(
                post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionsDTO)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound())
                .andReturn().response
        assertEquals(response.errorMessage,"Invalid Account Exception")
    }
}
