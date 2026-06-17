package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.ui.theme.*
import com.example.viewmodel.ShopViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(viewModel: ShopViewModel, onBack: () -> Unit) {
    val cartItems by viewModel.cartItems.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Shopping Cart", color = VarnikaText) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = VarnikaPrimary)
            )
        },
        containerColor = VarnikaPrimary,
        bottomBar = {
            if (cartItems.isNotEmpty()) {
                val subtotal = cartItems.sumOf { it.price * it.quantity }
                val tax = subtotal * 0.1
                val total = subtotal + tax

                Surface(color = VarnikaSecondary) {
                    Column(modifier = Modifier.padding(24.dp).windowInsetsPadding(WindowInsets.navigationBars)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Subtotal", color = VarnikaSecondaryAccent)
                            Text("₹${String.format("%.2f", subtotal)}", color = VarnikaText)
                        }
                        Spacer(Modifier.height(8.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Tax (10%)", color = VarnikaSecondaryAccent)
                            Text("₹${String.format("%.2f", tax)}", color = VarnikaText)
                        }
                        Divider(modifier = Modifier.padding(vertical = 16.dp), color = VarnikaPrimary)
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Total", color = VarnikaText, style = MaterialTheme.typography.titleLarge)
                            Text("₹${String.format("%.2f", total)}", color = VarnikaAccent, style = MaterialTheme.typography.titleLarge)
                        }
                        Spacer(Modifier.height(24.dp))
                        Button(
                            onClick = { /* Checkout */ },
                            modifier = Modifier.fillMaxWidth().height(56.dp),
                            shape = MaterialTheme.shapes.large,
                            colors = ButtonDefaults.buttonColors(containerColor = VarnikaAccent, contentColor = VarnikaPrimary)
                        ) {
                            Text("Checkout", style = MaterialTheme.typography.titleMedium)
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        if (cartItems.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize().padding(innerPadding), contentAlignment = Alignment.Center) {
                Text("Your cart is empty.", color = VarnikaSecondaryAccent)
            }
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize().padding(innerPadding).padding(16.dp)) {
                items(cartItems) { item ->
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                        colors = CardDefaults.cardColors(containerColor = VarnikaSecondary),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = item.imageUrl),
                                contentDescription = item.name,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.size(80.dp).clip(MaterialTheme.shapes.small)
                            )
                            Spacer(Modifier.width(16.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(item.name, color = VarnikaText, style = MaterialTheme.typography.titleMedium, maxLines = 1)
                                Text(item.brand, color = VarnikaSecondaryAccent, style = MaterialTheme.typography.bodySmall)
                                Spacer(Modifier.height(8.dp))
                                Text("₹${item.price}", color = VarnikaAccent, style = MaterialTheme.typography.titleMedium)
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.background(VarnikaPrimary, MaterialTheme.shapes.small)) {
                                    IconButton(onClick = { viewModel.updateCartQuantity(item.productId, item.quantity - 1) }, modifier = Modifier.size(32.dp)) {
                                        Icon(Icons.Rounded.Remove, contentDescription = "Decrease", tint = VarnikaText, modifier = Modifier.size(16.dp))
                                    }
                                    Text(item.quantity.toString(), color = VarnikaText, modifier = Modifier.width(24.dp), textAlign = TextAlign.Center)
                                    IconButton(onClick = { viewModel.updateCartQuantity(item.productId, item.quantity + 1) }, modifier = Modifier.size(32.dp)) {
                                        Icon(Icons.Rounded.Add, contentDescription = "Increase", tint = VarnikaText, modifier = Modifier.size(16.dp))
                                    }
                                }
                                IconButton(onClick = { viewModel.removeFromCart(item.productId) }) {
                                    Icon(Icons.Rounded.Delete, contentDescription = "Remove", tint = VarnikaError)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
