package com.bignerdranch.android.photogallery.data

import android.net.Uri
import com.google.gson.annotations.SerializedName
//Идентификатор фотографии совпадает со значением
//атрибута photo_id в разметке JSON. Мы уже сохранили его в
//поле id объекта GalleryItem.? Немного покопавшись в документации, мы
//находим, что атрибут owner в JSON содержит идентификатор
//пользователя. Таким образом, извлекая атрибут owner, мы
//можем построить URL-адрес по атрибутам из JSON фотографии:
//https://www.flickr.com/photos/владелец/
//идентификатор
data class GalleryItem(var title: String = "",
                       var id: String = "",
                       @SerializedName("url_s") var url: String = "",
                       @SerializedName("owner") var owner: String = "")
{
    val photoPageUri: Uri
        get() {
            return Uri.parse("https://www.flickr.com/photos/")
                .buildUpon()
                .appendPath(owner)
                .appendPath(id)
                .build()
        }
}