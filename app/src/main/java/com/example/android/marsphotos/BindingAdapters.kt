package com.example.android.marsphotos

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.android.marsphotos.network.MarsPhoto
import com.example.android.marsphotos.overview.MarsApiStatus
import com.example.android.marsphotos.overview.PhotoGridAdapter

/**
 * Create the drawable object from the Android framework
 * Instead of manually create from scratch <-- lead to crappy coding style
 * Using a Binding Adapter
 * @BindingAdapter <-- data binding to execute this binding adapter
 * when a [View] item has the "imageUrl" attribute
 */
@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    // "let" is used to invoke one or more functions on results of call chains
    imgUrl?.let {
        /**
         * Convert the URL string to a "URI" object
         * using the [toUri] method
         * using the [buildUpon().scheme("https")] for HTTPS scheme
         * calling [build()] to build the object
         */
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()

        // use the "load(){}" from Coil
        imgView.load(imgUri) {
            // Sets the placeholder loading image to use while loading
            placeholder(R.drawable.loading_animation)
            error(R.drawable.ic_broken_image)
        }
    }
}

/**
 * [BindingAdapter]
 * Initialize: [PhotoGridAdapter] with the list of [Marsphoto] objects
 * Using: [BindingAdapter]
 *  to set the [RecyclerView] data causes data binding to automatically observe
 *  the [LiveData] for the list of [MarsPhoto]
 */
@BindingAdapter("listData")
fun bindRecyclerView(
    recyclerView: RecyclerView,
    data: List<MarsPhoto>?) {
    // cast recyclerview.adapter to PhotoGridAdapter
    val adapter = recyclerView.adapter as PhotoGridAdapter
    // This tells the RecyclerView when a new list is available
    adapter.submitList(data)
    }

@BindingAdapter("marsApiStatus")
fun bindStatus(statusImageView: ImageView,
            status: MarsApiStatus?){
    when (status){
        MarsApiStatus.Loading ->{
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        MarsApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        MarsApiStatus.DONE ->{
            statusImageView.visibility = View.GONE //hide the "ImageView"
        }
    }
}
