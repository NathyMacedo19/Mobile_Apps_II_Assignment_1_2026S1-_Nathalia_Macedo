Student Registration Portal

Mobile Apps II – Assignment 1 (20%) Nathalia Macedo · BSc in Computing · Dorset College Dublin · Semester 1, 2026

An Android app to register students and search for them by ID. Built with Kotlin, Jetpack Compose and a Room database.

✨ Features
Home page to navigate to registration or search
Register a student with ID, name, course and year of study
Built-in validation: year must be between 1 and 4, all fields are required
Unique student IDs: a duplicate ID is rejected instead of overwriting an existing record
Search by student ID to show the student's name, course and year, or a clear error message if no student is found
Data is saved locally with Room, so records remain after the app is closed
Custom purple theme and centre-aligned top bars on all screens

🏗️ Architecture
The app follows the MVVM pattern (Model – View – ViewModel):

UI (Compose screens)  →  StudentViewModel  →  StudentRepository  →  StudentDao  →  Room database
        ↑                        │
        └──── StateFlow ─────────┘   (screens observe state and update automatically)
Folder	Contents
data/	Student (entity), StudentDao, AppDatabase, StudentRepository
ui/	StudentViewModel (state + validation)
ui/screens/	HomeScreen, AddStudentScreen, SearchStudentScreen
ui/navigation/	AppNavigation (NavHost and routes)
ui/theme/	Colours, typography and theme

🛠️ Tech Stack
Kotlin · Jetpack Compose · Material 3
Room (with KSP) for local storage
ViewModel + StateFlow for UI state
Navigation Compose

▶️ How to Run
Clone the repository:
   git clone https://github.com/NathyMacedo19/Mobile_Apps_II_Assignment_1_2026S1-_Nathalia_Macedo.git
Open the project in Android Studio and let Gradle sync.
Select an emulator or device (Android 9 / API 28 or higher).
Click Run ▶.

🧪 Quick Test
Register a student, e.g. S1, Ana, Computing, year 2.
Try year 5: an error is shown and nothing is saved.
Try registering S1 again: a duplicate ID error is shown.
Search S1: the student's details appear.
Search S999: a "not found" message appears.

📄 Report & AI Declaration
The full report, including design considerations, test results, references, the answers to Section 2 and the AI usage declaration, is in ASSIGNMENT_REPORT.md.
