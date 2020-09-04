package com.gbancarel.adoptyourpet.interactor.data.listAnimal

import org.junit.Test
import junit.framework.Assert.assertEquals

class AddressTest {

    @Test
    fun testWhenAddress1NotNull() {
        // GIVEN
        val address = Address(
            "address1",
            null,
            "Washington",
            "State",
            null,
            "US"
        )

        // WHEN - THEN
        assertEquals(address.printable(), "address1, Washington, State, US")
    }

    @Test
    fun testWhenAddress2NotNull() {
        // GIVEN
        val address = Address(
            null,
            "address2",
            "Washington",
            "State",
            null,
            "US"
        )

        // WHEN - THEN
        assertEquals(address.printable(), "address2, Washington, State, US")
    }

    @Test
    fun testWhenAddressNull() {
        // GIVEN
        val address = Address(
            null,
            null,
            "Washington",
            "State",
            null,
            "US"
        )

        // WHEN - THEN
        assertEquals(address.printable(), null)
    }

}