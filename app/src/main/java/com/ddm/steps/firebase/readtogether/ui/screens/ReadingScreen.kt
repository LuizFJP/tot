package com.ddm.steps.firebase.readtogether.ui.screens

import android.content.Context
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.ddm.steps.firebase.readtogether.R
import kotlinx.coroutines.CoroutineScope

@Composable
fun ReadingScreen(
    drawerState: DrawerState,
    scope: CoroutineScope,
    title: String
) {
    val context = LocalContext.current
    val mockedPages = listOf( R.drawable.page_one, R.drawable.page_two, R.drawable.page_three)
    var currentPage by remember { mutableIntStateOf(0) }
    val totalPages = mockedPages.size
    Scaffold(
        topBar = {
            TopBar(scope, drawerState, searchIconVisible = true, screen = title)
        },
        content = { paddingValues ->
            Surface(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .clickable {
                        currentPage = (currentPage + 1) % totalPages
                    }
            ) {
                BookPage(mockedPages, currentPage, context)
                BookProgressBar(currentPage, totalPages)
            }
        }
    )
}

@Composable
fun BookPage(mockedPages: List<Int>, currentPage: Int, context: Context) {
    val image = remember(currentPage) {
        BitmapFactory.decodeResource(context.resources, mockedPages[currentPage])
            .asImageBitmap()
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color(0xFFFDF1D4))
            .padding(16.dp)
    ) {
        Image(
            bitmap = image,
            contentDescription = "Book Page $currentPage",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(3f / 4f)
        )
    }
}

@Composable
fun BookProgressBar(currentPage: Int, totalPages: Int) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = "${currentPage + 1}/$totalPages",
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Slider(
            value = currentPage.toFloat() / (totalPages - 1),
            onValueChange = { /* TODO: Handle slider scroll */ },
            modifier = Modifier.fillMaxWidth()
        )
    }
}