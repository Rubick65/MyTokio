package com.example.mytokio.model.dataObjects

import android.R
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.flow.MutableStateFlow

data class Recomendacion(
    override val id: Int,
    @DrawableRes override val imagen: Int,
    @StringRes override val titulo: Int,
    @StringRes val url: Int,
    @StringRes val descripcion: Int,
    var favoritos: MutableState<Boolean> = mutableStateOf(false),
    var raiting: MutableState<Int> = mutableIntStateOf(0)
) : Categoria(id, imagen, titulo)
