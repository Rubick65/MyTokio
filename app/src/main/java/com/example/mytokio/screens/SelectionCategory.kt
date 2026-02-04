package com.example.mytokio.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mytokio.R
import com.example.mytokio.data.categorias
import com.example.mytokio.data.monumentos
import com.example.mytokio.data.zonasTematicas
import com.example.mytokio.model.dataObjects.Categoria
import com.example.mytokio.ui.theme.MyTokioTheme

/**
 * Crea la card general para todas las categorías y recomendaciones
 */
@Composable
fun SelectionCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    hasBorder: Boolean,
    categoria: Categoria
) {
    // Carta principal para todas las categorías y recomendaciones
    Card(
        onClick = onClick, // Que ocurrirá cuando se haga click
        modifier = modifier.padding(
            end = dimensionResource(R.dimen.category_card_end_padding),
            top = dimensionResource(R.dimen.category_card_top_padding)
        ),
        shape = RoundedCornerShape( // Forma de las cards
            topEnd = dimensionResource(R.dimen.top_bottom_end),
            bottomEnd = dimensionResource(R.dimen.top_bottom_end)
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary // Color para la card
        ),
        border = BorderStroke( // Borde para la card
            width = dimensionResource(R.dimen.card_border_width),
            color = Color.Black
        )

    ) {
        // Crea la información que se encuentra dentro de la card
        SelectionCardInformation(hasBorder = hasBorder, categoria = categoria)

    }
}


/**
 * Crea la imagen y el texto de cada card
 */
@Composable
fun SelectionCardInformation(
    modifier: Modifier = Modifier,
    hasBorder: Boolean,
    categoria: Categoria
) {
    Row( // Todos los elementos están en fila
        modifier = modifier
            .fillMaxWidth(), // Ocupa el maximo espacio disponible
        verticalAlignment = Alignment.CenterVertically // Todos sus elementos estan centrados verticalmente
    ) {
        Card( // Card para contener a la imagen
            modifier = Modifier
                .height(dimensionResource(R.dimen.image_card_height))// Todas tienen un tamaño fijo

                .padding(
                    top = dimensionResource(R.dimen.image_card_padding),
                    bottom = dimensionResource(R.dimen.image_card_padding),
                    start = dimensionResource(R.dimen.image_card_padding),
                    end = dimensionResource(R.dimen.image_card_end_padding)
                )
                .weight(0.8f),
            shape = RoundedCornerShape( // El borde no está redondeado
                0.dp
            ),

            // En caso de que se indique que tiene que tener borde se pondrá uno
            border = if (hasBorder) BorderStroke(
                width = dimensionResource(R.dimen.image_card_border),
                color = Color.Black
            ) else null

        ) {
            Image(
                // Imagen que representa cada catetegoría o recomendación
                painter = painterResource(categoria.imagen),
                contentDescription = stringResource(categoria.titulo),
                contentScale = ContentScale.Crop, // Ocupa todo el espacio
                modifier = Modifier,
            )

        }


        Text( // Texto con el nombre de cada categoría y/o recomendación
            text = stringResource(categoria.titulo),
            color = MaterialTheme.colorScheme.tertiary,
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .weight(1.2f),
            softWrap = true,
            maxLines = Int.MAX_VALUE,
            overflow = TextOverflow.Clip
        )
    }
}


@Composable
fun SelectionCardList(
    modifier: Modifier = Modifier,
    backgroundImage: Int,
    selectionList: List<Categoria>
) {

    Box(modifier = modifier.fillMaxSize()) {
        Image(
            painter = painterResource(backgroundImage),
            contentDescription = null, //Quitamos descripcíon de audio.
            contentScale = ContentScale.Crop, //Para que ocupe toda la pantalla
            modifier = Modifier.fillMaxSize()
        )

        LazyColumn(modifier = Modifier) {

            items(items = selectionList) { item ->

                SelectionCard(
                    onClick = {},
                    categoria = item,
                    hasBorder = true
                )

            }

        }
    }


}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SelectionCardPreview() {
    MyTokioTheme {
        SelectionCard(
            onClick = {},
            modifier = Modifier.fillMaxWidth(),
            categoria = zonasTematicas[2],
            hasBorder = true
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SelectionListPreview() {
    MyTokioTheme {
        SelectionCardList(
            selectionList = monumentos,
            backgroundImage = R.drawable.fondoclaro

        )
    }
}

