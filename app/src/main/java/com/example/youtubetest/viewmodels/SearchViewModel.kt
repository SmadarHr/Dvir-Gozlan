package com.example.youtubetest.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.youtubetest.api.YoutubeApiService
import com.example.youtubetest.models.SearchResultItem
import com.example.youtubetest.utils.Constants.API_KEY
import com.example.youtubetest.utils.Constants.GOOGLE_BASE_URL
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchViewModel : ViewModel() {
    private val youtubeApi: YoutubeApiService
    private val _liveData = MutableLiveData<List<SearchResultItem>>()
    companion object{
        const val TAG = "SearchViewModel"
    }
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(GOOGLE_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient())
            .build()

        youtubeApi = retrofit.create(YoutubeApiService::class.java)
    }

    fun performSearch(searchQuery: String) {
        viewModelScope.launch {
            when (val result = searchVideos(searchQuery)) {
                is SearchResult.Success -> {
                    _liveData.postValue(result.searchResult)
                }
                is SearchResult.Error -> {
                   TODO() // Handle Error
                }
            }
        }
    }

    private suspend fun searchVideos(searchQuery: String): SearchResult {
        return try {
            val response = youtubeApi.searchVideos(query = searchQuery, apiKey = API_KEY)
            val videos: List<SearchResultItem> = response.body()?.items ?: emptyList()
            SearchResult.Success(videos)
        } catch (e: Exception) {
            Log.e(TAG,"Exception get data: "+e.message)
            SearchResult.Error(e)
        }
    }

    fun getLiveData():LiveData<List<SearchResultItem>>{
        return _liveData
    }

}

sealed class SearchResult {
    data class Success(val searchResult: List<SearchResultItem>) : SearchResult()
    data class Error(val exception: Exception) : SearchResult()
}