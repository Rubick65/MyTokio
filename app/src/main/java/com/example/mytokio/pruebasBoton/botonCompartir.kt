package com.example.mytokio.pruebasBoton

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
import com.example.mytokio.R

/**
 * Funcion para preparar el intent de compartir
 * con el mensaje que le pongas
 */
@Composable
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
/**
 * Funcion para crear el boton de compartir ubicacion
 */
@Composable
fun ShareButton(
    @StringRes urlId: Int,
)
{
    //Se indica que se ejecutara una accion
    val context = LocalContext.current
    val url = stringResource(id = urlId)
    val uri = "https://www.google.com/maps/search/?api=1&query=${Uri.encode(url)}"
    val compartir = stringResource(R.string.comparte_este_sitio)

    //Creamos el mensaje a enviar
    val mensaje = buildString {
        append(stringResource(R.string.recomendacion))
        append(stringResource(R.string.Separacion))
        append(uri)
    }
    val intent = intentShare(mensaje)

    IconButton(
        onClick = {
            context.startActivity(
                Intent.createChooser(
                    intent,
                    compartir
                )
            )
        }
    )
    {
        Icon(
            painter = painterResource(R.drawable.iconocompartir),
            contentDescription = stringResource(R.string.compartir_recomendacion),
            modifier = Modifier.size(dimensionResource(R.dimen.icon_size))
        )
    }
}