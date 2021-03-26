package io.pismo.controller

import com.fasterxml.jackson.databind.ObjectMapper
import io.pismo.domain.Accounts
import io.pismo.service.AccountsService
import io.pismo.test.GeneralTest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import static org.junit.jupiter.api.Assertions.*


class AccountsControllerTest extends GeneralTest{

    @Autowired
    MockMvc mvc

    @Autowired
    ObjectMapper objectMapper

    @Autowired
    AccountsService accountsService

    @Test
    void account_CreateWithDocumentNumber(){
        def account = new Accounts(documentNumber: UUID.randomUUID().toString())

        def response = mvc.perform(
                post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(account)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated())
                .andReturn().response

        Accounts accountsReturn = objectMapper.readerFor(Accounts.class).readValue(response.contentAsString)
        assertEquals(accountsReturn.documentNumber,account.documentNumber)
        assertNotNull(accountsReturn.id)
    }

    @Test
    void account_ValidateCreateWithoutDocumentNumber(){
        def response = mvc.perform(
                post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest())
                .andReturn().response

        assertEquals(response.errorMessage,"Document Number is Required")
    }

    @Test
    void account_CreateAccountInServiceAndRecoveredByRest(){
        def account = accountsService.create(new Accounts(documentNumber: UUID.randomUUID().toString()))

        def response = mvc.perform(get("/accounts/${account.id}"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn().response

        Accounts accountsReturn = objectMapper.readerFor(Accounts.class).readValue(response.contentAsString)
        assertEquals(accountsReturn.documentNumber,account.documentNumber)
        assertEquals(accountsReturn.id,account.id)
    }

    @Test
    void account_RecoveryInvalidAccount(){
        def response = mvc.perform(get("/accounts/${Integer.MAX_VALUE}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound())
                .andReturn().response
        assertEquals(response.errorMessage,"Account Not Found")
    }

}
