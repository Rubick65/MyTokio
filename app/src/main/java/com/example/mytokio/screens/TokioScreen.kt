package com.example.mytokio.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.mytokio.R


enum class MyTokioScreen(@StringRes val titulo: Int) {
    Categorias(titulo = R.string.categoria_nombre),
    Recomendacion(titulo = R.string.recomendacion_nombre)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TokioTopAppBar(
    modifier: Modifier,
    navigateUp: () -> Unit,
    canNavigateBack: Boolean,
    lastScreen: Boolean,
    recomendationText: () -> String,
    currenScreen: MyTokioScreen
) {
    TopAppBar(
        title = {
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = if (lastScreen) recomendationText() else stringResource(
                        currenScreen.titulo
                    ),
                    modifier = Modifier.align(Alignment.Center),
                    textAlign = TextAlign.Center
                )
            }
        },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.flecha_atras),
                    )
                }
            }
        }
    )
}




//@Preview(showBackground = true)
//@Composable
//fun TokioAppBarPreview() {
//    MyTokioTheme {
//        TokioTopAppBar(
//            navigateUp = {},
//            canNavigateBack = false,
//            lastScreen = false,
//            recomendationText = returnText(),
//            currenScreen = null
//
//        )
//    }
//}

//fun returnText(): String {
//    return "Hola";
//}
