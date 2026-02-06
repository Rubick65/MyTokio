package com.example.mytokio.pruebasBoton

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.mytokio.R
import com.example.mytokio.ui.theme.MyTokioTheme
import androidx.core.net.toUri

@Composable
fun MapButton(
    @StringRes urlId: Int,
    )
{   //Necesario para ejecutar intents
    val context = LocalContext.current
    val url = stringResource(id = urlId)
    val urlMaps = stringResource(id = R.string.enlace_maps)

    //formato para abrir google maps
    val uri = "geo:0,0?q=${Uri.encode(url)}".toUri()

    IconButton(
        onClick = {
            //Intent para ejecutar el uri
            val maps = Intent(Intent.ACTION_VIEW,uri).apply {
                //Se especifica que se abra en google maps
                setPackage(urlMaps)
            }
            try {
                //Se intenta abrir en google maps
                context.startActivity(maps)
            }
            catch(e: ActivityNotFoundException) {
                //En caso de dar error, se ejecutara en el navegador por defecto
                val navegadorUri = "https://www.google.com/maps/search/?api=1&query=${Uri.encode(url)}".toUri()
                val navegadorIntent = Intent(Intent.ACTION_VIEW,navegadorUri)
                context.startActivity(navegadorIntent)
            }
        },
        )
    {
        Icon(
            painter = painterResource(R.drawable.iconomaps),
            contentDescription = stringResource(R.string.abrir_maps),
            Modifier.size(dimensionResource(R.dimen.icon_size))
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMapButton() {
    MyTokioTheme {   // si usas MaterialTheme o tu tema propio
        MapButton(urlId = R.string.url_palacio)
    }
}

