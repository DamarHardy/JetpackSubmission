package com.damar.jetpacksubmission.ui.home.misc

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.damar.jetpacksubmission.R

class PageTransformer : ViewPager2.PageTransformer {
    private lateinit var imagePoster: View
    private lateinit var imageBackdrop: View
    override fun transformPage(page: View, position: Float) {
        val pageWidth = page.width
        imagePoster = page.findViewById(R.id.image_poster)
        imageBackdrop = page.findViewById(R.id.image_backdrop)
        page.apply {
            if (position <= 1 && position >= -1){
                imageBackdrop.translationX = -position*(pageWidth/2)
            }
        }
    }
}