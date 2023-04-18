package com.example.trabalhog1

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections

public class FragmentInicialDirections private constructor() {
  public companion object {
    public fun actionFragmentInicialToFragmentLista(): NavDirections =
        ActionOnlyNavDirections(R.id.action_fragmentInicial_to_fragmentLista)
  }
}
