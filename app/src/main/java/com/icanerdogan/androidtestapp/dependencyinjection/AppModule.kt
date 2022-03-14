package com.icanerdogan.androidtestapp.dependencyinjection

import android.content.Context
import androidx.room.Room
import com.icanerdogan.androidtestapp.API.RetrofitAPI
import com.icanerdogan.androidtestapp.room.ArtDatabase
import com.icanerdogan.androidtestapp.util.Util.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

// Buradaki Fonksiyonlar istenilen yerde kullanılabilir!

// SingletonComponent: Olması durumunda tüm aktivite boyunca hayatta kalmasını sağlarız!
@Module
@InstallIn(SingletonComponent::class)
object AppModule  {

    @Singleton
    @Provides
    fun injectRoomDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        ArtDatabase::class.java,
        "ArtBookDB"
    ).build()

    @Singleton
    @Provides
    fun injectDao(database: ArtDatabase) = database.artDao()

    @Singleton
    @Provides
    fun injectRetrofitAPI() : RetrofitAPI{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(RetrofitAPI::class.java)
    }


}