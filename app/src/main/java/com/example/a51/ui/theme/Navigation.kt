package com.example.a51.ui.theme

import androidx.compose.runtime.Composable



import com.example.a51.ReworkViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavType
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument


sealed class Screen(val route: String) {
    data object List : Screen("list")
    data object Detail : Screen("detail")
    data object EditScreen : Screen("edit_screen/{itemId}") {
        // use Long for timestamp-based id
        fun createRoute(itemId: Long) = "edit_screen/$itemId"
    }
}

@Composable
fun Navigation(navController: NavHostController = rememberNavController(),
               viewModel: ReworkViewModel = viewModel()
) {
    NavHost (navController, startDestination = Screen.List.route) {
        composable(Screen.List.route) {
            FirstScreen(viewModel, navController)
        }
        composable(
            Screen.Detail.route,
        ) { backStackEntry ->
            AddNoteScreen(
                navController = navController,
                viewModel = viewModel
            )
        }
        composable(
            Screen.EditScreen.route,
            arguments = listOf(navArgument("itemId") { type = NavType.LongType })
        ) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getLong("itemId") ?: 0L
            EditNoteScreen(
                navController = navController,
                viewModel = viewModel,
                itemId = itemId
            )
        }
    }
}