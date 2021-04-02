package com.wednesday.itunesapi.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.wednesday.itunesapi.data.DataClass

@Entity(tableName = "search_results_table")
data class SearchDataClass(
        @PrimaryKey(autoGenerate = false)
        var searchText: String,

        @ColumnInfo(name = "search_result")
        val searchResultString: String? = null
)
