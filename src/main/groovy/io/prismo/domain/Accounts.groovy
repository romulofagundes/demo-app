package io.prismo.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table
class Accounts {

    @Id
    @Column(name="Account_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id

    @Column(name="Document_Number")
    String documentNumber
}
