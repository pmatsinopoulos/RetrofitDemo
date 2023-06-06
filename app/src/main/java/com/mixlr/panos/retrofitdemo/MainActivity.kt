package com.mixlr.panos.retrofitdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.mixlr.panos.retrofitdemo.databinding.ActivityMainBinding
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retService = RetrofitInstance.getRetrofitInstance().create(AlbumService::class.java)
        val responseForOneAlbum : LiveData<Response<AlbumsItem>> = liveData {
            val response = retService.getAlbumById(2)
            emit(response)
        }
        responseForOneAlbum.observe(this) {
            val result = it.body()?.title
            binding.textView.text = result
        }
    }
}