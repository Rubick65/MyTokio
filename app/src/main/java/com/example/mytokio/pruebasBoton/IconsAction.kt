package com.example.mytokio.pruebasBoton

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.net.toUri
import com.example.mytokio.R

/**
 * Funcion para crear el intent de compartir recomendacion
 */
fun intentShare(
    mensaje: String
): Intent
{
    //Preparamos intent de enviar
    return Intent(Intent.ACTION_SEND).apply {
        type = "text/plain" //Especificamos que va a ser texto
        putExtra(Intent.EXTRA_TEXT, mensaje)
    }
}

@Composable
fun shareRecomendation(@StringRes urlId: Int): () -> Unit {
    //Necesario para ejecutar intents
    val context = LocalContext.current
    val url = stringResource(id = urlId)
    //Transformamos la url a un enlace de google
    val uri = "https://www.google.com/maps/search/?api=1&query=${Uri.encode(url)}"
    val compartir = stringResource(R.string.comparte_este_sitio)

    //Creamos el mensaje a enviar
    val mensaje = buildString {
        append(stringResource(R.string.recomendacion))
        append(stringResource(R.string.Separacion))
        append(uri) //enlace de google
    }
    val intent = intentShare(mensaje)
    return {
        context.startActivity(
            Intent.createChooser(
                intent,
                compartir
            )
        )
    }
}

@Composable
fun mapRecomendation(@StringRes urlId: Int): () -> Unit {
    //Necesario para ejecutar intents
    val context = LocalContext.current
    val url = stringResource(id = urlId)
    val urlMaps = stringResource(id = R.string.enlace_maps)

    //formato para abrir google maps
    val uri = "geo:0,0?q=${Uri.encode(url)}".toUri()

    //formato para abrir en navegador
    val navegadorUri = "https://www.google.com/maps/search/?api=1&query=${Uri.encode(url)}".toUri()

    //Intent para abrir en el navegador en caso de fallo
    val navegadorIntent = Intent(Intent.ACTION_VIEW,navegadorUri)

    val maps = Intent(Intent.ACTION_VIEW,uri).apply {
      //Se especifica que se abra en google maps
      setPackage(urlMaps)
    }
    return {
        try {
            //Se intenta abrir en google maps
            context.startActivity(maps)
        } catch(e: ActivityNotFoundException) {
            //En caso de dar error, se ejecutara en el navegador por defecto
            context.startActivity(navegadorIntent)
        }
    }
}