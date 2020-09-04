package com.gbancarel.adoptyourpet.interactor.data.listAnimal

data class Address(
    val address1: String?,
    val address2: String?,
    val city: String,
    val state: String,
    val postCode: String?,
    val country: String
) {
    fun printable(): String? {
        if (address1 != null) {
            return listOf(address1, city, state, country).joinToString(", ")
        }
        if (address2 != null) {
            return listOf(address2, city, state, country).joinToString(", ")
        }
        return null
    }
}