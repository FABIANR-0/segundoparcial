package com.segundoparcial.model

data class User(
    val id: Long = 0,
    val name: String,
    val type: String,
    val description: String,
    val img: String
)
