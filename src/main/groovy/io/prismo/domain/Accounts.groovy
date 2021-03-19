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

    @Id
    @Column(name="account_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("account_id")
    Long id

    @Column(name="document_number")
    @JsonProperty("document_number")
    String documentNumber
}
