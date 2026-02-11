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

    private val _uiState = MutableStateFlow(TokioUiState()) // Estado de la interfaz
    val uiState: StateFlow<TokioUiState> = _uiState // Datos de la interfaz

    // Lo que ocurre cuando se inicializa
    init {
        initializeUIState()
    }

    /**
     * Incializa el estado de la ui
     */
    private fun initializeUIState() {
        _uiState.value =
            TokioUiState(
                categoryList = categorias // Lista de categorías
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

    /**
     * Selecciona la categoría y actualiza el view model
     */
    private fun selectCategory(categoryID: Int) {
        // Filtra todas las categorías por el id pasado
        val categoryFilter = uiState.value.categoryList.find { it.id == categoryID }

        // Actualiza el estado de la intefaz
        _uiState.update {
            it.copy(
                currentCategory = categoryFilter!! // Cambia la categoría actual y asegura que no sea nulo
            )
        }

    }

    /**
     * Selecciona la recomendación actual
     */
    fun selectRecommendation(recommendationID: Int) {
        // Filtra la recomendación actual en función del id pasado
        val recommendationFilter =
            uiState.value.selectedListOfRecommendations.find { it.id == recommendationID }
        _uiState.update {
            it.copy(
                currentRecomendation = recommendationFilter!! // Cambia la recomendación actual, y asegura que no sea nula
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
                selectedListOfRecommendations = selectedRecomendation // Cambia la lista de recomendaciones
            )
        }
    }


    /**
     * Indica que función debe ocurrir cuando se hace click en favoritos
     */
    fun favoriteFunctions(recomendation: Recomendacion) {

        // Si no está en favoritos
        if (!recomendation.favoritos.value)
        // Se añade a la lista de recomendacions
            addToFavorite(recomendation)
        else
        // Se elimina de la lista de recomendaciones
            deleteFromFavorite(recomendation)

        recomendation.favoritos.value =
            !recomendation.favoritos.value // Cambia el estado de favoritos del objeto
    }

    /**
     * Añade a la lista de favoritos una recomendación
     */
    private fun addToFavorite(recomendation: Recomendacion) {
        val actualList = uiState.value.listOfFavoriteRecomendations // Saca la lista actual
        actualList.add(recomendation) // Añade a la lista
        // Actualiza la lista actual
        _uiState.update { state ->
            state.copy(
                listOfFavoriteRecomendations = actualList // Cambia la lista de de favoritos
            )
        }
    }

    /**
     * Elimina un elemento de la lista de favoritos
     */
    private fun deleteFromFavorite(recomendation: Recomendacion) {
        val updatedList = uiState.value.listOfFavoriteRecomendations // Saca la lista actual
        updatedList.remove(recomendation) // Elimina el elemento
        // Actualiza el estado de la ui
        _uiState.update { state ->
            state.copy(
                listOfFavoriteRecomendations = updatedList // Cambia la lista de recomendaciones
            )
        }

    }

    /**
     * Cambia el rating de la recomendación actual
     */
    fun ratingChange(recommendation: Recomendacion, i: Int) {
        // Cambia el rating actual
        recommendation.raiting.value =
            if (recommendation.raiting.value == i) 0 else i
    }


}