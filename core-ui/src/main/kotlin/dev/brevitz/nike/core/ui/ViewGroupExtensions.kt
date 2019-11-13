package dev.brevitz.nike.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View =
    LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)

inline fun <reified T : View> ViewGroup.inflateAs(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): T =
    inflate(layoutRes, attachToRoot) as T
