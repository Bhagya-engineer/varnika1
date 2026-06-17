package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactScreen(onBack: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Contact Us", color = VarnikaText) },
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
        ) {
            Text("Get in Touch", style = MaterialTheme.typography.headlineMedium, color = VarnikaAccent)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Have questions about our AI or products? We're here to help.", color = VarnikaSecondaryAccent, style = MaterialTheme.typography.bodyMedium)
            
            Spacer(modifier = Modifier.height(32.dp))
            
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = VarnikaAccent,
                    unfocusedBorderColor = VarnikaSecondaryAccent,
                    focusedTextColor = VarnikaText,
                    unfocusedTextColor = VarnikaText
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = VarnikaAccent,
                    unfocusedBorderColor = VarnikaSecondaryAccent,
                    focusedTextColor = VarnikaText,
                    unfocusedTextColor = VarnikaText
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = message,
                onValueChange = { message = it },
                label = { Text("Message") },
                modifier = Modifier.fillMaxWidth().height(120.dp),
                maxLines = 4,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = VarnikaAccent,
                    unfocusedBorderColor = VarnikaSecondaryAccent,
                    focusedTextColor = VarnikaText,
                    unfocusedTextColor = VarnikaText
                )
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = { /* Send message */ },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = MaterialTheme.shapes.large,
                colors = ButtonDefaults.buttonColors(containerColor = VarnikaAccent, contentColor = VarnikaPrimary)
            ) {
                Text("Send Message", style = MaterialTheme.typography.titleMedium)
            }
            
            Spacer(modifier = Modifier.height(48.dp))
            
            Card(colors = CardDefaults.cardColors(containerColor = VarnikaSecondary), shape = MaterialTheme.shapes.large, modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text("Contact Details", style = MaterialTheme.typography.titleMedium, color = VarnikaText)
                    Spacer(modifier = Modifier.height(16.dp))
                    Row {
                        Icon(Icons.Rounded.Email, contentDescription = null, tint = VarnikaAccent)
                        Spacer(modifier = Modifier.width(16.dp))
                        Text("support@varnika.ai", color = VarnikaSecondaryAccent)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row {
                        Icon(Icons.Rounded.Phone, contentDescription = null, tint = VarnikaAccent)
                        Spacer(modifier = Modifier.width(16.dp))
                        Text("+1 (555) 123-4567", color = VarnikaSecondaryAccent)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row {
                        Icon(Icons.Rounded.LocationOn, contentDescription = null, tint = VarnikaAccent)
                        Spacer(modifier = Modifier.width(16.dp))
                        Text("San Francisco, CA", color = VarnikaSecondaryAccent)
                    }
                }
            }
        }
    }
}
