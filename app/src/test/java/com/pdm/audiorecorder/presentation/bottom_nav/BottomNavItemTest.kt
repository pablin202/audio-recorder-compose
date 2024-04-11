package com.pdm.audiorecorder.presentation.bottom_nav

import org.junit.Assert.*
import org.junit.Test
import com.pdm.audiorecorder.R

class BottomNavItemTest {
    @Test
    fun `Home item has correct properties`() {
        val home = BottomNavItem.Home
        assertEquals("home", home.route)
        assertEquals(R.drawable.record_voice_over, home.icon)
        assertEquals("Home", home.title)
    }

    @Test
    fun `List item has correct properties`() {
        val list = BottomNavItem.List
        assertEquals("list", list.route)
        assertEquals(R.drawable.format_list_bulleted, list.icon)
        assertEquals("Records", list.title)
    }
}