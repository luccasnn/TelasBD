package com.example.myapplication

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface CategoriaDAO {

    @Insert
    suspend fun inserir(categoria: CategoriaDb)

    @Update
    suspend fun atualizar(categoria: CategoriaDb)

    @Delete
    suspend fun deletar(categoria: CategoriaDb)

    @Query("SELECT * FROM categorias ORDER BY nome ASC")
    suspend fun buscarTodas(): List<CategoriaDb>
}