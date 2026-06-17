package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("About Varnika", color = VarnikaText) },
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
            modifier = Modifier.fillMaxSize().padding(innerPadding).padding(24.dp).verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_varnika_1781689689188),
                contentDescription = "Logo",
                modifier = Modifier.size(120.dp).clip(MaterialTheme.shapes.large)
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text("Your Room, Reimagined by AI", style = MaterialTheme.typography.headlineMedium, color = VarnikaAccent, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                "Varnika is an AI-powered interior design platform that helps users transform their rooms using artificial intelligence.",
                style = MaterialTheme.typography.bodyLarge,
                color = VarnikaText,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
            Spacer(modifier = Modifier.height(32.dp))
            
            Card(colors = CardDefaults.cardColors(containerColor = VarnikaSecondary), shape = MaterialTheme.shapes.large, modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text("Our Mission", style = MaterialTheme.typography.titleLarge, color = VarnikaAccent)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("To democratize luxury interior design by making it accessible, personalized, and effortless through generative AI.", color = VarnikaSecondaryAccent, style = MaterialTheme.typography.bodyMedium)
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    Text("Why Choose Varnika?", style = MaterialTheme.typography.titleLarge, color = VarnikaAccent)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("We combine cutting-edge AI vision with a curated marketplace of premium furniture to seamlessly bridge the gap between imagination and reality.", color = VarnikaSecondaryAccent, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}
