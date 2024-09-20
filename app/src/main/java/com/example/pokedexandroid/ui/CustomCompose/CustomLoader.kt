package com.example.pokedexandroid.ui.CustomCompose

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.pokedexandroid.R

@Composable
fun CustomLoader() {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val angle by infiniteTransition.animateFloat(
        initialValue = 0F,
        targetValue = 360F,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing)
        ), label = ""
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF78909C)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier
                .height(65.dp)
                .width(65.dp)
                .graphicsLayer {
                    rotationZ = angle
                },
            painter = painterResource(id = R.drawable.ic_pokeball),
            contentDescription = "Pokeball Loader"
        )
    }
}