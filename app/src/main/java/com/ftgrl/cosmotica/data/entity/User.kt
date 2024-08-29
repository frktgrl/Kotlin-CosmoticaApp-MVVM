package com.ftgrl.cosmotica.data.entity

import java.io.Serializable

data class User(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val telephone: String = "",
    val address: String = "",
    val favorite: List<Int> = emptyList()
) : Serializable
