package com.example.map

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigate() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login_screen") {
        composable("login_screen") { Login(navController) }
        composable("forgot_password_screen") { ForgotPass(navController) }
        composable("map_screen") { Map() }
    }
}