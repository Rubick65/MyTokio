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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mytokio.R
import com.example.mytokio.ui.theme.MyTokioTheme

@Composable
fun ButtonMap(
    @StringRes urlId: Int,
    )
{
    val context = LocalContext.current //Necesario para ejecutar intents
    val url = stringResource(id = urlId)
    //formato para abrir google maps
    val uri = Uri.parse("geo:0,0?q=${Uri.encode(url)}")

    IconButton(
        onClick = {
            //Intent para ejecutar el uri
            val maps = Intent(Intent.ACTION_VIEW,uri).apply {
                //Se especifica que se abra en google maps
                setPackage(context.getString(R.string.enlace_maps))
            }
            try {
                //Se intenta abrir en google maps
                context.startActivity(maps)
            }
            catch(e: ActivityNotFoundException) {
                //En caso de dar error, se ejecutara en el navegador por defecto
                val navegadorUri = Uri.parse("https://www.google.com/maps/search/?api=1&query=${Uri.encode(url)}")
                val navegadorIntent = Intent(Intent.ACTION_VIEW,navegadorUri)
                context.startActivity(navegadorIntent)
            }
        },
        )
    {
        Icon(
            painter = painterResource(R.drawable.iconomaps),
            contentDescription = null,
            modifier = Modifier.size(30.dp)
        )
    }
}



@Preview(showBackground = true)
@Composable
fun PreviewButtonMap() {
    MyTokioTheme {   // si usas MaterialTheme o tu tema propio
        ButtonMap(urlId = R.string.url_palacio)
    }
}

