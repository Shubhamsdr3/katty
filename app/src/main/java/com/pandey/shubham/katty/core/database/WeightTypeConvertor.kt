package com.pandey.shubham.katty.core.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.pandey.shubham.katty.core.utils.fromJson
import com.pandey.shubham.katty.feature.feed.data.dtos.Weight


/**
 * Created by shubhampandey
 */
class WeightTypeConvertor {

    @TypeConverter
    fun fromWeight(weight: Weight?): String? {
        return Gson().toJson(weight)
    }

    @TypeConverter
    fun toWeight(json: String?): Weight? {
        return Gson().fromJson(json)
    }
}