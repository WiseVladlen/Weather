package com.example.weather.utils

import android.content.Context
import com.example.weather.domain.entities.Location
import com.google.gson.Gson
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStreamReader
import com.example.weather.domain.common.Result

object JSONHelper {

    fun read(context: Context, filename: String): Result<List<Location>> {
        return try {
            InputStreamReader(context.assets.open(filename)).let { streamReader ->
                Result.Success(
                    Gson().fromJson(streamReader, Array<Location>::class.java).toList()
                )
            }
        } catch (exception: FileNotFoundException) {
            Result.Error(exception)
        } catch (exception: IOException) {
            Result.Error(exception)
        } catch (exception: Exception) {
            Result.Error(exception)
        }
    }
}