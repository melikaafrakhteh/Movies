package com.afrakhteh.movies.util.storage

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.view.Display
import com.afrakhteh.movies.util.consts.KEYS

object MyShared {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor


    @SuppressLint("CommitPrefEdits")
    fun getInstance(context: Context) {
        sharedPreferences = context.getSharedPreferences(KEYS.SHARED_PREF, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }

    fun save(key: String, value: String) {
        editor.putString(key, value).apply()
    }

    fun load(key: String): String {
        return sharedPreferences.getString(key, "")!!
    }

    fun clear() {
        editor.clear()
        editor.apply()
    }
}