package com.icanerdogan.androidtestapp.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.icanerdogan.androidtestapp.model.Art
import com.icanerdogan.androidtestapp.model.ImageResponse
import com.icanerdogan.androidtestapp.repository.ArtRepositoryInterface
import com.icanerdogan.androidtestapp.util.Resource

// Network işlemleriyle uğraşamak için burada verilerle oynayacağız!
class FakeArtRepositoryTest : ArtRepositoryInterface {
    private val arts = mutableListOf<Art>()
    private val artsLiveData = MutableLiveData<List<Art>>(arts)

    override suspend fun insertArt(art: Art) {
        arts.add(art)
        refreshData()
    }

    override suspend fun deleteArt(art: Art) {
        arts.remove(art)
        refreshData()
    }

    override fun getArt(): LiveData<List<Art>> {
        return artsLiveData
    }

    override suspend fun searchImage(imageString: String): Resource<ImageResponse> {
        return Resource.success(ImageResponse(listOf(),0,0))
    }

    private fun refreshData(){
        artsLiveData.postValue(arts)
    }
}