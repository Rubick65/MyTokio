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
    fun selectRecommendationList(categoryID: Int) {

        selectCategory(categoryID)

        // En función del id se selecciona una lista de reomendaciones u otra
        val temporalRecomendationList = when (categoryID) {
            1 -> zonasTematicas
            2 -> cafeterias
            3 -> parques
            4 -> templos
            5 -> monumentos
            else -> uiState.value.listOfFavoriteRecomendations
        }

        // Se actualiza la lista de recomendaciones
        updateRecomendationList(temporalRecomendationList)

    }

    private fun selectCategory(categoryID: Int) {
        val categoryFilter = uiState.value.categoryList.find { it.id == categoryID }

        _uiState.update {
            it.copy(
                currentCategory = categoryFilter!!
            )
        }

    }

    fun selectRecommendation(recommendationID: Int) {
        val recommendationFilter =
            uiState.value.selectedListOfRecommendations.find { it.id == recommendationID }
        _uiState.update {
            it.copy(
                currentRecomendation = recommendationFilter!!
            )

        }
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


    fun favoriteFunctions(recomendation: Recomendacion) {
        recomendation.favoritos.value = !recomendation.favoritos.value

        if (recomendation.favoritos.value)
            addToFavorite(recomendation)
        else
            deleteFromFavorite(recomendation)
    }

    private fun addToFavorite(recomendation: Recomendacion) {
        val actualList = uiState.value.listOfFavoriteRecomendations
        actualList.add(recomendation)
        _uiState.update { state ->
            state.copy(
                listOfFavoriteRecomendations = actualList
            )
        }
    }

    private fun deleteFromFavorite(recomendation: Recomendacion) {
        val updatedList = uiState.value.listOfFavoriteRecomendations
        updatedList.remove(recomendation)

        _uiState.update { state ->
            state.copy(
                listOfFavoriteRecomendations = updatedList
            )
        }

    }


}