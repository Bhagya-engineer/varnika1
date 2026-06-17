package com.example.ui.screens

import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.AddAPhoto
import androidx.compose.material.icons.rounded.AutoAwesome
import androidx.compose.material.icons.rounded.PhotoCamera
import androidx.compose.material.icons.rounded.PhotoLibrary
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.R
import com.example.ui.theme.*
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UploadScreen(
    onBack: () -> Unit,
    onAnalyze: () -> Unit
) {
    var hasUploaded by remember { mutableStateOf(false) }
    var isAnalyzing by remember { mutableStateOf(false) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var imageBitmap by remember { mutableStateOf<Bitmap?>(null) }

    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            imageUri = uri
            imageBitmap = null
            hasUploaded = true
        }
    }

    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap: Bitmap? ->
        if (bitmap != null) {
            imageBitmap = bitmap
            imageUri = null
            hasUploaded = true
        }
    }

    LaunchedEffect(isAnalyzing) {
        if (isAnalyzing) {
            delay(2500)
            onAnalyze()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Redesign Room", style = MaterialTheme.typography.titleLarge) },
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
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(MaterialTheme.shapes.large)
                    .background(VarnikaSecondary)
                    .border(1.dp, VarnikaAccent.copy(alpha = 0.1f), MaterialTheme.shapes.large)
                    .clickable(enabled = false) { },
                contentAlignment = Alignment.Center
            ) {
                if (hasUploaded) {
                    if (imageBitmap != null) {
                        AsyncImage(
                            model = imageBitmap,
                            contentDescription = "Camera Photo",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    } else if (imageUri != null) {
                        AsyncImage(
                            model = imageUri,
                            contentDescription = "Gallery Photo",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        // Fallback to dummy
                        Image(
                            painter = painterResource(id = R.drawable.img_room_before_1781688663187),
                            contentDescription = "Uploaded Room",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    
                    if (isAnalyzing) {
                        val infiniteTransition = rememberInfiniteTransition()
                        val alpha by infiniteTransition.animateFloat(
                            initialValue = 0.2f,
                            targetValue = 0.8f,
                            animationSpec = infiniteRepeatable(
                                animation = tween(1000, easing = LinearEasing),
                                repeatMode = RepeatMode.Reverse
                            ), label = "scanning"
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(VarnikaAccent.copy(alpha = alpha))
                        )
                    }
                } else {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            Icons.Rounded.AddAPhoto, 
                            contentDescription = "Upload",
                            modifier = Modifier.size(48.dp),
                            tint = VarnikaSecondaryAccent
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Select an option below", color = VarnikaSecondaryAccent)
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))

            if (!hasUploaded) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Button(
                        onClick = { cameraLauncher.launch() },
                        modifier = Modifier.weight(1f).height(56.dp),
                        shape = MaterialTheme.shapes.medium,
                        colors = ButtonDefaults.buttonColors(containerColor = VarnikaSecondary)
                    ) {
                        Icon(Icons.Rounded.PhotoCamera, contentDescription = null, tint = VarnikaText)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Camera", color = VarnikaText)
                    }
                    Button(
                        onClick = { galleryLauncher.launch("image/*") },
                        modifier = Modifier.weight(1f).height(56.dp),
                        shape = MaterialTheme.shapes.medium,
                        colors = ButtonDefaults.buttonColors(containerColor = VarnikaSecondary)
                    ) {
                        Icon(Icons.Rounded.PhotoLibrary, contentDescription = null, tint = VarnikaText)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Gallery", color = VarnikaText)
                    }
                }
            } else if (!isAnalyzing) {
                TextButton(onClick = { hasUploaded = false }) {
                    Text("Remove / Change Photo", color = VarnikaError)
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            if (hasUploaded) {
                Button(
                    onClick = { isAnalyzing = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    enabled = !isAnalyzing,
                    shape = MaterialTheme.shapes.large,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = VarnikaAccent,
                        contentColor = VarnikaPrimary,
                        disabledContainerColor = VarnikaSecondary,
                        disabledContentColor = VarnikaSecondaryAccent
                    )
                ) {
                    if (isAnalyzing) {
                        CircularProgressIndicator(color = VarnikaPrimary, modifier = Modifier.size(24.dp))
                        Spacer(modifier = Modifier.width(16.dp))
                        Text("Analyzing Room with AI...")
                    } else {
                        Icon(Icons.Rounded.AutoAwesome, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Generate AI Redesign", style = MaterialTheme.typography.titleMedium)
                    }
                }
            }
        }
    }
}
