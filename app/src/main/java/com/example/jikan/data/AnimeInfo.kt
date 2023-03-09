package com.example.jikan.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AnimeInfo(val Title : String, val imageUrl : String?,  val synopsis : String) : Parcelable
