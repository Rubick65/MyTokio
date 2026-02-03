package com.example.mytokio.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.example.mytokio.data.categorias
import com.example.mytokio.data.zonasTematicas
import com.example.mytokio.model.dataObjects.Categoria
import com.example.mytokio.ui.theme.MyTokioTheme

@Composable
fun SelectionCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
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
            Image(
                painter = painterResource(categoria.imagen),
                contentDescription = stringResource(categoria.titulo),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .padding(10.dp)
                    .weight(0.8f),
            )

            Text(
                text = stringResource(categoria.titulo),
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Start,
                modifier = Modifier.weight(1.2f),
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
    selectionList: List<Categoria>
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {

        items(items = selectionList) { item ->

            SelectionCard(
                onClick = {},
                categoria = item
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
            categoria = zonasTematicas[2]
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SelectionListPreview() {
    MyTokioTheme {
        SelectionCardList(
            selectionList = categorias

        )
    }
}

