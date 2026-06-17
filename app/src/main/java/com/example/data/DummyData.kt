package com.example.data

import com.example.R

data class Product(
    val id: String,
    val name: String,
    val brand: String,
    val category: String,
    val material: String,
    val details: String,
    val price: Double,
    val rating: Double,
    val imageRes: Int,
    val relatedImages: List<Int> = listOf()
)

object DummyData {
    val products = listOf(
        Product(
            id = "f_sofa_1",
            name = "Aura L-Shape Sofa",
            brand = "Varnika Exclusive",
            category = "Furniture",
            material = "Premium Leather",
            details = "A highly comfortable luxury L-shaped living room sofa with an elegant minimal design, warm neutral colors, and soft lighting.",
            price = 54999.0,
            rating = 4.9,
            imageRes = R.drawable.product_sofa_1781689707160
        ),
        Product(
            id = "f_chair_1",
            name = "Nordic Lounge Chair",
            brand = "Scandi Studio",
            category = "Furniture",
            material = "Oak Wood & Linen",
            details = "A minimalist luxury lounge chair, elegant, modern design, professional studio lighting, neutral background.",
            price = 12499.0,
            rating = 4.8,
            imageRes = R.drawable.img_product_chair_1781688649403
        ),
        Product(
            id = "f_chair_2",
            name = "Executive Modern Office Chair",
            brand = "WorkSpace",
            category = "Furniture",
            material = "Mesh & Aluminum",
            details = "Ergonomic modern office chair for long working hours.",
            price = 8999.0,
            rating = 4.5,
            imageRes = R.drawable.img_product_chair_1781688649403
        ),
        Product(
            id = "d_plant_1",
            name = "Lush Monstera",
            brand = "Nature Inside",
            category = "Decorations",
            material = "Ceramic Pot",
            details = "A photorealistic lush indoor potted Monstera plant, luxury ceramic pot, modern interior styling.",
            price = 2499.0,
            rating = 4.7,
            imageRes = R.drawable.product_plant_1781689765006
        ),
        Product(
            id = "l_lamp_1",
            name = "Halo Brass Floor Lamp",
            brand = "Lumina",
            category = "Lighting",
            material = "Brass & Frosted Glass",
            details = "A photorealistic modern elegant floor lamp, brass and warm light, minimal design, luxury interior style.",
            price = 4999.0,
            rating = 4.9,
            imageRes = R.drawable.product_lamp_1781689725459
        ),
        Product(
            id = "r_rug_1",
            name = "Earthy Texture Area Rug",
            brand = "WeaveWorks",
            category = "Rugs",
            material = "Wool & Cotton",
            details = "A photorealistic textured modern abstract area rug, luxury minimal design, warm earthy tones.",
            price = 6999.0,
            rating = 4.6,
            imageRes = R.drawable.product_rug_1781689750855
        ),
        Product(
            id = "s_storage_1",
            name = "Minimalist Media Console",
            brand = "Varnika Exclusive",
            category = "Storage",
            material = "Walnut Wood",
            details = "Low profile media console with soft-close drawers and elegant modern details.",
            price = 15499.0,
            rating = 4.8,
            imageRes = R.drawable.img_product_chair_1781688649403 // Placeholder
        ),
        Product(
            id = "a_art_1",
            name = "Abstract Horizon Line",
            brand = "Gallery V",
            category = "Paintings",
            material = "Canvas & Oak Frame",
            details = "A minimalist abstract wall painting in a frame.",
            price = 5999.0,
            rating = 4.5,
            imageRes = R.drawable.img_hero_room_1781688636581 // Placeholder
        )
    )

    fun getProductsByCategory(category: String): List<Product> {
        if (category == "All") return products
        return products.filter { it.category == category }
    }
}
