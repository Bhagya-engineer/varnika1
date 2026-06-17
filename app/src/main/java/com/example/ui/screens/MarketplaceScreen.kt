package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.data.DummyData
import com.example.data.Product
import com.example.viewmodel.ShopViewModel
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarketplaceScreen(
    viewModel: ShopViewModel,
    initialCategory: String,
    onBack: () -> Unit,
    onProductClick: (String) -> Unit,
    onNavigateToCart: () -> Unit,
    onNavigateToWishlist: () -> Unit
) {
    val categories = listOf("All", "Furniture", "Decorations", "Lighting", "Rugs", "Storage", "Paintings")
    var selectedCategory by remember { mutableStateOf(if (categories.contains(initialCategory)) initialCategory else "All") }
    val products = remember(selectedCategory) { DummyData.getProductsByCategory(selectedCategory) }

    val selectedTabIndex = categories.indexOf(selectedCategory)

    val cartItems by viewModel.cartItems.collectAsState()
    val cartCount = cartItems.sumOf { it.quantity }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Shop", style = MaterialTheme.typography.titleLarge) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = onNavigateToWishlist) {
                        Icon(Icons.Rounded.FavoriteBorder, contentDescription = "Wishlist")
                    }
                    IconButton(onClick = onNavigateToCart) {
                        BadgedBox(
                            badge = { 
                                if (cartCount > 0) {
                                    Badge(containerColor = VarnikaAccent, contentColor = VarnikaPrimary) { Text("$cartCount") }
                                }
                            }
                        ) {
                            Icon(Icons.Rounded.ShoppingCart, contentDescription = "Cart")
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = VarnikaPrimary)
            )
        },
        containerColor = VarnikaPrimary
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            ScrollableTabRow(
                selectedTabIndex = selectedTabIndex,
                containerColor = VarnikaPrimary,
                contentColor = VarnikaAccent,
                edgePadding = 16.dp,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                        color = VarnikaAccent
                    )
                }
            ) {
                categories.forEachIndexed { index, title ->
                    Tab(
                        selected = index == selectedTabIndex,
                        onClick = { selectedCategory = title },
                        text = { Text(title, color = if (index == selectedTabIndex) VarnikaAccent else VarnikaSecondaryAccent) }
                    )
                }
            }
            
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(products) { product ->
                    ProductCard(
                        product = product,
                        viewModel = viewModel,
                        onClick = { onProductClick(product.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun ProductCard(product: Product, viewModel: ShopViewModel, onClick: () -> Unit) {
    val isWishlisted by viewModel.isWishlisted(product.id).collectAsState(initial = false)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = VarnikaSecondary),
        border = BorderStroke(1.dp, VarnikaAccent.copy(alpha = 0.1f))
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
            ) {
                Image(
                    painter = painterResource(id = product.imageRes),
                    contentDescription = product.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                IconButton(
                    onClick = { viewModel.toggleWishlist(product, isWishlisted) },
                    modifier = Modifier.align(Alignment.TopEnd).padding(4.dp)
                ) {
                    Icon(
                        if (isWishlisted) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder,
                        contentDescription = "Save",
                        tint = if (isWishlisted) VarnikaAccent else VarnikaPrimary
                    )
                }
            }
            Column(modifier = Modifier.padding(12.dp)) {
                Text(product.name, style = MaterialTheme.typography.titleMedium, color = VarnikaText, maxLines = 1)
                Spacer(modifier = Modifier.height(4.dp))
                Text(product.brand, style = MaterialTheme.typography.bodySmall, color = VarnikaSecondaryAccent, maxLines = 1)
                Spacer(modifier = Modifier.height(8.dp))
                Text("₹${product.price}", style = MaterialTheme.typography.titleMedium, color = VarnikaAccent)
            }
        }
    }
}
