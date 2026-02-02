package com.example.mytokio.model.dataObjects

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Recomendacion(
    override val id: Int,
    @DrawableRes override val imagen: Int,
    @StringRes override val titulo: Int,
    val descripcion: String
) : Categoria(id, imagen, titulo)
