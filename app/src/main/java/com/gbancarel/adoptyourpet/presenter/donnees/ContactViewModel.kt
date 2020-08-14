package com.gbancarel.adoptyourpet.presenter.donnees

import com.gbancarel.adoptyourpet.interactor.donnes.Adress

data class ContactViewModel (
        val email: String?,
        val phone: String?,
        val address: Adress?
)