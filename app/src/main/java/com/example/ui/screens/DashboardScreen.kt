package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.ui.theme.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onNavigateToUpload: () -> Unit,
    onNavigateToMarketplace: () -> Unit,
    onNavigateToAbout: () -> Unit,
    onNavigateToContact: () -> Unit,
    onNavigateToAdmin: () -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(drawerContainerColor = VarnikaSecondary) {
                Spacer(Modifier.height(32.dp))
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.logo_varnika_1781689689188),
                        contentDescription = "Logo",
                        modifier = Modifier.size(48.dp).clip(MaterialTheme.shapes.small)
                    )
                    Spacer(Modifier.width(16.dp))
                    Text("Varnika", style = MaterialTheme.typography.titleLarge, color = VarnikaText)
                }
                Divider(color = VarnikaPrimary)
                NavigationDrawerItem(label = { Text("Home") }, selected = true, onClick = { scope.launch { drawerState.close() } }, colors = NavigationDrawerItemDefaults.colors(selectedContainerColor = VarnikaPrimary, unselectedContainerColor = VarnikaSecondary))
                NavigationDrawerItem(label = { Text("About Varnika") }, selected = false, onClick = { scope.launch { drawerState.close() }; onNavigateToAbout() }, colors = NavigationDrawerItemDefaults.colors(unselectedContainerColor = VarnikaSecondary))
                NavigationDrawerItem(label = { Text("Contact Us") }, selected = false, onClick = { scope.launch { drawerState.close() }; onNavigateToContact() }, colors = NavigationDrawerItemDefaults.colors(unselectedContainerColor = VarnikaSecondary))
                NavigationDrawerItem(label = { Text("Admin Dashboard") }, selected = false, onClick = { scope.launch { drawerState.close() }; onNavigateToAdmin() }, colors = NavigationDrawerItemDefaults.colors(unselectedContainerColor = VarnikaSecondary))
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = R.drawable.logo_varnika_1781689689188),
                                contentDescription = "Logo",
                                modifier = Modifier.size(32.dp).clip(MaterialTheme.shapes.small)
                            )
                            Spacer(Modifier.width(12.dp))
                            Text("Varnika", style = MaterialTheme.typography.titleLarge)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = VarnikaPrimary),
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Rounded.Menu, contentDescription = "Menu")
                        }
                    },
                    actions = {
                        IconButton(onClick = { /* TODO */ }) {
                            Icon(Icons.Rounded.Notifications, contentDescription = "Alerts")
                        }
                        IconButton(onClick = { /* TODO */ }) {
                            Icon(Icons.Rounded.AccountCircle, contentDescription = "Profile")
                        }
                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = onNavigateToUpload,
                    containerColor = VarnikaAccent,
                    contentColor = VarnikaPrimary
                ) {
                    Icon(Icons.Filled.Add, "Upload Room")
                }
            },
            containerColor = VarnikaPrimary
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                Text(
                    text = "Welcome to Varnika",
                    style = MaterialTheme.typography.headlineLarge,
                    color = VarnikaText
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Your Room, Reimagined by AI",
                    style = MaterialTheme.typography.bodyMedium,
                    color = VarnikaSecondaryAccent
                )
                
                Spacer(modifier = Modifier.height(32.dp))
                Text("Recent Designs", style = MaterialTheme.typography.titleMedium, color = VarnikaText)
                Spacer(modifier = Modifier.height(16.dp))
                
                LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    items(3) { index ->
                        Card(
                            modifier = Modifier
                                .width(280.dp)
                                .height(180.dp)
                                .clickable { },
                            shape = MaterialTheme.shapes.large,
                            colors = CardDefaults.cardColors(containerColor = VarnikaSecondary),
                            border = BorderStroke(1.dp, VarnikaAccent.copy(alpha = 0.1f))
                        ) {
                            Image(
                                painter = painterResource(if (index == 0) R.drawable.img_room_after_1781688680567 else R.drawable.img_hero_room_1781688636581),
                                contentDescription = "Designed Room",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(32.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Curated for You", style = MaterialTheme.typography.titleMedium, color = VarnikaText)
                    TextButton(onClick = onNavigateToMarketplace) {
                        Text("Shop All", color = VarnikaAccent)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                
                LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    val recommendedProducts = com.example.data.DummyData.products.shuffled().take(6)
                    items(recommendedProducts.size) { index ->
                        val product = recommendedProducts[index]
                        Card(
                            modifier = Modifier
                                .width(280.dp)
                                .clickable { },
                            shape = MaterialTheme.shapes.large,
                            colors = CardDefaults.cardColors(containerColor = VarnikaSecondary),
                            border = BorderStroke(1.dp, VarnikaAccent.copy(alpha = 0.1f))
                        ) {
                            Column {
                                Image(
                                    painter = painterResource(id = product.imageRes),
                                    contentDescription = product.name,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxWidth().height(160.dp)
                                )
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                                        Text(product.name, style = MaterialTheme.typography.titleMedium, color = VarnikaText)
                                        Text("${(85..99).random()}% Match", style = MaterialTheme.typography.labelSmall, color = VarnikaAccent)
                                    }
                                    Text(product.brand, style = MaterialTheme.typography.bodySmall, color = VarnikaSecondaryAccent)
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text("₹${product.price}", style = MaterialTheme.typography.titleMedium, color = VarnikaAccent)
                                    
                                    Spacer(modifier = Modifier.height(12.dp))
                                    Text("AI matched because: ${product.material} complements your new layout.", style = MaterialTheme.typography.bodySmall, color = VarnikaSecondaryAccent)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
