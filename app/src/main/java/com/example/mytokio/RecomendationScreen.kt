package com.example.mytokio

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.mytokio.data.defautlRecomendation
import com.example.mytokio.model.dataObjects.Recomendacion
import com.example.mytokio.pruebasBoton.mapRecomendation
import com.example.mytokio.pruebasBoton.shareRecomendation
import com.example.mytokio.ui.theme.MyTokioTheme

// Pantalla del sitio
@Composable
fun RecomendationScreen(
    modifier: Modifier = Modifier,
    currentRecommendation: Recomendacion
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.recommendation_screen_padding)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        item {
            RecommendedImageBox(
                currentImage = currentRecommendation.imagen,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(
                modifier = Modifier.height(
                    dimensionResource(R.dimen.recommendation_space_medium)
                )
            )
        }

        item {
            RatingAndFavRow(
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(
                modifier = Modifier.height(
                    dimensionResource(R.dimen.recommendation_space_small)
                )
            )
        }

        item {
            MapsAndShareRow(
                modifier = Modifier.fillMaxWidth(),
                currentRecommendation = currentRecommendation
            )

            Spacer(
                modifier = Modifier.height(
                    dimensionResource(R.dimen.recommendation_space_large)
                )
            )
        }

        item {
            RecommendedDescriptionBox(
                currentDescription = currentRecommendation.descripcion,
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            Spacer(modifier = Modifier.fillParentMaxHeight(0.15f))
        }
    }
}

// Imagen principal
@Composable
fun RecommendedImageBox(
    modifier: Modifier = Modifier,
    @DrawableRes currentImage: Int
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(16f / 9f)
    ) {
        Image(
            painter = painterResource(id = currentImage),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize()
        )
    }
}

/**
 * Fila con:
 * --> RatingBar
 * --> Botón fav
 */
@Composable
fun RatingAndFavRow(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        RatingBar()
        ActionImageButton()
    }
}

/**
 * Fila inferior con:
 * --> Maps
 * --> Compartir
 */
@Composable
fun MapsAndShareRow(
    modifier: Modifier = Modifier,
    currentRecommendation: Recomendacion
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(
            dimensionResource(R.dimen.action_buttons_spacing)
        )
    ) {
        ActionImageButtonIntent(
            icon = R.drawable.icono_compartir_nitido,
            contentDescription = R.string.compartir_recomendacion,
            onClick = shareRecomendation(currentRecommendation.url)
        )

        ActionImageButtonIntent(
            icon = R.drawable.icono_maps_nitido,
            contentDescription = R.string.abrir_maps,
            onClick = mapRecomendation(currentRecommendation.url)
        )
    }
}

// Caja de descripción
@Composable
fun RecommendedDescriptionBox(
    modifier: Modifier = Modifier,
    @StringRes currentDescription: Int
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(
                dimensionResource(R.dimen.recommendation_description_height)
            ),
        shape = RoundedCornerShape(
            dimensionResource(R.dimen.recommendation_description_corner)
        ),
        border = BorderStroke(
            dimensionResource(R.dimen.recommendation_description_border),
            MaterialTheme.colorScheme.primary
        )
    ) {
        Box(
            modifier = Modifier.padding(
                dimensionResource(R.dimen.recommendation_description_padding)
            )
        ) {
            Text(
                text = stringResource(currentDescription),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

// Botón con intent (maps / compartir)
@Composable
fun ActionImageButtonIntent(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    @StringRes contentDescription: Int,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier.size(
            dimensionResource(R.dimen.action_button_size)
        ),
        shape = RoundedCornerShape(50)
    ) {
        IconButton(onClick = onClick) {
            Icon(
                painter = painterResource(icon),
                contentDescription = stringResource(contentDescription),
                modifier = Modifier.size(
                    dimensionResource(R.dimen.icon_size)
                )
            )
        }
    }
}

// Botón favorito
@Composable
fun ActionImageButton(
    modifier: Modifier = Modifier
) {
    var isFavorite by remember { mutableStateOf(false) }

    Card(
        modifier = modifier.size(
            dimensionResource(R.dimen.action_button_size)
        ),
        shape = RoundedCornerShape(50)
    ) {
        IconButton(onClick = { isFavorite = !isFavorite }) {
            Image(
                painter = painterResource(
                    if (isFavorite) R.drawable.corarojo else R.drawable.cora
                ),
                contentDescription = null,
                modifier = Modifier.size(
                    dimensionResource(R.dimen.icon_size)
                )
            )
        }
    }
}

// RatingBar
@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    maxStars: Int = 5
) {
    var rating by remember { mutableStateOf(0) }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 1..maxStars) {
            val isSelected = i <= rating

            Icon(
                imageVector = if (isSelected) {
                    Icons.Filled.Star
                } else {
                    Icons.Outlined.Star
                },
                contentDescription = null,
                tint = if (isSelected) {
                    Color(0xFFFFC107)
                } else {
                    Color.Gray
                },
                modifier = Modifier
                    .size(dimensionResource(R.dimen.rating_star_size))
                    .clickable {
                        rating = if (rating == i) 0 else i
                    }
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RecomendationScreenPreview() {
    MyTokioTheme {
        RecomendationScreen(
            currentRecommendation = defautlRecomendation
        )
    }
}
