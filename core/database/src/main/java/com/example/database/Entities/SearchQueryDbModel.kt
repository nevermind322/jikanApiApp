package com.example.database.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SearchQuery")
class SearchQueryDbModel (
    @PrimaryKey val query : String
)