/*
 * Copyright (C) 2021 The Android Open Source Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.marsphotos.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.marsphotos.network.MarsApi
import com.example.android.marsphotos.network.MarsApiService
import com.example.android.marsphotos.network.MarsPhoto
import kotlinx.coroutines.launch
import java.lang.Exception

/**
 * The [enum] is short for enumeration
 * Meaning: ordered list of all the items in a collection
 * Each [enum] constant is an object of the enum class
 */
enum class MarsApiStatus {Loading, ERROR, DONE}

/**
 * The [ViewModel] that is attached to the [OverviewFragment].
 */
class OverviewViewModel : ViewModel() {

    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<MarsApiStatus>()

    // A mutable property _photos to store a single MarsPhoto object
    private val _photos = MutableLiveData<List<MarsPhoto>>()
    val photos: LiveData<List<MarsPhoto>> = _photos

    // The external immutable LiveData for the request status
    val status: LiveData<MarsApiStatus> = _status
    /**
     * Call getMarsPhotos() on init so we can display status immediately.
     */
    init {
        getMarsPhotos()
    }

    /**
     * Gets Mars photos information from the Mars API Retrofit service and updates the
     * [MarsPhoto] [List] [LiveData].
     */
    private fun getMarsPhotos() {
        //_status.value = "Set the Mars API status response here!"
        viewModelScope.launch {
            // waiting for the data
            _status.value = MarsApiStatus.Loading

            try {
                // Assign the first Mars photo retrieved to the new variable _photo
                _photos.value = MarsApi.retrofitService.getPhotos()
                //_status.value = "Success: Mars properties retrieved"
                _status.value = MarsApiStatus.DONE

                //val listResult = MarsApi.retrofitService.getPhotos()
                //_status.value = "Success: ${listResult.size} Mars photos retrieved."
            } catch (e: Exception) {
                //_status.value = "Failure: ${e.message}"
                _status.value = MarsApiStatus.ERROR
                // clears the Recycler view
                _photos.value = listOf()
            }
        }
    }
}
