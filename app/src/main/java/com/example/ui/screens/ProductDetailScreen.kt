package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.DummyData
import com.example.ui.theme.*
import com.example.viewmodel.ShopViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    viewModel: ShopViewModel,
    productId: String,
    onBack: () -> Unit,
    onNavigateToCart: () -> Unit
) {
    val product = DummyData.products.find { it.id == productId }
    if (product == null) {
        onBack()
        return
    }

    val isWishlisted by viewModel.isWishlisted(product.id).collectAsState(initial = false)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.toggleWishlist(product, isWishlisted) }) {
                        Icon(
                            if (isWishlisted) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder,
                            contentDescription = "Wishlist",
                            tint = if (isWishlisted) VarnikaError else VarnikaText
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        containerColor = VarnikaPrimary,
        bottomBar = {
            Surface(color = VarnikaSecondary, shadowElevation = 8.dp) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .windowInsetsPadding(WindowInsets.navigationBars),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("Total Price", color = VarnikaSecondaryAccent, style = MaterialTheme.typography.bodySmall)
                        Text("₹${product.price}", color = VarnikaAccent, style = MaterialTheme.typography.headlineMedium)
                    }
                    Button(
                        onClick = { 
                            viewModel.addToCart(product)
                            onNavigateToCart()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = VarnikaAccent, contentColor = VarnikaPrimary),
                        shape = MaterialTheme.shapes.medium,
                        modifier = Modifier.height(56.dp).padding(start = 16.dp).fillMaxWidth()
                    ) {
                        Icon(Icons.Rounded.ShoppingCart, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text("Add to Cart")
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = innerPadding.calculateBottomPadding())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
            ) {
                Image(
                    painter = painterResource(id = product.imageRes),
                    contentDescription = product.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
            
            Column(modifier = Modifier.padding(24.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Top) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(product.brand.uppercase(), color = VarnikaSecondaryAccent, style = MaterialTheme.typography.labelMedium, letterSpacing = androidx.compose.ui.unit.TextUnit(2f, androidx.compose.ui.unit.TextUnitType.Sp))
                        Spacer(Modifier.height(8.dp))
                        Text(product.name, color = VarnikaText, style = MaterialTheme.typography.headlineMedium)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.background(VarnikaSecondary, RoundedCornerShape(16.dp)).padding(horizontal = 12.dp, vertical = 6.dp)) {
                        Icon(Icons.Rounded.Star, contentDescription = "Rating", tint = VarnikaAccent, modifier = Modifier.size(16.dp))
                        Spacer(Modifier.width(4.dp))
                        Text(product.rating.toString(), color = VarnikaText, style = MaterialTheme.typography.bodyMedium)
                    }
                }
                
                Spacer(modifier = Modifier.height(32.dp))
                
                Text("Description", color = VarnikaText, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text(product.details, color = VarnikaSecondaryAccent, style = MaterialTheme.typography.bodyMedium, lineHeight = androidx.compose.ui.unit.TextUnit(24f, androidx.compose.ui.unit.TextUnitType.Sp))
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Column(modifier = Modifier.weight(1f).background(VarnikaSecondary, MaterialTheme.shapes.medium).padding(16.dp)) {
                        Text("Material", color = VarnikaSecondaryAccent, style = MaterialTheme.typography.labelMedium)
                        Spacer(Modifier.height(4.dp))
                        Text(product.material, color = VarnikaText, style = MaterialTheme.typography.bodyMedium)
                    }
                    Column(modifier = Modifier.weight(1f).background(VarnikaSecondary, MaterialTheme.shapes.medium).padding(16.dp)) {
                        Text("Category", color = VarnikaSecondaryAccent, style = MaterialTheme.typography.labelMedium)
                        Spacer(Modifier.height(4.dp))
                        Text(product.category, color = VarnikaText, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }
}
