package com.example.mytokio.model.dataObjects

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

open class Categoria(
    open val id: Int,
    @DrawableRes open val imagen: Int,
    @StringRes open val titulo: Int,
)