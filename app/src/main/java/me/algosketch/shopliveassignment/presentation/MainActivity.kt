package me.algosketch.shopliveassignment.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import me.algosketch.shopliveassignment.presentation.navigation.*
import me.algosketch.shopliveassignment.presentation.ui.theme.ShopLiveAssignmentTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShopLiveAssignmentTheme {
                SlApp()
            }
        }
    }
}

@Composable
fun SlApp() {

    val navController = rememberNavController()
    val currentNavBackStack = navController.currentBackStackEntryAsState()
    val currentDestination = currentNavBackStack.value?.destination
    val currentScreen = bottomNavScreens.find { it.route == currentDestination?.route } ?: Search

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background,
    ) {
        Scaffold(
            bottomBar = { SlBottomNav(
                allScreens = bottomNavScreens,
                onTabSelected = { destination ->
                    navController.navigateSingleTopTo(destination.route)
                },
                currentScreen = currentScreen
            ) }
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                SlNavHost(
                    navController = navController,
                )
            }
        }
    }
}

@Composable
fun SlBottomNav(
    allScreens: List<BottomNavDestination>,
    onTabSelected: (BottomNavDestination) -> Unit,
    currentScreen: BottomNavDestination,
) {
    Surface(
        elevation = 8.dp,
        modifier = Modifier.background(color = Color.White)
    ) {
        Row {
            allScreens.forEach { screen ->
                val selected = currentScreen == screen

                Box(
                    modifier = Modifier
                        .weight(1f, true)
                        .height(48.dp)
                        .clickable { onTabSelected(screen) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        screen.route,
                        color = if (selected) Color.Blue else Color.Black
                    )
                }
            }
        }
    }
}