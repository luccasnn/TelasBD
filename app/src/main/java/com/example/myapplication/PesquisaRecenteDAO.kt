package com.example.myapplication

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PesquisaRecenteDAO {

    @Insert
    suspend fun inserir(pesquisa: PesquisaRecente)

    @Query("SELECT * FROM pesquisas_recentes ORDER BY id DESC")
    suspend fun buscarTodas(): List<PesquisaRecente>

    @Delete
    suspend fun deletar(pesquisa: PesquisaRecente)
}