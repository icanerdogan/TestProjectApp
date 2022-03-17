package com.icanerdogan.androidtestapp.room

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.icanerdogan.androidtestapp.model.Art
import com.icanerdogan.androidtestapp.service.ArtDao
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

// Small Test -> Unit Test
// Medium Test -> Integration Test
// Large Test -> UI Test
@SmallTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class ArtDaoTest {
    @get: Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get: Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("testDatabase")
    lateinit var database: ArtDatabase

    private lateinit var dao : ArtDao

    @Before
    fun setup(){
        // inMemoryDatabaseBuilder : RAM'de saklıyor ve siliniyor! Testler için kullanılır
        /*database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), ArtDatabase::class.java
        ).allowMainThreadQueries().build()*/
        hiltRule.inject()
        dao = database.artDao()
    }

    @After
    fun teardown(){
        database.close()
    }

    @Test
    fun insertArtTesting() = runBlockingTest{
        val exampleArt = Art("Mona Lisa", "Da Vinci", 1700, "test",1)
        dao.insertArt(exampleArt)

        // Eklenmiş mi eklenmemiş mi kontrol edilen kısım Burada da LiveDataTestingUtil ile livedatayı çeviriyoruz!
        val  list = dao.observeArts().getOrAwaitValue()
        assertThat(list).contains(exampleArt)
    }

    @Test
    fun deleteArtTesting() = runBlockingTest{
        val deleteArt = Art("Scream", "Munch",1800,"test.com",2)
        dao.insertArt(deleteArt)
        dao.deleteArt(deleteArt)

        val  list = dao.observeArts().getOrAwaitValue()
        // Var mı kontrolü!
        assertThat(list).doesNotContain(deleteArt)
    }

}