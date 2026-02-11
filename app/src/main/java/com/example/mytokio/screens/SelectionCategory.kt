package com.example.mytokio.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
    onClick: (Int) -> Unit,
    hasBorder: Boolean,
    categoria: Categoria
) {
    // Carta principal para todas las categorías y recomendaciones
    Card(
        onClick = { onClick(categoria.id) }, // Que ocurrirá cuando se haga click
        modifier = modifier.padding(
            end = dimensionResource(R.dimen.category_card_end_padding),
            top = dimensionResource(R.dimen.category_card_top_padding)
        ),
        shape = RoundedCornerShape( // Forma de las cards
            0.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary // Color para la card
        ),
        border = BorderStroke( // Borde para la card
            width = dimensionResource(R.dimen.card_border_width),
            color = MaterialTheme.colorScheme.tertiary
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
            .padding(dimensionResource(R.dimen.image_card_padding))
            .fillMaxWidth(), // Ocupa el maximo espacio disponible
        verticalAlignment = Alignment.CenterVertically // Todos sus elementos estan centrados verticalmente
    ) {
        Card( // Card para contener a la imagen
            modifier = Modifier
                .aspectRatio(if (hasBorder) 3f / 2f else 1.5f / 1.2f)
                .padding(
                    end = dimensionResource(
                        if (hasBorder) R.dimen.image_card_end_padding
                        else R.dimen.image_card_end_padding_category
                    )
                )
                .weight(if (hasBorder) 0.7f else 0.5f),
            shape = RoundedCornerShape( // El borde no está redondeado
                0.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent
            ),

            // En caso de que se indique que tiene que tener borde se pondrá uno
            border = if (hasBorder) BorderStroke(
                width = dimensionResource(R.dimen.image_card_border),
                color = MaterialTheme.colorScheme.tertiary
            ) else null

        ) {
            Image(
                // Imagen que representa cada catetegoría o recomendación
                painter = painterResource(categoria.imagen),
                contentDescription = stringResource(categoria.titulo),
                contentScale = if (hasBorder) ContentScale.Crop else ContentScale.Fit,
                modifier = Modifier.fillMaxSize(),
            )
        }

        Text( // Texto con el nombre de cada categoría y/o recomendación
            text = stringResource(categoria.titulo),
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .weight(1f),
            softWrap = true,
            maxLines = Int.MAX_VALUE,
            overflow = TextOverflow.Clip
        )
    }
}

/**
 * Lista con las diferentes cards de las categorías y recomendaciones
 */
@Composable
fun SelectionCardList(
    modifier: Modifier = Modifier,
    hasBorder: Boolean = false,
    onClick: (Int) -> Unit,
    selectionList: List<Categoria>
) {
    // Columna que permite deslizamiento
    LazyColumn(modifier = modifier.fillMaxSize()) {
        // Se recorre la lista de elementos
        items(items = selectionList) { item ->
            // Y se va añadiendo cada card a la columna
            SelectionCard(
                onClick = onClick,
                categoria = item,
                hasBorder = hasBorder
            )

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
            hasBorder = false
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SelectionListPreview() {
    MyTokioTheme {
        SelectionCardList(
            selectionList = monumentos,
            onClick = {}

        )
    }
}

