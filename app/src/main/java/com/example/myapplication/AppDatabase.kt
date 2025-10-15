package com.example.myapplication

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Adicione as novas classes de entidade aqui
@Database(entities = [PesquisaRecente::class, CategoriaDb::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    // Adicione os novos DAOs aqui
    abstract fun pesquisaRecenteDAO(): PesquisaRecenteDAO
    abstract fun categoriaDAO(): CategoriaDAO

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "twitch_app_database" // Nome do banco
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}