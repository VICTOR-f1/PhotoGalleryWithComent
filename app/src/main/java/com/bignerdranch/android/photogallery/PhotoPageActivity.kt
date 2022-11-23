package com.bignerdranch.android.photogallery

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

//При нажатии на фотографии открывается
//PhotoPageActivity. Индикатор отображает информацию о
//ходе загрузки страниц, а на панели приложения появляется
//подзаголовок с текстом, полученным в
//onReceivedTitle(...). После завершения загрузки
//индикатор прогресса исчезает
class PhotoPageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_page)

        val fm = supportFragmentManager
        val currentFragment = fm.findFragmentById(R.id.fragment_container)

        if (currentFragment == null) {
            val fragment = intent.data?.let { PhotoPageFragment.newInstance(it) }
            fragment?.let { fm.beginTransaction().add(R.id.fragment_container, it).commit() }
        }
    }

    companion object {
        fun newIntent(context: Context, photoPageUri: Uri): Intent {
            return Intent(context, PhotoPageActivity::class.java).apply {
                data = photoPageUri
            }
        }
    }
}