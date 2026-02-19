package com.example.mytokio.buttons

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.net.toUri
import com.example.mytokio.R

// David López , Ruben Martín, Hugo de Pablo

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
        putExtra(Intent.EXTRA_TEXT, mensaje) //Le introducimos el mensaje
    }
}

/**
 * Funcion para compartir la ubicacion actual a otras aplicaciones
 * @StringRes urlID : url de la ubicacion a enviar
 */
@Composable
fun shareRecomendation(@StringRes urlId: Int): () -> Unit {
    //Necesario para ejecutar intents
    val context = LocalContext.current
    val url = stringResource(id = urlId)

    //Transformamos la url a un enlace de google valido
    val uri = "https://www.google.com/maps/search/?api=1&query=${Uri.encode(url)}"
    val compartir = stringResource(R.string.comparte_este_sitio)

    //Creamos el mensaje a enviar
    val mensaje = buildString {
        append(stringResource(R.string.recomendacion))
        append(stringResource(R.string.Separacion))
        append(uri) //enlace de google
    }
    //Obtenemos el intent con el mensaje
    val intent = intentShare(mensaje)
    return {
        //Se inicia el intent con createChooser para compartir
        context.startActivity(
            Intent.createChooser(
                intent,
                compartir
            )
        )
    }
}

/**
 * Funcion para abrir en maps o en navegador la ubicacion actual
 * @StringRes urlId: Url a visualizar
 */
@Composable
fun mapRecomendation(@StringRes urlId: Int): () -> Unit {
    //Necesario para ejecutar intents
    val context = LocalContext.current
    val url = stringResource(id = urlId)
    //url de maps necesaria para formatear
    val urlMaps = stringResource(id = R.string.enlace_maps)

    //formato para abrir google maps
    val uri = "geo:0,0?q=${Uri.encode(url)}".toUri()

    //formato para abrir en navegador
    val navegadorUri = "https://www.google.com/maps/search/?api=1&query=${Uri.encode(url)}".toUri()

    //Intent para abrir en el navegador en caso de fallo
    val navegadorIntent = Intent(Intent.ACTION_VIEW,navegadorUri)

    val mapsIntent = Intent(Intent.ACTION_VIEW,uri).apply {
      //Se especifica que se abra en google maps
      setPackage(urlMaps)
    }
    return {
        try {
            //Se intenta abrir en google maps
            context.startActivity(mapsIntent)
        } catch(e: ActivityNotFoundException) {
            //En caso de dar error, se ejecutara en el navegador por defecto
            //con el otro intent
            context.startActivity(navegadorIntent)
        }
    }
}