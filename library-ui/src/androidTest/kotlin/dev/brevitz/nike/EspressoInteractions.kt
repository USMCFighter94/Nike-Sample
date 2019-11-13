package dev.brevitz.nike

import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf

fun clickItemAtPosition(@IdRes resId: Int, position: Int) = clickItemAtPosition(withId(resId), position)

fun clickItemAtPosition(viewMatcher: Matcher<View>, position: Int): ViewInteraction = onView(viewMatcher)
    .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(position, click()))

fun clickTabMatching(@IdRes tabHostId: Int, matcher: Matcher<View>): ViewInteraction =
    onView(allOf(matcher, isDescendantOfA(withId(tabHostId)))).perform(click())
