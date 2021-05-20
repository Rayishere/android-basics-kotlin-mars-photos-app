package com.example.android.marsphotos.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.marsphotos.databinding.GridViewItemBinding
import com.example.android.marsphotos.network.MarsPhoto

/**
 * [DiffUtil] implementation in the [ListAdapter]
 * Advantage: every time some item in the [Recyclerview] is added, removed, or changed
 * The whole list don't get refreshed. Only the items that have been changed are refreshed.
 */
class PhotoGridAdapter:ListAdapter<
        MarsPhoto,
        PhotoGridAdapter.MarsPhotoViewHolder>(DiffCallback) {
    /**
     * An inner class definition for [MarsPhotoViewHolder]
     * extends [RecyclerView.ViewHolder]
     * [@param] GridViewItemBinding
     * ---> The variable for binding the [MarsPhoto] to the layout
     * ---> Pass the variable into the [MarsPhotoViewHolder]
     * The base [ViewHolder] class requires a view in its constructor
     * --> passing the binding root view
     */
    class MarsPhotoViewHolder(
        private var binding: GridViewItemBinding):
    RecyclerView.ViewHolder(binding.root){
        fun bind(marsPhoto:MarsPhoto){
            // Need to alter [grid_view_item.xml] <data> name property to [photo]
            binding.photo = marsPhoto
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PhotoGridAdapter.MarsPhotoViewHolder {
        return MarsPhotoViewHolder(GridViewItemBinding.inflate(
            LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: PhotoGridAdapter.MarsPhotoViewHolder, position: Int) {
        val marsPhot = getItem(position)
        holder.bind(marsPhot)
    }

    /**
     * A companion object definition for [DiffCallback]
     * extends [DiffUtil.ItemCallback] with the generic type of object you want to compare
     * [MarsPhoto]
     */
    companion object DiffCallback: DiffUtil.ItemCallback<MarsPhoto>() {
        override fun areItemsTheSame(oldItem: MarsPhoto, newItem: MarsPhoto): Boolean {
            // Decide whether two objects represent the same item
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MarsPhoto, newItem: MarsPhoto): Boolean {
            // Check whether two items have the same data
            return oldItem.imgSrcUrl == newItem.imgSrcUrl
        }

    }

}