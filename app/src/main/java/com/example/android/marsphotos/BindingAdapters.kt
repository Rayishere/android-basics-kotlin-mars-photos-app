package com.example.android.marsphotos

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import coil.load

/**
 * Create the drawable object from the Android framework
 * Instead of manually create from scratch <-- lead to crappy coding style
 * Using a Binding Adapter
 * @BindingAdapter <-- data binding to execute this binding adapter
 * when a [View] item has the "imageUrl" attribute
 */
@BindingAdapter("imageUrl")
fun bindImage(imgView:ImageView, imgUrl:String?){
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
        imgView.load(imgUri){
            // Sets the placeholder loading image to use while loading
            placeholder(R.drawable.loading_animation)
            error(R.drawable.ic_broken_image)
        }
    }
}