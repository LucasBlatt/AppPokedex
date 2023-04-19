package com.example.trabalhog1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

private const val ARG_PARAM1 = "url"

class FragmentPokemonsDetalhes : Fragment() {
    private var param1: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pokemons_detalhes, container, false)

            lifecycleScope.launch {
                data class Name(val name: String)
                data class Type(val type: Name)
                data class PokemonSprite(val front_default: String)
                data class Pokemon(val name: String, val sprites: PokemonSprite, val types: List<Type>)

                val data  = fetchApiDataPokemons()
                val response  = Gson().fromJson(data, Pokemon::class.java)

                view.findViewById<TextView>(R.id.name_pokemon).text = response.name

                response.types.elementAtOrNull(1)?.let{
                    view.findViewById<TextView>(R.id.type_pokemon).text = "Tipo: ${response.types[0].type.name} ${response.types[1].type.name}"
                }

                Glide.with(view.context)
                    .load(response.sprites.front_default)
                    .into(view.findViewById(R.id.imagem))
            }

            return view
    }

suspend fun fetchApiDataPokemons(): String {
    return withContext(Dispatchers.IO) {
        val response = URL("${param1}").readText()
        response
    }
}

    companion object {
        fun newInstance(param1: String) =
            FragmentPokemonsDetalhes().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}