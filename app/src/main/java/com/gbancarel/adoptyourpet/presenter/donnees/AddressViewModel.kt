package com.gbancarel.adoptyourpet.presenter.donnees

data class AddressViewModel (
        val address1: String?,
        val address2: String?,
        val city: String,
        val state: String,
        val postCode: String?,
        val country: String
)