package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

import androidx.compose.material3.Shapes
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp

val Shapes = Shapes(
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(24.dp),
    large = RoundedCornerShape(32.dp)
)

private val DarkColorScheme =
  darkColorScheme(
    primary = VarnikaAccent,
    onPrimary = VarnikaPrimary,
    secondary = VarnikaSecondaryAccent,
    onSecondary = VarnikaPrimary,
    tertiary = VarnikaAccent,
    background = VarnikaPrimary,
    surface = VarnikaSecondary,
    onBackground = VarnikaText,
    onSurface = VarnikaText,
    surfaceVariant = VarnikaSecondary,
    onSurfaceVariant = VarnikaText,
    error = VarnikaError,
    onError = VarnikaText
  )

private val LightColorScheme = DarkColorScheme // Force dark elegant theme for luxury feel

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = true, // Always dark for luxury feel
  dynamicColor: Boolean = false, // Disable dynamic colors to keep brand colors intact
  content: @Composable () -> Unit,
) {
  val colorScheme = DarkColorScheme

  MaterialTheme(colorScheme = colorScheme, typography = Typography, shapes = Shapes, content = content)
}
