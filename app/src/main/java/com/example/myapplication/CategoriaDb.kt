package com.example.myapplication

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categorias")
data class CategoriaDb(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nome: String,
    val tipo: String,
    val espectadores: String
    // O R.drawable.imagem não é salvo no banco,
    // ele será associado na tela (SearchScreen)
)