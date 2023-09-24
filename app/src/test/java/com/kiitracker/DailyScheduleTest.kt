package com.kiitracker

import com.kiitracker.data.A16Routine
import com.kiitracker.domain.DailySchedule
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.Test


class DailyScheduleTest {
    @Test
    fun `Test Daily Routine`() {/*
        val routine = Routine(Section.A16, A16Routine.weeklyRoutine, "CLA5")
        routine.buildRoutine()*/
        val json = Json { prettyPrint = true }
        //println(A16Routine.weeklyRoutine)
        println(json.encodeToString(A16Routine.weeklyRoutine))
    }
}