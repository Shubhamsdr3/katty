package com.pandey.shubham.katty.core.database

import android.R.attr.value
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pandey.shubham.katty.feature.feed.data.dtos.Weight
import java.lang.reflect.Type


/**
 * Created by shubhampandey
 */
class WeightTypeConvertor {

    @TypeConverter
    fun fromWeight(weight: Weight?): String? {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toWeight(json: String?): Weight? {
        val weightType: Type = object : TypeToken<Weight?>() {}.type
        return Gson().fromJson(json, weightType)
    }
}