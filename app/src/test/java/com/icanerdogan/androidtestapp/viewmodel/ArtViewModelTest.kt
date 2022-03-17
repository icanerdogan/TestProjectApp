package com.icanerdogan.androidtestapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.icanerdogan.androidtestapp.MainCoroutineRule
import com.icanerdogan.androidtestapp.getOrAwaitValueTest
import com.icanerdogan.androidtestapp.repo.FakeArtRepository
import com.icanerdogan.androidtestapp.util.Status
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ArtViewModelTest {
    private lateinit var viewModel: ArtViewModel

    @get: Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get: Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup(){
        viewModel = ArtViewModel(FakeArtRepository())
    }

    @Test
    fun `insert art without year returns error`(){
        viewModel.makeArt("MonaLisa", "Da Vinci", "")
        // insertArtMessage ile dönecek olan livedatayı getOrAwaitValueTest ile çeviriyorum!
        val value = viewModel.insertArtMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }
    @Test
    fun `insert art without name returns error`(){
        viewModel.makeArt("", "Da Vinci", "2342")
        val value = viewModel.insertArtMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.SUCCESS)
    }
    @Test
    fun `insert art without artist name returns error`(){
        viewModel.makeArt("MonaLisa", "", "5673")
        val value = viewModel.insertArtMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.SUCCESS)
    }
}