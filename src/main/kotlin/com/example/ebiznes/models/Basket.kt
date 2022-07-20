package com.example.ebiznes.models

import javax.persistence.*


@Entity
class Basket {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0

    @ManyToMany
    var products: List<Product>? = null

    @OneToOne
    var users: Users? = null

}