package com.example.mytokio.model.dataObjects

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Recomendacion(
    override val id: Int,
    @DrawableRes override val imagen: Int,
    @StringRes override val titulo: Int,
    @StringRes val url: Int,
    @StringRes val descripcion: Int
) : Categoria(id, imagen, titulo)
