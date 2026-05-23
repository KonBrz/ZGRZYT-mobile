package com.zgrzyt.mobile

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.zgrzyt.mobile.ui.login.LoginScreen
import com.zgrzyt.mobile.ui.tickets.CreateTicketScreen
import com.zgrzyt.mobile.ui.tickets.TicketDetailsScreen
import com.zgrzyt.mobile.ui.tickets.TicketsScreen
import com.zgrzyt.mobile.data.repository.SessionManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.LaunchedEffect

@Composable

fun AppNavigation() {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        SessionManager.loadSession(context)
    }
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = if (SessionManager.isLoggedIn()) "tickets" else "login"
    ) {
        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate("tickets") {
                        popUpTo("login") {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable("tickets") {
            TicketsScreen(
                onTicketClick = { ticketId ->
                    navController.navigate("ticketDetails/$ticketId")
                },
                onCreateClick = {
                    navController.navigate("createTicket")
                },
                onLogout = {
                    SessionManager.clearSession(context)

                    navController.navigate("login") {
                        popUpTo("tickets") {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable("createTicket") {
            CreateTicketScreen(
                onBack = {
                    navController.popBackStack()
                },
                onCreated = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = "ticketDetails/{ticketId}",
            arguments = listOf(
                navArgument("ticketId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val ticketId = backStackEntry.arguments?.getInt("ticketId") ?: 0

            TicketDetailsScreen(
                ticketId = ticketId,
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}