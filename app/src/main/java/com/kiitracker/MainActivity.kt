package com.kiitracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.kiitracker.domain.interfaces.Auth
import com.kiitracker.ui.navigation.AppNavHost
import com.kiitracker.ui.navigation.HomeRoute
import com.kiitracker.ui.navigation.SignInRoute
import com.kiitracker.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var auth: Auth

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                val navController = rememberNavController()
                val startDestination: Any = if (auth.currentUser != null) HomeRoute else SignInRoute
                AppNavHost(
                    navController = navController,
                    auth = auth,
                    startDestination = startDestination
                )
            }
        }
    }
}
