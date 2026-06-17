package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.ui.theme.*
import com.example.viewmodel.ShopViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WishlistScreen(
    viewModel: ShopViewModel,
    onBack: () -> Unit,
    onProductClick: (String) -> Unit
) {
    val wishlistItems by viewModel.wishlistItems.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Wishlist", color = VarnikaText) },
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
        if (wishlistItems.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize().padding(innerPadding), contentAlignment = Alignment.Center) {
                Text("Your wishlist is empty.", color = VarnikaSecondaryAccent)
            }
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize().padding(innerPadding).padding(16.dp)) {
                items(wishlistItems) { item ->
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp).clickable { onProductClick(item.productId) },
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
                            IconButton(onClick = { viewModel.removeFromWishlist(item.productId) }) {
                                Icon(Icons.Rounded.Delete, contentDescription = "Remove", tint = VarnikaError)
                            }
                        }
                    }
                }
            }
        }
    }
}
