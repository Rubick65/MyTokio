package com.example.mytokio.ui.theme

import android.os.Build
import androidx.annotation.DrawableRes
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext
import com.example.mytokio.R

private val DarkColorScheme = darkColorScheme(
    primary = CARDS_OSCURO, //Color de fondo de cards
    secondary = LETRAS_OSCURO, //Color de letras
    tertiary = BORDES_OSCURO //Borde de cards
)

private val LightColorScheme = lightColorScheme(
    primary = CARDS_CLARO, //Color de fondo de cards
    secondary = LETRAS_CLARO, //Color de letras
    tertiary = BORDES_CLARO //Borde de cards

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

data class MyTokioImages(
    @DrawableRes val background: Int
)

// Creamos un CompositionLocal para poder acceder desde cualquier composable
val LocalImages = staticCompositionLocalOf<MyTokioImages> {
    error("No Images provided")
}

@Composable
fun MyTokioTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val images = MyTokioImages(
        background = if (darkTheme)
            R.drawable.fondooscuro
        else
            R.drawable.fondoclaro
    )

    CompositionLocalProvider(LocalImages provides images) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
    
}