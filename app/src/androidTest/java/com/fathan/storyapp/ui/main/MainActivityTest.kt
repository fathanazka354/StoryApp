package com.fathan.storyapp.ui.main

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.filters.MediumTest
import com.fathan.storyapp.JsonConverter
import com.fathan.storyapp.R
import com.fathan.storyapp.data.remote.ApiConfig.Companion.BASE_URL
import com.fathan.storyapp.utils.EspressoIdlingResource
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.isClickable
import com.fathan.storyapp.ui.detail.DetailStoryActivity

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {

    @get:Rule
    val rule = ActivityScenarioRule(MainActivity::class.java)

    private val mockWebServer = MockWebServer()


    @Before
    fun setUp() {
        ActivityScenario.launch(MainActivity::class.java)
        mockWebServer.start(8080)
        BASE_URL = "http://127.0.0.1:8080/"
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }
    @After
    fun tearDown() {
        mockWebServer.shutdown()
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }


    @Test
    fun getStory() {
        Espresso.onView(withId(R.id.rv_story))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(withId(R.id.rv_story))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(5))
    }

    @Test
    fun getListStory_ToMapView() {

        Espresso.onView(withId(R.id.btn_map))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.btn_map)).perform(ViewActions.click())
        Espresso.pressBack()
    }

//    @Test
//    fun loadDetailStory(){
//        Intents.init()
//        Espresso.onView(withId(R.id.rv_story))
//            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.click()))
//        intended(hasComponent(DetailStoryActivity::class.java.simpleName))
//        Espresso.onView(withId(R.id.detail_story))
//            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
//    }

//    @Test
//    fun loadDetailNews() {
//        Intents.init()
//        Espresso.onView(withId(R.id.rv_story)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,
//            ViewActions.click()
//        ))
//        intended(hasComponent(DetailStoryActivity::class.java.name))
//        Espresso.onView(withId(R.id.detail_story))
//            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
//    }

}