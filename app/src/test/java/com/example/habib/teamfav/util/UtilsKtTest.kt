package com.example.habib.teamfav.util

import org.junit.Test

import org.junit.Assert.*
import java.text.SimpleDateFormat

class UtilsKtTest {

    @Test
    fun toSimpleString() {
        val date = SimpleDateFormat("MM/dd/yyy")
        val dat = date.parse("02/28/2018")
        assertEquals("Wed, 28 Feb 2018",toSimpleString(dat))
    }
}