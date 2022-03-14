package com.icanerdogan.androidtestapp.service

import androidx.lifecycle.LiveData
import androidx.room.*
import com.icanerdogan.androidtestapp.model.Art

@Dao
interface ArtDao {
    // OnConflictStrategy Mesela aynı ID'ye sahipler gelirse ne yapmamız gerektiğini söylüyoruz!
    // Ignore: Göz ardı eder
    // Abort: İptal eder
    // Replace: Yerine yazar. Aynı ID'de birden fazla şey yazarsak yerine yazar.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArt(art : Art)

    @Delete
    suspend fun deleteArt(art: Art)

    // suspend yazmaya gerek yok çünkü LiveData kendisi suspend olarak çalışır!
    @Query("SELECT * FROM arts")
    fun observeArts() : LiveData<List<Art>>


}