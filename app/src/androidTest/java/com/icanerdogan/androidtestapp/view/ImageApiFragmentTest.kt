package com.icanerdogan.androidtestapp.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import com.icanerdogan.androidtestapp.R
import com.icanerdogan.androidtestapp.adapter.ImageRecyclerAdapter
import com.icanerdogan.androidtestapp.launchFragmentInHiltContainer
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
class ImageApiFragmentTest {
    @get: Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get: Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var fragmentFactory: ArtFragmentFactory

    @Before
    fun setup(){
        hiltRule.inject()
    }

    @Test
    fun selectImage(){
        val navController = Mockito.mock(NavController::class.java)
        val testViewModel = ArtViewModel(FakeArtRepositoryTest())
        val selectedImageUrl = "ibrahimcanerdogan.com"

        launchFragmentInHiltContainer<ImageAPIFragment>(
            factory =  fragmentFactory
        ){
            Navigation.setViewNavController(requireView(), navController)
            viewModel = testViewModel
            // Burada imagerecyclerview içinde boş bir eleman var!
            imageRecyclerAdapter.images = listOf(selectedImageUrl)
        }

        Espresso.onView(withId(R.id.imageApiRecyclerView)).perform(
            // RecyclerView içindeki bir pozisyona göre işlem yapma!
            RecyclerViewActions.actionOnItemAtPosition<ImageRecyclerAdapter.ImageViewHolder>(
                0, click()
            )
        )
        // o boş elemana tıklandığında geri dönmesi gerekiyor!
        Mockito.verify(navController).popBackStack()

        assertThat(testViewModel.selectedImageUrl.getOrAwaitValue()).isEqualTo(selectedImageUrl)
    }
}