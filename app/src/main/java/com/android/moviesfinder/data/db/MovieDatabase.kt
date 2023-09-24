package com.android.moviesfinder.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [MovieEntity::class], version = 1
)
abstract class MovieDatabase : RoomDatabase() {

    abstract val dao: MovieDao
}
