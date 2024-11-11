package com.ddm.steps.firebase.readtogether

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ddm.steps.firebase.readtogether.ui.screens.NavigationDrawerContent
import com.ddm.steps.firebase.readtogether.ui.screens.SignInScreen
import com.ddm.steps.firebase.readtogether.ui.screens.SignUpScreen
import com.ddm.steps.firebase.readtogether.ui.screens.FriendsScreen
import com.ddm.steps.firebase.readtogether.ui.screens.LibraryScreen
import com.ddm.steps.firebase.readtogether.ui.screens.ProfileScreen
import com.ddm.steps.firebase.readtogether.ui.services.AuthService
import com.ddm.steps.firebase.readtogether.ui.theme.ReadTogetherTheme
import com.ddm.steps.firebase.readtogether.ui.viewModels.SignInViewModel
import com.ddm.steps.firebase.readtogether.ui.viewModels.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.ddm.steps.firebase.readtogether.ui.screens.ReadingScreen
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        FirebaseAnalytics.getInstance(this).setAnalyticsCollectionEnabled(false)

        enableEdgeToEdge()

        if (!hasRequiredPermissions()) {
            ActivityCompat.requestPermissions(
                this, CAMERAX_PERMISSIONS, 0
            )
        }


        setContent {
            ReadTogetherTheme {
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scope = rememberCoroutineScope()
                val navController = rememberNavController()

                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        NavigationDrawerContent(navController, scope, drawerState) { selected ->
                            scope.launch { drawerState.close() }
                        }
                    }
                ) {
                    Scaffold(
                        modifier = Modifier
                            .fillMaxSize()
                            .systemBarsPadding()
                    ) {
                        SideEffect {
                            window.statusBarColor = Color.White.toArgb()
                            WindowCompat.getInsetsController(window, window.decorView)
                                .isAppearanceLightStatusBars = true
                        }

                        NavHost(navController = navController, startDestination = "signIn") {
                            val authService = AuthService()
                            composable("main") {
                                LibraryScreen(drawerState, scope) { title ->
                                    navController.navigate("reading/$title")
                                }
                            }
                            composable("friends") {
                                FriendsScreen(drawerState, scope)
                            }
                            composable("profile") {
                                ProfileScreen(drawerState, scope)
                            }
                            composable(
                                "reading/{title}",
                                arguments = listOf(navArgument("title") {
                                    type = NavType.StringType
                                })
                            ) { backStackEntry ->
                                val title =
                                    backStackEntry.arguments?.getString("title") ?: "Default Title"
                                ReadingScreen(
                                    drawerState = drawerState,
                                    scope = scope,
                                    title = title
                                )
                            }
                            composable("signIn") {
                                val viewModel by viewModels<SignInViewModel>()
                                val uiState by viewModel.uiState.collectAsState()
                                val context = LocalContext.current
                                SignInScreen(
                                    onSignInClick = { signInUiState ->
                                        authService.loginUser(
                                            user = signInUiState,
                                            onComplete = { navController.navigate("main") },
                                            onFailure = {
                                                Toast.makeText(
                                                    context,
                                                    "Login failed. Please try again.",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            })
                                    },
                                    onSignUpClick = { navController.navigate("signUp") },
                                    signInUiState = uiState
                                )
                            }
                            composable("signUp") {
                                val viewModel by viewModels<SignUpViewModel>()
                                val uiState by viewModel.uiState.collectAsState()
                                SignUpScreen(
                                    signUpUiState = uiState,
                                    returnPrevious = { navController.popBackStack() },
                                    createUser = { signUpUiState ->
                                        authService.createUser(
                                            signUpUiState
                                        ) {
                                            navController.popBackStack()
                                        }
                                    },
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun hasRequiredPermissions(): Boolean {
        return CAMERAX_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(
                applicationContext,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    companion object {
        private val CAMERAX_PERMISSIONS = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.POST_NOTIFICATIONS
        )
    }
}
