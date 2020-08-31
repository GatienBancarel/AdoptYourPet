package com.gbancarel.adoptyourpet.repository.local

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "animals")
data class PetAnimalLocal(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val type: String,
    val breed: String?,
    val color: String?,
    val age: String?,
    val gender: String?,
    val size: String?,
    @Embedded val environment: EnvironmentLocal?,
    val name: String,
    val description: String?,
    @Embedded val photo: PhotoLocal?,
    @Embedded val contact: ContactLocal?
)

data class EnvironmentLocal(
    val children: Boolean,
    val dog: Boolean,
    val cat: Boolean
)

data class PhotoLocal(
    val small: String,
    val medium: String,
    val large: String,
    val full: String
)

data class ContactLocal(
    val email: String?,
    val phone: String?,
    @Embedded val address: AddressLocal?
)

data class AddressLocal(
    val address1: String?,
    val address2: String?,
    val city: String,
    val state: String,
    val postCode: String?,
    val country: String
)