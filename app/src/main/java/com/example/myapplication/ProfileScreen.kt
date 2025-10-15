package com.example.myapplication.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.R

@Composable
fun TwitchProfileScreen(navController: NavController) {
    val context = LocalContext.current

    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(Color(0xFF9147FF))
        ) {
            TextButton(
                onClick = { Toast.makeText(context, "Função ainda não implementada!", Toast.LENGTH_SHORT).show() },
                modifier = Modifier.align(Alignment.TopEnd).padding(8.dp)
            ) {
                Text("Editar Perfil", color = Color.White, fontSize = 16.sp)
            }
        }
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.offset(y = (-24).dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.fotoperfil),
                        contentDescription = "Foto do Perfil",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(96.dp)
                            .clip(CircleShape)
                            .border(width = 3.dp, color = Color.Black, shape = CircleShape)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("12.3k seguidores", color = Color.White, fontSize = 14.sp)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text("Meu Perfil", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Última vez ao vivo: 2h atrás", color = Color.Gray, fontSize = 14.sp)
                }
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        Divider(color = Color.Gray.copy(alpha = 0.3f), modifier = Modifier.padding(horizontal = 16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            val opcoes = listOf(
                "Meu canal" to "👤", "Painel do criador" to "📊", "Inscrições" to "⭐",
                "Drops" to "🎁", "Twitch Turbo" to "⚡", "Configurações da conta" to "⚙️"
            )
            items(opcoes) { (texto, icone) ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { /* Ação para cada item */ }
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                ) {
                    Text(icone, fontSize = 24.sp)
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(texto, fontSize = 18.sp, color = Color.White)
                    Spacer(modifier = Modifier.weight(1f))
                    Text(">", fontSize = 20.sp, color = Color.Gray)
                }
                Divider(color = Color.Gray.copy(alpha = 0.3f), modifier = Modifier.padding(horizontal = 16.dp))
            }
        }
    }
}
