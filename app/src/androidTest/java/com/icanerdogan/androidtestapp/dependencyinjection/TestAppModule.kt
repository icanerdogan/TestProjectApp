package com.icanerdogan.androidtestapp.dependencyinjection

import android.content.Context
import androidx.room.Room
import com.icanerdogan.androidtestapp.room.ArtDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    // Named ile ArtDao'ya da eklendiğinde neyin Inject edileceğini gösteriyoruz!
    @Provides
    @Named("testDatabase")
    fun injectInMemoryRoom(@ApplicationContext context: Context) =
        Room.inMemoryDatabaseBuilder(context, ArtDatabase::class.java)
            .allowMainThreadQueries()
            .build()


}