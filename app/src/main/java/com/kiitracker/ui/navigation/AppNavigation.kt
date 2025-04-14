package com.kiitracker.ui.navigation

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kiitracker.domain.interfaces.Auth
import com.kiitracker.ui.screens.home.HomeScreen
import com.kiitracker.ui.screens.home.HomeViewModel
import com.kiitracker.ui.screens.settings.SettingsScreen
import com.kiitracker.ui.screens.settings.SettingsViewModel
import com.kiitracker.ui.screens.sign_in.SignInScreen
import com.kiitracker.ui.screens.sign_in.SignInViewModel
import kotlinx.serialization.Serializable

@Serializable
data object SignInRoute

@Serializable
data object HomeRoute

@Serializable
data object SettingsRoute

@SuppressLint("ContextCastToActivity")
@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    auth: Auth,
    startDestination: Any
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        composable<SignInRoute> {
            val viewModel: SignInViewModel = hiltViewModel()
            val state by viewModel.state.collectAsStateWithLifecycle()
            val activity = LocalContext.current as ComponentActivity

            LaunchedEffect(key1 = state.isSignInSuccessful) {
                if (state.isSignInSuccessful) {
                    navController.navigate(HomeRoute) {
                        popUpTo<SignInRoute> {
                            inclusive = true
                        }
                    }
                    viewModel.resetSignInState()
                }
            }

            SignInScreen(
                state = state,
                onSignInClick = {
                    viewModel.login(activity)
                }
            )
        }

        composable<HomeRoute> {
            val viewModel: HomeViewModel = hiltViewModel()
            val state by viewModel.state.collectAsStateWithLifecycle()
            HomeScreen(
                user = auth.currentUser,
                state = state,
                onNavigateToSettings = {
                    navController.navigate(SettingsRoute)
                }
            )
        }

        composable<SettingsRoute> {
            val viewModel: SettingsViewModel =  hiltViewModel()
            val state by viewModel.state.collectAsStateWithLifecycle()
            SettingsScreen(
                state = state,
                user = auth.currentUser,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onLogoutClicked = {
                    viewModel.logout {
                        navController.navigate(SignInRoute) {
                            popUpTo(HomeRoute) {
                                inclusive = true
                            }
                        }
                    }
                },
                onSaturdayPrefChanged = viewModel::setSaturdayPreference
            )
        }
    }
}