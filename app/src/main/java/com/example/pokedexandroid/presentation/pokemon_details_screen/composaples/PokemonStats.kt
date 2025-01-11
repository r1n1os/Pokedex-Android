package com.example.pokedexandroid.presentation.pokemon_details_screen.composaples

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.pokedexandroid.domain.model.Stats
import com.example.pokedexandroid.utils.HelperMethods

@Composable
fun PokemonStat(stat: Stats, color: Color) {
    var progress by remember { mutableStateOf(0F) }
    val progressAnimDuration = 5000
    val progressAnimation by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = progressAnimDuration, easing = FastOutSlowInEasing),
        label = "",
    )
    Row(
        modifier = Modifier
            .padding(start = 21.dp, end = 21.dp)
    ) {
        Text(
            modifier = Modifier
                .width(50.dp),
            text = "${HelperMethods.fullPokemonStatNameToShorten(statName = stat.name)}: ",
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = color
            )
        )
        LinearProgressIndicator(
            progress = {
                progressAnimation
            },
            trackColor = Color.LightGray,
            color = color,
            modifier = Modifier
                .clip(
                    shape = RoundedCornerShape(45),
                )
                .fillMaxWidth()
                .height(15.dp)
                .align(Alignment.CenterVertically),
        )
        LaunchedEffect(LocalLifecycleOwner.current) {
            progress = stat.value.toFloat()/ 100
        }
    }
    Spacer(modifier = Modifier.height(15.dp))
}