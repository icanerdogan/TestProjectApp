package com.icanerdogan.androidtestapp.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.FragmentFactory
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import com.icanerdogan.androidtestapp.R
import com.icanerdogan.androidtestapp.launchFragmentInHiltContainer
import com.icanerdogan.androidtestapp.model.Art
import com.icanerdogan.androidtestapp.repo.FakeArtRepositoryTest
import com.icanerdogan.androidtestapp.room.getOrAwaitValue
import com.icanerdogan.androidtestapp.viewmodel.ArtViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import javax.inject.Inject

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class ArtDetailsFragmentTest {
    @get: Rule
    var hiltRule = HiltAndroidRule(this)

    @get: Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var fragmentFactory: ArtFragmentFactory

    @Before
    fun setup(){
        hiltRule.inject()
    }
    @Test
    fun testNavigationFromArtDetailsToImageAPI(){
        val navController = Mockito.mock(NavController::class.java)

        launchFragmentInHiltContainer<ArtDetailsFragment>(
            factory = FragmentFactory()
        ){
            Navigation.setViewNavController(requireView(), navController)
        }

        Espresso.onView(ViewMatchers.withId(R.id.artDetailImageView)).perform(click())

        Mockito.verify(navController).navigate(
            ArtDetailsFragmentDirections.actionArtDetailsFragmentToImageAPIFragment()
        )
    }

    @Test
    fun testOnBackPressed(){
        val navController = Mockito.mock(NavController::class.java)

        launchFragmentInHiltContainer<ArtDetailsFragment>(
            factory = FragmentFactory()
        ){
            Navigation.setViewNavController(requireView(), navController)
        }
        // Geri Tuşuna bas!
        pressBack()
        // Geri gitti mi?
        Mockito.verify(navController).popBackStack()
    }

    @Test
    fun testSave(){
        val testViewModel = ArtViewModel(FakeArtRepositoryTest())
        launchFragmentInHiltContainer<ArtDetailsFragment>(
            factory = fragmentFactory
        ){
            viewModel = testViewModel
        }

        // Burada Obje oluşturduk!
        Espresso.onView(withId(R.id.artDetailArtNameEditText)).perform(replaceText("Attempt Name"))
        Espresso.onView(withId(R.id.artDetailArtistNameEditText)).perform(replaceText("Attempt Artist Name"))
        Espresso.onView(withId(R.id.artDetailYearEditText)).perform(replaceText("1500"))
        //Save butonuna tıklandığında
        Espresso.onView(withId(R.id.artDetailSaveButton)).perform(click())
        // view model içindeki listede bu değer var mı?
        assertThat(testViewModel.artList.getOrAwaitValue()).contains(
            Art("Attempt Name","Attempt Artist Name",1500,"")
        )
    }
}