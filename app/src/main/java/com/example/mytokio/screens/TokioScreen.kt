package com.example.mytokio.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mytokio.R
import com.example.mytokio.model.MyTokioViewModel
import com.example.mytokio.model.TokioUiState
import com.example.mytokio.ui.theme.LocalImages


enum class MyTokioScreen() {
    Categorias,
    ListaRecomendacion,
    Recomendacion,

}

enum class TokioContentType() {
    OnlyCategory,
    CategoryAndRecommendation
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TokioTopAppBar(
    navigateUp: () -> Unit,
    canNavigateBack: Boolean,
    @StringRes titel: Int

) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(titel),
                style = MaterialTheme.typography.titleLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
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

@Composable
fun MyTokioApp(
    windowSize: WindowWidthSizeClass,
    viewModel: MyTokioViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {

    val contentType: TokioContentType
    val background = LocalImages.current.background
    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    val currentScreen = MyTokioScreen.valueOf(
        backStackEntry?.destination?.route ?: MyTokioScreen.Categorias.name
    )

    when (windowSize) {
        WindowWidthSizeClass.Compact -> {
            contentType = TokioContentType.OnlyCategory
        }

        WindowWidthSizeClass.Medium -> {
            contentType = TokioContentType.OnlyCategory
        }

        WindowWidthSizeClass.Expanded -> {
            contentType = TokioContentType.CategoryAndRecommendation
        }

        else -> {
            contentType = TokioContentType.OnlyCategory
        }
    }

    val uiState by viewModel.uiState.collectAsState()

    Scaffold(

        topBar = {
            TokioTopAppBar(
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
                titel = currentTitleText(currentScreen, uiState)
            )
        }

    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Image(
                painter = painterResource(background),
                contentDescription = null, //Quitamos descripcíon de audio.
                contentScale = ContentScale.Crop, //Para que ocupe toda la pantalla
                modifier = Modifier.fillMaxSize()
            )

            NavHost(
                navController = navController,
                startDestination = MyTokioScreen.Categorias.name,
                modifier = Modifier
                    .fillMaxSize()
            ) {


                composable(MyTokioScreen.Categorias.name) {
                    CategoriasScreen(
                        contentType,
                        uiState = uiState,
                        navController = navController,
                        viewModel = viewModel,
                        category = true
                    )
                }

                composable(MyTokioScreen.ListaRecomendacion.name) {
                    CategoriasScreen(
                        contentType,
                        uiState = uiState,
                        navController = navController,
                        viewModel = viewModel,
                        category = false
                    )


                }

                composable(route = MyTokioScreen.Recomendacion.name) {
                    RecomendationScreen(
                        currentRecommendation = uiState.currentRecomendation,
                        // Aquí se pasa el contentType, que sirve para indicar cuando la pantalla tiene un tamaño y otro
                        contentType = contentType,
                        onRatingClick = { recomendation, i ->
                            viewModel.ratingChange(
                                recomendation,
                                i
                            )
                        },
                        onClick = { viewModel.favoriteFunctions(it) }
                    )

                }
            }


        }


    }
}

@Composable
fun CategoriasScreen(
    contentType: TokioContentType,
    category: Boolean,
    uiState: TokioUiState,
    navController: NavHostController,
    viewModel: MyTokioViewModel
) {

    if (contentType == TokioContentType.OnlyCategory) {

        if (category)
            SelectionCardList(
                selectionList = uiState.categoryList,
                onClick = {
                    viewModel.selectRecommendationList(it)
                    navController.navigate(MyTokioScreen.ListaRecomendacion.name)
                }
            )
        else
            SelectionCardList(
                selectionList = uiState.selectedListOfRecommendations,
                hasBorder = true,
                onClick = {
                    viewModel.selectRecommendation(it)
                    navController.navigate(MyTokioScreen.Recomendacion.name)
                }
            )


    } else {

        Row {
            SelectionCardList(
                selectionList = uiState.categoryList,
                onClick = {
                    viewModel.selectRecommendationList(it)
                },
                modifier = Modifier.weight(1f)
            )

            SelectionCardList(
                selectionList = uiState.selectedListOfRecommendations,
                hasBorder = true,
                onClick = {
                    viewModel.selectRecommendation(it)
                    navController.navigate(MyTokioScreen.Recomendacion.name)
                },
                modifier = Modifier.weight(1f)
            )
        }
    }
}


fun currentTitleText(currenScreen: MyTokioScreen, uiState: TokioUiState): Int {
    val currentTitle = when (currenScreen) {
        MyTokioScreen.Categorias -> R.string.categoria_nombre
        MyTokioScreen.ListaRecomendacion -> uiState.currentCategory.titulo
        MyTokioScreen.Recomendacion -> uiState.currentRecomendation.titulo
    }

    return currentTitle
}

