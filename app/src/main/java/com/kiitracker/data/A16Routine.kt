package com.kiitracker.data

import com.kiitracker.domain.Campus
import com.kiitracker.domain.Course
import com.kiitracker.domain.DailySchedule
import com.kiitracker.domain.TimeSlot

object A16Routine {
    private val monday = DailySchedule(
        classRoom = "CLA3",
        courses = listOf(
            Course.Phy(Campus.Campus12, TimeSlot.Slot10),
            Course.ScLs(Campus.Campus12, TimeSlot.Slot11),
            Course.EVS(Campus.Campus12, TimeSlot.Slot12),
            Course.DE_LA(Campus.Campus12, TimeSlot.Slot13),
            Course.DE_LA(Campus.Campus12, TimeSlot.Slot14),
        ),
        day = "Mon"
    )
    private val tuesday = DailySchedule(
        classRoom = "C-14",
        courses = listOf(
            // BME8
            Course.Elective.BME(Campus.Campus12, TimeSlot.Slot1, "CL16"),
            Course.Phy(Campus.Campus3, TimeSlot.Slot10),
            Course.Lab.PL_T_Programming(Campus.Campus15, TimeSlot.Custom("11.00", "2.00")),
        ),
        day = "Tue"
    )
    private val wednesday = DailySchedule(
        classRoom = "C-13",
        courses = listOf(
            // OT5
            Course.Elective.OT(Campus.Campus3, TimeSlot.Slot1, "C-14"),
            Course.Lab.PhyLab(TimeSlot.Custom("10:00", "12:00")),
            Course.EVS(Campus.Campus3, TimeSlot.Slot13),
            Course.DE_LA(Campus.Campus3, TimeSlot.Slot14),
        ),
        day = "Wed"
    )
    private val thursday = DailySchedule(
        classRoom = "C-14",
        courses = listOf(
            // BME8
            Course.Elective.BME(Campus.Campus12, TimeSlot.Slot1, "CL4"),
            Course.ScLs(Campus.Campus3, TimeSlot.Slot10),
            Course.Phy(Campus.Campus3, TimeSlot.Slot11),
            Course.Lab.ED_GRAPHICS(TimeSlot.Custom("12.00", "2.00"))
        ),
        day = "Thu"
    )
    private val friday = DailySchedule(
        classRoom = "CL16",
        courses = listOf(
            // OT5
            Course.Elective.OT(Campus.Campus12, TimeSlot.Slot1, "CLA5"),
            Course.DE_LA(Campus.Campus12, TimeSlot.Slot10),
            Course.Lab.PL_T_Programming(Campus.Campus15, TimeSlot.Custom("11.00", "2.00")),
        ),
        day = "Fri"
    )
    val weeklyRoutine = listOf(
        monday, tuesday, wednesday, thursday, friday
    )
}