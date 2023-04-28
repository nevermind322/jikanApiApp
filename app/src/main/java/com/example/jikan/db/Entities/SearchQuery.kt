package com.example.jikan.db.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class SearchQuery (
    @PrimaryKey val query : String
)