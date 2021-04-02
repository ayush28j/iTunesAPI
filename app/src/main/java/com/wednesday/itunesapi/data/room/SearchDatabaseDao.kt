package com.wednesday.itunesapi.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SearchDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(result: SearchDataClass)

    @Query("SELECT search_result from search_results_table WHERE searchText = :text")
    suspend fun get(text: String): String?

    @Query("SELECT * from search_results_table")
    suspend fun getAll(): List<SearchDataClass>

}

