package com.caiquesenna.cadastrocliente.data

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import com.caiquesenna.cadastrocliente.model.Cliente

@Database(entities = [Cliente::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun clienteDao(): ClienteDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // Se a INSTANCE não for nula, retorna ela. Se for, cria o banco.
            return INSTANCE ?: synchronized(this) {
                val instance = androidx.room.Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "cadastro_cliente_database" // Nome do arquivo do banco
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}