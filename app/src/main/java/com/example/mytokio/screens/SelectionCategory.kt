package com.example.mytokio.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mytokio.R
import com.example.mytokio.data.categorias
import com.example.mytokio.data.zonasTematicas
import com.example.mytokio.model.dataObjects.Categoria
import com.example.mytokio.ui.theme.MyTokioTheme

@Composable
fun SelectionCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    hasBorder: Boolean,
    categoria: Categoria
) {
    Card(
        onClick = onClick,
        modifier = modifier.padding(end = 40.dp, top = 1.dp),
        shape = RoundedCornerShape(
            topStart = 0.dp,
            topEnd = 50.dp,
            bottomStart = 0.dp,
            bottomEnd = 50.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        border = BorderStroke(
            width = 4.dp,
            color = Color.Black
        )

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                modifier = Modifier
                    .size(100.dp)
                    .padding(top = 10.dp, bottom = 10.dp, start = 10.dp, end = 5.dp)
                    .weight(0.8f),
                shape = RoundedCornerShape(
                    0.dp
                ),

                border = if (hasBorder) BorderStroke(width = 2.dp, color = Color.Black) else null

            ) {
                Image(
                    painter = painterResource(categoria.imagen),
                    contentDescription = stringResource(categoria.titulo),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier,
                )

            }


            Text(
                text = stringResource(categoria.titulo),
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

        LazyColumn(modifier = modifier.fillMaxSize()) {

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
            selectionList = zonasTematicas,
            backgroundImage = 0

        )
    }
}

