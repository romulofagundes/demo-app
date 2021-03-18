package io.prismo.domain

import com.fasterxml.jackson.annotation.JsonProperty

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
class Transactions {

    @Id
    @Column(name="Transaction_ID")
    @JsonProperty("Transaction_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="Account_ID",nullable = false)
    Accounts accounts

    @OneToOne
    @JoinColumn(name="OperationType_ID",nullable = false)
    OperationalTypes operationalTypes

    @Column(name="Amount")
    @JsonProperty("Amount")
    BigDecimal amount

    @Column(name="EventDate",insertable = false, updatable = false)
    @JsonProperty("EventDate")
    Date eventDate = new Date()
}
