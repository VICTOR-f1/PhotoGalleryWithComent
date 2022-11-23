package com.bignerdranch.android.photogallery

import android.content.Context
import android.preference.PreferenceManager
import androidx.core.content.edit

private const val PREF_SEARCH_QUERY = "searchQuery"
private const val PREF_LAST_RESULT_ID = "lastResultId"
private const val PREF_IS_POLLING = "isPolling"

object QueryPreferences {
   // получить сохраненный запрос
    fun getStoredQuery(context: Context): String {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        return prefs.getString(PREF_SEARCH_QUERY, "")!!
    }
    //установить сохраненный запрос

    fun setStoredQuery(context: Context, query: String) {
        PreferenceManager.getDefaultSharedPreferences(context).edit{putString(PREF_SEARCH_QUERY, query)}
    }
    //получить последний идентификатор результата
    fun getLastResultId(context: Context): String {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(PREF_LAST_RESULT_ID, "")!!
    }
    //установить последний идентификатор результата
    fun setLastResultId(context: Context, lastResultId: String) {
        PreferenceManager.getDefaultSharedPreferences(context).edit {
            putString(PREF_LAST_RESULT_ID, lastResultId)
        }
    }
    //воспроизвести уведомление
    fun isPolling(context: Context): Boolean {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(PREF_IS_POLLING, false)
    }
    //установить уведомление

    fun setPolling(context: Context, isOn:
    Boolean) {
        PreferenceManager.getDefaultSharedPreferences(context).edit {
            putBoolean(PREF_IS_POLLING, isOn)
        }
    }
}