package com.bignerdranch.android.photogallery

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.bignerdranch.android.photogallery.api.FlickrFetchr
import com.bignerdranch.android.photogallery.data.GalleryItem
//Функция FlickrFetchr().fetchPhotos() вызывается в
//блоке init{} модели PhotoGalleryViewModel. Она запускает
//запрос данных фото при первом создании ViewModel. Так как
//ViewModel создается только один рз в течение жизненного
//цикла владельца (при первом запросе из класса
//ViewModelProviders), запрос выполнится только один раз
//(при запуске пользователем PhotoGalleryFragment). Когда
//пользователь поворачивает устройство или иным образом
//инициирует изменение конфигурации, ViewModel останется в
//памяти, а воссозданная версия фрагмента сможет получить
//доступ к результатам оригинального запроса через ViewModel.
class PhotoGalleryViewModel(private val app: Application) : AndroidViewModel(app) {
    val galleryItemLiveData: LiveData<List<GalleryItem>>
    private val flickrFetchr = FlickrFetchr()
    private val mutableSearchTerm = MutableLiveData<String>()
    val searchTerm: String
    get() = mutableSearchTerm.value ?: ""
    init {
        mutableSearchTerm.value = QueryPreferences.getStoredQuery(app)
        galleryItemLiveData = Transformations.switchMap(mutableSearchTerm) { searchTerm ->
            if (searchTerm.isBlank()) {
                flickrFetchr.fetchPhotos()
            } else {
                flickrFetchr.searchPhotos(searchTerm)
            }
        }
    }
    fun fetchPhotos(query: String = "") {
        QueryPreferences.setStoredQuery(app, query)
        mutableSearchTerm.value = query
    }
}