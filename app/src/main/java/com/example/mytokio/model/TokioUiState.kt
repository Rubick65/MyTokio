package com.example.mytokio.model

import com.example.mytokio.data.defautlRecomendation
import com.example.mytokio.model.dataObjects.Categoria
import com.example.mytokio.model.dataObjects.Recomendacion

data class TokioUiState(
    val categoryList: List<Categoria> = emptyList(),
    val selectedListOfRecommendations: List<Recomendacion> = emptyList(),
    val listOfFavoriteRecomendations: MutableList<Recomendacion> = mutableListOf(),
    val currentRecomendation: Recomendacion = defautlRecomendation
)
