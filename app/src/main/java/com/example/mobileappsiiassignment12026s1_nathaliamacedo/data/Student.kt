package com.example.mobileappsiiassignment12026s1_nathaliamacedo.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students")
data class Student(
    @PrimaryKey
    val studentId: String,
    val name: String,
    val course: String,
    val year: Int
)
