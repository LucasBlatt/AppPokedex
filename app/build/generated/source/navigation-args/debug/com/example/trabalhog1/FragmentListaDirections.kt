package com.example.trabalhog1

import android.os.Bundle
import androidx.navigation.NavDirections
import kotlin.Int
import kotlin.String

public class FragmentListaDirections private constructor() {
  private data class ActionFragmentListaToFragmentPokemonsDetalhes(
    public val url: String
  ) : NavDirections {
    public override val actionId: Int = R.id.action_fragmentLista_to_fragmentPokemonsDetalhes

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putString("url", this.url)
        return result
      }
  }

  public companion object {
    public fun actionFragmentListaToFragmentPokemonsDetalhes(url: String): NavDirections =
        ActionFragmentListaToFragmentPokemonsDetalhes(url)
  }
}
