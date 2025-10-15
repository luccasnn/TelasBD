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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import java.util.UUID

data class CrudItem(val id: String = UUID.randomUUID().toString(), val text: String)

@Composable
fun TwitchActivitiesScreen(navController: NavController) {
    var abaSelecionada by rememberSaveable { mutableStateOf("notificacoes") }

    val notificacoes = remember {
        mutableStateListOf(
            CrudItem(text = "É o dia do video game! Compartilhe um clipe para desbloquear o distintivo GGWP!"),
            CrudItem(text = "Sua inscrição no canal do Alanzoka foi renovada."),
            CrudItem(text = "Gaules entrou ao vivo: 'CS2 - RUMO AO MAJOR!'")
        )
    }

    val sussurros = remember {
        mutableStateListOf(
            CrudItem(text = "user1: E aí, tudo bem?"),
            CrudItem(text = "user2: Vamos jogar mais tarde?"),
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
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
                    text = "Notificações (${notificacoes.size})",
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
                    text = "Sussurros (${sussurros.size})",
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
                CrudList(
                    items = notificacoes,
                    icon = "🟣",
                    onAddItem = { text -> notificacoes.add(0, CrudItem(text = text)) },
                    onUpdateItem = { item, newText ->
                        val index = notificacoes.indexOf(item)
                        if (index != -1) {
                            notificacoes[index] = item.copy(text = newText)
                        }
                    },
                    onDeleteItem = { item -> notificacoes.remove(item) }
                )
            }
            "sussurros" -> {
                CrudList(
                    items = sussurros,
                    icon = "귓", // A different icon for whispers
                    onAddItem = { text -> sussurros.add(0, CrudItem(text = text)) },
                    onUpdateItem = { item, newText ->
                        val index = sussurros.indexOf(item)
                        if (index != -1) {
                            sussurros[index] = item.copy(text = newText)
                        }
                    },
                    onDeleteItem = { item -> sussurros.remove(item) }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrudList(
    items: List<CrudItem>,
    icon: String,
    onAddItem: (String) -> Unit,
    onUpdateItem: (CrudItem, String) -> Unit,
    onDeleteItem: (CrudItem) -> Unit
) {
    var newItemText by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = newItemText,
                onValueChange = { newItemText = it },
                label = { Text("Novo item") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                if (newItemText.isNotBlank()) {
                    onAddItem(newItemText)
                    newItemText = ""
                }
            }) {
                Text("Add")
            }
        }

        if (items.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Nenhum item.", color = Color.Gray, fontSize = 18.sp)
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                items(items = items, key = { it.id }) { item ->
                    var isEditing by remember { mutableStateOf(false) }
                    var updatedText by remember { mutableStateOf(item.text) }

                    if (isEditing) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            OutlinedTextField(
                                value = updatedText,
                                onValueChange = { updatedText = it },
                                modifier = Modifier.weight(1f)
                            )
                            IconButton(onClick = {
                                onUpdateItem(item, updatedText)
                                isEditing = false
                            }) {
                                Icon(imageVector = Icons.Default.Done, contentDescription = "Salvar", tint = Color.Green)
                            }
                        }
                    } else {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = icon, fontSize = 24.sp, modifier = Modifier.padding(top = 4.dp))
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(text = item.text, color = Color.White, fontSize = 16.sp, modifier = Modifier.weight(1f))
                            IconButton(onClick = { isEditing = true }) {
                                Icon(imageVector = Icons.Default.Edit, contentDescription = "Editar", tint = Color.Gray)
                            }
                            IconButton(onClick = { onDeleteItem(item) }) {
                                Icon(imageVector = Icons.Default.Delete, contentDescription = "Deletar", tint = Color.Gray)
                            }
                        }
                    }
                }
            }
        }
    }
}
