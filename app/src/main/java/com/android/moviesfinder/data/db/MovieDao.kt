package com.android.moviesfinder.data.db

import android.util.Log
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(movies: List<MovieEntity>)

    @Query("SELECT * FROM movieentity ORDER BY `order` ASC")
    fun pagingSource(): PagingSource<Int, MovieEntity>

    @Query("DELETE FROM movieentity")
    suspend fun clearAll()
}
