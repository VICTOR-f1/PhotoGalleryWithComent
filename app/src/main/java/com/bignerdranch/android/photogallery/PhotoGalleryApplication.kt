package com.bignerdranch.android.photogallery

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build

const val NOTIFICATION_CHANNEL_ID = "flickr_poll"
//приложение должно создать хотя бы один канал для
//поддержки Android Oreo и версий выше. Официально предела
//количества каналов, которые может создать приложение, не
//существует. Но вы должны быть разумны и сохранять это число
//маленьким и осмысленным для пользователя. Помните, что
//наша цель — позволить пользователю настраивать уведомления
//в вашем приложении. Добавление слишком большого
//количества каналов может в конечном итоге запутать
//пользователя и ухудшить его восприятие.
//Создайте класс PhotoGalleryApplication. Расширьте
//Application и переопределите функцию
//Application.onCreate(), добавляет возможность создать и
//добавить канал, если устройство работает под управлением
//Android Oreo или выше.
class PhotoGalleryApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.notification_channel_name)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance)
            val notificationManager: NotificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

}