# Mobile Apps II - Assignment 1 Report (20%)

**Student Name:** Nathalia Macedo  
**Course:** Mobile Apps II  
**Institution:** Dorset College Year 3  

---

## Section 1: Application Implementation Overview

### 1. App Architecture & Features
The application is built using modern Android development practices:
- **Language & Framework:** Kotlin, Jetpack Compose, Material 3.
- **Database:** Room Database (`AppDatabase`, `StudentDao`, `Student`).
- **Architecture Pattern:** MVVM (Model-View-ViewModel) with Unidirectional Data Flow (`StateFlow`).
- **Navigation:** Jetpack Compose Navigation (`NavHost`, `NavController`).

### 2. Pages Implemented
1. **Home Page (`HomeScreen.kt`)**:
   - Navigation Hub allowing quick access to student registration and student search.
2. **Registration Page (`AddStudentScreen.kt`)**:
   - Form for entering Student ID, Full Name, Course Name, and Year of Study.
   - **Inbuilt Data Validation**: Validates that Year is a valid number between **1 and 4**. Displays clear field error feedback if invalid.
   - Saves record directly into Room database.
3. **Student Search Page (`SearchStudentScreen.kt`)**:
   - Search input field for Student ID.
   - Displays Student Name, Course, Year, and ID when found.
   - Displays a prominent error message when student ID is not found in the database.

---

## Section 2: Theory Questions & Answers

### Question 1 (3 Marks)
**What are the distinct advantages of using LiveData in Android development? Give a hypothetical example to demonstrate your answer.**

#### Answer:
LiveData is an observable data holder class that is lifecycle-aware. Key advantages include:

1. **Lifecycle Awareness**: LiveData only updates observers that are in an active lifecycle state (`STARTED` or `RESUMED`). This eliminates app crashes caused by stopped Activities or Fragments attempting to update UI.
2. **Prevention of Memory Leaks**: Observers are bound to a `LifecycleOwner` and automatically clean up their references when the owner is destroyed.
3. **Up-to-Date Data Handling**: If an Activity or Fragment becomes active again (e.g., after screen rotation or returning from background), it immediately receives the latest data value.
4. **No Manual Lifecycle Management**: Developers do not need to manually subscribe or unsubscribe in `onStart()` or `onStop()`.

#### Hypothetical Example:
```kotlin
// 1. ViewModel holds LiveData
class StudentViewModel : ViewModel() {
    private val _student = MutableLiveData<Student>()
    val student: LiveData<Student> = _student

    fun loadStudent(id: String) {
        viewModelScope.launch {
            _student.value = repository.getStudentById(id)
        }
    }
}

// 2. Activity observes LiveData bound to its lifecycle
class StudentActivity : AppCompatActivity() {
    private val viewModel: StudentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student)

        // Observe LiveData with 'this' as LifecycleOwner
        viewModel.student.observe(this) { student ->
            // UI updates automatically ONLY when Activity is active.
            // If the user leaves the screen, observers are automatically unsubscribed.
            findViewById<TextView>(R.id.txtName).text = student.name
            findViewById<TextView>(R.id.txtCourse).text = student.course
        }
    }
}
```

---

### Question 2 (3 Marks)
**Propose two different means of navigating with data, outlining the distinct advantages of either.**

#### Answer:

#### Method 1: Jetpack Navigation with Route Arguments / Safe Args
- **Mechanism**: Passing primary keys or simple parameters through navigation route URLs (e.g., `student_detail/{studentId}`) or Safe Args bundles.
- **Distinct Advantages**:
  1. **Centralized Graph & Type Safety**: Route arguments provide compile-time safety and clear contract definitions between destinations.
  2. **Native Deep Linking**: URLs and arguments easily map to system deep links or notifications.
  3. **Automatic Back-stack & Lifecycle Handling**: Navigation component handles back-stack management and screen transition animations cleanly.

#### Method 2: Shared / Scoped ViewModel Data Flow
- **Mechanism**: Storing selected or active data in a Shared/Scoped ViewModel or Repository, where Screen A updates the state (e.g. `selectedStudentId`), and Screen B observes that state upon navigation.
- **Distinct Advantages**:
  1. **Avoids Transaction Size Limits**: Prevents `TransactionTooLargeException` caused by trying to pass large or complex serializable objects via intent/navigation bundles.
  2. **Single Source of Truth**: Ensures data consistency because Screen B reads live, up-to-date data directly from the Repository/Room DB rather than stale cached parameters.
  3. **Lightweight Routes**: Route definitions remain simple without bloated parameter lists.
