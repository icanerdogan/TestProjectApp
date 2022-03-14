package com.icanerdogan.androidtestapp.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import javax.inject.Inject

// Bu sınıfta yani FragmentFactory sınıfında diğer sınıflar için Inject edilecek şeyler yazılır!
class ArtFragmentFactory @Inject constructor(
    // Burada Glide Inject edilirken RequestManager kullanılıyor kenisine has bir özellik!
    private val glide : RequestManager
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){
            ArtDetailsFragment::class.java .name-> ArtDetailsFragment(glide)
            else -> return super.instantiate(classLoader, className)
        }

    }
}