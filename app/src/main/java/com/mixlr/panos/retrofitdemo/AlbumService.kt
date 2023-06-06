package com.mixlr.panos.retrofitdemo

import retrofit2.Response
import retrofit2.http.GET

interface AlbumService {
    // we are willing to use Kotlin with Retrofit
    @GET("/albums")
    suspend fun getAlbums(): Response<Albums>
}