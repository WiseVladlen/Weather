package com.example.weather.data.db

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.weather.data.db.dao.LocationDao
import com.example.weather.data.db.dao.WeatherDao
import com.example.weather.data.db.entities.LocationEntity
import com.example.weather.data.db.entities.WeatherEntity
import com.example.weather.data.mappers.LocationToLocationEntityMapper
import com.example.weather.domain.common.Result
import com.example.weather.utils.JSONHelper
import com.example.weather.utils.CITY_LIST_FILENAME
import com.example.weather.utils.CURRENT_LOCATION_FILENAME
import com.example.weather.utils.DATABASE_NAME
import kotlinx.coroutines.*

private val TAG = AppDatabase::class.java.simpleName

@Database(
    entities = [LocationEntity::class, WeatherEntity::class],
    version = 1,
    exportSchema = false,
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun locationDao(): LocationDao
    abstract fun weatherDao(): WeatherDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                buildDatabase(context).also { database ->
                    INSTANCE = database
                }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .addCallback(RoomDatabaseCallback(context))
                .build()
        }
    }

    private class RoomDatabaseCallback(private val context: Context) : Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            INSTANCE?.let { instance ->
                CoroutineScope(Dispatchers.IO + SupervisorJob()).launch {
                    try {
                        listOf(CITY_LIST_FILENAME, CURRENT_LOCATION_FILENAME).forEach { filename ->
                            preinitialize(filename, instance)
                        }
                    } catch (exception: Exception) {
                        exception.message?.let { Log.e(TAG, it) }
                    } finally {
                        cancel()
                    }
                }
            }
        }

        private suspend fun preinitialize(filename: String, instance: AppDatabase) {
            when (val result = JSONHelper.read(context, filename)) {
                is Result.Success -> {
                    LocationToLocationEntityMapper().let { mapper ->
                        instance.locationDao().insertLocations(
                            result.data.map { location ->
                                mapper.mapEntity(location)
                            }
                        )
                    }
                }
                is Result.Error -> result.exception.message?.let { Log.e(TAG, it) }
            }
        }
    }
}