package com.example.ebiznes.models

import javax.persistence.*

@Entity
class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0

    @Column
    var name = ""


}