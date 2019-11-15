package dev.brevitz.nike.library.ui

import androidx.annotation.IdRes
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.Matchers.allOf

fun viewIsShown(@IdRes id: Int): ViewInteraction = onView(withId(id)).check(matches(isDisplayed()))

fun textIsDisplayed(text: String): ViewInteraction = onView(withText(text)).check(matches(isDisplayed()))

fun assertChildWithText(text: String): ViewInteraction = onView(allOf(withText(text), isDescendantOfA(withId(R.id.rosterViewPager))))
    .check(matches(isSelected()))
