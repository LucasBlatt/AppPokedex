package com.example.trabalhog1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.kittinunf.fuel.*
import com.github.kittinunf.result.*
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.net.URL

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class FragmentLista : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_lista, container, false)

        lifecycleScope.launch {

            data class Pokemon(val name: String, val url: String)
            data class PokemonsListagem(val results: List<Pokemon>)
            data class Sprites(val front_default: String)
            data class ModelPokemon(val sprites: Sprites)

            val data  = fetchApiDataPokemonsLista()
            val response  = Gson().fromJson(data, PokemonsListagem::class.java)

            val buttonIds = listOf(R.id.Nome1, R.id.Nome2, R.id.Nome3,R.id.Nome4,R.id.Nome5,R.id.Nome6,R.id.Nome7,R.id.Nome8,R.id.Nome9)
            val imageIds = listOf(R.id.Imagem1, R.id.Imagem2, R.id.Imagem3,R.id.Imagem4,R.id.Imagem5,R.id.Imagem6,R.id.Imagem7,R.id.Imagem8,R.id.Imagem9)

            for (i: Int in 0..8){
                var pokemonJson : String
                runBlocking {
                    pokemonJson = fetchApiDataPokemons(response.results[i].url)
                }

                val pokemonData = Gson().fromJson(pokemonJson, ModelPokemon::class.java)

                view.findViewById<TextView>(buttonIds[i]).text = response.results[i].name

                Glide.with(view.context)
                    .load(pokemonData.sprites.front_default)
                    .into(view.findViewById(imageIds[i]))
            }

            val cardsIds = listOf(R.id.Card1, R.id.Card2, R.id.Card3,R.id.Card4,R.id.Card5,R.id.Card6,R.id.Card7,R.id.Card8,R.id.Card9)

            for (i in 0..8){
                view.findViewById<CardView>(cardsIds[i]).setOnClickListener {
                    val action =FragmentListaDirections.actionFragmentListaToFragmentPokemonsDetalhes(response.results[i].url)
                    findNavController().navigate(action)
                }
            }
        }
        return view
    }

    suspend fun fetchApiDataPokemonsLista(): String {
        return withContext(Dispatchers.IO) {
            val response = URL("https://pokeapi.co/api/v2/pokemon?limit=9&offset=0").readText()
            response
        }
    }
    suspend fun fetchApiDataPokemons(url: String): String {
        return withContext(Dispatchers.IO) {
            val response = URL(url).readText()
            response
        }
    }
}
