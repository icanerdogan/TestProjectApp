package com.icanerdogan.androidtestapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// Hilt entegrasyonunu Retrofit ve Room Builder için yapıyoruz! Her yerde tekrar oluşturmamak içindir!
// Hilt başlangıç sınıfı burası.
// Manifest içine eklemeyi unutma!
@HiltAndroidApp
class ArtBookApplication : Application() {}