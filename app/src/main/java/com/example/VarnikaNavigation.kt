package com.example

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ui.screens.*
import com.example.viewmodel.ShopViewModel

enum class VarnikaScreens {
    Login,
    Dashboard,
    Upload,
    Analysis,
    Customization,
    Marketplace,
    ProductDetail,
    Wishlist,
    Cart,
    About,
    Contact,
    Admin
}

@Composable
fun VarnikaApp(viewModel: ShopViewModel = viewModel()) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = VarnikaScreens.Login.name,
        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(300)) },
        exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(300)) },
        popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(300)) },
        popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(300)) }
    ) {
        composable(route = VarnikaScreens.Login.name) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(VarnikaScreens.Dashboard.name) {
                        popUpTo(VarnikaScreens.Login.name) { inclusive = true }
                    }
                }
            )
        }
        composable(route = VarnikaScreens.Dashboard.name) {
            DashboardScreen(
                onNavigateToUpload = { navController.navigate(VarnikaScreens.Upload.name) },
                onNavigateToMarketplace = { navController.navigate(VarnikaScreens.Marketplace.name) },
                onNavigateToAbout = { navController.navigate(VarnikaScreens.About.name) },
                onNavigateToContact = { navController.navigate(VarnikaScreens.Contact.name) },
                onNavigateToAdmin = { navController.navigate(VarnikaScreens.Admin.name) }
            )
        }
        composable(route = VarnikaScreens.Upload.name) {
            UploadScreen(
                onBack = { navController.popBackStack() },
                onAnalyze = { navController.navigate(VarnikaScreens.Analysis.name) }
            )
        }
        composable(route = VarnikaScreens.Analysis.name) {
            AnalysisScreen(
                onBack = { navController.popBackStack() },
                onShopNow = { navController.navigate(VarnikaScreens.Marketplace.name) },
                onCustomize = { navController.navigate(VarnikaScreens.Customization.name) }
            )
        }
        composable(route = VarnikaScreens.Customization.name) {
            CustomizationScreen(onBack = { navController.popBackStack() })
        }
        composable(
            route = "${VarnikaScreens.Marketplace.name}?category={category}",
            arguments = listOf(navArgument("category") { 
                type = NavType.StringType
                defaultValue = "All"
            })
        ) { backStackEntry ->
            val category = backStackEntry.arguments?.getString("category") ?: "All"
            MarketplaceScreen(
                viewModel = viewModel,
                initialCategory = category,
                onBack = { navController.popBackStack() },
                onProductClick = { productId ->
                    navController.navigate("${VarnikaScreens.ProductDetail.name}/$productId")
                },
                onNavigateToCart = { navController.navigate(VarnikaScreens.Cart.name) },
                onNavigateToWishlist = { navController.navigate(VarnikaScreens.Wishlist.name) }
            )
        }
        composable(
            route = "${VarnikaScreens.ProductDetail.name}/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.StringType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: return@composable
            ProductDetailScreen(
                viewModel = viewModel,
                productId = productId,
                onBack = { navController.popBackStack() },
                onNavigateToCart = { navController.navigate(VarnikaScreens.Cart.name) }
            )
        }
        composable(route = VarnikaScreens.Wishlist.name) {
            WishlistScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onProductClick = { productId ->
                    navController.navigate("${VarnikaScreens.ProductDetail.name}/$productId")
                }
            )
        }
        composable(route = VarnikaScreens.Cart.name) {
            CartScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
        composable(route = VarnikaScreens.About.name) {
            AboutScreen(onBack = { navController.popBackStack() })
        }
        composable(route = VarnikaScreens.Contact.name) {
            ContactScreen(onBack = { navController.popBackStack() })
        }
        composable(route = VarnikaScreens.Admin.name) {
            AdminScreen(onBack = { navController.popBackStack() })
        }
    }
}
