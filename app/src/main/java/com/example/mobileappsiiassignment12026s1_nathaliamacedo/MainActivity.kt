package com.example.mobileappsiiassignment12026s1_nathaliamacedo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.mobileappsiiassignment12026s1_nathaliamacedo.ui.StudentViewModel
import com.example.mobileappsiiassignment12026s1_nathaliamacedo.ui.navigation.AppNavigation
import com.example.mobileappsiiassignment12026s1_nathaliamacedo.ui.theme.MobileAppsIIAssignment12026S1NathaliaMacedoTheme

class MainActivity : ComponentActivity() {

    private val viewModel: StudentViewModel by viewModels {
        StudentViewModel.Factory(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MobileAppsIIAssignment12026S1NathaliaMacedoTheme {
                AppNavigation(viewModel = viewModel)
            }
        }
    }
}
