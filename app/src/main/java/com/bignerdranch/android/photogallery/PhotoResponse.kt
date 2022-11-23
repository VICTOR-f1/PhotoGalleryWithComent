package com.bignerdranch.android.photogallery

import com.bignerdranch.android.photogallery.data.GalleryItem
import com.google.gson.annotations.SerializedName
//класс PhotoResponse для сопоставления с
//объектом "photos" JSON-данных. Поместите новый класс в
//пакет api, так как этот класс является побочным эффектом
//вашей реализации десериализации Flickr API, а не объектом
//модели, с которым работает остальная часть вашего
//приложения.
class PhotoResponse {
    @SerializedName("photo")
    lateinit var galleryItems:
            List<GalleryItem>


}