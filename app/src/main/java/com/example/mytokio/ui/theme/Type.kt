package com.example.mytokio.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.Font
import com.example.mytokio.R

// Set of Material typography styles to start with

val Bungee =FontFamily(
    Font(R.font.bungee_regular)
)

val BungeeSpice = FontFamily(
    Font(R.font.bungeespice_regular)
)

val Jaro = FontFamily(
    Font(R.font.jaro_regular)
)

val Rye = FontFamily(
    Font(R.font.rye_regular)
)

val SansitasWashed = FontFamily(
    Font(R.font.sansitaswashed_bold),
    Font(R.font.sansitaswashed_regular)
)

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)