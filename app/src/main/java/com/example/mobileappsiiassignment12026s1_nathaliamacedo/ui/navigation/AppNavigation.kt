package com.example.mobileappsiiassignment12026s1_nathaliamacedo.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mobileappsiiassignment12026s1_nathaliamacedo.ui.StudentViewModel
import com.example.mobileappsiiassignment12026s1_nathaliamacedo.ui.screens.AddStudentScreen
import com.example.mobileappsiiassignment12026s1_nathaliamacedo.ui.screens.HomeScreen
import com.example.mobileappsiiassignment12026s1_nathaliamacedo.ui.screens.SearchStudentScreen

object Routes {
    const val HOME = "home"
    const val ADD_STUDENT = "add_student"
    const val SEARCH_STUDENT = "search_student"
}

@Composable
fun AppNavigation(
    viewModel: StudentViewModel,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Routes.HOME
    ) {
        composable(Routes.HOME) {
            HomeScreen(
                onNavigateToAddStudent = { navController.navigate(Routes.ADD_STUDENT) },
                onNavigateToSearchStudent = { navController.navigate(Routes.SEARCH_STUDENT) }
            )
        }

        composable(Routes.ADD_STUDENT) {
            AddStudentScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Routes.SEARCH_STUDENT) {
            SearchStudentScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
