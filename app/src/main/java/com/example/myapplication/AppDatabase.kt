package com.example.myapplication

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// A versão foi incrementada para 2 para refletir a mudança no esquema (adição de CategoriaDb)
@Database(entities = [PesquisaRecente::class, CategoriaDb::class], version = 2)
abstract class AppDatabase : RoomDatabase() {

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
                )
                // Permite que o Room recrie o banco de dados se não houver uma migração.
                // Essencial para evitar crashes quando o esquema muda.
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}