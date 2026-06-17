package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.ShoppingBag
import androidx.compose.material.icons.rounded.SwapHoriz
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.ui.theme.*
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalysisScreen(
    onBack: () -> Unit,
    onShopNow: () -> Unit,
    onCustomize: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("AI Design Results", style = MaterialTheme.typography.titleLarge) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = VarnikaPrimary)
            )
        },
        containerColor = VarnikaPrimary
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            BeforeAfterSlider(
                beforeImage = R.drawable.img_room_before_1781688663187,
                afterImage = R.drawable.img_room_after_1781688680567,
                modifier = Modifier.fillMaxWidth().height(300.dp)
            )
            
            Column(modifier = Modifier.padding(24.dp)) {
                Text("Design Analysis", style = MaterialTheme.typography.headlineMedium, color = VarnikaText)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "We transformed your standard living space into a warm, luxury modern environment. Earthy tones emphasize natural lighting while minimalist furniture reduces clutter.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = VarnikaSecondaryAccent
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Card(
                    colors = CardDefaults.cardColors(containerColor = VarnikaSecondary),
                    shape = MaterialTheme.shapes.large,
                    border = BorderStroke(1.dp, VarnikaAccent.copy(alpha = 0.1f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("AI Recommendations", style = MaterialTheme.typography.titleMedium, color = VarnikaText)
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        val recommendations = listOf(
                            "Add warm neutral wall colors",
                            "Incorporate a textured area rug",
                            "Upgrade to a minimal lounge chair",
                            "Install warm accent lighting"
                        )
                        
                        recommendations.forEach { rec ->
                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 8.dp)) {
                                Icon(Icons.Rounded.CheckCircle, contentDescription = null, tint = VarnikaAccent, modifier = Modifier.size(20.dp))
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(rec, color = VarnikaSecondaryAccent, style = MaterialTheme.typography.bodyMedium)
                            }
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(32.dp))
                
                Button(
                    onClick = onCustomize,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = MaterialTheme.shapes.large,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = VarnikaAccent,
                        contentColor = VarnikaPrimary
                    )
                ) {
                    Icon(Icons.Rounded.ShoppingBag, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Customize Your Room", style = MaterialTheme.typography.titleMedium)
                }
            }
        }
    }
}

@Composable
fun BeforeAfterSlider(beforeImage: Int, afterImage: Int, modifier: Modifier = Modifier) {
    var sliderPosition by remember { mutableStateOf(0.5f) }
    
    BoxWithConstraints(modifier = modifier) {
        val constraintsScope = this
        val maxW = constraintsScope.maxWidth
        val widthPx = with(LocalDensity.current) { maxW.toPx() }
        
        // After Image (Base)
        Image(
            painter = painterResource(id = afterImage),
            contentDescription = "After",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        
        // Before Image (Clipped)
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(maxW * sliderPosition)
                .clip(androidx.compose.foundation.shape.GenericShape { size, _ ->
                    lineTo(size.width, 0f)
                    lineTo(size.width, size.height)
                    lineTo(0f, size.height)
                    close()
                })
        ) {
            Image(
                painter = painterResource(id = beforeImage),
                contentDescription = "Before",
                contentScale = ContentScale.Crop,
                // Make sure the image inside takes the size of the whole BoxWithConstraints
                modifier = Modifier.requiredWidth(maxW).fillMaxHeight()
            )
        }
        
        // Slider Line & Handle
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(4.dp)
                .offset { IntOffset((widthPx * sliderPosition).roundToInt() - 2.dp.roundToPx(), 0) }
                .background(VarnikaAccent)
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        sliderPosition = (sliderPosition + dragAmount.x / widthPx).coerceIn(0f, 1f)
                    }
                }
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset(x = (-14).dp)
                    .size(32.dp)
                    .background(VarnikaAccent, RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Rounded.SwapHoriz, contentDescription = "Drag to compare", tint = VarnikaPrimary)
            }
        }
        
        // Labels
        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
                .background(VarnikaPrimary.copy(alpha = 0.7f), RoundedCornerShape(8.dp))
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text("BEFORE", color = VarnikaText, style = MaterialTheme.typography.labelSmall)
        }
        
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .background(VarnikaAccent.copy(alpha = 0.9f), RoundedCornerShape(8.dp))
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text("AFTER", color = VarnikaPrimary, style = MaterialTheme.typography.labelMedium)
        }
    }
}

