package com.example.attendance.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.attendance.presentation.card.CardsRoute
import com.example.attendance.presentation.checkin.KioskRoute

object Routes {
    const val KIOSK = "kiosk"
    const val CARDS = "cards"
}

@Suppress("ParamsComparedByRef")
@Composable
fun AppNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(navController, startDestination = Routes.KIOSK) {
        composable(Routes.KIOSK) {
            // Phase 3 puts the admin PIN screen between these two.
            KioskRoute(onOpenAdmin = { navController.navigate(Routes.CARDS) })
        }
        composable(Routes.CARDS) {
            CardsRoute(onBack = { navController.popBackStack() })
        }
    }
}