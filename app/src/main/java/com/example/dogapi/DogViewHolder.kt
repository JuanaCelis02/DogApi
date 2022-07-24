package com.example.dogapi

import android.media.Image
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.dogapi.databinding.ItemDogBinding
import com.squareup.picasso.Picasso

class DogViewHolder (view: View):RecyclerView.ViewHolder(view){

    private val binding = ItemDogBinding.bind(view)

    /**
     * Esto se va a llamar por cada celda que se va a mostrar, recibe la imagen
     */
    fun bind(image: String){
        Picasso.get().load(image).into(binding.ivDog)
    }
}