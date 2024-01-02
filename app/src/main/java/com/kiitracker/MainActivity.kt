package com.kiitracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kiitracker.domain.interfaces.Auth
import com.kiitracker.ui.routes.Routes
import com.kiitracker.ui.screens.home.HomeScreen
import com.kiitracker.ui.screens.home.HomeViewModel
import com.kiitracker.ui.screens.settings.SettingsScreen
import com.kiitracker.ui.screens.sign_in.SignInScreen
import com.kiitracker.ui.screens.sign_in.SignInViewModel
import com.kiitracker.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
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
                NavHost(
                    navController = navController,
                    startDestination =
                    if (auth.currentUser != null) Routes.HOME
                    else Routes.SIGN_IN
                ) {

                    composable(Routes.SIGN_IN) {
                        val viewModel by viewModels<SignInViewModel>()
                        val state by viewModel.state.collectAsStateWithLifecycle()

                        LaunchedEffect(key1 = state.isSignInSuccessful) {
                            if (state.isSignInSuccessful) {
                                navController.navigate(Routes.HOME) {
                                    popUpTo(Routes.SIGN_IN) {
                                        inclusive = true
                                    }
                                }
                                viewModel.resetSignInState()
                            }
                        }

                        SignInScreen(
                            state = state,
                            onSignInClick = {
                                viewModel.triggerLoadingState()
                                lifecycleScope.launch {
                                    val result = auth.login(this@MainActivity)
                                    viewModel.onSignInResult(result)
                                    viewModel.triggerLoadingState()
                                }
                            }
                        )
                    }

                    composable(Routes.HOME) {
                        val viewModel by viewModels<HomeViewModel>()
                        val state by viewModel.state.collectAsStateWithLifecycle()
                        HomeScreen(
                            user = auth.currentUser,
                            routine = state.routine,
                            onNavigateToSettings = {
                                navController.navigate(Routes.SETTINGS)
                            }
                        )
                    }

                    composable(Routes.SETTINGS) {
                        SettingsScreen(
                            user = auth.currentUser,
                            onNavigateBack = {
                                navController.popBackStack()
                            },
                            onLogoutClicked = {
                                lifecycleScope.launch {
                                    auth.logout()
                                    navController.navigate(Routes.SIGN_IN) {
                                        popUpTo(Routes.HOME) {
                                            inclusive = true
                                        }
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}
