package com.wednesday.itunesapi

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.wednesday.itunesapi.data.DataClass
import com.wednesday.itunesapi.data.Results
import com.wednesday.itunesapi.data.room.SearchDataClass
import com.wednesday.itunesapi.data.room.SearchDatabaseDao
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


class MainViewModel(
    val database: SearchDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    val result = MutableLiveData<List<Results>>()

    init {
        viewModelScope.launch {
            val mlist = database.getAll()
            mlist.forEach {
                Log.d("DEBUGGG", "${it.searchText} -> ${it.searchResultString}")
            }
        }
    }

    fun getFromDatabase(text: String) {
        viewModelScope.launch {
            val jsonString = database.get(text)
            if(jsonString==null || jsonString.isEmpty()) {
                result.value = emptyList()
            }
            else{
                result.value = gson.fromJson(jsonString, DataClass::class.java).results
            }
        }
    }

    val gson = Gson()

    fun searchOnline(text: String){

        val retrofit = Retrofit.Builder()
            .baseUrl("https://itunes.apple.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val rAPIs = retrofit.create(retrofitAPIS::class.java)
        val call = rAPIs.getResult(text)
        call.enqueue(object : Callback<DataClass> {
            override fun onResponse(
                call: Call<DataClass>,
                response: Response<DataClass>
            ) {
                if (response.body() != null) {
                    if(response.body()!!.resultCount!=0) {
                        result.value = response.body()!!.results
                        viewModelScope.launch {
                            database.insert(SearchDataClass(text, gson.toJson(response.body())))
                        }
                    }
                    else
                        result.value = emptyList()
                }
            }

            override fun onFailure(call: Call<DataClass>, t: Throwable) {

            }

        })

    }

    interface retrofitAPIS{
        @GET("search")
        fun getResult(@Query("term") term: String): Call<DataClass>
    }
}