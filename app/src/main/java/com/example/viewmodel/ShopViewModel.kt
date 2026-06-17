package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.AppDatabase
import com.example.data.CartEntity
import com.example.data.DummyData
import com.example.data.Product
import com.example.data.WishlistEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ShopViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getDatabase(application)
    private val wishlistDao = db.wishlistDao()
    private val cartDao = db.cartDao()

    val products = DummyData.products

    val wishlistItems: StateFlow<List<WishlistEntity>> = wishlistDao.getAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val cartItems: StateFlow<List<CartEntity>> = cartDao.getAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun isWishlisted(productId: String) = wishlistDao.isWishlisted(productId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    fun toggleWishlist(product: Product, isWishlisted: Boolean) {
        viewModelScope.launch {
            if (isWishlisted) {
                wishlistDao.deleteById(product.id)
            } else {
                wishlistDao.insert(WishlistEntity(product.id, product.name, product.price, product.brand, product.category, product.imageRes))
            }
        }
    }
    
    fun removeFromWishlist(productId: String) {
        viewModelScope.launch {
            wishlistDao.deleteById(productId)
        }
    }

    fun addToCart(product: Product) {
        viewModelScope.launch {
            // Check if already in cart
            val existing = cartItems.value.find { it.productId == product.id }
            if (existing != null) {
                cartDao.updateQuantity(product.id, existing.quantity + 1)
            } else {
                cartDao.insert(CartEntity(product.id, product.name, product.price, product.brand, product.category, product.imageRes, 1))
            }
        }
    }
    
    fun updateCartQuantity(productId: String, newQuantity: Int) {
        viewModelScope.launch {
            if (newQuantity <= 0) {
                cartDao.deleteById(productId)
            } else {
                cartDao.updateQuantity(productId, newQuantity)
            }
        }
    }

    fun removeFromCart(productId: String) {
        viewModelScope.launch {
            cartDao.deleteById(productId)
        }
    }
}
