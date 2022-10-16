package com.hakan.happybirthdayapp.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var name: String,
    var surname: String,
    var date: String,
    var departmentText: String,
    var departmentPosition: Int,
    var descriptor: String,
) : Parcelable


