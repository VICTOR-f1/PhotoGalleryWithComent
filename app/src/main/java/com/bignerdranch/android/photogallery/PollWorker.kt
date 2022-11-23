package com.bignerdranch.android.photogallery

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.bignerdranch.android.photogallery.api.FlickrFetchr
import com.bignerdranch.android.photogallery.data.GalleryItem

private const val TAG = "PollWorker"

class PollWorker(val context: Context, workerParameters: WorkerParameters)
    : Worker(context, workerParameters) {

    //Функция doWork() вызывается из фонового потока,
    //поэтому вы можете выполнять в ней любые долгосрочные
    //задачи. Возвращаемые значения функции указывают на
    //состояние работы. В данном случае возвращается информация
    //об успехе, так как функция просто выводит сообщение в
    //консоль.
    //Функция doWork() может вернуть ошибку, если работа не
    //может быть завершена. В этом случае рабочий запрос
    //выполняться не будет. Он также может вернуть результат
    //повторной попытки, если ошибка была временной и вы хотите,
    //чтобы в будущем работа снова запустилась.
    //PollWorker знает только то, как выполнить фоновую
    //работу. Вам нужен еще один компонент для планирования
    //работы.
    override fun doWork(): Result {
        val query = QueryPreferences.getStoredQuery(context)
        val lastResultId = QueryPreferences.getLastResultId(context)
        val items: List<GalleryItem> = if (query.isEmpty()) {
            FlickrFetchr().fetchPhotosRequest()
                .execute()
                .body()
                ?.photos
                ?.galleryItems
        } else {
            FlickrFetchr().searchPhotosRequest(query)
                .execute()
                .body()
                ?.photos
                ?.galleryItems
        } ?: emptyList()

        if (items.isEmpty()) {
            return Result.success()
        }

        val resultId = items.first().id
        if (resultId == lastResultId) {
            Log.i(TAG, "Got an old result: $resultId")
        } else {
            Log.i(TAG, "Got a new result: $resultId")
            QueryPreferences.setLastResultId(context, resultId)

            val intent = PhotoGalleryActivity.newIntent(context)
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

            val resources = context.resources
            val notification = NotificationCompat
                .Builder(context, NOTIFICATION_CHANNEL_ID)
                .setTicker(resources.getString(R.string.new_pictures_title))
                .setSmallIcon(android.R.drawable.ic_menu_report_image)
                .setContentTitle(resources.getString(R.string.new_pictures_title))
                .setContentText(resources.getString(R.string.new_pictures_text))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()

            showBackgroundNotification(0, notification)
        }
        return Result.success()
    }
    private fun showBackgroundNotification(requestCode: Int, notification: Notification) {
        val intent =
            Intent(ACTION_SHOW_NOTIFICATION).apply {
                putExtra(REQUEST_CODE, requestCode)
                putExtra(NOTIFICATION, notification)
            }
        context.sendOrderedBroadcast(intent, PERM_PRIVATE)
    }
    companion object {
        const val ACTION_SHOW_NOTIFICATION = "com.bignerdranch.android.photogallery.SHOW_NOTIFICATION"
        const val PERM_PRIVATE = "com.bignerdranch.android.photogallery.PRIVATE"
        const val REQUEST_CODE = "REQUEST_CODE"
        const val NOTIFICATION = "NOTIFICATION"
    }
}