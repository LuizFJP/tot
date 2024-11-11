package com.ddm.steps.firebase.readtogether.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ddm.steps.firebase.readtogether.ui.theme.DarkBlue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Preview
@Composable
fun TopBarPreview() {
//    TopBar()
}

@Composable
fun TopBar(
    scope: CoroutineScope,
    drawerState: DrawerState,
    searchIconVisible: Boolean = false,
    screen: String = "TopBar"
) {
    TopBarContent(scope, drawerState, searchIconVisible, screen)
}

@Composable
fun TopBarContent(
    scope: CoroutineScope,
    drawerState: DrawerState,
    searchIconVisible: Boolean,
    screen: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF0C0D10)),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            onClick = {
                scope.launch {
                    if (drawerState.isClosed) {
                        drawerState.open()
                    } else {
                        drawerState.close()
                    }
                }
            }
        ) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu Bar Icon",
                modifier = Modifier.size(32.dp),
                tint = Color.White
            )
        }

        Text(
            text = screen,
            fontSize = 24.sp,
            modifier = Modifier.padding(vertical = 8.dp),
            color = Color.White
        )

        if (searchIconVisible) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon",
                    modifier = Modifier
                        .size(32.dp)
                        .padding(end = 10.dp),
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
fun NavigationDrawerContent(
    navController: NavController,
    scope: CoroutineScope,
    drawerState: DrawerState,
    param: (Any) -> Job
) {
    ModalDrawerSheet {
        Text(text = "Menu", modifier = Modifier.padding(16.dp))
        HorizontalDivider()
        NavigationDrawerItem(
            label = { Text(text = "Biblioteca") },
            selected = false,
            onClick = {
                navController.navigate("main")
                scope.launch { drawerState.close() }
            },
        )
        NavigationDrawerItem(
            label = { Text(text = "Amigos") },
            selected = false,
            onClick = {
                navController.navigate("friends")
                scope.launch { drawerState.close() }
            }
        )
        NavigationDrawerItem(
            label = { Text(text = "Meu Perfil") },
            selected = false,
            onClick = {
                navController.navigate("profile")
                scope.launch { drawerState.close() }
            }
        )
        NavigationDrawerItem(
            label = { Text(text = "Sign Out") },
            selected = false,
            onClick = {
                navController.navigate("main")
                scope.launch { drawerState.close() }
            }
        )
    }
}
