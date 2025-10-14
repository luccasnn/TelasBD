package com.example.myapplication

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun TwitchHomeScreen(navController: NavController) {
    var selectedTab by remember { mutableStateOf("Ao vivo") }

    val liveStreams = remember {
        listOf(
            LiveStream("Gaules", R.drawable.streamer1, R.drawable.cs2, "o Major esta chegando !!!", "CS2", "50,2 mil", listOf("Engraçado")),
            LiveStream("alanzoka", R.drawable.streamer4, R.drawable.valorant, "VALORANT com a galera", "Valorant", "23,5 mil", listOf("FPS", "Engraçado")),
            LiveStream("BaianoTV", R.drawable.streamer3, R.drawable.lol, "CBLOL - Cobertura completa", "League of Legends", "18,1 mil", listOf("eSports", "CBLOL"))
        )
    }

    val clips = remember {
        listOf(
            TwitchClip(R.drawable.telalive, "0:15", "22,3 mil", "há 6 dias", R.drawable.streamer2, "Maka hits nasty AK one-tap", "betboom_cs_a", "kingdempz"),
            TwitchClip(R.drawable.lol, "0:30", "15,8 mil", "há 6 horas", R.drawable.streamer3, "kensizor 1v5 ninja", "inkmate0", "luismmira"),
            TwitchClip(R.drawable.valorant, "0:17", "14,2 mil", "há 2 dias", R.drawable.streamer4, "kyousuke 2 HS", "betboom_cs_a", "luismmira")
        )
    }

    LazyColumn(
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        item {
            HeaderTabs(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it }
            )
            Spacer(modifier = Modifier.height(24.dp))
        }

        when (selectedTab) {
            "Ao vivo" -> {
                items(liveStreams) { stream ->
                    StreamPreviewItem(stream = stream)
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
            "Clipes" -> {
                items(clips) { clip ->
                    ClipPreviewItem(clip = clip)
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}