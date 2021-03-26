package io.pismo.domain

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToOne
import javax.persistence.Table

@Entity
@Table
@ToString(includePackage = false,includeNames = true)
@EqualsAndHashCode
class Transactions {

    @Id
    @Column(name="transaction_id")
    @JsonProperty("transaction_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="account_id",nullable = false)
    Accounts accounts

    @OneToOne
    @JoinColumn(name="operationtype_id",nullable = false)
    OperationalTypes operationalTypes

    @Column(name="amount")
    @JsonProperty("amount")
    BigDecimal amount

    @Column(name="eventdate",insertable = false, updatable = false)
    @JsonProperty("eventdate")
    Date eventDate = new Date()
}
