package com.example.jikan

import android.app.Application
import com.example.jikan.utils.NetworkClient

class JikanApp : Application() {
    val service = NetworkClient
}