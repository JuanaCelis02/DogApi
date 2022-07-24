package com.example.dogapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dogapi.databinding.ActivityMainBinding
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

    /**
     * Creamos la corrutina para que lo que se ejecute dentro sea asincrono (En un hilo secundario)
     */

    private fun searchByName(query:String){
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(IApiService::class.java).getDogsByBreeds("$query/images") // Variable call con un Response de DogsResponse
            val puppies = call.body()  //El body es donde esta la respuesta (El objeto que queremos)
            if(call.isSuccessful){
                //Show recyclerview
            }else{
                //Show errorr
            }
        }
    }

}