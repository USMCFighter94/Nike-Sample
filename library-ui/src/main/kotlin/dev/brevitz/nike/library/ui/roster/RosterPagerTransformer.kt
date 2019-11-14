package dev.brevitz.nike.library.ui.roster

import android.content.res.Resources
import android.view.View
import androidx.core.view.ViewCompat
import androidx.viewpager2.widget.ViewPager2
import dev.brevitz.nike.library.ui.R
import kotlin.math.abs
import kotlin.math.max

internal class RosterPagerTransformer(resources: Resources) : ViewPager2.PageTransformer {
    private val pageMargin = resources.getDimensionPixelOffset(R.dimen.pageMargin)
    private val pageOffset = resources.getDimensionPixelOffset(R.dimen.offset)

    override fun transformPage(page: View, position: Float) {
        val offset = position * -(2 * pageOffset + pageMargin)

        page.translationX = if (ViewCompat.getLayoutDirection(page) == ViewCompat.LAYOUT_DIRECTION_RTL) {
            -offset
        } else {
            offset
        }

        when {
            position <= 1 -> {
                val scale = max(MIN_SCALE, 1 - abs(position))
                page.scaleX = scale
                page.scaleY = scale
                page.alpha = max(MIN_ALPHA, 1 - abs(position))
            }
            else -> page.alpha = 0f
        }
    }

    private companion object {
        private const val MIN_SCALE = 0.8f
        private const val MIN_ALPHA = 0.5f
    }
}
