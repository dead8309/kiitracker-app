package com.kiitracker.core.data

import android.util.Log
import androidx.datastore.core.DataStore
import com.kiitracker.core.datastore.proto.UserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject


data class UserDomainPreferences(
    val saturdayRoutineDay: String = "saturday",
)

class KiitrackerPreferencesDataSource @Inject constructor(
    private val dataStore: DataStore<UserPreferences>,
) {
    private val TAG: String = "KiitrackerPreferences"

    val userPreferences: Flow<UserDomainPreferences> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.e(TAG, "Error reading preferences.", exception)
                emit(UserPreferences.getDefaultInstance())
            } else {
                throw exception
            }
        }.map { protoPrefs ->
            mapUserPreferences(protoPrefs)
        }

    suspend fun setSaturdayRoutineDay(day: String) {
        try {
            dataStore.updateData { currentPreferences ->
                currentPreferences.toBuilder()
                    .setSaturdayRoutineOverrideDay(day)
                    .build()
            }
        } catch (e: IOException) {
            Log.e(TAG, "Failed to update Saturday routine day.", e)
        }
    }

    private fun mapUserPreferences(proto: UserPreferences): UserDomainPreferences {
        val saturdayRoutineDay = proto.saturdayRoutineOverrideDay.ifEmpty { "saturday" }
        return UserDomainPreferences(
            saturdayRoutineDay = saturdayRoutineDay,
        )
    }
}