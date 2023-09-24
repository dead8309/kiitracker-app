package com.kiitracker.domain

import kotlinx.serialization.Serializable

@Serializable
sealed class TimeSlot(
    open val startTime: String,
    open val endTime: String
) {
    @Serializable
    /** "8.00" - "9.00" */
    data object Slot1 : TimeSlot("8.00", "9.00")

    @Serializable
    /** "9.00", "10.00" */
    data object Slot2 : TimeSlot("9.00", "10.00")

    @Serializable
    /**"10.00" - "11.00" */
    data object Slot3 : TimeSlot("10.00", "11.00")

    @Serializable
    /** "11.00" - "12.00" */
    data object Slot4 : TimeSlot("11.00", "12.00")

    @Serializable
    /** "12.00" - "1.00" */
    data object Slot5 : TimeSlot("12.00", "1.00")

    @Serializable
    /** "1.00" - "2.00" */
    data object Slot6 : TimeSlot("1.00", "2.00")

    @Serializable
    /** "3.00" - "4.00" */
    data object Slot7 : TimeSlot("3.00", "4.00")

    @Serializable
    /** "4.00" - "5.00" */
    data object Slot8 : TimeSlot("4.00", "5.00")

    @Serializable
    /** "5.00" - "6.00" */
    data object Slot9 : TimeSlot("5.00", "6.00")

    @Serializable
    /** "9.20" - "10.20" */
    data object Slot10 : TimeSlot("9.20", "10.20")

    @Serializable
    /** "10.20" - "11.20" */
    data object Slot11 : TimeSlot("10.20", "11.20")

    @Serializable
    /** "11.20" - "12.20" */
    data object Slot12 : TimeSlot("11.20", "12.20")

    @Serializable
    /** "12.20" - "1.20" */
    data object Slot13 : TimeSlot("12.20", "1.20")

    @Serializable
    /** "1.20" - "2.20" */
    data object Slot14 : TimeSlot("1.20", "2.20")

    @Serializable
    // Custom timeslot class
    class Custom(val start: String, val end: String) : TimeSlot(start, end)
}
