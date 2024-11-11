package com.ddm.steps.firebase.readtogether.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.ddm.steps.firebase.readtogether.ui.services.SendFriendInvite
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun FriendsScreen(
    drawerState: DrawerState,
    scope: CoroutineScope
) {
    Scaffold(
        topBar = {
            TopBar(scope, drawerState, screen = "Amigos")
        },
        content = { paddingValues ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                color = Color(0xFF0C0D10)
            ) {
                Content()
            }
        }
    )
}

@Composable
fun Content() {
    var friendCode by remember { mutableStateOf("AO674I") }
    var referralCode by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0C0D10))
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .background(Color(0xFF0B2F46))
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Meu código: $friendCode",
                        color = Color(0xFFEF5350),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Adicionar amigo:",
                        color = Color.White,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    BasicTextField(
                        value = referralCode,
                        onValueChange = { referralCode = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .background(Color(0xFFE4A4B7), shape = RoundedCornerShape(12.dp))
                            .padding(16.dp),
                        singleLine = true,
                        decorationBox = { innerTextField ->
                            if (referralCode.isEmpty()) {
                                Text(
                                    text = "",
                                    color = Color(0xFFE4A4B7),
                                    fontSize = 16.sp,
                                    textAlign = TextAlign.Center
                                )
                            }
                            innerTextField()
                        }
                    )

                    val coroutineScope = rememberCoroutineScope()
                    IconButton(onClick = { coroutineScope.launch { SendFriendInvite(referralCode) } }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "Search Icon",
                            modifier = Modifier
                                .size(80.dp)
                                .padding(end = 10.dp),
                            tint = Color.Black
                        )
                    }
                }

            }

        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Text(
                text = "Lista de Solicitações:",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            FriendRequestItem(name = "Ellie Fredricksen", imageUrl = "https://your-image-url.com")
            FriendRequestItem(name = "Vanellope Von", imageUrl = "https://your-image-url.com")
            FriendRequestItem(name = "Korra", imageUrl = "https://your-image-url.com")
        }
    }
}

@Composable
fun FriendRequestItem(name: String, imageUrl: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color(0xFFB94069), shape = RoundedCornerShape(16.dp))
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberImagePainter(imageUrl),
            contentDescription = name,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = name,
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = "Accept",
            modifier = Modifier
                .size(24.dp)
                .clickable { },
            tint = Color.White
        )

        Spacer(modifier = Modifier.width(16.dp))

        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Reject",
            modifier = Modifier
                .size(24.dp)
                .clickable { },
            tint = Color.White
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FriendsScreenPreview() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    FriendsScreen(drawerState, scope)
}
