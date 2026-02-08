package com.example.mytokio

import android.media.Image
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import com.example.mytokio.data.defautlRecomendation
import com.example.mytokio.model.dataObjects.Recomendacion
import com.example.mytokio.ui.theme.MyTokioTheme

//Pantalla del sitio
@Composable
fun RecomendationScreen(
    modifier: Modifier = Modifier,
    currentRecommendation: Recomendacion
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        item {
            RecommendedImageBox(
                currentImage = currentRecommendation.imagen,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))


        }

        item {
            RatingAndFavRow(modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.height(6.dp))

        }

        item {
            MapsAndShareRow(modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.height(12.dp))

        }

        item {

            RecommendedDescriptionBox(
                currentDescription = currentRecommendation.descripcion,
                modifier = Modifier.fillMaxWidth()
            )

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

    ) {
        Image(
            painter = painterResource(id = currentImage),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}

/**
 * Fila con:
 * --> RatingBar
 * --> Boton fav
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
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ActionImageButton()
        ActionImageButton()
    }
}


//Caja de descripcion
@Composable
fun RecommendedDescriptionBox(
    modifier: Modifier = Modifier,
    @StringRes currentDescription: Int
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
    ) {
        Box(
            modifier = Modifier
                .padding(12.dp)
        ) {
            Text(
                text = stringResource(currentDescription),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}


// Botones
@Composable
fun ActionImageButton(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.size(44.dp),
        shape = RoundedCornerShape(50)
    ) {
        // aqui van las imagenes de los 3 botones
    }
}


//RatingBar
@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    maxStars: Int = 5
) {
    var rating by remember { mutableStateOf(0) }

    Row(modifier = modifier) {
        for (i in 1..maxStars) {
            Image(
                imageVector = if (i <= rating) {
                    Icons.Filled.Star
                } else {
                    Icons.Outlined.Star
                },
                contentDescription = null,
                modifier = Modifier
                    .size(45.dp)
                    .clickable { rating = i }
            )
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RecomendationScreenPreview() {
    MyTokioTheme() {
        RecomendationScreen(
            currentRecommendation = defautlRecomendation
        )
    }

}

