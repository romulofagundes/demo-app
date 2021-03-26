package io.pismo.domain

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table
@ToString(includePackage = false,includeNames = true)
@EqualsAndHashCode
class OperationalTypes {

    @Id
    @Column(name="operationtype_id")
    @JsonProperty("operationtype_id")
    Long id

    @Column(name="description0")
    @JsonProperty("description0")
    String description
}
