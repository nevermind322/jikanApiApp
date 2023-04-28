package com.example.jikan

import android.app.Application
import androidx.room.Room
import com.example.jikan.db.AppDb
import com.example.jikan.utils.NetworkClient

class JikanApp : Application() {
    val service = NetworkClient
    val db by lazy{ Room.databaseBuilder(this, AppDb::class.java, "databaseClass").build()}


}