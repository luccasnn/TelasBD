package com.example.myapplication

import android.content.Context
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

enum class ActiveCrud {
    PESQUISA, CATEGORIA
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController) {
    val context = LocalContext.current
    var pesquisaRecenteDAO by remember { mutableStateOf<PesquisaRecenteDAO?>(null) }
    var categoriaDAO by remember { mutableStateOf<CategoriaDAO?>(null) }

    LaunchedEffect(context) {
        withContext(Dispatchers.IO) {
            val db = AppDatabase.getDatabase(context)
            pesquisaRecenteDAO = db.pesquisaRecenteDAO()
            categoriaDAO = db.categoriaDAO()
        }
    }

    var activeCrud by remember { mutableStateOf<ActiveCrud?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF18181B))
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Profile Picture",
                modifier = Modifier.size(80.dp),
                tint = Color.White
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text("Luccas", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Text("Ver perfil", color = Color.Gray, fontSize = 16.sp)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        ProfileOption(text = "Minhas inscrições")
        ProfileOption(text = "Drops")
        ProfileOption(text = "Meus emotes")
        ProfileOption(text = "Configurações")

        Divider(color = Color.Gray.copy(alpha = 0.5f), modifier = Modifier.padding(vertical = 8.dp))

        // Botões para mostrar os CRUDs
        ProfileOption(text = "CRUD Pesquisas", onClick = { activeCrud = if (activeCrud == ActiveCrud.PESQUISA) null else ActiveCrud.PESQUISA })
        ProfileOption(text = "CRUD Categorias", onClick = { activeCrud = if (activeCrud == ActiveCrud.CATEGORIA) null else ActiveCrud.CATEGORIA })

        if (activeCrud != null) {
            Spacer(modifier = Modifier.height(24.dp))
            Divider(color = Color.Gray.copy(alpha = 0.5f))
            Spacer(modifier = Modifier.height(8.dp))
        }

        when (activeCrud) {
            ActiveCrud.PESQUISA -> pesquisaRecenteDAO?.let { CrudPesquisaRecente(dao = it) }
            ActiveCrud.CATEGORIA -> categoriaDAO?.let { CrudCategorias(dao = it) }
            null -> {}
        }
    }
}

@Composable
fun ProfileOption(text: String, onClick: (() -> Unit)? = null) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = onClick != null) { onClick?.invoke() }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text, color = Color.White, fontSize = 18.sp)
        Icon(Icons.Default.ArrowForward, contentDescription = null, tint = Color.Gray)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrudPesquisaRecente(dao: PesquisaRecenteDAO) {
    val scope = rememberCoroutineScope()
    var pesquisas by remember { mutableStateOf(emptyList<PesquisaRecente>()) }
    var novoTermo by remember { mutableStateOf("") }

    val textFieldColors = TextFieldDefaults.outlinedTextFieldColors(
        focusedBorderColor = Color(0xFF9147FF), textColor = Color.White, cursorColor = Color(0xFF9147FF),
        unfocusedBorderColor = Color.Gray, focusedLabelColor = Color.White, unfocusedLabelColor = Color.Gray
    )

    LaunchedEffect(dao) { scope.launch { pesquisas = dao.buscarTodas() } }
    fun reloadPesquisas() { scope.launch { pesquisas = dao.buscarTodas() } }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text("Gerenciar Pesquisas Recentes", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(value = novoTermo, onValueChange = { novoTermo = it }, label = { Text("Nova Pesquisa") }, modifier = Modifier.weight(1f), colors = textFieldColors)
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                if (novoTermo.isNotBlank()) {
                    scope.launch { dao.inserir(PesquisaRecente(termo = novoTermo)); novoTermo = ""; reloadPesquisas() }
                }
            }) { Text("Add") }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(pesquisas, key = { it.id }) { pesquisa ->
                var isEditing by remember { mutableStateOf(false) }
                var updatedTermo by remember { mutableStateOf(pesquisa.termo) }
                Column {
                    if (isEditing) {
                        Row(modifier = Modifier.padding(vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                            OutlinedTextField(value = updatedTermo, onValueChange = { updatedTermo = it }, modifier = Modifier.weight(1f), colors = textFieldColors)
                            IconButton(onClick = {
                                scope.launch { dao.atualizar(pesquisa.copy(termo = updatedTermo)); isEditing = false; reloadPesquisas() }
                            }) { Icon(Icons.Default.Done, contentDescription = "Salvar", tint = Color.Green) }
                        }
                    } else {
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp)) {
                            Text(pesquisa.termo, color = Color.White, modifier = Modifier.weight(1f))
                            IconButton(onClick = { isEditing = true }) { Icon(Icons.Default.Edit, contentDescription = "Editar", tint = Color.Gray) }
                            IconButton(onClick = { scope.launch { dao.deletar(pesquisa); reloadPesquisas() } }) { Icon(Icons.Default.Delete, contentDescription = "Deletar", tint = Color.Gray) }
                        }
                    }
                    Divider(color = Color.Gray.copy(alpha = 0.3f))
                }
            }
        }
    }
}


// --- CRUD de Categorias --- //

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrudCategorias(dao: CategoriaDAO) {
    val scope = rememberCoroutineScope()
    var categorias by remember { mutableStateOf(emptyList<CategoriaDb>()) }

    var showDialog by remember { mutableStateOf(false) }
    var categoriaParaEditar by remember { mutableStateOf<CategoriaDb?>(null) }

    fun reloadCategorias() { scope.launch { categorias = dao.buscarTodas() } }

    LaunchedEffect(dao) { reloadCategorias() }

    if (showDialog) {
        val categoriaDialog = Box {
            val categoriaDialog = CategoriaDialog(
                categoria = categoriaParaEditar,
                onDismiss = {
                    showDialog = false
                    categoriaParaEditar = null
                },
                onConfirm = { categoria: CategoriaDb ->
                    scope.launch {
                        if (categoria.id == 0) dao.inserir(categoria) else dao.atualizar(categoria)
                        reloadCategorias()
                        showDialog = false
                        categoriaParaEditar = null
                    }
                },
                onDelete = { categoria: CategoriaDb ->
                    scope.launch {
                        dao.deletar(categoria)
                        reloadCategorias()
                        showDialog = false
                        categoriaParaEditar = null
                    }
                }
            )
        }
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text("Gerenciar Categorias", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            FloatingActionButton(
                onClick = { categoriaParaEditar = null; showDialog = true },
                modifier = Modifier.size(40.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Adicionar Categoria")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(categorias, key = { it.id }) { categoria ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { categoriaParaEditar = categoria; showDialog = true }
                        .padding(vertical = 12.dp)
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(categoria.nome, color = Color.White)
                        Text("Tipo: ${categoria.tipo} - Espectadores: ${categoria.espectadores}", color = Color.Gray, fontSize = 12.sp)
                    }
                    Icon(Icons.Default.Edit, contentDescription = "Editar", tint = Color.Gray)
                }
                Divider(color = Color.Gray.copy(alpha = 0.3f))
            }
        }
    }
}

