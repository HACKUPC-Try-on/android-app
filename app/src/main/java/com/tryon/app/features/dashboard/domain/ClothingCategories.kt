package com.tryon.app.features.dashboard.domain

import androidx.annotation.DrawableRes
import com.tryon.app.R

sealed class ClothingCategories(
    val id: String,
    @DrawableRes val iconRes: Int
) {
    class Shirt : ClothingCategories(id = SHIRT_CODE, R.drawable.ic_shirt)
    class Pants : ClothingCategories(id = PANTS_CODE, R.drawable.ic_pants)
    class Bag : ClothingCategories(id = BAG_CODE, R.drawable.ic_bag)
    class Shoes : ClothingCategories(id = SHOES_CODE, R.drawable.ic_shoes)
    class Sweater : ClothingCategories(id = SWEATER_CODE, R.drawable.ic_sweater)

    companion object {
        const val SHIRT_CODE = "shirt"
        const val PANTS_CODE = "pants"
        const val BAG_CODE = "bag"
        const val SHOES_CODE = "shoes"
        const val SWEATER_CODE = "sweater"

        fun getCategoryFromCode(code: String): ClothingCategories =
            when(code) {
                SHIRT_CODE -> Shirt()
                PANTS_CODE -> Pants()
                BAG_CODE -> Bag()
                SHOES_CODE -> Shoes()
                SWEATER_CODE -> Sweater()
                else -> throw Throwable(message = "Category not recognized")
            }
    }
}
