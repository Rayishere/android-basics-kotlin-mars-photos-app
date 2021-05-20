package com.example.android.marsphotos.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

// a constant for assessing the NASA Mars photos
private const val BASE_URL =
    "https://android-kotlin-fun-mars-server.appspot.com"

// create the Moshi object
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

/**
 * A Retrofit builder to build & create a Retrofit object
 * Retrofit needs the base URI for the web service
 * A converter factory to build a web services
 * ==> fetch a JSON response from the web service
 * ==> return a String
 */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

/**
 * An interface [MarsApiService]
 * Defines how Retrofit talks to the web server using HTTP requests
 */

interface MarsApiService {
    // the response string from the web service
    @GET("photos")
    // call this method from within a coroutine
    suspend fun getPhotos(): List<MarsPhoto>
}

/**
 * A public object [MarsApi] to initialize the Retrofit service
 * The public singleton object (expensive to create, globally access)
 * accessed from the rest of the app
 */
object MarsApi {
    // a lazily retrofit object property
    val retrofitService: MarsApiService by lazy {
        retrofit.create(MarsApiService::class.java)
    }
}
