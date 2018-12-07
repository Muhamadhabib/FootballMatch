package com.example.habib.teamfav.home

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.example.habib.teamfav.R.id.*
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeActivityTest{
    @Rule
    @JvmField var activityRule = ActivityTestRule(HomeActivity::class.java)
    @Test
    fun testHomeActivity(){
        delay()
        onView(withId(spinner))
                .check(matches(isDisplayed()))
        onView(withId(spinner)).perform(click())
        onView(withText("Spanish La Liga")).perform(click())
        delay()
        onView(withId(listPrev))
                .check(matches(isDisplayed()))
        delay()
        onView(withId(listPrev)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(1))
        onView(withId(listPrev)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()))
        delay()
        onView(withId(add_fav)).check(matches(isDisplayed()))
        delay()
        onView(withId(add_fav)).perform(click())
        pressBack()
        delay()
        onView(withId(bottom_navigation)).check(matches(isDisplayed()))
        onView(withId(fav)).perform(click())
        delay()

        onView(withId(bottom_navigation)).check(matches(isDisplayed()))
        onView(withId(next)).perform(click())
        delay()
        onView(withId(spinner))
                .check(matches(isDisplayed()))
        onView(withId(spinner)).perform(click())
        onView(withText("Spanish La Liga")).perform(click())
        delay()
        onView(withId(listNext))
                .check(matches(isDisplayed()))
        delay()
        onView(withId(listNext)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(1))
        onView(withId(listNext)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()))
        delay()
        onView(withId(add_fav)).check(matches(isDisplayed()))
        delay()
        onView(withId(add_fav)).perform(click())
        pressBack()
        delay()
        onView(withId(bottom_navigation)).check(matches(isDisplayed()))
        onView(withId(fav)).perform(click())
        delay()

        onView(withId(bottom_navigation)).check(matches(isDisplayed()))
        onView(withId(team)).perform(click())
        delay()
        onView(withId(spinner))
                .check(matches(isDisplayed()))
        onView(withId(spinner)).perform(click())
        onView(withText("Spanish La Liga")).perform(click())
        delay()
        onView(withId(listEvent))
                .check(matches(isDisplayed()))
        delay()
        onView(withId(listEvent)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(1))
        onView(withId(listEvent)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()))
        delay()
        onView(withId(add_fav)).check(matches(isDisplayed()))
        delay()
        onView(withId(add_fav)).perform(click())
        pressBack()
        delay()
        onView(withId(bottom_navigation)).check(matches(isDisplayed()))
        onView(withId(favo)).perform(click())
        delay()
    }

    private fun delay(){
        try {
            Thread.sleep(2000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}