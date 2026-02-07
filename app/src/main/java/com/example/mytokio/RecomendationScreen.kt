package com.example.mytokio

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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

//Pantalla del sitio
@Composable
fun RecomendationScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        TopRecommendedBar()

        Spacer(modifier = Modifier.height(12.dp))

        RecommendedImageBox()

        Spacer(modifier = Modifier.height(8.dp))

        RatingAndFavRow()

        Spacer(modifier = Modifier.height(6.dp))

        MapsAndShareRow()

        Spacer(modifier = Modifier.height(12.dp))

        RecommendedDescriptionBox()
    }
}


//Barra superior sitio recomendado
@Composable
fun TopRecommendedBar(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "",
            style = MaterialTheme.typography.titleMedium
        )
    }
}


// Imagen principal
@Composable
fun RecommendedImageBox(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(260.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.jojo),
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
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp),
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            Text(
                text = "",
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
    var rating by remember { mutableStateOf(3) }

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
    RecomendationScreen()
}

