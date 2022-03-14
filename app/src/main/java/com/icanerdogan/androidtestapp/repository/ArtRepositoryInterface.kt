package com.icanerdogan.androidtestapp.repository

import androidx.lifecycle.LiveData
import com.icanerdogan.androidtestapp.model.Art
import com.icanerdogan.androidtestapp.model.ImageResponse
import com.icanerdogan.androidtestapp.util.Resource

// Bu sınıfı test yapacağımız için oluşturduk. Arayüzler sayesinde testleri daha kolay uygulayabiliriz.

// Test ortamında daha stabil bir ortam yaratmak için suspend fonksiyonları veya internetten veri çekme gibi olaylar olmaz
// asıl amaç uygulamamnın çalışıp çalışmasıdır.

//Testlerde networking ve threading işlemleri yapılmaz. Mesela Retrofitten tüm veriler gelmiş gibi davranılır.
interface ArtRepositoryInterface {
    suspend fun insertArt(art : Art)

    suspend fun deleteArt(art: Art)

    fun getArt() : LiveData<List<Art>>

    suspend fun searchImage(imageString: String) : Resource<ImageResponse>
}