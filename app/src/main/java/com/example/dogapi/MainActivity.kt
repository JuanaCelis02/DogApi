package com.example.dogapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethod
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dogapi.databinding.ActivityMainBinding
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: DogAdapter
    private val dogImages = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.svDogs.setOnQueryTextListener(this)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        adapter = DogAdapter(dogImages)
        binding.rvDogs.layoutManager = LinearLayoutManager(this)
        binding.rvDogs.adapter = adapter
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
            /**
             * Volvemos al hilo principal, lo que se ejecute dentro se ejecutara en el hilo principal
             */
            runOnUiThread{
                if(call.isSuccessful){
                   val images = puppies?.images ?: emptyList() //Si puppies es nullo entonces va a ser una lista vacia (Pero no sera null)
                    dogImages.clear()
                   dogImages.addAll(images)
                    adapter.notifyDataSetChanged()
                }else{
                    showError()
                }
                hideKeyBoard() //Ocultara el teclado cuando le demos buscar
            }

        }
    }

    private fun hideKeyBoard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.viewRoot.windowToken, 0)
    }

    private fun showError() {
        Toast.makeText(this,"Ha ocurrido un error", Toast.LENGTH_SHORT).show()
    }

    /**
     * Cuando termine de buscar y le de en buscar llamara este metodo
     */
    override fun onQueryTextSubmit(query: String?): Boolean {
        if(!query.isNullOrBlank()){
            searchByName(query.lowercase())
        }
        return true
    }

    /**
     * Nos van a avisar cuando el usuario escriba algo en la barra de texto
     */
    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

}