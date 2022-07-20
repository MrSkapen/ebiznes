package com.example.ebiznes.models

import javax.persistence.*


@Entity
class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0

    @Column
    var name = ""

    @Column
    var price = ""

    @Column
    var urlImage = ""

    @ManyToOne
    var category: Category? = null

    @Column
    var amount: Int = 0
}
