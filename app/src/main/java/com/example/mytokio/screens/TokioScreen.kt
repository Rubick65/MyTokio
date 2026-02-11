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

// Diferentes pantallas de la aplicación
enum class MyTokioScreen() {
    Categorias, ListaRecomendacion, Recomendacion,

}

// Diferentes tipos de de distribución de pantallas
enum class TokioContentType() {
    OnlyCategory, CategoryAndRecommendation
}

/**
 * Crea la aplicación
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TokioTopAppBar(
    navigateUp: () -> Unit, canNavigateBack: Boolean, @StringRes titel: Int

) {
    CenterAlignedTopAppBar(title = {
        Text(
            text = stringResource(titel),
            style = MaterialTheme.typography.titleLarge,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }, navigationIcon = {
        if (canNavigateBack) {
            IconButton(onClick = navigateUp) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.flecha_atras),
                )
            }
        }
    })
}

@Composable
fun MyTokioApp(
    windowSize: WindowWidthSizeClass,
    viewModel: MyTokioViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {

    // Para el tipo de contenido
    val contentType: TokioContentType
    // Saca la imagen de fondo actual
    val background = LocalImages.current.background
    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    val currentScreen = MyTokioScreen.valueOf(
        backStackEntry?.destination?.route ?: MyTokioScreen.Categorias.name
    )

    // En función del tamaño de pantalla
    when (windowSize) {
        WindowWidthSizeClass.Compact -> {
            // Solo una pantalla a la vez
            contentType = TokioContentType.OnlyCategory // En caso de pantallas pequeñas
        }

        WindowWidthSizeClass.Medium -> {
            // Solo una pantalla a la vez

            contentType = TokioContentType.OnlyCategory // En caso de pantallas medianas
        }

        WindowWidthSizeClass.Expanded -> {
            // Doble pantalla
            contentType = TokioContentType.CategoryAndRecommendation // En caso de pantallas grandes
        }

        else -> {
            contentType = TokioContentType.OnlyCategory
        }
    }

    // Estado de la interfaz de usuario
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        // Barra superior
        topBar = {
            // Crea la barra superior
            TokioTopAppBar(
                canNavigateBack = navController.previousBackStackEntry != null, // Indica cuando se puede navegar hacia atras
                navigateUp = { navController.navigateUp() }, titel = currentTitleText(
                    currentScreen, uiState
                ) // Indica el texto que tiene que poner
            )
        }

    ) { innerPadding ->

        // Box para contener toda la app
        Box(
            modifier = Modifier
                .fillMaxSize() // Ocupa el espacio máximo
                .padding(innerPadding) // Respetando el padding interno
        ) {
            // Imagen de fondo
            Image(
                painter = painterResource(background),
                contentDescription = null, //Quitamos descripcíon de audio.
                contentScale = ContentScale.Crop, //Para que ocupe toda la pantalla
                modifier = Modifier.fillMaxSize()// Ocupa toda la pantalla
            )

            // Nav host para la navegación por las diferentes pantallas
            NavHost(
                navController = navController, // Contraldor para pasar enter pantallas
                startDestination = MyTokioScreen.Categorias.name, // Pantalla de inicio
                modifier = Modifier.fillMaxSize() // Ocupa todo el espacio disponible
            ) {

                // Primera pantalla
                composable(MyTokioScreen.Categorias.name) {
                    // Se crea la pantalla
                    CategoryScreen(
                        contentType, // En función del tipo de contenido
                        uiState = uiState, // uiState para el uso y tratamiento de datos
                        navController = navController, // Controlador para pasar entre las diferentes pantallas
                        viewModel = viewModel, // Modelo de datos para las funciones
                        category = true // Indica si se tiene que mostrar la categoria o la lista de recomendaciones
                    )
                }

                // Pantalla de Lista de recomendaciones
                composable(MyTokioScreen.ListaRecomendacion.name) {
                    // Crea la lista de recomendaciones
                    CategoryScreen(
                        contentType, // En función el tipo de contenido
                        uiState = uiState, // uiState para el uso y tratamiento de datos
                        navController = navController, // Controlador para pasar entre las diferentes pantallas
                        viewModel = viewModel,// Modelo de datos para las funciones
                        category = false // Indica si se tiene que mostrar la categoria o la lista de recomendaciones
                    )
                }


                // Pantalla de recomendación
                composable(route = MyTokioScreen.Recomendacion.name) {
                    RecomendationScreen(
                        currentRecommendation = uiState.currentRecomendation, // Recomendación actual
                        contentType = contentType, // Manera en la que se muestra el contenido
                        onRatingClick = { recommendation, i -> // Lo que ocurre cuando se hace click en el rating
                            viewModel.ratingChange(
                                recommendation, i
                            )
                        },
                        onClick = { viewModel.favoriteFunctions(it) } // Lo que ocurre cuando se hace click en favoritos
                    )


                }
            }


        }


    }
}

/**
 * Indica que es lo que se va a mostrar en función de la distribución de la pantalla actual
 */
@Composable
fun CategoryScreen(
    contentType: TokioContentType,
    category: Boolean,
    uiState: TokioUiState,
    navController: NavHostController,
    viewModel: MyTokioViewModel
) {

    // En función del tipo de contenido
    if (contentType == TokioContentType.OnlyCategory) {

        // Si es la lista de categorias
        if (category)
        // Se crea la lista de categorías normalmente
            SelectionCardList(
                selectionList = uiState.categoryList, // Lista de categorías
                onClick = {
                    viewModel.selectRecommendationList(it)
                    navController.navigate(MyTokioScreen.ListaRecomendacion.name)
                }) // Cuando se hace click, se navega a la lista de recomendaciones
        else
        // Se crea la lista de recomendaciones normal
            SelectionCardList(
                selectionList = uiState.selectedListOfRecommendations, // Lista de recomendaciones seleccionada
                hasBorder = true, // Indica si debe tener borde o no
                onClick = {
                    viewModel.selectRecommendation(it)
                    navController.navigate(MyTokioScreen.Recomendacion.name)
                }) // Cuando se hace click navega a la recomendación clickeada


    } else { // En caso de estar en un dispositvo expandido

        // Se crea una fila
        Row {
            // Aquí se ponen las dos listas en la misma pantalla, dando el efecto de tener dos pantallas en una
            // Aquí se pone la lista de categorías normales
            SelectionCardList(
                selectionList = uiState.categoryList,
                onClick = {
                    viewModel.selectRecommendationList(it)
                },
                modifier = Modifier.weight(1f) // Ocupa la mitad de la pantalla
            )

            // Crea la lista de recomendaciones justo al lado de la lista de categorías
            SelectionCardList(
                selectionList = uiState.selectedListOfRecommendations,
                hasBorder = true,
                onClick = {
                    viewModel.selectRecommendation(it)
                    navController.navigate(MyTokioScreen.Recomendacion.name)
                },
                modifier = Modifier.weight(1f) // Ocupa la otra mitad de la pantalla
            )
        }
    }
}


/**
 * Indica que texto se debe mostrar en función de la pantalla en la que se encuentre
 */
fun currentTitleText(currenScreen: MyTokioScreen, uiState: TokioUiState): Int {
    val currentTitle = when (currenScreen) {
        MyTokioScreen.Categorias -> R.string.categoria_nombre
        MyTokioScreen.ListaRecomendacion -> uiState.currentCategory.titulo
        MyTokioScreen.Recomendacion -> uiState.currentRecomendation.titulo
    }

    return currentTitle
}

