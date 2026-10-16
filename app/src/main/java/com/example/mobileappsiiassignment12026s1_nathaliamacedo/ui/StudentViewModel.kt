package com.example.mobileappsiiassignment12026s1_nathaliamacedo.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mobileappsiiassignment12026s1_nathaliamacedo.data.AppDatabase
import com.example.mobileappsiiassignment12026s1_nathaliamacedo.data.Student
import com.example.mobileappsiiassignment12026s1_nathaliamacedo.data.StudentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class RegistrationUiState(
    val studentId: String = "",
    val name: String = "",
    val course: String = "",
    val year: String = "",
    val studentIdError: String? = null,
    val nameError: String? = null,
    val courseError: String? = null,
    val yearError: String? = null,
    val isSuccess: Boolean = false,
    val message: String? = null
)

data class SearchUiState(
    val searchQuery: String = "",
    val searchedStudent: Student? = null,
    val searchError: String? = null,
    val isSearching: Boolean = false,
    val hasSearched: Boolean = false
)

class StudentViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: StudentRepository

    init {
        val studentDao = AppDatabase.getDatabase(application).studentDao()
        repository = StudentRepository(studentDao)
    }

    // Registration UI State
    private val _registrationUiState = MutableStateFlow(RegistrationUiState())
    val registrationUiState: StateFlow<RegistrationUiState> = _registrationUiState.asStateFlow()

    // Search UI State
    private val _searchUiState = MutableStateFlow(SearchUiState())
    val searchUiState: StateFlow<SearchUiState> = _searchUiState.asStateFlow()

    // Registration Form Setters
    fun onStudentIdChange(newValue: String) {
        _registrationUiState.value = _registrationUiState.value.copy(
            studentId = newValue,
            studentIdError = null,
            isSuccess = false,
            message = null
        )
    }

    fun onNameChange(newValue: String) {
        _registrationUiState.value = _registrationUiState.value.copy(
            name = newValue,
            nameError = null,
            isSuccess = false,
            message = null
        )
    }

    fun onCourseChange(newValue: String) {
        _registrationUiState.value = _registrationUiState.value.copy(
            course = newValue,
            courseError = null,
            isSuccess = false,
            message = null
        )
    }

    fun onYearChange(newValue: String) {
        _registrationUiState.value = _registrationUiState.value.copy(
            year = newValue,
            yearError = null,
            isSuccess = false,
            message = null
        )
    }

    // Register Student
    fun registerStudent() {
        val state = _registrationUiState.value
        val studentIdClean = state.studentId.trim()
        val nameClean = state.name.trim()
        val courseClean = state.course.trim()
        val yearClean = state.year.trim()

        var isValid = true
        var studentIdErr: String? = null
        var nameErr: String? = null
        var courseErr: String? = null
        var yearErr: String? = null

        if (studentIdClean.isEmpty()) {
            studentIdErr = "Student ID cannot be empty"
            isValid = false
        }

        if (nameClean.isEmpty()) {
            nameErr = "Student name cannot be empty"
            isValid = false
        }

        if (courseClean.isEmpty()) {
            courseErr = "Course cannot be empty"
            isValid = false
        }

        // Data Validation for Year (1-4 years)
        val yearInt = yearClean.toIntOrNull()
        if (yearInt == null || yearInt !in 1..4) {
            yearErr = "Year must be a valid number between 1 and 4"
            isValid = false
        }

        if (!isValid) {
            _registrationUiState.value = state.copy(
                studentIdError = studentIdErr,
                nameError = nameErr,
                courseError = courseErr,
                yearError = yearErr,
                isSuccess = false,
                message = "Please fix the validation errors above."
            )
            return
        }

        val newStudent = Student(
            studentId = studentIdClean,
            name = nameClean,
            course = courseClean,
            year = yearInt!!
        )

        viewModelScope.launch {
            try {
                repository.insertStudent(newStudent)
                _registrationUiState.value = RegistrationUiState(
                    isSuccess = true,
                    message = "Student '$nameClean' (ID: $studentIdClean) successfully registered!"
                )
            } catch (e: Exception) {
                _registrationUiState.value = state.copy(
                    isSuccess = false,
                    message = "A student with ID '\$studentIdClean' already exists." // AI-assisted (Claude, 15/10/2026): clearer error message for duplicate IDs
                )
            }
        }
    }

    fun resetRegistrationForm() {
        _registrationUiState.value = RegistrationUiState()
    }

    // Search Setters & Actions
    fun onSearchQueryChange(newValue: String) {
        _searchUiState.value = _searchUiState.value.copy(
            searchQuery = newValue,
            searchError = null
        )
    }

    fun searchStudent() {
        val query = _searchUiState.value.searchQuery.trim()
        if (query.isEmpty()) {
            _searchUiState.value = _searchUiState.value.copy(
                searchedStudent = null,
                searchError = "Please enter a Student ID to search",
                hasSearched = true
            )
            return
        }

        _searchUiState.value = _searchUiState.value.copy(
            isSearching = true,
            searchError = null,
            hasSearched = true
        )

        viewModelScope.launch {
            val student = repository.getStudentById(query)
            if (student != null) {
                _searchUiState.value = _searchUiState.value.copy(
                    searchedStudent = student,
                    searchError = null,
                    isSearching = false
                )
            } else {
                _searchUiState.value = _searchUiState.value.copy(
                    searchedStudent = null,
                    searchError = "No student found with ID '$query' in database.",
                    isSearching = false
                )
            }
        }
    }

    fun clearSearch() {
        _searchUiState.value = SearchUiState()
    }

    class Factory(private val application: Application) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(StudentViewModel::class.java)) {
                return StudentViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
