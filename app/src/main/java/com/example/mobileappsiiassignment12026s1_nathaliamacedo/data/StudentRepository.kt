package com.example.mobileappsiiassignment12026s1_nathaliamacedo.data

import kotlinx.coroutines.flow.Flow

class StudentRepository(private val studentDao: StudentDao) {

    suspend fun insertStudent(student: Student) {
        studentDao.insertStudent(student)
    }

    suspend fun getStudentById(studentId: String): Student? {
        return studentDao.getStudentById(studentId)
    }

    fun getAllStudents(): Flow<List<Student>> {
        return studentDao.getAllStudents()
    }
}
