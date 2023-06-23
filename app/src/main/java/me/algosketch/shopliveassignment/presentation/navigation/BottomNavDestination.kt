package me.algosketch.shopliveassignment.presentation.navigation

interface BottomNavDestination {
    val route: String
}

object Search : BottomNavDestination {
    override val route = "SEARCH"
}

object Favorite : BottomNavDestination {
    override val route = "FAVORITE"
}

val bottomNavScreens = listOf(Search, Favorite)