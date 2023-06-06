package com.mixlr.panos.retrofitdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.mixlr.panos.retrofitdemo.databinding.ActivityMainBinding
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val retService = RetrofitInstance.getRetrofitInstance().create(AlbumService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getRequestWithQueryParameters()
        getRequestWithPathParameters()
    }

    private fun getRequestWithQueryParameters() {
        val responseLiveData: LiveData<Response<Albums>> = liveData {
            val response: Response<Albums> = retService.getSortedAlbums(3)
            emit(response)
        }
        responseLiveData.observe(this) {
            var albumsList = it.body()?.listIterator()
            if (albumsList != null) {
                while (albumsList.hasNext()) {
                    val albumsItem = albumsList.next()
                    val result = " " + "Album Title: ${albumsItem.title}" + "\n" +
                                 " " + "Album Id: ${albumsItem.id}" + "\n" +
                                 " " + "User Id: ${albumsItem.userId}" + "\n\n\n"
                    binding.textView.append(result)
                }
            }
        }
    }

    private fun getRequestWithPathParameters() {
        val pathResponse: LiveData<Response<AlbumsItem>> = liveData {
            val response = retService.getAlbumById(24)
            emit(response)
        }
        pathResponse.observe(this) {
            val title = it.body()?.title
            Toast.makeText(applicationContext, title, Toast.LENGTH_LONG).show()
        }
    }
}