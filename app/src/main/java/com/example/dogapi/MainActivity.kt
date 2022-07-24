package com.example.dogapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dogapi.databinding.ActivityMainBinding
import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    /**
     * Este objeto Retrofit va a tener toda la url original, el conversor de gson al modelo de datos y toda la configuracion para hacer llamadas a internet
     */
    private fun getRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/breed/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


}