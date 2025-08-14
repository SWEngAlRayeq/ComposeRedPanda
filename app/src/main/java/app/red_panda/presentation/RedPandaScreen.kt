package app.red_panda.presentation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import app.red_panda.presentation.viewmodel.RedPandaViewModel
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest

@Composable
fun RedPandaScreen(viewModel: RedPandaViewModel = hiltViewModel()) {

    val state by viewModel.state.collectAsState()
    val uiState = state
    var flipped by remember { mutableStateOf(false) }
    val rotation by animateFloatAsState(
        targetValue = if (flipped) 180f else 0f,
        animationSpec = tween(600)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        Color(0xFFFFEFDB),
                        Color(0xFFFFE6EE)
                    )
                )
            )
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(Modifier.height(20.dp))
            Text(
                "Red Panda Delight",
                fontSize = 26.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF3B2E2E)
            )
            Spacer(Modifier.height(12.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth(0.92f)
                        .heightIn(min = 420.dp)
                        .clip(RoundedCornerShape(24.dp)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        when {
                            uiState.isLoading -> CircularProgressIndicator()
                            uiState.error != null -> Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("Error: ${uiState.error}")
                                Spacer(Modifier.height(8.dp))
                                Button(onClick = { viewModel.loadRedPanda() }) { Text("Retry") }
                            }

                            uiState.redPanda != null -> {
                                val r = uiState.redPanda
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(20.dp),
                                    contentAlignment = Alignment.Center
                                ) {

                                    Box(
                                        modifier = Modifier
                                            .graphicsLayer {
                                                rotationY = rotation
                                                cameraDistance = 12 * density
                                            }
                                            .fillMaxSize(),
                                        contentAlignment = Alignment.TopCenter
                                    ) {
                                        if (rotation <= 90f) {
                                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                                SubcomposeAsyncImage(
                                                    model = ImageRequest.Builder(LocalContext.current)
                                                        .data(r.imageUrl)
                                                        .crossfade(true)
                                                        .build(),
                                                    contentDescription = "Red panda",
                                                    modifier = Modifier
                                                        .size(260.dp)
                                                        .clip(CircleShape),
                                                    contentScale = ContentScale.Crop,
                                                    loading = {
                                                        Box(
                                                            modifier = Modifier
                                                                .size(260.dp)
                                                                .clip(CircleShape)
                                                                .background(Color.LightGray)
                                                        )
                                                    }
                                                )
                                                Spacer(Modifier.height(18.dp))
                                                Text(
                                                    "Tap card to flip for fact",
                                                    style = MaterialTheme.typography.bodyMedium
                                                )
                                            }
                                        }
                                    }

                                    Box(
                                        modifier = Modifier
                                            .graphicsLayer {
                                                rotationY = rotation - 180f
                                                cameraDistance = 12 * density
                                            }
                                            .fillMaxSize(),
                                        contentAlignment = Alignment.TopCenter
                                    ) {
                                        if (rotation > 90f) {
                                            Column(
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .verticalScroll(rememberScrollState())
                                                    .padding(18.dp),
                                                horizontalAlignment = Alignment.CenterHorizontally
                                            ) {
                                                Text(
                                                    "Red Panda Fact",
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 20.sp
                                                )
                                                Spacer(Modifier.height(12.dp))
                                                Text(
                                                    text = r.fact,
                                                    textAlign = TextAlign.Center,
                                                    style = MaterialTheme.typography.bodyLarge,
                                                    modifier = Modifier.fillMaxWidth()
                                                )
                                                Spacer(Modifier.height(18.dp))
                                                Button(onClick = {
                                                    viewModel.loadRedPanda()
                                                    flipped = false
                                                }) { Text("Another Fact") }
                                            }
                                        }
                                    }

                                    Box(
                                        modifier = Modifier
                                            .matchParentSize()
                                            .pointerInput(Unit) {
                                                detectTapGestures { _ -> flipped = !flipped }
                                            })
                                }
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(12.dp))
            Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                Text("New Red Panda", color = Color.Gray)
                FloatingActionButton(onClick = {
                    viewModel.loadRedPanda()
                    flipped = false
                }) {
                    Icon(Icons.Default.Refresh, contentDescription = "Refresh")
                }
            }
            Spacer(Modifier.height(12.dp))
        }
    }
}