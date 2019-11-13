package dev.brevitz.nike

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.Matchers.allOf

fun textIsDisplayed(text: String): ViewInteraction = onView(withText(text)).check(matches(isDisplayed()))

fun assertTabWithTextIsSelected(tab: TabName): ViewInteraction =
    onView(allOf(withText(tab.title), isDescendantOfA(withId(R.id.mainTabLayout))))
        .check(matches(isSelected()))

fun assertChildWithText(text: String): ViewInteraction = onView(allOf(withText(text), isDescendantOfA(withId(R.id.matchRecyclerView))))
    .check(matches(isSelected()))
