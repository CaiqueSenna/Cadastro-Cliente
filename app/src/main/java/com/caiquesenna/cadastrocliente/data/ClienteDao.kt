package com.caiquesenna.cadastrocliente.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.caiquesenna.cadastrocliente.model.Cliente

@Dao
interface ClienteDao {
    @Insert
    suspend fun inserir(cliente: Cliente)

    @Query("SELECT * FROM Cliente")
    suspend fun listar(): List<Cliente>

    //Busca um cliente pelo CNPJ (ajuste o nome da coluna se for diferente no seu Model)
    @Query("SELECT * FROM Cliente WHERE cnpj = :cnpj LIMIT 1")
    suspend fun buscarPorCnpj(cnpj: String): Cliente?

    //Atualiza os dados do cliente baseado na Primary Key
    @Update
    suspend fun atualizar(cliente: Cliente)

    //Remove o cliente baseado na Primary Key
    @Delete
    suspend fun deletar(cliente: Cliente)
}