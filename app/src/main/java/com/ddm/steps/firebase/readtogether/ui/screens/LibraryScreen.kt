package com.ddm.steps.firebase.readtogether.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.ddm.steps.firebase.readtogether.ui.states.BookUiState
import com.ddm.steps.firebase.readtogether.ui.states.TabItem
import com.ddm.steps.firebase.readtogether.ui.viewModels.BookViewModel
import kotlinx.coroutines.CoroutineScope

@Composable
fun LibraryScreen(
    drawerState: DrawerState,
    scope: CoroutineScope,
    readBook: (title: String) -> Unit
) {
    Scaffold(
        topBar = {
           TopBar(scope, drawerState, searchIconVisible = true, screen = "Library")
        },
        content = { paddingValues ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                color = Color(0xFF0C0D10)
            ) {
                Surface(readBook)
            }
        }
    )
}

@Composable
fun Surface(readBook: (title: String) -> Unit) {
    val colors = listOf(
        Color.White,
        Color(0xFFFF8C00),
        Color(0xFFFFD700),
        Color(0xFF32CD32),
        Color(0xFF4682B4),
        Color(0xFF9400D3)
    )
    val titles = mutableListOf(
        "Todos",
        "Favoritos",
        "Lidos",
        "Desisti de ler",
        "Quero ler",
        "Lendo com amigo"
    )
    var books: MutableList<BookUiState> = ArrayList()

    for (i in 0..5) {
        books.add(
            BookUiState(
                "Título teste",
                "Author teste",
                "https://cdl-static.s3-sa-east-1.amazonaws.com/covers/gg/9788535907728/coracao-de-tinta.jpg",
                currentStatus = i
            )
        )
    }

    val tabItens: MutableList<TabItem> = ArrayList()
    for ((index, title) in titles.withIndex()) {
        tabItens.add(
            TabItem(
                title = title,
                unselectedIcon = Icons.Default.FavoriteBorder,
                selectedIcon = Icons.Default.Favorite,
                color = colors[index]
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {

        var selectedTabIndex by remember { mutableIntStateOf(0) }
        val pagerState = rememberPagerState { tabItens.size }
        LaunchedEffect(selectedTabIndex) {
            pagerState.animateScrollToPage(selectedTabIndex)
        }
        LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
            if (!pagerState.isScrollInProgress) {
                selectedTabIndex = pagerState.currentPage
            }
        }

        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = Color(0xFF0C0D10)
        ) {
            tabItens.forEachIndexed { index, item ->
                Tab(
                    selected = index == selectedTabIndex,
                    onClick = { selectedTabIndex = index },
                    modifier = Modifier.size(120.dp),
                    icon = {
                        Icon(
                            imageVector = if (index == selectedTabIndex) {
                                item.selectedIcon
                            } else item.unselectedIcon,
                            contentDescription = item.title,
                            tint = colors[index]
                        )
                    },
                )
            }
        }

        Text(
            text = "${titles[selectedTabIndex]} ${
                books.filter { book -> selectedTabIndex == book.currentStatus }.count()
            }",
            color = colors[selectedTabIndex],
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) { index ->
            LazyVerticalGrid(
                modifier = Modifier.fillMaxWidth(),
                columns = GridCells.Adaptive(minSize = 128.dp),
            ) {
                val booksFiltered = books.filter { book -> selectedTabIndex == book.currentStatus }
                items(booksFiltered) { book ->
                    BookItem(
                        title = book.title,
                        author = book.author,
                        imageUrl = book.imageUrl,
                        backgroundColor = colors[book.currentStatus],
                        readBook = readBook
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookItem(
    title: String,
    author: String,
    imageUrl: String,
    backgroundColor: Color,
    viewModel: BookViewModel = hiltViewModel(),
    readBook: (title: String) -> Unit
) {
    val showModal = remember { mutableStateOf(false) }
    val uiState by viewModel.uiState.collectAsState()
    Column(
        modifier = Modifier
            .clickable { readBook(title) }
            .height(240.dp)
            .width(120.dp)
            .background(backgroundColor, shape = RoundedCornerShape(16.dp))
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = rememberImagePainter(imageUrl),
            contentDescription = title,
            modifier = Modifier
                .size(80.dp)
                .padding(bottom = 8.dp),
            contentScale = ContentScale.Crop
        )
        Row {
            Column {
                Text(text = title)
                Text(text = author)
            }
            IconButton(onClick = { showModal.value = true }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Search Icon",
                    modifier = Modifier
                        .size(80.dp)
                        .padding(end = 10.dp),
                    tint = Color.Black
                )
            }
            if (showModal.value) {
                BookOption(
                    bookUiState = uiState,
                    onDismiss = { showModal.value = false },
                    onFavoriteToggle = {
                        showModal.value = false
                    }
                )
            }
        }


    }
}

@Preview(showBackground = true)
@Composable
fun LibraryScreenPreview() {
    var navControllerMock: NavController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    val funcMocked: (a: String) -> Unit = {}
    LibraryScreen(drawerState, scope, funcMocked)
}
