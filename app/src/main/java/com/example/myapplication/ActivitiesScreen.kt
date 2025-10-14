package com.example.myapplication


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun TwitchActivitiesScreen(navController: NavController) {
    var abaSelecionada by rememberSaveable { mutableStateOf("notificacoes") }

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Atividade",
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 24.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.clickable { abaSelecionada = "notificacoes" }
            ) {
                Text(
                    text = "Notificações (12)",
                    color = if (abaSelecionada == "notificacoes") Color.White else Color.Gray,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .height(3.dp)
                        .width(120.dp)
                        .background(if (abaSelecionada == "notificacoes") Color(0xFF9147FF) else Color.Transparent)
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.clickable { abaSelecionada = "sussurros" }
            ) {
                Text(
                    text = "Sussurros (3)",
                    color = if (abaSelecionada == "sussurros") Color.White else Color.Gray,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .height(3.dp)
                        .width(100.dp)
                        .background(if (abaSelecionada == "sussurros") Color(0xFF9147FF) else Color.Transparent)
                )
            }
        }
        Spacer(modifier = Modifier.height(24.dp))

        when (abaSelecionada) {
            "notificacoes" -> {
                val notificacoes = listOf(
                    "É o dia do video game! Compartilhe um clipe para desbloquear o distintivo GGWP!",
                    "Sua inscrição no canal do Alanzoka foi renovada.",
                    "Gaules entrou ao vivo: 'CS2 - RUMO AO MAJOR!'"
                )
                LazyColumn(verticalArrangement = Arrangement.spacedBy(20.dp)) {
                    items(notificacoes) { texto ->
                        Row(verticalAlignment = Alignment.Top) {
                            Text(text = "🟣", fontSize = 24.sp, modifier = Modifier.padding(top = 4.dp))
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(text = texto, color = Color.White, fontSize = 16.sp)
                        }
                    }
                }
            }
            "sussurros" -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Nenhum sussurro novo.", color = Color.Gray, fontSize = 18.sp)
                }
            }
        }
    }
}
