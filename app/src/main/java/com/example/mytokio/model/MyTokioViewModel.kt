package com.example.mytokio.model

import androidx.lifecycle.ViewModel
import com.example.mytokio.data.cafeterias
import com.example.mytokio.data.categorias
import com.example.mytokio.data.monumentos
import com.example.mytokio.data.parques
import com.example.mytokio.data.templos
import com.example.mytokio.data.zonasTematicas
import com.example.mytokio.model.dataObjects.Recomendacion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class MyTokioViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(TokioUiState())
    val uiState: StateFlow<TokioUiState> = _uiState

    init {
        initializeUIState()
    }

    private fun initializeUIState() {
        _uiState.value =
            TokioUiState(
                categoryList = categorias
            )
    }

    /**
     * Selecciona la lista de recomendaciones a mostrar
     * en función del id de la categoría a la que pertenezcan
     */
    fun selectRecomendationList(categoryID: Int) {

        // Lista temporal para guardar las recomendaciones
        var temporalRecomendationList: List<Recomendacion>

        // En función del id se selecciona una lista de reomendaciones u otra
        when (categoryID) {
            1 -> temporalRecomendationList = zonasTematicas
            2 -> temporalRecomendationList = cafeterias
            3 -> temporalRecomendationList = parques
            4 -> temporalRecomendationList = templos
            else -> temporalRecomendationList = monumentos
        }

        // Se actualiza la lista de recomendaciones
        updateRecomendationList(temporalRecomendationList)

    }

    /**
     * Actualiza la lista de reomendaciones
     */
    private fun updateRecomendationList(selectedRecomendation: List<Recomendacion>) {
        // Cambia la lista seleccionada por la nueva lista
        _uiState.update {
            it.copy(
                selectedListOfRecommendations = selectedRecomendation
            )
        }
    }

    fun addToFavorite(recomendation: Recomendacion) {
        val actualList = uiState.value.listOfFavoriteRecomendations
        actualList.add(recomendation)
        _uiState.update { state ->
            state.copy(
                listOfFavoriteRecomendations = actualList
            )
        }
    }


}