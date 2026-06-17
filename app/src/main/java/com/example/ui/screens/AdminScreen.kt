package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Analytics
import androidx.compose.material.icons.rounded.Inventory
import androidx.compose.material.icons.rounded.People
import androidx.compose.material.icons.rounded.Receipt
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Admin Dashboard", color = VarnikaText) },
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
            Text("Overview", style = MaterialTheme.typography.titleLarge, color = VarnikaText)
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                AdminStatCard(title = "Users", value = "1,248", icon = Icons.Rounded.People, modifier = Modifier.weight(1f))
                AdminStatCard(title = "Orders", value = "342", icon = Icons.Rounded.Receipt, modifier = Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                AdminStatCard(title = "Products", value = "8", icon = Icons.Rounded.Inventory, modifier = Modifier.weight(1f))
                AdminStatCard(title = "Revenue", value = "₹35,27,500", icon = Icons.Rounded.Analytics, modifier = Modifier.weight(1f))
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            Text("Quick Actions", style = MaterialTheme.typography.titleLarge, color = VarnikaText)
            Spacer(modifier = Modifier.height(16.dp))
            
            Button(onClick = { /* Add Product */ }, modifier = Modifier.fillMaxWidth().height(56.dp), shape = MaterialTheme.shapes.large, colors = ButtonDefaults.buttonColors(containerColor = VarnikaSecondary, contentColor = VarnikaText)) {
                Text("Add New Product")
            }
            Spacer(modifier = Modifier.height(12.dp))
            Button(onClick = { /* Manage Orders */ }, modifier = Modifier.fillMaxWidth().height(56.dp), shape = MaterialTheme.shapes.large, colors = ButtonDefaults.buttonColors(containerColor = VarnikaSecondary, contentColor = VarnikaText)) {
                Text("Manage Orders")
            }
            Spacer(modifier = Modifier.height(12.dp))
            Button(onClick = { /* Manage Users */ }, modifier = Modifier.fillMaxWidth().height(56.dp), shape = MaterialTheme.shapes.large, colors = ButtonDefaults.buttonColors(containerColor = VarnikaSecondary, contentColor = VarnikaText)) {
                Text("Manage Users")
            }
        }
    }
}

@Composable
fun AdminStatCard(title: String, value: String, icon: androidx.compose.ui.graphics.vector.ImageVector, modifier: Modifier = Modifier) {
    Card(colors = CardDefaults.cardColors(containerColor = VarnikaSecondary), shape = MaterialTheme.shapes.large, modifier = modifier.height(120.dp)) {
        Column(modifier = Modifier.padding(16.dp).fillMaxSize(), verticalArrangement = Arrangement.Center) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(icon, contentDescription = null, tint = VarnikaAccent, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(title, color = VarnikaSecondaryAccent, style = MaterialTheme.typography.bodyMedium)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(value, color = VarnikaText, style = MaterialTheme.typography.headlineMedium)
        }
    }
}
