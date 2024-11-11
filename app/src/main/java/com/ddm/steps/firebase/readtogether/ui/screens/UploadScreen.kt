package com.ddm.steps.firebase.readtogether.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FilePresent
import androidx.compose.material.icons.filled.UploadFile
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun UploadScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2C2C2C)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Upload",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = Color(0xFFE53935),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .border(
                    BorderStroke(2.dp, Color(0xFF616161)),
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.UploadFile,
                    contentDescription = "Upload",
                    tint = Color(0xFFFF4081),
                    modifier = Modifier.size(40.dp)
                )
                Text(
                    "Click to Upload or drag and drop",
                    color = Color(0xFFB0BEC5)
                )
                Text(
                    "(Max. File size: 25 MB)",
                    fontSize = 12.sp,
                    color = Color(0xFFB0BEC5)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Upload progress or list of files
        UploadStatusList()
    }
}

@Composable
fun UploadStatusList() {
    Column(modifier = Modifier.fillMaxWidth()) {
        UploadItem(fileName = "HannahBusing_Resume.pdf", fileSize = "200 KB", progress = 40)
        UploadItem(fileName = "HannahBusing_Resume.pdf", fileSize = "200 KB", progress = 100)
        UploadItem(
            fileName = "HannahBusing_Resume.pdf", fileSize = "200 KB",
            failed = true
        )
        UploadItem(fileName = "HannahBusing_Resume.pdf", fileSize = "200 KB", link = true)
    }
}

@Composable
fun UploadItem(
    fileName: String,
    fileSize: String,
    progress: Int? = null,
    failed: Boolean = false,
    link: Boolean = false
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1E1E1E)
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.FilePresent,
                contentDescription = "File",
                tint = Color.White,
                modifier = Modifier.size(40.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = fileName, color = Color.White, fontWeight = FontWeight.Bold)
                Text(text = fileSize, color = Color.Gray)

                progress?.let {
                    LinearProgressIndicator(
                        progress = it / 100f,
                        modifier = Modifier.fillMaxWidth(),
                        color = if (it < 100) Color(0xFFFF4081) else Color(0xFF00E676)
                    )
                }

                if (failed) {
                    Text(
                        "Upload failed, please try again",
                        color = Color(0xFFFF5252),
                        fontWeight = FontWeight.Bold
                    )
                    ClickableText(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(color = Color.Red)) {
                                append("Try again")
                            }
                        },
                        onClick = { /* Handle retry */ }
                    )
                }

                if (link) {
                    ClickableText(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(color = Color.Cyan)) {
                                append("Click to view")
                            }
                        },
                        onClick = { /* Handle view */ }
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            IconButton(onClick = { /* Handle delete */ }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color(0xFFFF4081)
                )
            }
        }
    }
}

@Preview()
@Composable
fun PreviewUploadScreen() {
    UploadScreen()
}
