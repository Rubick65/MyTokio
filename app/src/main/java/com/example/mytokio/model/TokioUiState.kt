package com.example.mytokio.model

import com.example.mytokio.R
import com.example.mytokio.data.defaultCategory
import com.example.mytokio.data.defautlRecomendation
import com.example.mytokio.model.dataObjects.Categoria
import com.example.mytokio.model.dataObjects.Recomendacion

data class TokioUiState(
    val categoryList: List<Categoria> = emptyList(),
    val currentCategory: Categoria = defaultCategory,
    val selectedListOfRecommendations: List<Recomendacion> = emptyList(),
    val listOfFavoriteRecomendations: MutableList<Recomendacion> = mutableListOf(),
    val currentRecomendation: Recomendacion = defautlRecomendation
)
