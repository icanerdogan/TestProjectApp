package com.icanerdogan.androidtestapp.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.icanerdogan.androidtestapp.adapter.ArtRecyclerAdapter
import com.icanerdogan.androidtestapp.adapter.ImageRecyclerAdapter
import javax.inject.Inject

// Bu sınıfta yani FragmentFactory sınıfında diğer sınıflar için Inject edilecek şeyler yazılır!
class ArtFragmentFactory @Inject constructor(
    private val artRecyclerAdapter: ArtRecyclerAdapter,
    // Burada Glide Inject edilirken RequestManager kullanılıyor kenisine has bir özellik!
    private val glide : RequestManager,
    private val imageRecyclerAdapter: ImageRecyclerAdapter
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){
            ArtFragment::class.java.name -> ArtFragment(artRecyclerAdapter)
            ImageAPIFragment::class.java.name -> ImageAPIFragment(imageRecyclerAdapter)
            ArtDetailsFragment::class.java .name-> ArtDetailsFragment(glide)
            else -> return super.instantiate(classLoader, className)
        }

    }
}