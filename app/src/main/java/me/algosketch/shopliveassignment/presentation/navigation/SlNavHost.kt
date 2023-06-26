package me.algosketch.shopliveassignment.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import me.algosketch.shopliveassignment.presentation.favorite.FavoriteScreen
import me.algosketch.shopliveassignment.presentation.search.SearchScreen

@Composable
fun SlNavHost(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = Search.route,
    ) {
        composable(route = Search.route) {
            SearchScreen()
        }
        composable(route = Favorite.route) {
            FavoriteScreen()
        }
    }
}