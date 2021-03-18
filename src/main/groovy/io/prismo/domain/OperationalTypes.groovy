package io.prismo.domain

import com.fasterxml.jackson.annotation.JsonProperty

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table
class OperationalTypes {

    @Id
    @Column(name="OperationType_ID")
    @JsonProperty("OperationType_ID")
    Long id

    @JsonProperty("Description0")
    @Column(name="Description0")
    String description
}
