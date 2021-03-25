package io.prismo.domain

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table
@ToString(includePackage = false,includeNames = true)
@EqualsAndHashCode
class Accounts {

    public static BigDecimal INITIAL_CREDIT_LIMIT = new BigDecimal("500")

    @Id
    @Column(name="account_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("account_id")
    Long id

    @Column(name="document_number",nullable = false)
    @JsonProperty(value="document_number",required = true)
    String documentNumber


    @Column(name="available_limit_credit",nullable = false)
    @JsonProperty(value="available_limit_credit")
    BigDecimal availableLimitCredit = INITIAL_CREDIT_LIMIT
}
