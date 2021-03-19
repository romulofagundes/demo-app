package io.prismo.controller

import com.fasterxml.jackson.databind.ObjectMapper
import io.prismo.domain.Accounts
import io.prismo.service.AccountsService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Title

@Title("Testando AccountController")
@Narrative("Esta especificação valida as operações relacinadas ao AccountController.")
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AccountsControllerSpec extends Specification{

    @Autowired
    MockMvc mvc

    @Autowired
    ObjectMapper objectMapper

    @Autowired
    AccountsService accountsService

    @Test
    def "validando se o numero do documento esta de acordo com o enviado"(){
        given: "criando o objeto accounts"
        def account = new Accounts(documentNumber: "12345678900")

        when: "realiza o envio do account"
        def response = mvc.perform(
                post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(account))).andReturn().response
        then: "validando retorno e documento criado"
        response.status == HttpStatus.CREATED.value()

        and:
        Accounts accountsRetorno = objectMapper.readerFor(Accounts.class).readValue(response.contentAsString)
        accountsRetorno.documentNumber == account.documentNumber
        accountsRetorno.id != null
    }

    @Test
    def "validando a necessidade de colocar o document number"(){
        when: "realiza o envio do account vazio"
        def response = mvc.perform(
                post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")).andReturn().response
        then: "validando retorno e documento com erro"
        response.status == HttpStatus.BAD_REQUEST.value()

        and:
        response.errorMessage == "Document Number is Required"
    }

    @Test
    def "criado a account number, pelo service e recuperando via get"(){
        when: "criando account"
        def account = accountsService.create(new Accounts(documentNumber: "00987654321"))

        then: "recuperando objeto criado"
        def response = mvc.perform(get("/accounts/${account.id}")).andReturn().response

        then: "validando status e objeto recuperado recuperado"
        response.status == HttpStatus.OK.value()

        and:
        Accounts accountsRetorno = objectMapper.readerFor(Accounts.class).readValue(response.contentAsString)
        accountsRetorno.documentNumber == account.documentNumber
        accountsRetorno.id == account.id

    }

}
